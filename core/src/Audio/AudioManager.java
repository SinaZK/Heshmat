package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

import heshmat.MainActivity;

public class AudioManager 
{
	MainActivity act;
	public AudioManager(MainActivity a) 
	{
		act = a;
	}
	
	public Sound neckBreakSound, carCrashSound, buttonClickSound, buyItemSound, coinCollectSound, fuelCollectSound;
	
	ArrayList<Sound> allSounds = new ArrayList<Sound>();
	
	public void load()
	{
		neckBreakSound = Gdx.audio.newSound(Gdx.files.internal("sfx/crack.ogg"));
		carCrashSound = Gdx.audio.newSound(Gdx.files.internal("sfx/crash.ogg"));
		buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sfx/click.ogg"));
		buyItemSound = Gdx.audio.newSound(Gdx.files.internal("sfx/purchase.ogg"));
		coinCollectSound = Gdx.audio.newSound(Gdx.files.internal("sfx/coin.ogg"));
		fuelCollectSound = Gdx.audio.newSound(Gdx.files.internal("sfx/fuel.ogg"));
	
		allSounds.add(neckBreakSound);
		allSounds.add(carCrashSound);
		allSounds.add(buttonClickSound);
		allSounds.add(buyItemSound);
		allSounds.add(coinCollectSound);
		allSounds.add(fuelCollectSound);
		
	}
	
	public void dispose()
	{
		for(int i = 0;i < allSounds.size();i++)
			allSounds.get(i).dispose();
		
	}
	
	public Sound carEngSound; 
	public void loadCarSound()
	{
		if(carEngSound != null)
			carEngSound.dispose();
		
	}
	
	public long soundID;
	public void playCarSound()
	{
	}
}
