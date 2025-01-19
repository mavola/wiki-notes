package com.wikinotes.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class MarkdownProcessor {
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownProcessor() {
        MutableDataSet options = new MutableDataSet();
        this.parser = Parser.builder(options).build();
        this.renderer = HtmlRenderer.builder(options).build();
    }

    public String render(String markdown) {
        return renderer.render(parser.parse(markdown));
    }
}
