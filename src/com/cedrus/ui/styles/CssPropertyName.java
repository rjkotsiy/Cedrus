package com.cedrus.ui.styles;

import java.util.Map;

public enum CssPropertyName {
    BACKGROUND_COLOR("-fx-background-color: "),
    BACKGROUND_RADIUS("-fx-background-radius: "),
    BORDER_COLOR("-fx-border-color: "),
    BORDER_RADIUS_COLOR("-fx-border-radius: "),
    OPACITY("-fx-opacity: "),
    TEXT_FILL("-fx-text-fill: ");

    private String name;

    CssPropertyName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String generateStyle(Map<CssPropertyName, String> styleMap) {
        StringBuilder stringBuilder = new StringBuilder();
        styleMap.forEach((k,v) -> {
            stringBuilder.append(k.getName());
            stringBuilder.append(v);
            stringBuilder.append(";");
        });
        return stringBuilder.toString();
    }
}
