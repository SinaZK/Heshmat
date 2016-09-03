package game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import BazarPurchase.PurchaseHelperDesktop;
import GooglePlay.DesktopGoogleServices;
import GooglePlay.DesktopNativeMultimedia;
import heshmat.MainActivity;

public class DesktopLauncher {
	
	private static PurchaseHelperDesktop purchase;
	
	public static void main (String[] arg)
	{
		purchase = new PurchaseHelperDesktop();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 800;
		config.height = 480;
		
//		config.width = 1280;
//		config.height = 720;
		
//		config.width = 960;
//		config.height = 540;
		
		config.width = 1600;config.height = 900;
		
//		config.width = 320;
//		config.height = 240;
		
		new LwjglApplication(new MainActivity(new DesktopGoogleServices(), purchase, new DesktopNativeMultimedia()), config);
	}
}
