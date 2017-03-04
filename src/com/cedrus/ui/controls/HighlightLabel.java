package com.cedrus.ui.controls;

/**
 * @file HighlightLabel.java
 * 
 * @brief Custom label with highlight text ability
 * 
 * @note Copyright (c) 2016 SoftServe, Inc. Confidential and proprietary. All
 *       rights reserved.
 * 
 * @author Pavlo Antentyk (panten@softserveinc.com)
 * 
 * @since 2016.09.09
 */


import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HighlightLabel extends HBox{
	private static final String STYLE = "-fx-background-color: yellow ; -fx-text-fill: black ;";
	
	private String text;
	private String highlight;
	
	public HighlightLabel() {
		super();
		
	}
	
	
	public HighlightLabel(String text) {
		super();
		this.text = text;
		initialize();
	}


	public HighlightLabel(String text, String highlight) {
		super();
		this.text = text;
		this.highlight = highlight;
		initialize();
	}

	private void initialize() {
		this.getChildren().clear();
		if (highlight != null && !highlight.trim().isEmpty() && text.toLowerCase().contains(this.highlight.toLowerCase())) {
			
			int index = text.toLowerCase().indexOf(this.highlight.toLowerCase());
			
			String begin = text.substring(0, index);
			String light = text.substring(index, index + this.highlight.length());
			String end = text.substring(begin.length() + light.length());
			
			if (!begin.isEmpty()) {
				Label labelBegin = new Label(begin);
				this.getChildren().add(labelBegin);
			}
			
			Label labeLight = new Label(light);
			labeLight.setStyle(STYLE);
			this.getChildren().add(labeLight);

			if(!end.isEmpty()) {
				Label labeEnd = new Label(end);
				this.getChildren().add(labeEnd);
			}
		}else {
			this.getChildren().add(new Label(this.text));
		}
	}
	
	
	public void setText(String strText) {
		this.text = strText;
		initialize();
	}


	public String getText() {
		return text;
	}
	
	public void highLightText(String highlight) {
		this.highlight = highlight;
		initialize();
	}
	
}