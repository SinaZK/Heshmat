package GooglePlay;


import GoogleServices.IGoogleServices;

public class DesktopGoogleServices implements IGoogleServices
{
	@Override
	public void signIn()
	{
//		System.out.println("DesktopGoogleServies: signIn()");
	}

	@Override
	public void signOut()
	{
//		System.out.println("DesktopGoogleServies: signOut()");
	}

	@Override
	public void submitScore(int wave)
	{

	}

	@Override
	public void showWaveScores()
	{

	}

	@Override
	public void rateGame()
	{
//		System.out.println("DesktopGoogleServices: rateGame()");
	}

	@Override
	public boolean isSignedIn()
	{
//		System.out.println("DesktopGoogleServies: isSignedIn()");
		return false;
	}

	@Override
	public void showTerrainScore(int terrainID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isHaveLuckyPatcher() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void makeToastShorts(String s) {
//		System.out.println("DesktopGoogleServices: Short Toast : " + s);
		
	}

	@Override
	public void makeToastLong(String s) {
//		System.out.println("DesktopGoogleServices: Long Toast : " + s);
		
	}

	@Override
	public void Countly(String name) {
//		System.out.println("DesktopGoogleServices: Countly " + name);
		
	}

	@Override
	public void disableAds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableAds()
	{

	}

	@Override
	public void changeLayoutToGDX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeLayoutToRawAndroid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showTapsell() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tapSellGivenPurchaseFlowDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tapSellIsSthPurchased() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int tapSellGetPurchasedID()
	{
		return 0;
	}

	@Override
	public void loadVDO()
	{

	}

	@Override
	public boolean haveVDO()
	{
		return false;
	}

	@Override
	public void playVDO()
	{

	}

	@Override
	public void tapsellCheckVideo()
	{

	}

	@Override
	public int getAward()
	{
		return -1;
	}

	@Override
	public void consumeAward()
	{

	}

}