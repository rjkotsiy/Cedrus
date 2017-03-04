package com.cedrus.ui.styles;

import java.text.MessageFormat;

public class ThemeManager {
    public static final String MCT_BLACK_RGBA = "rgba(51, 51, 51, {0})";
    public static final String IRIS_BLUE = "rgba(0, 180, 213, {0})";
    public static final String WHITE = "rgba(255, 255, 255, {0})";
    public static final String GREY = "rgba(215, 223, 229, {0})";

    public static final String TRANSPARENT = "transparent";
    public static final String COLOR_WHITE = "#ffffff";
    public static final String COLOR_BLACK = "#000000";
    public static final String COLOR_IRIS_BLUE = "#00b4d5";
    public static final String COLOR_WHISPER = "#e7e7e7";
    public static final String COLOR_ALICE_BLUE = "#f6fafd";
    public static final String COLOR_NAVY_BLUE = "#0D77BF";
    public static final String COLOR_MIDNIGHT_BLUE = "#171b65";
    public static final String COLOR_CLOUD_BURST = "#323c4e";
    public static final String COLOR_SHUTTLE_GREY = "#63666e";
    public static final String COLOR_YELLOW_SEA = "#f2983a";
    public static final String COLOR_LIGHT_GRAY = "#eeeeee";
    public static final String COLOR_RED = "#FF1313";
    public static final String COLOR_MATTERHORN = "#4D4D4D";
    public static final String COLOR_DROVER = "#fef1a5";
    public static final String COLOR_ALIZARIN = "#ee2941";
    public static final String COLOR_NERO = "#222222";
    public static final String COLOR_BALI_HAI = "#8c97a1";
    public static final String COLOR_GHOST = "#C6C6C7";
    public static final String COLOR_SPINDLE = "#b8c7d3";
    public static final String COLOR_VENETIAN_RED = "#D0021B";

    public static final String BUTTON_BORDER_WHISPER = COLOR_WHISPER;
    public static final String BUTTON_BORDER_IRIS_BLUE = COLOR_IRIS_BLUE;
    public static final String BUTTON_BORDER_GHOST = COLOR_GHOST;
    public static final String BUTTON_BACKGROUND_WHITE = COLOR_WHITE;
    public static final String BUTTON_BACKGROUND_IRIS_BLUE = COLOR_IRIS_BLUE;
    public static final String BUTTON_BACKGROUND_ALICE_BLUE = COLOR_ALICE_BLUE;

    public static final String TEXT_FILL_CLOUD_BURST = COLOR_CLOUD_BURST;
    public static final String TEXT_FILL_ALIZARIN = COLOR_ALIZARIN;
    public static final String TEXT_FILL_WHITE = COLOR_WHITE;
    public static final String TEXT_FILL_IRIS_BLUE = COLOR_IRIS_BLUE;
    public static final String TEXT_FILL_SHUTTLE_GREY = COLOR_SHUTTLE_GREY;
    public static final String TEXT_FILL_NERO = COLOR_NERO;
    public static final String TEXT_FILL_BALI_HAI = COLOR_BALI_HAI;
    public static final String TEXT_FILL_SPINDLE = COLOR_SPINDLE;
    public static final String TEXT_BACKGROUND_DROVER = COLOR_DROVER;

    public static final String LABEL_TEXT_SHUTTLE_GREY = COLOR_SHUTTLE_GREY;
    public static final String LABEL_TEXT_NAVY_BLUE = COLOR_NAVY_BLUE;
    public static final String ERROR_LABEL_TEXT = COLOR_VENETIAN_RED;

    private ThemeManager() {}

    public static String getColor(String color, String opacity) {
        return MessageFormat.format(color, opacity);
    }
}
