package com.cedrus.ui.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

import java.io.File;

public class ResourceManager {
	
	public static final File LOAD_LOGO = new File("./resources/MCT_Logo.png");

	public static final String X_BUTTON = "./resources/x.png";
	
	public static final ImageView NULL_IMAGE_VIEW = new ImageView(new File("").toURI().toString());

	public static final Image CONNECTING_MODE = new Image(
			new File("./resources/connection.gif").toURI().toString());
	
	public static final Image OFFLINE_MODE = new Image(
			new File("./resources/offline-mode.png").toURI().toString());

	public static final Image ONLINE_MODE = new Image(
			new File("./resources/online-mode.png").toURI().toString());
	
	public static final Image NULL_IMAGE = new Image(
			new File("./resources/null_image.png").toURI().toString());

	public static final Image YELLOW_INDICATOR = new Image(
			new File("./resources/status-settings-required.png").toURI().toString());

	public static final Image GREEN_INDICATOR = new Image(
			new File("./resources/status-committed.png").toURI().toString());

	public static final Image GREY_INDICATOR = new Image(
			new File("./resources/status-inactive.png").toURI().toString());

	public static final Image TICK_GREEN = new Image(
			new File("./resources/tick-green.png").toURI().toString());
	
	public static final Image GRAY_CONNECTOR_IMAGE = new Image(
			new File("./resources/connector-gray.png").toURI().toString());
	public static final Image GREEN_CONNECTOR_IMAGE = new Image(
			new File("./resources/connector-green.png").toURI().toString());
	public static final Image RED_CONNECTOR_IMAGE = new Image(
			new File("./resources/connector-red.png").toURI().toString());

	public static final Image AUTO_RETR_FAIL_INDICATOR = new Image(new File("./resources/auto-error.png").toURI().toString());

	public static final String EDIT_PERIOD = "./resources/edit_period.png";

	public static final String EDIT_PERIOD_HOVERED = "./resources/edit_period_hovered.png";

	public static final String DELETE_PERIOD_HOVERED = "./resources/delete_period_hovered.png";
	
	public static final String DELETE_PERIOD_NORMAL = "./resources/delete_period_normal.png";	

	public static final String RETRIEVE_BUTTON = "./resources/retrieve.png";

	public static final String RETRIEVE_ALL_ICON = "./resources/retrieve_all.png";

	public static final Image RETRIEVE_ALL_IMAGE = new Image(new File(RETRIEVE_ALL_ICON).toURI().toString());
	
	public static final Image REFRESH = new Image(new File("./resources/refresh.png").toURI().toString());

	public static final String PREGRESS_INDICATOR = "./resources/retrieve_indicator.gif";

	public static final Image PROGRESS_INDICATOR_IMAGE = new Image(new File("./resources/retrieve_indicator.gif").toURI().toString());
	
	public static final Image PERIOD_ACTION_INDICATOR_IMAGE = new Image(new File("./resources/period-action-progress.gif").toURI().toString());

	public static final Image ADD = new Image(new File("./resources/add.png").toURI().toString());
	
	public static final Image GET_PERIODS_WHITE = new Image(new File("./resources/get-periods-arrow-white.png").toURI().toString());

	public static final Image GET_PERIODS_BLUE = new Image(new File("./resources/get-periods-arrow-blue.png").toURI().toString());

	public static final Image CHANGE_PERIODS_SETTINGS = new Image(new File("./resources/periods-settings-view.png").toURI().toString());
	
	public static final Image COMMITED = new Image(new File("./resources/status-committed.png").toURI().toString());
	public static final Image METRIC_COLLECTION_MODE_ON = new Image(new File("./resources/auto-collection-on.png").toURI().toString());
	
	public static final Image METRIC_COLLECTION_MODE_OFF = new Image(new File("./resources/auto-collection-off.png").toURI().toString());
	

	public static final ImageView SAVETOABILITONMENUITEMIMAGE = new ImageView(
			new File("./resources/save-to-abiliton-menu-item.png").toURI().toString());
	
	public static final ImageView MINIMEZE_IMAGE = new ImageView(
			new File("./resources/minimize.png").toURI().toString());
	
	public static final ImageView MAXIMIZE_IMAGE = new ImageView(
			new File("./resources/maximize.png").toURI().toString());

	public static final ImageView ACTIVATEMENUITEMIMAGE = new ImageView(
			new File("./resources/activate-menu-item.png").toURI().toString());

	public static final ImageView SETTINGSMENUITEMIMAGE = new ImageView(
			new File("./resources/connector-settings-menu-item.png").toURI().toString());

	public static final Image SETTINGSMENUITEM = new Image(
			new File("./resources/connector-settings-menu-item.png").toURI().toString());
	
	public static final Image CALENDAR = new Image(
			new File("./resources/calendar-2.png").toURI().toString());
	
	public static final ImageView DONOTCOLLECTMENUITEMIMAGE = new ImageView(
			new File("./resources/do-not-collect-menu-item.png").toURI().toString());

	public static final ImageView RELOADMENUITEMIMAGE = new ImageView(
			new File("./resources/reload-menu-item.png").toURI().toString());

