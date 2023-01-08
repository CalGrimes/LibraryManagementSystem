package com.calgrimes.librarymanagementsystem;

import com.calgrimes.librarymanagementsystem.Postgres.PostgresCode;
import com.calgrimes.librarymanagementsystem.Postgres.PostgresCredentials;
import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;
import com.calgrimes.librarymanagementsystem.Helpers.DialogHelper;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.calgrimes.librarymanagementsystem.Main.dialect;

public class System {


    public System() {

    }

    public void createSchema(ActionEvent event) throws IOException {
        // Make a connection
        PostgresCredentials credentials = new PostgresCredentials("src/main/resources/psql.json");
        try
        {
            // Initialise PostgreSQL driver.
            Class.forName("org.postgresql.Driver");

            try
                    (
                            Connection conn = DriverManager.getConnection
                                    (
                                            String.format
                                                    (
                                                            "jdbc:postgresql://%s/%s?currentSchema=%s",

                                                            credentials.getIp(),
                                                            credentials.getDatabase(),
                                                            credentials.getSchema()
                                                    ),

                                            credentials.getUsername(),
                                            credentials.getPassword()
                                    )
                    )
            {

                String schema = credentials.getSchema();
                DSLContext dslContext = DSL.using(conn, dialect);

                Logger.logf("Dropping %s schema", LogLevel.UTILITY, schema);
                dslContext.dropSchema(schema).execute();

                Logger.logf("Creating %s schema", LogLevel.UTILITY, schema);
                dslContext.createSchema(schema).execute();


            } catch (SQLException e) {
                Logger.logf("Failed to retrieve connection, Error: %s", LogLevel.ERROR, PostgresCode.of(e.getSQLState()));
                e.printStackTrace();
                java.lang.System.exit(ExitStatus.CRITICAL.get());
            }
        }
        catch (ClassNotFoundException exception)
        {
            Logger.log("Failed to register PostgreSQL driver...", LogLevel.ERROR);
            exception.printStackTrace();
            java.lang.System.exit(ExitStatus.CRITICAL.get());
        }
        finally {
            DialogHelper.showAlertCompletion("Create / Reset Schema Execution Complete.");
        }
    }
}
