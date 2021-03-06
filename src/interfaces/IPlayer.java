package interfaces;
import java.util.HashMap;
import java.util.List;

import units.PlayerInfo;
import units.Unit;

public interface IPlayer {
	public void populate(HashMap<String, List<Unit>> store);
	/*passes the front end a list of all the objects that can be purchased*/
	
	public void updateMap(List<Unit> units);
	/*passes the player all of the objects that exist in the back end to 
	update the front end. This allows the front end to display all the objects
	that exist in the back end*/
	
	public void updateUserInfo(PlayerInfo player);
	/*passes the player the user's current information like gold, health, 
	 etc.*/

	public void showWin();
	/*tells the player a win condition has been met*/

	public void showLose();
	/*tells the player a lose condition has been met*/
}
