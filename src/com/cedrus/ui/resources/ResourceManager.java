package com.cedrus.ui.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ResourceManager {

	public static final ImageView NULL_IMAGE_VIEW = new ImageView(new File("").toURI().toString());

	public static final Image METRIC_COLLECTION_MODE_ON = new Image(new File("./resources/auto-collection-on.png").toURI().toString());

	public static final Image METRIC_COLLECTION_MODE_OFF = new Image(new File("./resources/auto-collection-off.png").toURI().toString());

	public static final Image CALENDAR = new Image(
			new File("./resources/calendar-2.png").toURI().toString());

	public static final Image SEARCH = new Image(
			new File("./resources/search.png").toURI().toString());

}
