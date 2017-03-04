package com.cedrus.ui.controls;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Note: if you want to use NumberField for time variables use setTime() method to show time in '03' format.
 *
 * @author Khrystyna Makar
 */
public class NumberField extends TextField {
    public enum Type {
        HOUR, MINUTE, NUMBER
    }
    
    private static final int DEFAULT_NUMBER_LENGTH = 9;

    private Type type;
    private Node nextFocusNode;
    private String previousValue;
	private Node parentNode;
	private int numberLength = DEFAULT_NUMBER_LENGTH;

    public NumberField() {
        super();
        this.setAlignment(Pos.CENTER);
        getStylesheets().add(CustomTextField.class.getResource("styles/custom-text-field.css").toExternalForm());
        addListeners();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Node getNextFocusNode() {
        return nextFocusNode;
    }

    public void setNextFocusNode(Node nextFocusNode) {
        this.nextFocusNode = nextFocusNode;
    }

    private void addListeners() {
		setOnMouseClicked(e -> {
			parentNode = getParent();
			if (parentNode != null && getScene() != null) {
				getScene().setOnMousePressed(clickEvent -> parentNode.requestFocus());
			}
		});

        this.setOnKeyPressed(event -> {
            if(this.isFocused() && (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE)){
                if (previousValue != null && event.getCode() == KeyCode.ESCAPE) {
                    setText(previousValue);
                }
                if (this.getNextFocusNode() != null) {
                    this.getNextFocusNode().requestFocus();
                }
                event.consume();
            }
        });

        //when numberField lose focus it saves current text to previous typed value
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                previousValue = getText();
            }
        });
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    public void setTime(int time) {
        String timeStr = String.valueOf(time);
        setText(timeStr.length() < 2 ? "0" + timeStr : timeStr);
        if (previousValue == null) {
            previousValue = getText();
        }
    }
    
    public void setTime(String time) {
        
        setText(time.length() < 2 ? "0" + time : time);
        if (previousValue == null) {
            previousValue = getText();
        }
    }
    
    public String getTime() {
        String timeStr = this.getText();
        if (timeStr.trim().length() == 1) {
        	return "0" + timeStr;
        }
        return timeStr;
    }

    private boolean validate(String text) {
        if (text.matches("[0-9]*") && !text.isEmpty()) {
            String futureText = getFutureText(text);
            if (futureText.length() > 2 && !type.equals(Type.NUMBER)) {
                return false;
            }
            if (futureText.length() > numberLength && type.equals(Type.NUMBER)) {
                return false;
            }
            long time = getText().isEmpty() ? Long.parseLong(text) : Long.parseLong(futureText.trim());
            if (type.equals(Type.MINUTE)) {
                return time < 60 && time >= 0;
            } else if (type.equals(Type.HOUR)) {
                return time < 24 && time >= 0;
            }
        }
        return text.matches("[0-9]*");
    }

    private String getFutureText(String text) {
        if (getSelectedText().isEmpty()) {
            return getText().substring(0, getCaretPosition()) + text + getText().substring(getCaretPosition(), getText().length());
        } else {
            return getText().substring(0, getSelection().getStart()) + text + getText().substring(getSelection().getEnd(), getText().length());
        }
    }

	public int getNumberLength() {
		return numberLength;
	}

	public void setNumberLength(int numberLength) {
		this.numberLength = numberLength;
	}
}
