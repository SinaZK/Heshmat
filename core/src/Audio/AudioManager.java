package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

import Misc.Log;
import heshmat.MainActivity;

public class AudioManager 
{
	public static  boolean IS_MUTE = false;

	MainActivity act;
	public AudioManager(MainActivity a) 
	{
		act = a;
	}
	
	public Sound buttonClickSound, buyItemSound;
	public Sound crashSound, enemyHit, reloadSound;
	public Music bgMusic, drivingMusic, shootingMusic, cinematicMusic;
	
	ArrayList<Sound> allSounds = new ArrayList<Sound>();
	ArrayList<Music> allMusics = new ArrayList<Music>();

	public void load()
	{
		loadSound();
		loadMusic();

		setVolume();
	}

	public void loadSound()
	{
		if(IS_MUTE)
			return;

		buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sfx/click.ogg"));
		buyItemSound = Gdx.audio.newSound(Gdx.files.internal("sfx/purchase.ogg"));
		crashSound = Gdx.audio.newSound(Gdx.files.internal("sfx/crash.ogg"));
		enemyHit = Gdx.audio.newSound(Gdx.files.internal("sfx/enemyhit.ogg"));
		reloadSound = Gdx.audio.newSound(Gdx.files.internal("sfx/reload.mp3"));

		allSounds.add(buttonClickSound);
		allSounds.add(buyItemSound);
		allSounds.add(crashSound);
		allSounds.add(enemyHit);
		allSounds.add(reloadSound);
	}

	public void loadMusic()
	{
		if(IS_MUTE)
			return;

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/music/bg.ogg"));
		drivingMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/music/driving.ogg"));
		drivingMusic.setVolume(0.1f);
		shootingMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/music/shooting.ogg"));
		cinematicMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/music/cinematic.ogg"));

		allMusics.add(bgMusic);
		allMusics.add(drivingMusic);
		allMusics.add(shootingMusic);
	}

	public void playCinematicMusic()
	{
		if(IS_MUTE)
			return;

		bgMusic.stop();
		drivingMusic.stop();
		shootingMusic.stop();
		cinematicMusic.play();
	}

	public void playBgMusic()
	{
		if(IS_MUTE)
			return;

		bgMusic.play();
		drivingMusic.stop();
		shootingMusic.stop();
		cinematicMusic.stop();
	}

	public void playDrivingMusic()
	{
		if(IS_MUTE)
			return;

		bgMusic.stop();
		drivingMusic.play();
		shootingMusic.stop();
		cinematicMusic.stop();
	}

	public void playShootingMusic()
	{
		if(IS_MUTE)
			return;

		bgMusic.stop();
		drivingMusic.stop();
		shootingMusic.play();
		cinematicMusic.stop();
	}

	public void playEnemyHit()
	{
		if(IS_MUTE)
			return;

		return;

//		if(!act.settingStatData.isSoundOn)
//			return;
//
//		enemyHit.play();
	}

	public void playReload()
	{
		if(IS_MUTE)
			return;

		if(!act.settingStatData.isSoundOn)
			return;

		reloadSound.play();
	}

	public void playClick()
	{
		if(IS_MUTE)
			return;

		if(!act.settingStatData.isSoundOn)
			return;

		buttonClickSound.play();
	}

	public void playPurchase()
	{
		if(IS_MUTE)
			return;

		if(!act.settingStatData.isSoundOn)
			return;

		buyItemSound.play();
	}

	public void playCrash()
	{
		if(IS_MUTE)
			return;

		if(!act.settingStatData.isSoundOn)
			return;

		crashSound.play();
	}
	
	public void playCarSound(Sound CarSound, long soundID, float speed, float maxSpeed)
	{
		if(IS_MUTE)
			return;

		if(!act.settingStatData.isSoundOn)
			return;

		float minPitch = 0.8f;
		float maxPitch = 1.5f;

		float pitch = minPitch + (speed / maxSpeed) * (maxPitch - minPitch);
		CarSound.setPitch(soundID, pitch);
	}

	public void pauseOnGameScene()
	{
		playBgMusic();
	}

	public void setVolume()
	{
		for(int i = 0;i < allMusics.size();i++)
			if(act.settingStatData.isMusicOn)
				allMusics.get(i).setVolume(1);
			else
				allMusics.get(i).setVolume(0);
	}

	public void playSound(Sound sound)
	{
		if(act.settingStatData.isSoundOn)
			sound.play();
	}

	public void dispose()
	{
		for(int i = 0;i < allSounds.size();i++)
			allSounds.get(i).dispose();

		for(int i = 0;i < allMusics.size();i++)
			allMusics.get(i).dispose();
	}
}
