package gamePlayer;

import java.util.Observable;
import controller.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * A class used to display the menu bar with menu items for Player.
 * There are methods to initialize the menubar and populate it with menuitems in the pane.
 * <p>
 * This class implements ViewNode interface.
 *
 * @return      menubar
 * @see         Player
 * @see         IViewNode
 */

public class Menus extends Observable implements IViewNode {
	private MenuBar myMenuBar;
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private HelpMenu helpMenu;
	private Controller myController;
	private Player myPlayer;
	private Map myMap;

	public Menus(Controller c){
		this.myController = c;
	}

	/**
	 * Initializes all the menus in menubar.
	 *
	 * @return the menu bar
	 */
	public MenuBar initialize(){
		myMap = new Map(myController, myPlayer);
		myMenuBar = new MenuBar();
		fileMenu = new FileMenu();
		editMenu = new EditMenu(myMap);//, myPlayer, myController);
		helpMenu = new HelpMenu();
		populate();
		return myMenuBar;
	}

	private void populate(){		
		myMenuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
	}

	/* (non-Javadoc)
	 * @see gamePlayer.IViewNode#setWidth(double)
	 */
	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		myMenuBar.setPrefWidth(width);
	}

	/* (non-Javadoc)
	 * @see gamePlayer.IViewNode#setHeight(double)
	 */
	@Override
	public void setHeight(double height) {
		myMenuBar.setPrefHeight(height);		
	}

}