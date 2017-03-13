package com.cedrus.ui.controls;

import com.cedrus.ui.styles.ThemeManager;
import com.cedrus.ui.resources.ResourceManager;
import com.cedrus.utils.DateTimeUtils;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDatePicker extends HBox {

	private CustomTextField dateField;
	private SmartButton pickerButton;
	private LocalDate date;

	private Stage stage;
	private AnchorPane pane;
	private DatePicker datePicker;
	private VBox calendarControl;
	private boolean allowEmpty;
	private ChangeListener<Number> scrollListener;
	private ScrollPane scroll;

	public StringProperty dateProperty() {
		return dateField.textProperty();
	}

	public CustomDatePicker() {
		super();
		createCalendarControl(null);
		initialize();
	}

	public CustomDatePicker(Callback<DatePicker, DateCell> factory) {
		super();
		createCalendarControl(factory);
		initialize();
	}

	private void initialize() {
		pane = new AnchorPane();
		dateField = new CustomTextField();

		dateField.textProperty().addListener((observable, oldValue, newValue) -> {
			// Note, panten: restrict max text length
			if (newValue.trim().isEmpty() && allowEmpty) {

				datePicker.setValue(null);

			} else if (newValue.length() > 10) {
				dateField.setText(oldValue);
				newValue = oldValue;
			}
			LocalDate dt = parseTime(newValue);
			if (dt != null) {
				date = dt;
				datePicker.setValue(date);
			}
		});

		dateField.focusedProperty().addListener(listener -> {
			if (!dateField.isFocused()) {
				String strDateTime = dateField.getText();
				if (strDateTime.isEmpty() && allowEmpty) {
					dateField.clear();
					date = null;
				} else {
					LocalDate dt = parseTime(strDateTime);
					if (dt != null) {
						date = dt;
						dateField.textProperty().set(strDateTime);
						datePicker.setValue(date);
					} else {
						if (date != null) {
							dateField.textProperty().set(DateTimeUtils.localDateToStringISO(date));
						} else {
							dateField.clear();
						}
					}
				}
			}
		});

		pickerButton = new SmartButtonBuilder().setImage(ResourceManager.CALENDAR).setBackgroundRadius(0.0)
				.setBackgroundColor(ThemeManager.TRANSPARENT).setHeight(30).setBorderColor(ThemeManager.TRANSPARENT)
				.setFocusTraversable(false).build();

		scrollListener = (listener, oldVal, newVal) -> {
			stage.close();
		};
		
		pickerButton.setOnMouseClicked(value -> {
			//hide calendar on scroll
			Node node = dateField.getParent();
			while (true) {
				if (node instanceof ScrollPane){
					scroll = (ScrollPane) node;
					scroll.vvalueProperty().addListener(scrollListener);
					break;
				} else if (node.getParent() != null){
					node = node.getParent();
				} else {
					break;
				}
			}
			if (!stage.isShowing()) {
				Bounds bounds = dateField.getBoundsInLocal();
				Bounds screenBounds = dateField.localToScreen(bounds);
				stage.setX(screenBounds.getMinX());
				stage.setY(screenBounds.getMaxY());
				updatePiker();
				if (date != null) {
					datePicker.setValue(date);
				}
				stage.showAndWait();
			} else {
				stage.close();
			}
		});

		this.getChildren().addAll(dateField, pickerButton);
		pickerButton.setPadding(new Insets(6, 0, 0, -30));
		createWindow();
		if (stage.isShowing()) {
			return;
		}
		createUI();
	}

	private void createWindow() {
		if (stage == null) {
			stage = new Stage(StageStyle.UNDECORATED);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setResizable(false);
			stage.initModality(Modality.NONE);
			stage.setAlwaysOnTop(true);
			StackPane root = new StackPane();
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
				if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
					stage.close();
				}
			});
			stage.setScene(scene);
			stage.focusedProperty().addListener(listener -> {
				if (!stage.isFocused()) {
					stage.close();
				}
			});
			root.getChildren().add(pane);
		}
		stage.setOnHiding(e -> {
			scroll.vvalueProperty().removeListener(scrollListener);
			dateField.requestFocus();
			if (datePicker.getValue() != null) {
				String strDateTime = datePicker.getValue().toString();
				updatePiker();
				date = parseTime(strDateTime);
				dateField.textProperty().set(strDateTime);
				dateField.setText(strDateTime);
			}
		});
	}

	private void updatePiker() {
		if (datePicker != null && datePicker.getValue() != null) {
			datePicker.setValue(datePicker.getValue().plusDays(1));
			datePicker.setValue(datePicker.getValue().minusDays(1));
		}
	}

	private void createCalendarControl(Callback<DatePicker, DateCell> factory) {
		datePicker = new DatePicker();
		if (factory != null) {
			datePicker.setDayCellFactory(factory);
		}
		DatePickerSkin skin = new DatePickerSkin(datePicker);
		calendarControl = (VBox) skin.getPopupContent();
		calendarControl.getChildren().get(1).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			stage.close();
		});
	}

	private void createUI() {
		HBox hbox = new HBox();
		hbox.setPrefWidth(60);
		hbox.setAlignment(Pos.CENTER);
		calendarControl.getChildren().remove(hbox);
		calendarControl.getChildren().add(hbox);
		pane.getChildren().clear();
		pane.getChildren().addAll(calendarControl);
	}

	private LocalDate parseTime(String dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(dateTime, formatter);
		} catch (Exception e) {
			return null;
		}
	}

	public LocalDate getDateValue() {
		return date;
	}

	public String getStringValue() {
		return dateField.getText();
	}

	public void setEditable(boolean b) {
		dateField.setEditable(b);
	}

	public void setHeight(double height) {
		dateField.setPrefHeight(height);
	}

	public void setOptionalText(String promtText) {
		dateField.setPromptText(promtText);
	}

	public void setValue(String localDate) {
		try {
			datePicker.setValue(DateTimeUtils.stringToLocalDate(localDate, DateTimeUtils.ISO_DATE));
			dateField.setText(localDate);
		} catch (Exception e) {
			LocalDate ld = DateTimeUtils.stringToLocalDate(localDate, DateTimeUtils.ISO_DATETIME);
			datePicker.setValue(ld);
			dateField.setText(ld.toString());
		}
	}

	public void setValue(LocalDate localDate) {
		datePicker.setValue(localDate);
		dateField.setText(DateTimeUtils.localDateToStringISO(localDate));
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

}