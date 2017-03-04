package com.cedrus.ui.controls;

import com.cedrus.ui.styles. CssPropertyName;
import com.cedrus.ui.styles.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SmartButtonBuilder {
	private SmartButton button;

	public SmartButtonBuilder() {
		this.button = new SmartButton();
	}

	public SmartButtonBuilder(SmartButton button) {
		this.button = button;
	}

	public static SmartButtonBuilder getDefaultBlueButtonBuilder() {
		return new SmartButtonBuilder().setBackgroundColor(ThemeManager.BUTTON_BACKGROUND_IRIS_BLUE)
				.setTextFill(ThemeManager.TEXT_FILL_WHITE);
	}

	public static SmartButtonBuilder getDefaultWhiteButtonBuilder() {
		return new SmartButtonBuilder().setBackgroundColor(ThemeManager.BUTTON_BACKGROUND_WHITE)
				.setTextFill(ThemeManager.TEXT_FILL_IRIS_BLUE).setBorderColor(ThemeManager.BUTTON_BACKGROUND_IRIS_BLUE);
	}

	public static SmartButtonBuilder getDefaultTextButtonBuilder() {
		return new SmartButtonBuilder().setTextFill(ThemeManager.TEXT_FILL_IRIS_BLUE)
				.setBackgroundColor(ThemeManager.TRANSPARENT).setBorderColor(ThemeManager.TRANSPARENT);
	}

	public static SmartButtonBuilder getLinkButtonBuilder() {
		return getDefaultTextButtonBuilder().setFocusTraversable(false).setPadding(Insets.EMPTY);
	}

	public static Border generateBorder(double radius, double width, String color, BorderStrokeStyle stroke) {
		CornerRadii borderRadius = new CornerRadii(radius);
		BorderWidths borderWidths = new BorderWidths(width);
		BorderStroke borderStroke = new BorderStroke(Paint.valueOf(color), stroke, borderRadius, borderWidths);
		return new Border(borderStroke);
	}

	public static Background generateBackground(String color, double radius, Insets insets) {
		Paint paint = (color == null) ? null : Paint.valueOf(color);
		BackgroundFill backgroundFill = new BackgroundFill(paint, new CornerRadii(radius), insets);
		return new Background(backgroundFill);
	}

	public SmartButtonBuilder setText(String name) {
		button.setText(name);
		return this;
	}

	/**
	 * @param textColor
	 *            may starts with "linear-gradient(" or "radial-gradient(". To
	 *            learn more and see examples please read
	 *            {@link javafx.scene.paint.Paint#valueOf(String) }
	 */
	public SmartButtonBuilder setTextFill(String textColor) {
		button.setTextFill(Paint.valueOf(textColor));
		return this;
	}

	public SmartButtonBuilder setFontSize(double size) {
		button.setTextFontSize(size);
		return this;
	}

	/**
	 * @param color
	 *            may starts with "linear-gradient(" or "radial-gradient(" To
	 *            learn more and see examples please read
	 *            {@link javafx.scene.paint.Paint#valueOf(String) }
	 */
	public SmartButtonBuilder setBorderColor(String color) {
		button.setBorderColor(color);
		return this;
	}

	public SmartButtonBuilder setBorderType(BorderStrokeStyle borderType) {
		button.setBorderStrokeStyle(borderType);
		return this;
	}

	public SmartButtonBuilder setBorderRadius(double radius) {
		button.setBorderRadius(radius);
		return this;
	}

	public SmartButtonBuilder setBorderWidth(double width) {
		button.setBorderWidth(width);
		return this;
	}

	/**
	 * @param color
	 *            may starts with "linear-gradient(" or "radial-gradient(" To
	 *            learn more and see examples please read
	 *            {@link javafx.scene.paint.Paint#valueOf(String) }
	 */
	public SmartButtonBuilder setBackgroundColor(String color) {
		button.getStyleMap().put(CssPropertyName.BACKGROUND_COLOR, color);
		return this;
	}

	public SmartButtonBuilder setBackgroundRadius(Double px) {
		button.getStyleMap().put(CssPropertyName.BACKGROUND_RADIUS, px.toString());
		return this;
	}

	public SmartButtonBuilder setImage(Image image) {
		button.setImage(image);
		return this;
	}

	public SmartButtonBuilder setImageTextGap(double gap) {
		button.setGraphicTextGap(gap);
		return this;
	}

	public SmartButtonBuilder setImageTextAlignment(ContentDisplay contentDisplay) {
		button.setContentDisplay(contentDisplay);
		return this;
	}

	public SmartButtonBuilder setButtonDisabled(boolean isDisabled) {
		button.setDisable(isDisabled);
		return this;
	}

	public SmartButtonBuilder setCursor(Cursor cursor) {
		button.setCursor(cursor);
		return this;
	}

	public SmartButtonBuilder setMaxHeight(double value) {
		button.setMaxHeight(value);
		return this;
	}

	public SmartButtonBuilder setMaxWidth(double value) {
		button.setMaxWidth(value);
		return this;
	}

	public SmartButtonBuilder setHeight(double value) {
		button.setPrefHeight(value);
		return this;
	}

	public SmartButtonBuilder setWidth(double value) {
		button.setPrefWidth(value);
		return this;
	}

	public SmartButtonBuilder setAlignment(Pos position) {
		button.setAlignment(position);
		return this;
	}

	public SmartButtonBuilder setPadding(Insets insets) {
		button.setPadding(insets);
		return this;
	}

	public SmartButtonBuilder setFocusTraversable(boolean focus) {
		button.setFocusTraversable(focus);
		return this;
	}

	public SmartButtonBuilder setFont(Font font) {
		button.setFont(font);
		return this;
	}

	public SmartButtonBuilder setBorderColorCss(String style) {
		button.getStyleMap().put(CssPropertyName.BORDER_COLOR, style);
		return this;
	}

	public SmartButtonBuilder setBorderRadiusCss(String style) {
		button.getStyleMap().put(CssPropertyName.BORDER_RADIUS_COLOR, style);
		return this;
	}

	/**
	 * @param cssClassName
	 *            Class name in attached to this object css file.
	 */
	public SmartButtonBuilder addStyleClass(String cssClassName) {
		button.getStyleClass().add(cssClassName);
		return this;
	}

	public SmartButton build() {
		Font font = new Font(button.getTextFont(), button.getTextFontSize());
		button.setFont(font);

		CornerRadii borderRadius = new CornerRadii(button.getBorderRadius());
		BorderWidths borderWidths = new BorderWidths(button.getBorderWidth());
		BorderStroke borderStroke = new BorderStroke(Paint.valueOf(button.getBorderColor()),
				button.getBorderStrokeStyle(), borderRadius, borderWidths);
		Border border = new Border(borderStroke);
		button.setBorder(border);

		String backgroundColor = button.getStyleMap().get(CssPropertyName.BACKGROUND_COLOR);
		Paint paint = (backgroundColor == null) ? null : Paint.valueOf(backgroundColor);
		BackgroundFill backgroundFill = new BackgroundFill(paint, borderRadius, null);
		button.setBackground(new Background(backgroundFill));

		ImageView imageView = new ImageView(button.getImage());
		if (imageView != null) {
			button.setGraphic(imageView);
		}

		button.setStyle(CssPropertyName.generateStyle(button.getStyleMap()));
		return button;
	}
}
