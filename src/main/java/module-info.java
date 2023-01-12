module com.calgrimes.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires eu.hansolo.tilesfx;

    requires org.jooq;
    requires org.jooq.codegen;
    requires org.jooq.meta;
    requires com.google.gson;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires json.simple;
    requires org.postgresql.jdbc;
    requires java.naming;
    requires org.jetbrains.annotations;


    opens com.calgrimes.librarymanagementsystem.Postgres to com.google.gson;
    opens com.calgrimes.librarymanagementsystem.DBObjects to com.google.gson;
    opens com.calgrimes.librarymanagementsystem to javafx.fxml;
    exports com.calgrimes.librarymanagementsystem;
}