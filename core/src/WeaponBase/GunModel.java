package WeaponBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import DataStore.CarStatData;
import DataStore.GunStatData;
import Entity.GunUpgradeButtons.FireRateUpgradeButton;
import Entity.GunUpgradeButtons.GunUpgradeButton;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Scene.Garage.GunSelectorTab;
import Sorter.GunSorter;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/14/16.
 * 06:49
 * just for showing the gun and selecting in gunSelectorTab in GarageScene
 */
public class GunModel
{
	public static String FIRE_RATE_STRING = "FIRE_RATE";
	public static String DAMAGE_STRING = "DAMAGE";
	public static String EOF = "END";

	MainActivity act;
	GunSelectorTab gunSelectorTab;
	public Sprite showSprite;
	public long price;

	public ArrayList <GunUpgradeButton> upgradeButtons = new ArrayList<GunUpgradeButton>();

	public GunModel(GunSelectorTab gunSelectorTab, int id)
	{
		this.gunSelectorTab = gunSelectorTab;
		this.act = gunSelectorTab.act;

		loadResources("gfx/weapons/" + GunSorter.gunPos[id] + "/", gunSelectorTab.garageScene.disposeTextureArray);
	}

	public void setPosition(float x, float y)
	{
		showSprite.setPosition(x, y);
	}

	public void setSize(float w, float h)
	{
		showSprite.setSize(w, h);
	}

	public void draw(Batch batch)
	{
		showSprite.draw(batch);
	}

	public void loadResources(String path, ArrayList<Texture> disposalArray)
	{
		ArrayList<Texture> use;
		if(act.sceneManager.gameScene == null)
			use = disposalArray;
		else
			use = act.sceneManager.gameScene.disposeTextureArray;

		showSprite = new Sprite(TextureHelper.loadTexture(path + "show.png", use));

		FileHandle f = Gdx.files.internal(path + "gun.gun");
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		try {

			String [] val;
			String in;

			in = dis.readLine();//price
			price = Long.valueOf(BodyStrings.getPartOf(in, 1));
			dis.readLine();//size
			dis.readLine();//HumanPos
			dis.readLine();//origin
			dis.readLine();//shootingPos
			dis.readLine();//rateOfFire
			dis.readLine();//physic

			dis.readLine();//blank line
			dis.readLine();//UPGRADES

			while (true)
			{
				in = dis.readLine();

				if(in.equals(EOF))
					break;

				if(in.equals(FIRE_RATE_STRING))
				{
					upgradeButtons.add(new FireRateUpgradeButton(gunSelectorTab));
				}

//				if(in.equals(DAMAGE_STRING))
//				{
//				}
			}

		}
		catch (IOException e)
		{
			Log.e("Tag", e.toString());
		}//catch

	}

	public void initUpgradeButtons(GunStatData gunStatData)
	{
		float startX = gunSelectorTab.DX + 80;
		float startY = gunSelectorTab.DY + 40;
		float width = 50;
		float height = 50;
		float padding = 20;

		for(int i = 0;i < upgradeButtons.size();i++)
		{
			upgradeButtons.get(i).setGunStatData(gunStatData);
			upgradeButtons.get(i).setPosition(startX + (width + padding) * (i - 1), startY);
			upgradeButtons.get(i).setSize(width, height);
		}
	}
}
