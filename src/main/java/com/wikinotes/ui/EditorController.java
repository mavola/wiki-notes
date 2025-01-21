package com.wikinotes.ui;

import com.wikinotes.services.FileService;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class EditorController extends StackPane {

    private final CodeArea codeArea;
    private final FileService fileService;

    public EditorController(FileService fileService) {
        this.fileService = fileService;
        this.codeArea = new CodeArea();

        codeArea.setParagraphGraphicFactory(line -> {
            Node lineNumber = LineNumberFactory.get(codeArea).apply(line);
            StackPane lineNumberPane = new StackPane(lineNumber);
            lineNumberPane.setPadding(new Insets(0, 10, 0, 0)); // --> margin-right: 10px
            return lineNumberPane;
        });

        getChildren().add(new VirtualizedScrollPane<>(codeArea));

        // Detecta clics en enlaces internos
        codeArea.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) { // Doble clic
                int position = codeArea.getCaretPosition();
                String link = findWikiLinkAtPosition(position);
                if (link != null) {
                    openWikiLink(link);
                }
            }
        });
    }

    // Propiedades de la clase CodeArea
    public ObservableValue<String> textProperty() {
        return codeArea.textProperty();
    }

    // Obtener el contenido del editor
    public String getMarkdownContent() {
        return codeArea.getText();
    }

    // Establecer el contenido del editor
    public void setMarkdownContent(String content) {
        codeArea.replaceText(content);
    }

    /**
     * Detecta enlaces en el texto del editor
     * @param position Posicion del cursor
     * */
    private String findWikiLinkAtPosition(int position) {
        String text = codeArea.getText();
        // Implementar la deteccion de enlaces en formato [texto](wiki:nombre)
        // Aca asumimos que los enlaces tienen el formato Markdown estandar
        int start = text.lastIndexOf("[", position);
        int end = text.indexOf(")", position);
        if (start >= 0 && end > start) {
            String possibleLink = text.substring(start, end + 1);
            if (possibleLink.contains("(wiki:")) {
                return possibleLink.substring(possibleLink.indexOf("(wiki:") + 6, possibleLink.length() - 1);
            }
        }
        return null;
    }

    /**
     * Abre el enlace a una nota de la wiki
     * @param noteName Nombre de la nota
     * */
    private void openWikiLink(String noteName) {
        String fileName = noteName + ".md";
        String content = fileService.readNote(fileService.getBasePath().resolve(fileName));
        if (content != null) {
            setMarkdownContent(content);
        }
    }
}
