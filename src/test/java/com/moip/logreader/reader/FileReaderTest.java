package com.moip.logreader.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.moip.logreader.exception.LogReaderException;

public class FileReaderTest {

    @Test(expected = LogReaderException.class)
    public void invalidFile() throws LogReaderException {
        // GIVEN
        FileReader fileReader = new FileReader(null);

        // WHEN
        fileReader.read(line -> {
            return null;
        });

        // THEN
        fail();
    }

    @Test
    public void SuccessToReadFile() throws LogReaderException {
        // GIVEN
        URL resource = this.getClass().getClassLoader().getResource("log.txt");

        FileReader fileReader = new FileReader(resource.getPath());

        // WHEN
        List<String> lines = fileReader.read(line -> {
            return line;
        });

        // THEN
        assertEquals(8, lines.size());
    }
}
