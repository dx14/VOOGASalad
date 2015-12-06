package editor.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import units.Level;
import units.Troop;
import editor.IView;
import editor.tabData.ITabData;
import editor.tabData.LevelsData;
import editor.tabData.PathsData;
import editor.tabData.TroopsData;

/**  Editor tab for Levels
 **/
public class LevelsTab extends Observable implements IView, ITab{
	private ScrollPane myTabView;
	private VBox myTabContent;
	private LevelsData myData;
	private List<Troop> myWave;
	private Button myAddButton;
	private Button myDeleteButton;
	private ListView<String> myPathEntriesList;
	private ObservableList<String> myEntriesToShow;
	private double mySpawnRate;
	private double mySpeed;
	private int myCurrentLevel = 1;

	/**  Constructor for editor tab for Levels
	 **/
	public LevelsTab(){
		myTabView = new ScrollPane();
		myTabContent = new VBox();
		myTabView.setContent(myTabContent);
		myWave = new ArrayList<Troop>();
	}
	
	private void initializeButtons() {
		HBox buttons = new HBox();
		myAddButton = new Button("Add New Level");
		myAddButton.setOnAction(e-> addLevel());
		myDeleteButton = new Button("Delete Level");
		myDeleteButton.setOnAction(e-> deleteLevel());
		buttons.getChildren().addAll(myAddButton, myDeleteButton);
		buttons.setAlignment(Pos.BOTTOM_RIGHT); 
		myTabContent.getChildren().add(buttons);
	}
	
	private void initializeLevels() {
		myEntriesToShow = FXCollections.observableArrayList();
		myPathEntriesList = new ListView<String>(myEntriesToShow);
		myPathEntriesList.setMinWidth(432);
		myTabContent.getChildren().add(myPathEntriesList);
	}
	
	private void addLevel() {
		Stage stage = new Stage();
		BorderPane levelPane = new BorderPane();
		Scene levelScene = new Scene(levelPane, 500, 300);
		HBox instructions = new HBox(150);
		ScrollPane troopOptions = new ScrollPane();
		ScrollPane chosenTroops = new ScrollPane();
		FlowPane possibleTroops = new FlowPane();
		FlowPane selectedTroops = new FlowPane();
		Label note = new Label("Choose troops to add to level:");
		Button finish = new Button("Finalize Level");
		finish.setOnAction(e -> finishLevel(stage));
		instructions.getChildren().addAll(note, finish);
		ArrayList<String> troopsList = new ArrayList<String>(TroopsData.myTroops.keySet());
		Collections.sort(troopsList);
		if (TroopsData.myTroops.keySet().size() == 0) {
			Label alert = new Label("You have not created any troops yet!");
			possibleTroops.getChildren().add(alert);
		}
		else {
			for (String troop : troopsList) {
				populateTroops(troop, possibleTroops, selectedTroops);
			}
		}
		troopOptions.setContent(possibleTroops);
		chosenTroops.setContent(selectedTroops);
		levelPane.setPadding(new Insets(20, 20, 20, 20));
		levelPane.setTop(instructions);
		levelPane.setCenter(chosenTroops);
		levelPane.setBottom(troopOptions);
		stage.setScene(levelScene);
		stage.show();
	}
	
	private void populateTroops(String troop, FlowPane options, FlowPane selected) {
		Image img = new Image(TroopsData.myTroops.get(troop).getStringAttribute("Image"));
		ImageView troopImage = new ImageView(img);
		troopImage.setFitWidth(20); 
		troopImage.setFitHeight(20);
		Button button = new Button(troop);
		button.setContentDisplay(ContentDisplay.TOP);
		button.setGraphic(troopImage);
		button.setOnAction(e -> addTroopToLevel(selected, troop));
		options.getChildren().add(button);
	}
	
	private void addTroopToLevel(FlowPane selectedTroops, String troop) {
		Troop selectedTroop = TroopsData.myTroops.get(troop);
		ImageView troopImg = new ImageView(new Image(selectedTroop.getStringAttribute("Image")));
		troopImg.setFitHeight(50);
		troopImg.setFitWidth(50);
		myWave.add(selectedTroop);
		selectedTroops.getChildren().add(troopImg);
	}
	
	private void deleteLevel() {
		String selected = myPathEntriesList.getSelectionModel().getSelectedItem();
		if (selected == null) {
			return;
		}
		myEntriesToShow.remove(selected);
		String selectedLevel = selected.split(" ")[1];
		myData.remove(selectedLevel);
	}
	
	private void finishLevel(Stage s) {
		List<String> myPaths = new ArrayList<String>();
		myPaths.addAll(PathsData.myPaths.keySet());
		Collections.sort(myPaths);
		Level l = new Level(Integer.toString(myCurrentLevel), new ArrayList<Troop>(myWave), myPaths, mySpawnRate, mySpeed);
		myEntriesToShow.add("Level "+l.getName() + " [" + myWave.size() + " troops]");
		myData.add(l.getName(), l);
		myCurrentLevel++;
		refresh();
		Alert finishAlert = new Alert(Alert.AlertType.CONFIRMATION, "Level "+l.getName()+" has been created!");
		finishAlert.show();
		s.close();
	}
	
	private void refresh() {
		mySpawnRate = 1.0;
		mySpeed = 1.0;
		myWave.clear();
	}
	
	@Override
	public Node getView() {
		return myTabView;
	}

	@Override
	public void setData(ITabData data) {
		myData = (LevelsData) data;
		initializeButtons();
		initializeLevels();
	}

}
