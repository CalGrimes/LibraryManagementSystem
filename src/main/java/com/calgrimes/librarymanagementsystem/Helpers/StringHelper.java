package com.calgrimes.librarymanagementsystem.Helpers;

import com.calgrimes.librarymanagementsystem.Postgres.TableName;

public class StringHelper {

    public static String referenceTableName(TableName tableName)
    {
        return String.format(".\"%s\"", tableName.toString());
    }

    public static String toString(TableName tableName)
    {
        return tableName.toString().toLowerCase();
    }

}
