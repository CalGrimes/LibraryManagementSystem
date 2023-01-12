package com.calgrimes.librarymanagementsystem;

import com.calgrimes.librarymanagementsystem.DBObjects.Book;
import com.calgrimes.librarymanagementsystem.Postgres.PostgresCode;
import com.calgrimes.librarymanagementsystem.Postgres.PostgresCredentials;
import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;
import com.calgrimes.librarymanagementsystem.Helpers.DialogHelper;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import java.io.IOException;
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

//    private int retreiveNewID

    public void addBook() {
        // Get the highest ID number from the table and addd 1
//        retreiveNewID

        // Show AddBook Dialog
        Optional<Book> result = DialogHelper.showAddBook();

        result.ifPresent(book -> {
            Logger.logf("New Book Entered: %s", LogLevel.DEBUG, new Gson().toJson(book).toString());

            // TODO: Perform action on book, like saving to a database or displaying on a table

            // Feedback to user
            DialogHelper.showAlertCompletion("New Book has been added to the database.");
        });
    }


    public void dropSchema(DSLContext dslContext, String schema)
    {
        Logger.logf("Dropping %s schema.", LogLevel.DEBUG, schema);
        dslContext.dropSchema(schema).cascade().execute();
        Logger.logf("Schema dropped: %s.", LogLevel.DEBUG, schema);
    }

    public void createSchema(DSLContext dslContext, String schema){
        Logger.logf("Creating %s schema", LogLevel.DEBUG, schema);

        dslContext.createSchema(schema).execute();
        dslContext.createTable("books")
                .column("book_id", INTEGER)
                .column("book_name", VARCHAR(35))
                .column("book_genre", VARCHAR(10))
                .column("book_price", NUMERIC(2))
                .constraints(
                        constraint("book_pk").primaryKey("book_id")
                ).execute();

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
