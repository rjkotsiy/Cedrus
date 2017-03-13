package com.cedrus.ui.controls;

import com.cedrus.ui.styles.CssPropertyName;
import com.cedrus.ui.styles.ThemeManager;
import com.cedrus.utils.OSDependStaff;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Paint;

import java.util.EnumMap;
import java.util.Map;

public class SmartButton extends Button {
    public static final double INITIAL_OPACITY = 0.75;
    private String textFont =                     "Tahoma";
    private double textFontSize =                 OSDependStaff.getDefaultButtonFontSize();
    private String borderColor =                  ThemeManager.BUTTON_BORDER_IRIS_BLUE;
    private BorderStrokeStyle borderStrokeStyle = BorderStrokeStyle.SOLID;
    private double borderRadius =                 2;
    private double borderWidth =                  1;
    private Image image;
    private Map<CssPropertyName, String> styleMap = new EnumMap<>(CssPropertyName.class);

    public SmartButton() {
        setTextFill(Paint.valueOf(ThemeManager.TEXT_FILL_IRIS_BLUE));
        styleMap.put(CssPropertyName.BACKGROUND_COLOR, ThemeManager.COLOR_WHITE);
        setCursor(Cursor.HAND);
        setOpacity(INITIAL_OPACITY);
        getStylesheets().add(SmartButton.class.getResource("../styles/css/custom-button.css").toExternalForm());
    }

    public String getTextFont() {
        return textFont;
    }

    
    public void setTextFont(String textFont) {
        this.textFont = textFont;
    }
    
    public double getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(double textFontSize) {
        this.textFontSize = textFontSize;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public BorderStrokeStyle getBorderStrokeStyle() {
        return borderStrokeStyle;
    }

    public void setBorderStrokeStyle(BorderStrokeStyle borderStrokeStyle) {
        this.borderStrokeStyle = borderStrokeStyle;
    }

    public double getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(double borderRadius) {
        this.borderRadius = borderRadius;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Map<CssPropertyName, String> getStyleMap() {
        return styleMap;
    }


}
