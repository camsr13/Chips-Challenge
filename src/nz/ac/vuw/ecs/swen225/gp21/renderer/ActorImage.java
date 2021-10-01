package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics2D;

import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

abstract class ActorImage {
	abstract void drawActor(Graphics2D g, Location chapPos, int tileSize);
}
