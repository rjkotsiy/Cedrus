package com.cedrus.ui.controls;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;

public class TextButton extends Button {

	private static final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 0, 1, 0, 0;";

	private Insets imageInsets = new Insets(5, 5, 5, 5);
	private Integer hSpace = 15;
	private Integer vSpace = 5;
	private Cursor defaultCursor = Cursor.HAND;
	private Label textLabel;

	public TextButton() {
		super();
	}

	public TextButton(String image, String text, String textColor) {
		setTextButton(new Image(new File(image).toURI().toString()), text, textColor);
	}

	public TextButton(String text, String textColor, Insets insets, Integer vSpace, Integer hSpace, Cursor cursor) {
		this.imageInsets = insets;
		this.vSpace = vSpace;
		this.hSpace = hSpace;
		this.defaultCursor = cursor;

		setTextButton(null, text, textColor);
	}

	public void setTextButton(Image image, String text, String textColor) {
		textLabel = new Label();

		ImageView imageView = new ImageView(image);

		textLabel.setText(text);
		textLabel.setStyle("-fx-text-fill:" + textColor);

		HBox box = new HBox();
		box.setSpacing(hSpace);
		box.setPadding(imageInsets);
		VBox vbox = new VBox();
		vbox.setSpacing(vSpace);

		setStyle(STYLE_NORMAL);
		this.setCursor(defaultCursor);

		box.getChildren().addAll(imageView, textLabel, vbox);
		setGraphic(box);
	}
	
	public void setTextUnderLine(boolean underLine) {
		if (underLine) {
			textLabel.setStyle("-fx-underline: true;");
		} else {
			textLabel.setStyle("-fx-underline: false;");
		}
	}
	
	public void setTextHighlight(String textColor) {
		textLabel.setTextFill(Color.web(textColor));
	}
}