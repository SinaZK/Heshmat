package Misc;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
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
		float y = cam.position.y - 70;
		
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

	public static Vector2 rotatePoint(float cx,float cy,float angle,Vector2 p)
	{
		angle = (float)Math.toRadians(angle);
		float s = (float)Math.sin(angle);
		float c = (float)Math.cos(angle);

		// translate point back to origin:
		p.x -= cx;
		p.y -= cy;

		// rotate point
		float xNew = p.x * c - p.y * s;
		float yNew = p.x * s + p.y * c;

		// translate point back:
		p.x = xNew + cx;
		p.y = yNew + cy;
		return p;
	}

	public static float distance(float x1, float y1, float x2, float y2)
	{
		return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
}
