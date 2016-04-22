package Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import heshmat.MainActivity;

public class BaseScene extends Stage
{
	public ArrayList<Texture> disposeTextureArray = new ArrayList<Texture>();
	public MainActivity act;
	public float DX;
	public float DY;
	
	public BaseScene(MainActivity act) 
	{
		super();
		this.act = act;
		Gdx.input.setInputProcessor(this);
	}
	
	public BaseScene(MainActivity act, Viewport v)
	{
		super(v);
		this.act = act;
		Gdx.input.setInputProcessor(this);
	}
	
	
	public void loadResources(){}
	
	public void create(){}
	
	public void attachChild(Actor a){addActor(a);}

	public void addToDisposeTextureArray(Texture t)
	{
		disposeTextureArray.add(t);
	}
	
	public void run()
	{
		act(Gdx.graphics.getDeltaTime());
		draw();
	}
	
	@Override
	public void dispose() 
	{
		for(int i = 0;i < disposeTextureArray.size();i++)
			disposeTextureArray.get(i).dispose();
		
		super.dispose();
	}
	
}
