package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * An observer that is notified when the game state changes
 * 
 * @author Rhysa
 *
 */
public interface Observer {

	/**
	 * Notify that the game state has changed
	 */
	public void update();
}
