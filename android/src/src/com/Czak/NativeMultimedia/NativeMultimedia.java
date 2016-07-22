package src.com.Czak.NativeMultimedia;

import android.media.MediaPlayer;

import com.czak.heshmat.AndroidLauncher;
import com.czak.heshmat.R;

import NativeMusic.INativeMultimediaInterface;
import heshmat.MainActivity;

/**
 * Created by sinazk on 7/22/16.
 * 03:16
 */
public class NativeMultimedia implements INativeMultimediaInterface
{
	AndroidLauncher act;

	MediaPlayer musicPlayer;

	boolean isMusicPlaying;

	public NativeMultimedia(AndroidLauncher a)
	{
		act = a;

		musicPlayer = new MediaPlayer();
		isMusicPlaying = false;
	}

	@Override
	public void playCinematicMusic()
	{
		isMusicPlaying = true;
		musicPlayer.stop();
		musicPlayer = MediaPlayer.create(act, R.raw.cinematic);
		musicPlayer.start();
	}

	@Override
	public void playBGMusic()
	{
		isMusicPlaying = true;
		musicPlayer.stop();
		musicPlayer = MediaPlayer.create(act, R.raw.bg);
		musicPlayer.start();
	}

	@Override
	public void playShootingModeMusic()
	{
		isMusicPlaying = true;
		musicPlayer.stop();
		musicPlayer = MediaPlayer.create(act, R.raw.shooting);
		musicPlayer.start();
	}

	@Override
	public void playDrivingModeMusic()
	{
		isMusicPlaying = true;
		musicPlayer.stop();
		musicPlayer = MediaPlayer.create(act, R.raw.driving);
		musicPlayer.start();
	}

	@Override
	public void onPause()
	{
		if(isMusicPlaying)
			musicPlayer.pause();
	}

	@Override
	public void onStop()
	{
		if(isMusicPlaying)
			musicPlayer.stop();
	}

	@Override
	public void onResume()
	{
		if(isMusicPlaying)
			musicPlayer.start();
	}
}
