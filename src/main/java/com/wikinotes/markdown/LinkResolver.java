package com.wikinotes.markdown;

import com.wikinotes.services.FileService;
import com.wikinotes.ui.EditorController;

public class LinkResolver {
    private final FileService fileService;

    public LinkResolver(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Resuelve un enlace interno a un archivo del sistema de archivos
     * @param href Enlace a resolver
     * */
    public String resolveLink(String href) {
        if (href.startsWith("wiki:")) {
            String noteName = href.substring(5);
            return fileService.getBasePath().resolve(noteName + ".md").toString();
        }
        return href; // Otros enlaces no se modifican
    }

    /**
     * Detecta enlaces en el texto del editor
     * @param position Posicion del cursor
     * */
    public String findWikiLinkAtPosition(String text, int position) {
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
}
