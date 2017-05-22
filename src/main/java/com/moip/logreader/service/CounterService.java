package com.moip.logreader.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.moip.logreader.exception.LogReaderException;
import com.moip.logreader.model.Count;
import com.moip.logreader.model.Line;
import com.moip.logreader.model.Result;
import com.moip.logreader.reader.FileReader;

public class CounterService {

    private static final String PATTERN_LOG = "^level=(\\w*) response_body=\"(.*)\" request_to=\"(.*)\" response_headers=(.*) response_status=\"(\\d+)\"";

    private Pattern pattern;
    private FileReader reader;

    public CounterService(FileReader reader) {
        this.reader = reader;

        // inicializa patter de reconhecimento do log
        this.pattern = Pattern.compile(PATTERN_LOG);
    }

    public Result getTop3UrlsAndStatusCount() throws LogReaderException {

        // recupera linhas do arquivo;
        List<Line> lines = reader.read(getFunctionLine());

        Map<String, Count> urlRequests = new TreeMap<>();
        Map<String, Count> statusRequest = new TreeMap<>();

        // filtra linhas vazias (linhas que não estão no padrão correto)
        lines.stream().filter(line -> Objects.nonNull(line)).forEach(line -> {
            String url = line.getUrl();
            String status = line.getStatus();

            // conta urls
            countUrl(urlRequests, url);

            // conta status;
            countStatus(statusRequest, status);
        });

        // ordena urls pela contagem de forma decrescente e pega somente as 3 primeiras posições;
        List<Count> top3urls = urlRequests.values().stream().sorted((c1, c2) -> Integer.compare(c2.getCount(), c1.getCount())).limit(3).collect(Collectors.toList());

        // ordena os status por contagem;
        List<Count> statusCounted = statusRequest.values().stream().sorted((c1, c2) -> Integer.compare(c2.getCount(), c1.getCount())).collect(Collectors.toList());

        // agrupa as contagens em um result;
        return new Result(top3urls, statusCounted);
    }

    /**
     * Função de leitura do arquivo
     * Extrai só o campos necessarios das linhas
     * @return
     */
    private Function<String, Line> getFunctionLine() {
        return line -> {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String url = matcher.group(3);
                String status = matcher.group(5);

                return new Line(url, status);
            }
            return null;
        };
    }

    /**
     * Agrupa e conta urls
     * @param urlRequests
     * @param url
     */
    private void countUrl(Map<String, Count> urlRequests, String url) {
        Count requestCount = urlRequests.getOrDefault(url, new Count(url));
        requestCount.increment();
        urlRequests.putIfAbsent(url, requestCount);
    }

    /**
     * Agrupa e conta status
     * @param statusRequest
     * @param status
     */
    private void countStatus(Map<String, Count> statusRequest, String status) {
        Count statusCount = statusRequest.getOrDefault(status, new Count(status));
        statusCount.increment();
        statusRequest.putIfAbsent(status, statusCount);
    }
}
