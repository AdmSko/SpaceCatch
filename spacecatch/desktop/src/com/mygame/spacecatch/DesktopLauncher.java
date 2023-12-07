package com.mygame.spacecatch;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygame.spacecatch.SpaceCatch;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(SpaceCatch.WIDTH,SpaceCatch.HEIGHT);
		config.setResizable(false);
		config.setTitle("SpaceCatch");
		new Lwjgl3Application(new SpaceCatch(), config);
	}
}
