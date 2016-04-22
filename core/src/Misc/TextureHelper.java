package Misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class TextureHelper 
{
	public static Texture loadTexture (String add, ArrayList<Texture> dispose)
	{
		Texture ret = new Texture(Gdx.files.internal(add));
		
		dispose.add(ret);
		
		return ret;
	}
}
