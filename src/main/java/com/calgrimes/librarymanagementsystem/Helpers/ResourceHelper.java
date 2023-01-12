package com.calgrimes.librarymanagementsystem.Helpers;

import java.io.IOException;
import java.nio.charset.Charset;

public class ResourceHelper
{
    /**
     * Returns the class loader from the current thread.
     */
    public static ClassLoader getClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Returns an internal resource as a string.
     *
     * @param path the path to the resource.
     * @param charset the character set to decode the resource into a string.
     * @return the requested resource as a string.
     */
    public static String readResourceAsString(final String path, Charset charset)
    {
        String result = "";

        try
        {
            result = new String(getClassLoader().getResourceAsStream(path).readAllBytes(), charset);
        }
        catch (IOException exception)
        {
            ExceptionHelper.throwIOException(path, exception);
        }

        return result;
    }
}
