package com.wikinotes.ui;

import com.wikinotes.services.FileService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Objects;

public class MainView extends Application {

    private final FileService fileService = new FileService("notes");

    @Override
    public void start(Stage primaryStage) {
        EditorController editorController = new EditorController(fileService);
        PreviewController previewController = new PreviewController();
        SidebarController sidebarController = new SidebarController(fileService);

        // Conecta barra lateral con el editor
        sidebarController.getNoteListView().getSelectionModel().selectedItemProperty().addListener((obs, oldNote, newNote) -> {
            if (newNote != null) {
                String content = fileService.readNote(fileService.getBasePath().resolve(newNote));
                editorController.setMarkdownContent(content);
            }
        });

        // Conecta editor con previsualización
        editorController.textProperty().addListener((obs, oldText, newText) -> {
            previewController.updatePreview(newText);
        });

        // Layout principal
        SplitPane mainLayout = new SplitPane();
        mainLayout.getItems().addAll(sidebarController, editorController, previewController);
        mainLayout.setDividerPositions(0.3, 0.7); // TODO: revisar proporciones

        BorderPane root = new BorderPane();
        root.setTop(createMenuBar(editorController));
        root.setCenter(mainLayout);

        // Configuracion de la escena
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/wikinotes.css")).toExternalForm());
        primaryStage.setTitle("Wiki Notes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Crea la barra de menu
     * @param editorController Controlador del editor
     * */
    private MenuBar createMenuBar(EditorController editorController) {
        MenuBar menuBar = new MenuBar();

        // Menu de archivo
        Menu fileMenu = new Menu("Archivo");
        fileMenu.getStyleClass().add("menu");

        MenuItem newNote = new MenuItem("Nueva Nota");
        newNote.getStyleClass().add("menu-item");
        newNote.setOnAction(e -> editorController.setMarkdownContent(""));

        MenuItem openNote = new MenuItem("Abrir Nota");
        openNote.getStyleClass().add("menu-item");
        openNote.setOnAction(e -> openFile(editorController));

        MenuItem saveNote = new MenuItem("Guardar Nota");
        saveNote.getStyleClass().add("menu-item");
        saveNote.setOnAction(e -> saveFile(editorController));

        fileMenu.getItems().addAll(newNote, openNote, saveNote);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    /**
     * Abre un archivo de notas
     * @param editorController Controlador del editor
     * */
    private void openFile(EditorController editorController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown Files", "*.md"));
        java.io.File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String content = fileService.readNote(file.toPath());
            editorController.setMarkdownContent(content);
        }
    }

    /**
     * Guarda el contenido del editor en un archivo .md
     * @param editorController Controlador del editor
     * */
    private void saveFile(EditorController editorController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown Files", "*.md"));
        java.io.File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            fileService.saveNote(file.getName(), editorController.getMarkdownContent());
        }
    }
}
