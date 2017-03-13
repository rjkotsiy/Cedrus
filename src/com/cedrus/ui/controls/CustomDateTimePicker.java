package com.cedrus.ui.controls;

import com.cedrus.ui.resources.ResourceManager;
import com.cedrus.ui.styles.ThemeManager;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomDateTimePicker extends HBox {
	public enum DurationType {
		NONE("NONE"), DAYS("DAYS"), WEEKS("WEEKS"), MONTHS("MONTHS");
		private String name;
		
		DurationType(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public static List<String> getNames(){
			List<String> names = new ArrayList<String>();
			for(DurationType durationType : values()) {
				names.add(durationType.name);
			}
			return names;
		}
    }
	
	public static final String DEFAULT_START_TIME = "00:00";
	public static final String DEFAULT_END_TIME = "23:59";
	public static final String EMPTY_DURATION_VALUE = "-";

	public enum BOUNDS_COMPARE_OPERATION {
		AFTER, BEFORE
	}

	private CustomTextField dateTimeField;
	private SmartButton pickerButton;
	private LocalDateTime dateTime;
	private LocalDateTime boundDateTime;
	private BOUNDS_COMPARE_OPERATION compareOperation;

	private Stage stage;
	private AnchorPane pane;
	private DatePicker datePicker;
	private NumberField hours;
	private NumberField minutes;
	private Label colon;
	private VBox calendarControl;
	private ChangeListener<Number> scrollListener;
	private boolean withDuration = false;
	private boolean allowEmpty;
	private LocalDateTime oldDateTimeValue;
	private ScrollPane scroll;
	private String defaultTime;


	public CustomDateTimePicker() {
		super();
		createCalendarControl(null);
		initialize();
	}

	public CustomDateTimePicker(boolean withDuration) {
		super();
		this.withDuration = withDuration;
		createCalendarControl(null);
		initialize();
	}

	public CustomDateTimePicker(Callback<DatePicker, DateCell> factory) {
		super();
		createCalendarControl(factory);
		initialize();
	}
	
	public CustomDateTimePicker(Callback<DatePicker, DateCell> factory, boolean withDuration) {
		super();
		this.withDuration = withDuration;
		createCalendarControl(factory);
		initialize();
	}

	public void setBoundDateTime(LocalDateTime boundDateTime) {
		this.boundDateTime = boundDateTime;
	}

	public void setRestrictedBoundsOperation(BOUNDS_COMPARE_OPERATION compareOperation) {
		this.compareOperation = compareOperation;
	}

	public StringProperty dateTimeProperty() {
		return dateTimeField.textProperty();
	}

	private boolean checkBoundsRestriction(String newDateTimeString) {
		LocalDateTime newDateTime = parseTime(newDateTimeString);
		if (newDateTime != null && boundDateTime != null && compareOperation.equals(BOUNDS_COMPARE_OPERATION.AFTER)
				&& (newDateTime.isAfter(boundDateTime))) {
			setValue(oldDateTimeValue);
			return false;
		} else if (newDateTime != null && boundDateTime != null
				&& compareOperation.equals(BOUNDS_COMPARE_OPERATION.BEFORE) && (newDateTime.isBefore(boundDateTime))) {
			setValue(oldDateTimeValue);
			return false;
		}
		return true;
	}

	private void initialize() {
		defaultTime = "";
		pane = new AnchorPane();
		dateTimeField = new CustomTextField();

		dateTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
			// Note, panten: restrict max text length
			if (newValue != null) {
				newValue = newValue.trim();
				if (newValue.isEmpty()) {
					dateTime = null;
					datePicker.setValue(null);
				} else if (newValue.length() > 16) {
					dateTimeField.setText(oldValue);
					newValue = oldValue;
				}
				LocalDateTime dt = parseTime(newValue);
				if (dt != null) {
					dateTime = dt;
					datePicker.setValue(dateTime.toLocalDate());
				}
			}
		});

		createTimeFields();

		dateTimeField.focusedProperty().addListener(listener -> {
			if (!dateTimeField.isFocused()) {
				if (dateTimeField.getText() == null) {
					dateTimeField.setText("");
				}
				String newDateTimeStr = dateTimeField.getText().trim();
				if (newDateTimeStr.isEmpty() && allowEmpty) {
					dateTimeField.clear();
					dateTime = null;
				} else {
					LocalDateTime newDateTime = parseTime(newDateTimeStr);
					if (!checkBoundsRestriction(newDateTimeStr)) {
						return;
					}
					if (newDateTime != null) {
						dateTime = newDateTime;
						dateTimeField.textProperty().set(dateTime.toString().replaceAll("T", " "));
						datePicker.setValue(dateTime.toLocalDate());
						hours.setTime(dateTime.getHour());
						minutes.setTime(dateTime.getMinute());
					} else {
						dateTime = oldDateTimeValue;
						if (dateTime != null) {
							dateTimeField.textProperty().set(DateTimeUtils.localDateTimeToStringHuman(dateTime));
						} else {
							dateTimeField.clear();
						}
					}
				}
			} else {
				oldDateTimeValue = getDateTimeValue();
			}
		});

		dateTimeField.addEventHandler(KeyEvent.ANY, eventHandler -> {
			if (eventHandler.getCode() == KeyCode.ENTER) {
				dateTimeField.getParent().requestFocus();
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
			Node node = dateTimeField.getParent();
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
				if (dateTimeField.getText().trim().isEmpty() && !allowEmpty) {
					setValue(oldDateTimeValue);
				}
				Bounds bounds = dateTimeField.getBoundsInLocal();
				Bounds screenBounds = dateTimeField.localToScreen(bounds);
				stage.setX(screenBounds.getMinX());
				stage.setY(screenBounds.getMaxY());
				updatePiker();
				if (dateTime != null) {
					datePicker.setValue(dateTime.toLocalDate());
				}
				if (hours.getTime().isEmpty()) {
					if (defaultTime != null && !defaultTime.isEmpty()) {
						hours.setTime(defaultTime.split(":")[0].trim());
					} else {
						hours.setTime(LocalTime.now().getHour());
					}
				}

				if (minutes.getTime().isEmpty()) {
					if (defaultTime != null && !defaultTime.isEmpty()) {
						minutes.setTime(defaultTime.split(":")[1].trim());
					} else {
						minutes.setTime(LocalTime.now().getMinute());
					}
				}
				
				oldDateTimeValue = getDateTimeValue();
				stage.showAndWait();
			} else {
				stage.close();
			}
		});

		this.getChildren().addAll(dateTimeField, pickerButton);
		pickerButton.setPadding(new Insets(6, 0, 0, -30));
		createWindow();
		if (stage.isShowing()) {
			return;
		}
		createUI();
	}

	private void createTimeFields() {
		hours = new NumberField();
		hours.setType(NumberField.Type.HOUR);
		hours.setAlignment(Pos.CENTER);
		hours.setMaxWidth(42);
		hours.setNextFocusNode(pane);

		minutes = new NumberField();
		minutes.setType(NumberField.Type.MINUTE);
		minutes.setAlignment(Pos.CENTER);
		minutes.setMaxWidth(42);
		minutes.setNextFocusNode(pane);

		colon = new Label(":");
		colon.setFont(new Font(16));
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
			if (scroll != null) {
				scroll.vvalueProperty().removeListener(scrollListener);
			}
			dateTimeField.requestFocus();
			if (datePicker.getValue() != null) {
				if (hours.getTime() == null || hours.getTime().trim().isEmpty()) {
					hours.setTime(dateTime.getHour());
				}

				if (minutes.getTime() == null || minutes.getTime().trim().isEmpty()) {
					minutes.setTime(dateTime.getMinute());
				}

				String newDateTimeStr = datePicker.getValue().toString() + " " + hours.getTime() + ":"
						+ minutes.getTime();
				updatePiker();
				dateTime = parseTime(newDateTimeStr);
				if (!checkBoundsRestriction(newDateTimeStr)) {
					return;
				}
				dateTimeField.textProperty().set(newDateTimeStr);
				dateTimeField.setText(newDateTimeStr);
				hours.setTime(hours.getTime());
				minutes.setTime(minutes.getTime());
				if (withDuration) {
					calendarControl.requestFocus();
				}
			}
		});
	}

	private void updatePiker() {
		if (datePicker != null && datePicker.getValue() != null) {
			datePicker.setValue(datePicker.getValue().plusDays(1));
			datePicker.setValue(datePicker.getValue().minusDays(1));
		} else if (datePicker != null && datePicker.getValue() == null) {
			datePicker.setValue(LocalDate.now());
			datePicker.setValue(null);
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
		hbox.getChildren().addAll(hours, colon, minutes);
		HBox.setMargin(hours, new Insets(5, 5, 5, 5));
		HBox.setMargin(minutes, new Insets(5, 5, 5, 5));
		hbox.setPrefWidth(60);
		hbox.setAlignment(Pos.CENTER);
		calendarControl.getChildren().remove(hbox);
		calendarControl.getChildren().add(hbox);
		pane.getChildren().clear();
		pane.getChildren().addAll(calendarControl);
	}

	private void createUIWithDuration() {
		HBox labelBox = new HBox();
		Font font = new Font(14);
		Label startTime = new Label("Start time");
		startTime.setFont(font);
		Label durationLabel = new Label("Duration");
		durationLabel.setFont(font);
		labelBox.getChildren().addAll(startTime, durationLabel);
		HBox.setMargin(durationLabel, new Insets(0, 0, 0, 62));
		VBox.setMargin(labelBox, new Insets(5, 0, 0, 15));

		HBox timeBox = new HBox();
		timeBox.getChildren().addAll(hours, colon, minutes);
		HBox.setMargin(hours, new Insets(5, 5, 5, 5));
		HBox.setMargin(minutes, new Insets(5, 5, 5, 5));
		timeBox.setPrefWidth(60);
		timeBox.setAlignment(Pos.CENTER_LEFT);
		VBox.setMargin(timeBox, new Insets(0, 0, 0, 10));

		calendarControl.getChildren().removeAll(labelBox, timeBox);
		calendarControl.getChildren().addAll(labelBox, timeBox);

		calendarControl.setPrefWidth(300);
		calendarControl.setPrefHeight(300);

		calendarControl.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().clear();
		pane.getChildren().addAll(calendarControl);
	}

	private LocalDateTime parseTime(String dateTime) {
		dateTime = dateTime.trim();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		try {
			return LocalDateTime.parse(dateTime, formatter);
		} catch (Exception e) {
			dateTime = dateTime + " " + defaultTime;
			try {
				return LocalDateTime.parse(dateTime, formatter);
			} catch (Exception e1) {
				return null;
			}
		}
	}

	public LocalDateTime getDateTimeValue() {
		return dateTime;
	}

	public String getStringValue() {
		return dateTimeField.getText();
	}

	public void setEditable(boolean b) {
		dateTimeField.setEditable(b);
	}

	public void setHeight(double height) {
		dateTimeField.setPrefHeight(height);
	}

	public void setPromptText(String promtText) {
		dateTimeField.setPromptText(promtText);
	}

	public void setValue(String localDate) {
		LocalDateTime localDateTime = parseTime(localDate);
		if (localDateTime != null) {
			datePicker.setValue(localDateTime.toLocalDate());
			dateTimeField.setText(DateTimeUtils.localDateTimeToStringHuman(localDateTime));
			hours.setTime(localDateTime.getHour());
			minutes.setTime(localDateTime.getMinute());
		} else {
			throw new IllegalArgumentException(
					"Invalid format of input value: format expected \"yyyy-MM-dd HH:mm\" or \"yyyy-MM-dd\" but recived value \""
							+ localDate
							+ "\". If you use format  \"yyyy-MM-dd\", defaultTime value might be not set.");
		}
	}

	public void setValue(LocalDate localDate) {
		datePicker.setValue(localDate);
		dateTimeField.setText(
				DateTimeUtils.localDateToStringISO(localDate) + " " + hours.getTime() + ":" + minutes.getTime());
	}

	public void setValue(LocalDateTime localDateTime) {
		if (localDateTime != null) {
			dateTime = localDateTime;
			datePicker.setValue(localDateTime.toLocalDate());
			dateTimeField.setText(DateTimeUtils.localDateTimeToStringHuman(localDateTime));
			hours.setTime(localDateTime.getHour());
			minutes.setTime(localDateTime.getMinute());
		} else {
			dateTime = null;
			datePicker.setValue(null);
			dateTimeField.setText("");
		}
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	public boolean isWithDuration() {
		return withDuration;
	}

	public String getDefaultTime() {
		return defaultTime;
	}

	public void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}

	public CustomTextField getDateTimeField() {
		return dateTimeField;
	}

}
