package gameEngine.requests;

import gameEngine.environments.RuntimeEnvironment;
import units.*;

/**
 * request for collision
 * @author Wanning
 *
 */
public class CollisionRequest extends Request {
	private Unit myUnit1 ;
	private Unit myUnit2;
	
	/**
	 * constructor
	 * @param unit1
	 * @param unit2
	 */
	public CollisionRequest (Unit unit1, Unit unit2) {
		super();
		myUnit1 = unit1;
		myUnit2 = unit2;
	}
	
	/**
	 * execute the collision logic
	 */
	@Override
	public void execute(RuntimeEnvironment re) {
		if(myUnit1.getFaction() != myUnit2.getFaction()){
			myUnit1.setHealth(myUnit1.getHealth() - myUnit2.getAttribute("CollisionDamage"));
			myUnit2.setHealth(myUnit2.getHealth() - myUnit1.getAttribute("CollisionDamage"));
			if (myUnit1.getHealth()<= 0){
				re.removeUnit(myUnit1.getID());
				re.getUnits().remove(myUnit1);
			}if (myUnit2.getHealth()<=0){
				re.removeUnit(myUnit2.getID());
				re.getUnits().remove(myUnit2);
			}
		}
	}
}
