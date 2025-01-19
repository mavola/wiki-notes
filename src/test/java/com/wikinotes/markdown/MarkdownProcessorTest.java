package com.wikinotes.markdown;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownProcessorTest {

    private final MarkdownProcessor markdownProcessor = new MarkdownProcessor();

    @Test
    void testMarkdownToHtml() {
        String markdown = "# Hola Mundo\n\nEsto es **bold** y esto es *italic*.";
        String expectedHtml = "<h1>Hola Mundo</h1>\n<p>Esto es <strong>bold</strong> y esto es <em>italic</em>.</p>\n";

        String result = markdownProcessor.render(markdown);
        assertEquals(expectedHtml, result, "La salida HTML debe coincidir con el resultado esperado.");
    }
}
