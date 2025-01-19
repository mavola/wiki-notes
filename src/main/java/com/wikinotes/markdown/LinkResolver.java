package com.wikinotes.markdown;

import com.wikinotes.services.FileService;

public class LinkResolver {
    private final FileService fileService;

    public LinkResolver(FileService fileService) {
        this.fileService = fileService;
    }

    public String resolveLink(String href) {
        if (href.startsWith("wiki:")) {
            String noteName = href.substring(5);
            return fileService.getBasePath().resolve(noteName + ".md").toString();
        }
        return href; // Otros enlaces no se modifican
    }
}
