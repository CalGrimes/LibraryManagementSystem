package com.calgrimes.librarymanagementsystem.Helpers;

import com.calgrimes.librarymanagementsystem.ExitStatus;
import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;

public class ExceptionHelper {

    public static void throwIOException(String path, Exception exception) {
        Logger.logf("An I/O exception occurred whilst accessing the path: %s%n", path, LogLevel.ERROR);
        exception.printStackTrace();

        System.exit(ExitStatus.CRITICAL.get());
    }
}