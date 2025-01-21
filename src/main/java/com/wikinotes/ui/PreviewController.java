package com.wikinotes.ui;

import com.wikinotes.markdown.MarkdownProcessor;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class PreviewController extends BorderPane {

    private final WebView webView;
    private final MarkdownProcessor markdownProcessor;

    public PreviewController() {
        this.webView = new WebView();
        this.markdownProcessor = new MarkdownProcessor();

        this.setCenter(webView);
    }

    /**
     * Actualiza la previsualización con el contenido Markdown
     * @param markdown Contenido del editor en formato Markdown
     * */
    public void updatePreview(String markdown) {
        String htmlContent = markdownProcessor.render(markdown);
        webView.getEngine().loadContent(htmlContent);
    }
}
