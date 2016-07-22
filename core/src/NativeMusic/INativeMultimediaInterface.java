package NativeMusic;

/**
 * Created by sinazk on 7/22/16.
 * 03:07
 */
public interface INativeMultimediaInterface
{
	public void playCinematicMusic();
	public void playBGMusic();
	public void playShootingModeMusic();
	public void playDrivingModeMusic();

	public void onPause();
	public void onStop();
	public void onResume();
}
