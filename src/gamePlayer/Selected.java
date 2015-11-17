package gamePlayer;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Selected {

	private View myView;
	private VBox myVBox;
	
	private Text unit;
	private Text health;
	private Text sell;
	
	public Selected(View view){
		this.myView = view;
		myVBox = new VBox();
		
		unit = new Text("Unit: ");
		health = new Text("Health: ");
		sell = new Text ("Sell for: ");
		
		myVBox.getChildren().addAll(unit, health, sell);
	}
	
	public void update(MapUnit myUnit){
		unit.setText("Unit: " + myUnit.getUnit().getStringAttribute("Name"));
		health.setText("Health: " + myUnit.getUnit().getAttribute("Health") 
				+ "/" + myUnit.getUnit().getAttribute("MaxHealth"));
		sell.setText("Sell for: " + (int) myUnit.getUnit().getAttribute("SellCost"));
	}
	
	public Node getDisplay(){
		return myVBox;
	}
	
}