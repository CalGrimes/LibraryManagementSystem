package com.calgrimes.librarymanagementsystem.Utilities;

import java.util.Arrays;
import java.util.List;

public class Logger
{
    private static List<LogPrinter> handlers = Arrays.asList(new LogPrinter());

    public static void add(LogPrinter logger)
    {
        handlers.add(logger);
    }
    
    public static void remove(LogPrinter logger)
    {
        handlers.remove(logger);
    }

    public static void log(String message, LogLevel level, boolean displayCaller, int stackTraceIndex)
    {
        for (var logger : handlers)
            logger.log(message, level, displayCaller, stackTraceIndex);
    }

    public static void log(String message, LogLevel level, boolean displayCaller)
    {
        /* Return the fifth entry in the stack trace by default
           since four calls have been made since the initial log. */
        log(message, level, displayCaller, 5);
    }

    public static void log(String message, LogLevel level)
    {
        log(message, level, false, 5);
    }

    public static void log(String message)
    {
        log(message, LogLevel.NONE, false, 5);
    }

    public static void logf(String format, LogLevel level, boolean displayCaller, int stackTraceIndex, Object ... contents)
    {
        log(String.format(format, contents), level, displayCaller, stackTraceIndex);
    }

    public static void logf(String format, LogLevel level, boolean displayCaller, Object ... contents)
    {
        log(String.format(format, contents), level, displayCaller);
    }

    public static void logf(String format, LogLevel level, Object ... contents)
    {
        log(String.format(format, contents), level);
    }

    public static void logf(String format, Object ... contents)
    {
        log(String.format(format, contents));
    }
}
