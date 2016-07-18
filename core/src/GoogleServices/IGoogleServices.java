package GoogleServices;

public interface IGoogleServices 
{
	public void signIn();
	public void signOut();
	public void submitScore(int wave);
	public void showWaveScores();
	public void showTerrainScore(int terrainID);
	public boolean isSignedIn();

	public boolean isHaveLuckyPatcher();
	public void makeToastShorts(String s);
	public void makeToastLong(String s);

	public void Countly(String name);

	public void disableAds();
	public void enableAds();

	public void rateGame();
	
	public void changeLayoutToGDX();
	public void changeLayoutToRawAndroid();
	
	public void showTapsell();
	public void tapSellGivenPurchaseFlowDone();
	public boolean tapSellIsSthPurchased();
	public int tapSellGetPurchasedID();

	public void loadVDO();//not used
	public boolean haveVDO();
	public void playVDO();
	public void tapsellCheckVideo();
	public int getAward();
	public void consumeAward();
}
