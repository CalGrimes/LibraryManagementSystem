package com.calgrimes.librarymanagementsystem;

import com.calgrimes.librarymanagementsystem.DBObjects.Book;
import com.calgrimes.librarymanagementsystem.Helpers.StringHelper;
import com.calgrimes.librarymanagementsystem.Postgres.PostgresCode;
import com.calgrimes.librarymanagementsystem.Postgres.PostgresCredentials;
import com.calgrimes.librarymanagementsystem.Postgres.TableName;
import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;
import com.calgrimes.librarymanagementsystem.Helpers.DialogHelper;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import static com.calgrimes.librarymanagementsystem.Main.dialect;

public class System {

    @FXML
    private TableView tblvBooks;

    private PostgresCredentials credentials = new PostgresCredentials("bin/psql.json");

    public System() {

    }

    private int retrieveMaxId()
    {
        DataSource ds = credentials.getDatasource();
        try {
            Connection conn = ds.getConnection();
            DSLContext dslContext = DSL.using(conn, dialect);
            String schema = credentials.getSchema();

            // Get Max Id from book table.
            Object res = dslContext
                    .select(
                            max(
                                    field(StringHelper.toString(TableName.BOOK) + "_id")))

                    .from(
                            table(schema + StringHelper.referenceTableName(TableName.BOOK))).fetch().getValue(0, "max");

            if (!(res == null)) {
                return (int) res;
            }
            else {
                return 0;
            }

        } catch (SQLException e) {
            Logger.logf("Failed PostgreSQL Execution. Error: %s", LogLevel.ERROR, PostgresCode.of(e.getSQLState()));
            java.lang.System.exit(ExitStatus.ERROR.get());
        }
        return 0;
    }

    private void insertBookRow(Book book) {
        DataSource ds = credentials.getDatasource();
        try {
            Connection conn = ds.getConnection();
            DSLContext dslContext = DSL.using(conn, dialect);
            String schema = credentials.getSchema();

            Query insertBook = dslContext.insertInto(
                    table(schema + StringHelper.referenceTableName(TableName.BOOK)),
                    field("book_id"),
                    field("title"),
                    field("author_firstname"),
                    field("author_lastname"),
                    field("genre"),
                    field("price"))
                    .values(
                            book.getBookId(),
                            book.getBookTitle(),
                            book.getAuthorFirst(),
                            book.getAuthorLast(),
                            book.getBookGenre(),
                            book.getBookPrice());

            insertBook.execute();
        } catch (SQLException e) {
            Logger.logf("Failed PostgreSQL Execution. Error: %s", LogLevel.ERROR, PostgresCode.of(e.getSQLState()));
        }


    }

    private Result<Record> selectAllBooks(DSLContext dslContext, String schema) {
        Logger.log("Fetching Books...", LogLevel.DEBUG);

        // Execute Select Statement
        Result<Record> res = dslContext.select().from(schema + StringHelper.referenceTableName(TableName.BOOK)).fetch();
        return res;


    }

    private void displayBooks(Result<Record> books) {
        // Store result as book object
        for (int i = 0; i < books.size(); i++) {
            Record record = books.get(i);
//            return new Book(record.get);
        }


        // Set to tableView
//        tblvBooks.getColumns().add(books);
//        ObservableList check = tblvBooks.getColumns();
        // TODO: Show row
        this.tblvBooks.getItems().add(books);

    }

    public void viewBooks(){
        DataSource ds = credentials.getDatasource();
        try {
            Connection conn = ds.getConnection();
            String schema = credentials.getSchema();

            DSLContext dslContext = DSL.using(conn, dialect);

            try {
                Result<Record> books = selectAllBooks(dslContext, schema);
                displayBooks(books);
            }
            catch (RuntimeException e){
                Logger.logf("%s schema does not exist.", LogLevel.WARNING, schema);
            }

            Logger.log("Books Displayed.", LogLevel.DEBUG);

        } catch (SQLException e) {
            Logger.logf("Failed PostgreSQL Execution. Error: %s", LogLevel.ERROR, PostgresCode.of(e.getSQLState()));
        }
    }

    public void addBook() {
        // Get the highest ID number from the table and add 1
        int bookId = retrieveMaxId() + 1;
        // Show AddBook Dialog
        Optional<Book> result = DialogHelper.showAddBook(bookId);

        result.ifPresent(book -> {
            Logger.logf("New Book Entered: %s", LogLevel.DEBUG, new Gson().toJson(book).toString());

            insertBookRow(book);
            // Feedback to user
            DialogHelper.showAlertCompletion("New Book has been added to the database.");
            Logger.log("New Book has been added to the database.", LogLevel.DEBUG);
        });
    }


    public static void dropSchema(DSLContext dslContext, String schema)
    {
        Logger.logf("Dropping %s schema.", LogLevel.DEBUG, schema);
        dslContext.dropSchema(schema).cascade().execute();
        Logger.logf("Schema dropped: %s.", LogLevel.DEBUG, schema);
    }

    public static void createSchema(DSLContext dslContext, String schema){
        Logger.logf("Creating %s schema", LogLevel.DEBUG, schema);

        dslContext.createSchema(schema).execute();
        dslContext.createTable("BOOK")
                .column("book_id", INTEGER)
                .column("title", VARCHAR(35).notNull())
                .column("author_firstname", VARCHAR(35).notNull())
                .column("author_lastname", VARCHAR(35).notNull())
                .column("genre", VARCHAR(10).notNull())
                .column("price", DECIMAL(10, 2).notNull())
                .constraints(
                        constraint("book_pk").primaryKey("book_id")
                ).execute();

        dslContext.createView("viewBooks")
                        .as(
                                dslContext.select().from(
                                        schema + StringHelper.referenceTableName(TableName.BOOK)
                                )
                        )
                .execute();

        Logger.logf("%s schema created.", LogLevel.DEBUG, schema);
    }

    public void resetSchema(ActionEvent event) {


        DataSource ds = credentials.getDatasource();
        try {
            Connection conn = ds.getConnection();
            String schema = credentials.getSchema();

            DSLContext dslContext = DSL.using(conn, dialect);

            try {
                dropSchema(dslContext, schema);
            }
            catch (RuntimeException e){
                Logger.logf("%s schema does not exist.", LogLevel.WARNING, schema);
            }
            finally {
                createSchema(dslContext, schema);
            }

            DialogHelper.showAlertCompletion("Create / Reset Schema Execution Complete.");
            Logger.log("Create / Reset Schema Execution Complete.", LogLevel.DEBUG);

        } catch (SQLException e) {
            Logger.logf("Failed PostgreSQL Execution. Error: %s", LogLevel.ERROR, PostgresCode.of(e.getSQLState()));
        }
    }





}
