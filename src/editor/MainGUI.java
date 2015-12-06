package editor;

import java.util.Observer;

import editor.tabData.DataController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI {
	
	private final int width = 1125;
	private final int height = 458;
	private Stage myStage;
	private Scene myScene;
	private BorderPane myPane;
	private ToolBar myToolBar;
	private TabPane myTabs;
	private ScrollPane myAttributes;
	private ScrollPane myRules;
	private Pane myBoardParent;
	public static SubScene myBoard;
	private DataController myDataController;
	
	
	/**  Constructor for MainGUI object which opens the default GUI for editor
	 **/
	public MainGUI() {
		myStage = new Stage();

		initialize();
		
		myPane = new BorderPane();
		GridPane grid = new GridPane();
		VBox boardBox = new VBox(myBoard); // to keep the game board anchored while the window is resized
		VBox editBox = new VBox();
		grid.add(boardBox, 0, 0);
		grid.add(editBox, 1, 0);
		
		myPane.setTop(myToolBar);
		myPane.setCenter(grid);

		HBox rulesAttributes = new HBox(myRules, myAttributes);
		editBox.getChildren().addAll(myTabs, rulesAttributes);
		
		grid.prefWidthProperty().bind(myPane.widthProperty());
		boardBox.setPrefWidth(myBoard.getWidth());
		editBox.prefWidthProperty().bind(grid.widthProperty().subtract(boardBox.getWidth()));
		myTabs.prefWidthProperty().bind(editBox.widthProperty());
		rulesAttributes.prefWidthProperty().bind(editBox.widthProperty());
		myRules.prefWidthProperty().bind(rulesAttributes.widthProperty().divide(2));
		myAttributes.prefWidthProperty().bind(rulesAttributes.widthProperty().divide(2));

		// set up scene
		myScene = new Scene(myPane, boardBox.getPrefWidth() + 465.0, myPane.getPrefHeight());
		myStage.setScene(myScene);
		myStage.show();
		myStage.setMinWidth(boardBox.getPrefWidth());
		myStage.setMinHeight(myScene.getHeight()); // doesnt work right
	}
	
	private void initialize() {
	//	String[] toolOptions = {"File", "Edit", "Options", "Help"};
		// intialize game data holders
		myDataController = new DataController();
		
		// initialize tool bar
		Header tb = new Header();
		SaveGame sv = new SaveGame(myDataController);
		tb.getView().getItems().add(sv.getSaveButton());
		myToolBar = (ToolBar) tb.getView();
//		myToolBar.setPrefWidth(width);
		
		// initialize game board
//		myBoardParent = new Pane();
		GameBoard gb = new GameBoard(new Pane(), 675, 420);
		myBoard = (SubScene) gb.getView();
		
		// initialize tabs list
//		String[] tabOptions = {"Scenes", "Towers", "Bullets", "Troops", "Level", "Game"};
//		TabsList tl = new TabsList(tabOptions);
//		myTabs = (TabPane) tl.getView();
		TabsListController tabController = new TabsListController(myDataController);
		myTabs = (TabPane) tabController.getView();
		
		// initialize rules box
		RulesBox rb = new RulesBox(myDataController);
		myRules = rb.getView();
		
		// initialize attributes box
		AttributesBox ab = new AttributesBox(myDataController);
		myAttributes = ab.getView();
		
		TabsList tl = tabController.getTabsList();
		// Make observer/observable relationships
		tl.addObserver(ab);
		tl.addObserver(rb);
		ab.addObserver((Observer) tabController.getTab("Towers"));
	}
	
	private void setConstraints() {
//		myPane.getColumnConstraints().addAll(
//				new ColumnConstraints(675),
//				new ColumnConstraints(225),
//				new ColumnConstraints(225));
//		myPane.getRowConstraints().addAll(
//				new RowConstraints(38),
//				//new RowConstraints(38),
//				//new RowConstraints(252),
//				new RowConstraints(240));
	}
	
}