	public static final ImageView EDIT_MENU_ITEM = new ImageView(
			new File("./resources/edit-menu-item.png").toURI().toString());

	public static final ImageView DELETE_MENU_ITEM = new ImageView(
			new File("./resources/delete-menu-item.png").toURI().toString());

	public static final ImageView EXPORT_MENU_ITEM = new ImageView(
			new File("./resources/export-menu-item.png").toURI().toString());

	public static final ImageView ARCHIVE_MENU_ITEM = new ImageView(
			new File("./resources/archive-menu-item.png").toURI().toString());
	
	public static final ImageView REOPENMENUITEMIMAGE = new ImageView(
			new File("./resources/reopen-menu-item.png").toURI().toString());

	public static final Image COMMIT_REQUIRED_IMAGE = new Image(new File("./resources/status-settings-required.png").toURI().toString());
	
	public static final String CANCEL_IMAGE = "./resources/cancel-add-period.png";

    public static final Image INFO = new Image(new File("./resources/info.png").toURI().toString());
    
    public static final Image LINK = new Image(new File("./resources/link.png").toURI().toString());
    
    public static final Image OPEN_HELP = new Image(new File("./resources/help-image.png").toURI().toString());
    
    public static final Image OPEN_HELP_TRANPARENT = new Image(new File("./resources/help-image-transparent.png").toURI().toString());

    public static final Image GEAR = new Image(new File("./resources/gear-2.png").toURI().toString());
    
    public static final String PROGRESS_INDICATOR = "./resources/preloader.gif";

	public static final Image ACTIVATE = new Image(new File("./resources/activate.png").toURI().toString());
	public static final String THREE_DOTS = "./resources/three-dot.png";
	public static final Image TIME = new Image(
			new File("./resources/time-copy.png").toURI().toString());
	public static final Image TRANSPARENT_PROCESS_INDICATOR = new Image(
			new File("./resources/transparent_process_indicator.gif").toURI().toString());
	public static final Image OVERLAP = new Image(new File("./resources/overlap.png").toURI().toString());
	public static final Image REMOVE = new Image(new File("./resources/remove.png").toURI().toString());

	public static final String RETRIEVE_SVG_PATH = "M0,14v4h16v-4H0z M14,17h-4v-2h4V17z M8,11.4L2.8,6.2l1.4-1.4L7,7.6V0h2v7.7l2.8-2.8l1.4,1.4L8,11.4z";
	public static final String CLOUD_SVG_PATH = "M12.9,4.7c-0.5-2.3-2.5-4-4.9-4c-1.9,0-3.6,1.1-4.4,2.7C1.6,3.6,0,5.3,0,7.3c0,2.2,1.8,4,4,4h8.7" +
			"c1.8,0,3.3-1.5,3.3-3.3C16,6.2,14.6,4.8,12.9,4.7z";
	public static final String REOPEN_SVG_PATH = "M3,6 L7,3 L7,9 L3,6 Z M10,12 L10,10 C10.8284271,10 11.5,9.32842712 11.5,8.5 C11.5,7.67157288 10.8284271,7 10,7 L10,5 C11.9329966,5 13.5,6.56700338 13.5,8.5 C13.5,10.4329966 11.9329966,12 10,12 Z M7,5 L10,5 L10,7 L7,7 L7,5 Z M9,10 L10,10 L10,12 L9,12 L9,10 Z";
	public static final String CROSS_LINE_SVG_PATH = "M8.91666667,7.08333333 L8.91666667,2.5 L7.08333333,2.5 L7.08333333,7.08333333 L2.5,7.08333333 L2.5,8.91666667 L7.08333333,8.91666667 L7.08333333,13.5 L8.91666667,13.5 L8.91666667,8.91666667 L13.5,8.91666667 L13.5,7.08333333 L8.91666667,7.08333333 Z";
	public static final String SERVER_SET_SVG_PATH = "M134.871,445.144 L131.935,440.112 C131.571,439.488 130.721,439 130,439 L119.25,439 C118.51,439 117.687,439.527 117.379,440.199 L115.046,445.292 C115.045,445.293 115.046,445.295 115.045,445.297 C115.018,445.359 115,445.428 115,445.5 L115,448 C115,449.654 116.346,451 118,451 L132,451 C133.654,451 135,449.654 135,448 L135,445.597 C135.007,445.565 135.02,445.534 135.02,445.5 C135.02,445.361 134.963,445.234 134.871,445.144 Z M131,449 L128,449 C127.724,449 127.5,448.776 127.5,448.5 C127.5,448.224 127.724,448 128,448 L131,448 C131.276,448 131.5,448.224 131.5,448.5 C131.5,448.776 131.276,449 131,449 Z M116.279,445 L118.287,440.617 C118.436,440.294 118.895,440 119.25,440 L130,440 C130.373,440 130.884,440.294 131.072,440.615 L133.63,445 L116.279,445 Z";

	private ResourceManager() {}

	public static SVGPath getSVGPath(String pathString, String rgbaColor) {
		SVGPath svgPath = new SVGPath();
		svgPath.setContent(pathString);
		svgPath.setFill(Paint.valueOf(rgbaColor));
		return svgPath;
	}
}
