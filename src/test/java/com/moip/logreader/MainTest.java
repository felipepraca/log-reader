package com.moip.logreader;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;

import org.junit.Test;

import com.moip.logreader.exception.LogReaderException;

public class MainTest {

    @Test
    public void invalidFile() throws LogReaderException {
        // GIVEN
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        String[] args = {};

        // WHEN
        Counter.main(args);

        // THEN
        String out = err.toString();

        assertEquals("É necessario informar um arquivo de log\n", out);
    }

    @Test
    public void successCountTop3UrlsAndStatus() throws LogReaderException {
        // GIVEN
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        URL resource = this.getClass().getClassLoader().getResource("log.txt");

        String[] args = { resource.getPath() };

        // WHEN
        Counter.main(args);

        // THEN
        String out = outContent.toString();

        String[] split = out.split("\n");

        assertEquals("Top 3 urls mais chamadas:", split[0]);
        assertEquals("https://url1.br - 3", split[1]);
        assertEquals("https://url2.br - 2", split[2]);
        assertEquals("https://url3.br - 1", split[3]);
        assertEquals("", split[4]);
        assertEquals("Contagem dos status das requisições", split[5]);
        assertEquals("201 - 3", split[6]);
        assertEquals("200 - 2", split[7]);
        assertEquals("500 - 2", split[8]);
    }

}
