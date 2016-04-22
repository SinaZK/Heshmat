package WeaponBase;

import GameScene.GameSceneNormal;
import heshmat.MainActivity;

public class BulletFactory
{
	public MainActivity act;
	public GameSceneNormal mScene;
	
	public BulletFactory(MainActivity a, GameSceneNormal pSceneNormal) 
	{
		act = a;
		mScene = pSceneNormal;
	}
}
