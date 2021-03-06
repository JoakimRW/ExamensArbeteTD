package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 720;
		config.addIcon("interface/icons/turret-icon.png", Files.FileType.Internal);
		config.title = Game.TITLE;
		config.fullscreen = Game.isFullscreen;
		new LwjglApplication(new Game(), config);
	}
}
