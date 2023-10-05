package com.example.infosystems.model.enums;

public enum FileType {
    /**
     * Picture from employee
     */
    PICTURE,

    /**
     * Сome other documents such as diplomas, certificates and others
     */
    DOCUMENT,

    /**
     * Contract File
     */
    CONTRACT;

    public static FileType getFileTypeByName(final String fileTypeName) {
        for (final FileType fileType : FileType.values()) {
            if (fileType.name().equalsIgnoreCase(fileTypeName)) {
                return fileType;
            }
        }
        throw new IllegalArgumentException("No FileType with name '" + fileTypeName
                + "' found!");
    }
}
