package GooglePlay;

import Misc.Log;
import NativeMusic.INativeMultimediaInterface;

/**
 * Created by sinazk on 7/22/16.
 * 03:52
 */
public class DesktopNativeMultimedia implements INativeMultimediaInterface
{
	@Override
	public void playCinematicMusic()
	{
		Log.e("DesktopMedia", "playing cinematic Music");
	}

	@Override
	public void playBGMusic()
	{
		Log.e("DesktopMedia", "playing BG Music");
	}

	@Override
	public void playShootingModeMusic()
	{
		Log.e("DesktopMedia", "playing ShootingMode Music");
	}

	@Override
	public void playDrivingModeMusic()
	{
		Log.e("DesktopMedia", "playing DrivingMode Music");
	}

	@Override
	public void onPause()
	{

	}

	@Override
	public void onStop()
	{

	}

	@Override
	public void onResume()
	{

	}
}
