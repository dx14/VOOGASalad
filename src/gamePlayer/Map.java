package gamePlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;

import controller.Controller;
import gameEngine.requests.BuyTowerRequest;
import interfaces.IRequest;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import units.Path;
import units.Point;
import units.Tower;
import units.Unit;

public class Map extends Observable implements IViewNode {
/*
 * Map.java is the actual game board where the game pieces are put into play. 
 */
	private Pane myPane;
	private MapUnit selectedUnit;
	private HashMap<Double, MapUnit> myImageMap;
	private HashMap<Double, ProgressBar> myHealthMap;
	private HashMap<Double, Circle> myTowerRangeMap;
	private Player myPlayer;
	private Controller myController;
	private boolean purchaseEnabled;
	private Unit potentialPurchase;
	private List<Line> myCurrentPaths;
	private ImageView background;
	private ImageView myImage;
	private Background bgImage;

	public Map(Controller c, Player p){
		this.myPlayer = p;
		this.myController = c;
		purchaseEnabled = false;
		//myPane = new Pane();

	}
	
	public Pane initialize(){
		myPane = new Pane();
		//myPane.setStyle("-fx-background-color: green;");
		Image grassBG = new Image(getClass().getClassLoader().getResourceAsStream("grass.jpg"));
		background = new ImageView(grassBG);
		myPane.getChildren().add(background);
		myPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if (purchaseEnabled){
					BuyTowerRequest buyRequest = new BuyTowerRequest((Tower) potentialPurchase, new Point(arg0.getSceneX(), arg0.getSceneY()));
					List<IRequest> requestSender = new ArrayList<IRequest>();
					requestSender.add(buyRequest);
					myController.update(requestSender);
					purchaseEnabled = false;
				}
			}

			
		});
		myImageMap = new HashMap<Double, MapUnit>();
		myHealthMap = new HashMap<Double, ProgressBar>();
		myTowerRangeMap = new HashMap<Double, Circle>();
		myCurrentPaths = new ArrayList<Line>();
		myImage = new ImageView();
		return myPane;
	}


	@Override
	public void setWidth(double width) {
		myPane.setPrefWidth(width);
	}

	@Override
	public void setHeight(double height) {
		myPane.setPrefHeight(height);
	}


	public void updateMap(List<Unit> units) {
		List<Double> onMap = new ArrayList<Double>();
		List<Double> removeUnits = new ArrayList<Double>();
		for (Unit unit : units) {
			if (!myImageMap.containsKey(unit.getAttribute("ID"))){
				MapUnit mapUnit = new MapUnit(new Image(unit.getStringAttribute("Image")),unit);
				mapUnit.setPreserveRatio(true);
				mapUnit.setFitHeight(35);
				ProgressBar health = mapUnit.getHealth();
				Circle towerRange = mapUnit.getPower();
				myImageMap.put(unit.getAttribute("ID"), mapUnit);
				myHealthMap.put(unit.getAttribute("ID"), health);
				myTowerRangeMap.put(unit.getAttribute("ID"), towerRange);
				myPane.getChildren().addAll(towerRange, mapUnit, health);
				mapUnit.setX(unit.getAttribute("X"));
				mapUnit.setY(unit.getAttribute("Y"));
				health.setLayoutX(unit.getAttribute("X"));
				health.setLayoutY(unit.getAttribute("Y")-10);
				health.setMaxWidth(40);
				if(unit.getStringAttribute("Type").equals("Tower")){
					towerRange.setCenterX(unit.getAttribute("X")+10);
					towerRange.setCenterY(unit.getAttribute("Y")+15);
					towerRange.setRadius(unit.getAttribute("Health"));
				}
				mapUnit.setOnMouseClicked(e->{
					selectedUnit = mapUnit;
					enableSelling(selectedUnit);
				});
				onMap.add(unit.getAttribute("ID"));
			} else if (myImageMap.containsKey(unit.getAttribute("ID"))) {
				ImageView imageview = myImageMap.get(unit.getAttribute("ID"));
				imageview.setX(unit.getAttribute("X"));
				imageview.setY(unit.getAttribute("Y"));
				ProgressBar health = myHealthMap.get(unit.getAttribute("ID"));
				health.setLayoutX(unit.getAttribute("X"));
				health.setLayoutY(unit.getAttribute("Y")-10);
				health.setProgress(unit.getAttribute("Health")/unit.getAttribute("MaxHealth"));
				Circle towerRange = myTowerRangeMap.get(unit.getAttribute("ID"));
				if(unit.getStringAttribute("Type").equals("Tower")){
					towerRange.setCenterX(unit.getAttribute("X")+10);
					towerRange.setCenterY(unit.getAttribute("Y")+15);
					towerRange.setRadius(unit.getAttribute("Health"));
				}
				//reset health value here
				onMap.add(unit.getAttribute("ID"));
			}
		}
		for (Entry<Double, MapUnit> entry : myImageMap.entrySet()){
			if (!onMap.contains(entry.getKey())){
				removeUnits.add(entry.getKey());
			}
		}
		for (double d : removeUnits) {
			myPane.getChildren().remove(myImageMap.get(d));
			myPane.getChildren().remove(myHealthMap.get(d));
			myPane.getChildren().remove(myTowerRangeMap.get(d));
			myImageMap.remove(d);
			myHealthMap.remove(d);
			myTowerRangeMap.remove(d);
		}
		
		if(selectedUnit!=null)
			updateSelected(selectedUnit);
		
	}
	
	public MapUnit getSelected(){
		return selectedUnit;
	}
	
	private void updateSelected(MapUnit myUnit) {
		myPlayer.updateSelected(myUnit);
	}

	public void setBackgroundMap(Image image) {
		myImage = new ImageView(image);
		//myCurrentBackground.getImage();
		System.out.println(myPane.getChildren().size());
		myPane.getChildren().remove(background);
        System.out.println(" selected");
		System.out.println(myPane.getChildren().size());
		myPane.getChildren().add(myImage);
		System.out.println("new size: " + myPane.getChildren().size());
	}

	private Line drawPath(Point startLoc, Point endLoc){
		Line path = new Line();
		path.setStartX(startLoc.getX()+25);
		path.setStartY(startLoc.getY()+25);
		path.setEndX(endLoc.getX()+25);
		path.setEndY(endLoc.getY()+25);
		path.setStrokeLineCap(StrokeLineCap.ROUND);
		path.setStrokeWidth(15);
		return path;
	}
	
	private void enableSelling(MapUnit mapUnit){
		myPlayer.enableSell(mapUnit);
	}

	public void enableTowerPurchase(Unit u) {
		purchaseEnabled = true;
		potentialPurchase = u;
	}
	
	
	public void showPaths(List<Path> pathsForLevel) {
		myPane.getChildren().removeAll(myCurrentPaths);
		myCurrentPaths.clear();
		for (Path p : pathsForLevel){
			List<Point> myPoints = p.getPoints();
			for (int i = 0; i < myPoints.size()-1; i++){
				myCurrentPaths.add(drawPath(myPoints.get(i),myPoints.get(i+1)));
			}
		}
		for (Line l : myCurrentPaths){
			l.setStroke(Color.AZURE);
		}
		myPane.getChildren().addAll(myCurrentPaths);
	}

}
