package Misc;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CameraHelper 
{
	
	public static float getXMin(OrthographicCamera cam, float zoom)
	{
		return cam.position.x - (cam.viewportWidth / 2) * (zoom);
	}
	
	public static float getXMax(OrthographicCamera cam, float zoom)
	{
		return cam.position.x + (cam.viewportWidth / 2) * (zoom);
	}
	
	public static float getXMin(OrthographicCamera cam)
	{
		return cam.position.x - (cam.viewportWidth / 2) * (cam.zoom);
	}
	
	public static float getXMax(OrthographicCamera cam)
	{
		return cam.position.x + (cam.viewportWidth / 2) * (cam.zoom);
	}
	
	public static float getYMax(OrthographicCamera cam)
	{
		return cam.position.y + (cam.viewportHeight / 2) * (cam.zoom);
	}
	
	public static float getYMin(OrthographicCamera cam)
	{
		return cam.position.y - (cam.viewportHeight / 2) * (cam.zoom);
	}
	
	public static void chaseCamera(OrthographicCamera cam, Actor cameraBitch)
	{
		float x = cam.position.x;
		float y = cam.position.y;
		
		float toX = cameraBitch.getX() + cameraBitch.getWidth() / 2;
		float toY = cameraBitch.getY() + cameraBitch.getHeight() / 2;
		
		cam.translate(-(x - toX), -(y - toY));
	}
	
	public static void chaseCamera(OrthographicCamera cam, float toX, float toY)
	{
		float x = cam.position.x;
		float y = cam.position.y;
		
		cam.translate(-(x - toX), -(y - toY));
	}
}
