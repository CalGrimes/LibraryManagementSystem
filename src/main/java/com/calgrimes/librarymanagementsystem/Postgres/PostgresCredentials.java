package com.calgrimes.librarymanagementsystem.Postgres;

import com.calgrimes.librarymanagementsystem.ExitStatus;
import com.calgrimes.librarymanagementsystem.Helpers.JsonHelper;
import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
public class PostgresCredentials {
    String ip;
    String database;
    String schema;
    String username;
    String password;

    public PostgresCredentials() { }

    public PostgresCredentials(String path)
    {
        var pc = load(path);

        ip       = pc.getIp();
        database = pc.getDatabase();
        schema   = pc.getSchema();
        username = pc.getUsername();
        password = pc.getPassword();
    }

    public PostgresCredentials load(String path)
    {
        if (Files.notExists(Path.of(path)))
        {
            Logger.logf("Unable to load Postgres credentials: %s%n", path, LogLevel.ERROR);
            System.exit(ExitStatus.ERROR.get());
        }
        return JsonHelper.deserialise(path, PostgresCredentials.class);
    }

    public String getIp()
    {
        return ip;
    }

    public String getDatabase()
    {
        return database;
    }

    public String getSchema()
    {
        return schema;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
