package com.wikinotes.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;
    private Path testDirectory;

    @BeforeEach
    void setUp() throws IOException {
        testDirectory = Files.createTempDirectory("notes_test");
        fileService = new FileService(testDirectory.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(testDirectory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        System.err.println("Fallo al borrar el file: " + file);
                    }
                });
    }

    @Test
    void testSaveAndReadNote() {
        String noteName = "test_note.md";
        String content = "Esto es un test de nota.";

        fileService.saveNote(noteName, content);
        String readContent = fileService.readNote(testDirectory.resolve(noteName));

        assertEquals(content, readContent, "El contenido de la nota debe coincidir.");
    }

    @Test
    void testGetAllNotes() {
        fileService.saveNote("note1.md", "Contenido 1");
        fileService.saveNote("note2.md", "Contenido 2");

        List<String> notes = fileService.getAllNotes();
        assertEquals(2, notes.size(), "Deberia haber dos notas.");
        assertTrue(notes.contains("note1.md"));
        assertTrue(notes.contains("note2.md"));
    }
}