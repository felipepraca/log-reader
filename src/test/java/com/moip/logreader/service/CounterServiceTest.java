package com.moip.logreader.service;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.moip.logreader.exception.LogReaderException;
import com.moip.logreader.model.Count;
import com.moip.logreader.model.Result;
import com.moip.logreader.reader.FileReader;

public class CounterServiceTest {

    @Test(expected = LogReaderException.class)
    public void errorForInvalidFile() throws LogReaderException {
        // GIVEN
        FileReader fileReader = new FileReader("");

        CounterService counterService = new CounterService(fileReader);

        // WHEN
        counterService.getTop3UrlsAndStatusCount();

        // THEN
        fail();
    }

    @Test
    public void countTop3UrlsAndStatus() throws LogReaderException {
        // GIVEN
        URL resource = this.getClass().getClassLoader().getResource("log.txt");

        FileReader fileReader = new FileReader(resource.getPath());

        CounterService counterService = new CounterService(fileReader);

        // WHEN
        Result result = counterService.getTop3UrlsAndStatusCount();

        // THEN
        List<Count> topUrls = result.getTopUrls();

        assertEquals(3, topUrls.size());

        assertEquals("https://url1.br", topUrls.get(0).getValue());
        assertEquals(3, topUrls.get(0).getCount());

        assertEquals("https://url2.br", topUrls.get(1).getValue());
        assertEquals(2, topUrls.get(1).getCount());

        assertEquals("https://url3.br", topUrls.get(2).getValue());
        assertEquals(1, topUrls.get(2).getCount());

        List<Count> statusCount = result.getStatusCount();

        assertEquals(3, statusCount.size());

        assertEquals("201", statusCount.get(0).getValue());
        assertEquals(3, statusCount.get(0).getCount());

        assertEquals("200", statusCount.get(1).getValue());
        assertEquals(2, statusCount.get(1).getCount());

        assertEquals("500", statusCount.get(2).getValue());
        assertEquals(2, statusCount.get(2).getCount());

    }
}
