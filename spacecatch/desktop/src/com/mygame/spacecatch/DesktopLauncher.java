package com.mygame.spacecatch;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Class that works as a desktop launcher of the game, required by LibGdx as a framework
 */
public class DesktopLauncher {
	/**
	 * main method for desktop usage, creates game object
	 * @param arg array of strings of Java string class
	 */
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(SpaceCatch.WIDTH,SpaceCatch.HEIGHT);
		config.setResizable(false);
		config.setTitle("SpaceCatch");
		new Lwjgl3Application(new SpaceCatch(), config);
	}
}
