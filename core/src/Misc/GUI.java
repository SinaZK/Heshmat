package Misc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GUI 
{
	public static void draw(SpriteBatch spriteBatch, Sprite s)
	{
		spriteBatch.draw(s, s.getX(), s.getY(), s.getOriginX(), s.getOriginY(),
				s.getWidth(), s.getHeight(), s.getScaleX(), s.getScaleY(), s.getRotation());
	}
	
	public static void draw(Batch spriteBatch, Sprite s)
	{
//		Log.e("Tag", "Drawing rot = " + s.getRotation());
		spriteBatch.draw(s, s.getX(), s.getY(), s.getOriginX(), s.getOriginY(),
				s.getWidth(), s.getHeight(), s.getScaleX(), s.getScaleY(), s.getRotation());
	}
	
	public static void draw(PolygonSpriteBatch spriteBatch, PolygonRegion s)
	{
		spriteBatch.draw(s, 0, 0, 0, 0,
				100, 100, 1, 1, 0);
	}
	
	public static void setTransparency(Sprite s, float tr)
	{
		s.setColor(s.getColor().r, s.getColor().g, s.getColor().b, tr);
	}

	public static Vector2 convertTouchPos(Camera camera, float tX, float tY)
	{
		Vector3 ret = new Vector3(tX, tY, 0);
		camera.unproject(ret);
		return new Vector2(ret.x, ret.y);
	}
}
