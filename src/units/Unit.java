package units;

import java.util.HashMap;
import java.util.Map;

public class Unit {
	protected String myName;
	protected double myHealth;
	protected double myCollisionDamage;
	protected String myImage;
//	protected Dimension myDimensions;
	protected Point myPosition;
	protected int myID;
	protected int myBuyCost;
	protected int mySellCost;
	protected Map<String, Double> myAttributes;
	protected Map<String, String> myStringAttributes;
	
	public Unit(String name, double health, double cd, String img, 
			Point p, int ID, int bc, int sc){
		this.myName = name;
		this.myHealth = health;
		this.myCollisionDamage = cd;
		this.myImage = img;
		this.myPosition = p;
		this.myID = ID;
		this.myBuyCost = bc;
		this.mySellCost = sc;
		
		myAttributes = new HashMap<String, Double>();
		myAttributes.put("Health", health);
		myAttributes.put("CollisionDamage", cd);
		myAttributes.put("ID", (double)ID);
		myAttributes.put("BuyCost", (double)bc);
		myAttributes.put("SellCost", (double)sc);
		myAttributes.put("X", (double)p.getX());
		myAttributes.put("Y", (double)p.getY());
		myStringAttributes = new HashMap<String, String>();
		myStringAttributes.put("Name", name);
		myStringAttributes.put("Image", img);
	}
	
	public Unit(){
		myAttributes = new HashMap<String, Double>();
		myStringAttributes = new HashMap<String, String>();
	}
	
	public double getAttribute(String attribute){
		return myAttributes.get(attribute);
	}
	
	public String getStringAttribute(String attribute){
		return myStringAttributes.get(attribute);
	}
	
	public void setAttribute(String attribute, double value){
		myAttributes.put(attribute, value);
	}
	
	public void setAttribute(String attribute, String value){
		myStringAttributes.put(attribute, value);
	}
	
	public String getImage() {
		return myImage;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return myName;
	}

	public int getCost() {
		// TODO Auto-generated method stub
		return myBuyCost;
	}


	public int getID(){
		return myID;
	}


	public Point getPoint() {
		// TODO Auto-generated method stub
		return myPosition;
	}
	
	public void setPoint(Point p){
		myPosition = p;
	}

}
