package com.calgrimes.librarymanagementsystem.Utilities;

import com.calgrimes.librarymanagementsystem.Main;

public class LogPrinter
{
    public void log(String message, LogLevel level, boolean displayCaller, int stackTraceIndex)
    {
        switch (level)
        {
            case UTILITY:
                ConsoleAttribute.set(ConsoleAttribute.GREEN);
                break;

            case WARNING:
                ConsoleAttribute.set(ConsoleAttribute.YELLOW);
                break;

            case ERROR:
                ConsoleAttribute.set(ConsoleAttribute.RED);
                break;
                
            case DEBUG:
            {
                // Don't display debug logs unless in debug mode.
                if (!Main.DEBUG)
                    return;

                // Prepend [DEBUG] to the message.
                message = ConsoleAttribute.GREEN + "[DEBUG] " + ConsoleAttribute.DEFAULT + message;

                break;
            }

            default:
                break;
        }

        System.out.println(displayCaller ? getCaller(stackTraceIndex) + message : message);

        ConsoleAttribute.reset();
    }

    private String getCaller(int index)
    {
        return "[" + Thread.currentThread().getStackTrace()[index].getMethodName() + "] ";
    }
}
