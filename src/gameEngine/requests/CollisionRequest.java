package gameEngine.requests;

import gameEngine.environments.RuntimeEnvironment;
import units.*;

public class CollisionRequest {
	private Unit myUnit1 ;
	private Unit myUnit2;
	
	public CollisionRequest (Unit unit1, Unit unit2) {
		super();
		myUnit1 = unit1;
		myUnit2 = unit2;
	}
	
	protected void execute(RuntimeEnvironment re) {

	}
}
