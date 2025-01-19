package com.wikinotes.ui;

import com.wikinotes.services.FileService;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public class SidebarController extends VBox {

    private final ListView<String> noteListView;
    private final FileService fileService;
    private final TextField searchField;

    public SidebarController(FileService fileService) {
        this.fileService = fileService;
        this.noteListView = new ListView<>();
        this.searchField = new TextField();

        searchField.setPromptText("Buscar...");
        searchField.textProperty().addListener((obs, oldText, newText) -> filterNotes(newText));

        this.getChildren().addAll(searchField, noteListView);
        refreshNoteList();
    }

    // Refrescar la lista de notas disponibles
    public void refreshNoteList() {
        List<String> notes = fileService.getAllNotes();
        noteListView.getItems().setAll(notes);
    }

    // Seleccionar una nota de la lista
    public String getSelectedNote() {
        return noteListView.getSelectionModel().getSelectedItem();
    }

    // Evento para manejar selecciones
    public void setOnNoteSelected(Runnable action) {
        noteListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                action.run();
            }
        });
    }

    // Filtrar notas por nombre
    private void filterNotes(String query) {
        List<String> notes = fileService.getAllNotes();
        noteListView.getItems().setAll(notes.stream()
                .filter(note -> note.toLowerCase().contains(query.toLowerCase()))
                .toList());
    }

    public ListView<String> getNoteListView() {
        return noteListView;
    }
}
