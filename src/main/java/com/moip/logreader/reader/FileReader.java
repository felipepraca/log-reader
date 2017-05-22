package com.moip.logreader.reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.moip.logreader.exception.LogReaderException;

public class FileReader {

    private String path;

    public FileReader(String path) {
        this.path = path;
    }

    public <T> List<T> read(Function<String, T> mapper) throws LogReaderException {
        
        // abre stream do arquivo
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            
            // mapea stream para objeto requisitado atravez do mapper;
            return stream.map(mapper).collect(Collectors.toList());
        } catch (Exception e) {
            throw new LogReaderException("Erro ao ler arquivo", e);
        }
    }

}
