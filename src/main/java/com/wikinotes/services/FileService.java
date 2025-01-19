package com.wikinotes.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
    private final Path basePath;

    public FileService(String directoryName) {
        this.basePath = Paths.get(System.getProperty("user.home"), directoryName);
        try {
            if (!Files.exists(basePath)) {
                Files.createDirectory(basePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el directorio de notas", e);
        }
    }

    public Path getBasePath() {
        return basePath;
    }

    public List<String> getAllNotes() {
        try {
            return Files.list(basePath)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error al listar las notas", e);
        }
    }

    public String readNote(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer la nota", e);
        }
    }

    public void saveNote(String fileName, String content) {
        try {
            Files.writeString(basePath.resolve(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la nota", e);
        }
    }
}
