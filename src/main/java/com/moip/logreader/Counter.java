package com.moip.logreader;

import com.moip.logreader.exception.LogReaderException;
import com.moip.logreader.model.Result;
import com.moip.logreader.reader.FileReader;
import com.moip.logreader.service.CounterService;

public class Counter {

    public static void main(String[] args) {

        String fileName = null;

        // valida se a path foi passada como parametro;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            // caso não tenha path exibe mensagem de erro e finaliza;
            System.err.println("É necessario informar um arquivo de log");
            return;
        }

        try {
            // cria leitor de arquivo;
            FileReader fileReader = new FileReader(fileName);

            // cria contador de logs;
            CounterService counterService = new CounterService(fileReader);

            // recuperar contagens
            Result result = counterService.getTop3UrlsAndStatusCount();

            System.out.println("Top 3 urls mais chamadas:");
            result.getTopUrls().forEach(System.out::println);

            System.out.println("\nContagem dos status das requisições");
            result.getStatusCount().forEach(System.out::println);
            
        } catch (LogReaderException e) {
            System.err.println(e.getMessage());
        }
    }
}
