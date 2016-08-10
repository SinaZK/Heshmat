package GoogleServices;

public interface IGoogleServices 
{
	public void signIn();
	public void signOut();
    public boolean isSignedIn();

    public void showAllLeaderBoards();

	public void submitLoopScore(int wave);
	public void showLoopScore();
    public void submitLineScore(int distance);
    public void showLineScore();

	public void makeToastShorts(String s);
	public void makeToastLong(String s);

	public void Countly(String name);

	public void disableAds();

	public void rateGame();
	
	public boolean haveVDO();
	public void playVDO();
	public void tapsellCheckVideo();
	public int getAward();
	public void consumeAward();
}
