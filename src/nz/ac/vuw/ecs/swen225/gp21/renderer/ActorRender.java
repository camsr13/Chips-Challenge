package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

/**
 * Handles the rendering of the non player enemies and their movements
 * Behaves like a modifies JLabel component
 * @author Jac Clarke
 *
 */
class ActorRender extends Animatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6775023084165233366L;
	
	
	/**
	 * Renders the individual actors on top of the board pane.
	 * Each actor checks for updates individually
	 * @param actor
	 */
	protected ActorRender(Actor actor, int tileSize) {
		
	}

	@Override
	void animate(int dir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void loadImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Location getBoardLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
