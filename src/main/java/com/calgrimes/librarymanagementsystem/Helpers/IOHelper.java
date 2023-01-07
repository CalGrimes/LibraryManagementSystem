package com.calgrimes.librarymanagementsystem.Helpers;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOHelper {

    /**
     * Reads a file and stores its contents in a string.
     *
     * @param file the path to the file.
     * @return a string pertaining to the contents of the file.
     */
    public static String readString(String file)
    {
        String readString = "";

        if (StringUtils.isEmpty(file) || !Files.exists(Path.of(file)))
            return readString;

        try
        {
            try
            {
                readString = Files.readString(Path.of(file));
            }
            catch (MalformedInputException exception)
            {
                // Ignore invalid UTF-8 data.
                CharsetDecoder utf8 = StandardCharsets.UTF_8.newDecoder();
                utf8.onMalformedInput(CodingErrorAction.IGNORE);
                utf8.onUnmappableCharacter(CodingErrorAction.IGNORE);

                // Attempt to manually parse the file.
                readString = utf8.decode(ByteBuffer.wrap(Files.readAllBytes(Path.of(file)))).toString();
            }
        }
        catch (IOException exception)
        {
            ExceptionHelper.throwIOException(file, exception);
        }

        return readString;
    }

}
