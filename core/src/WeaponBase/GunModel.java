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
import Entity.GunUpgradeButtons.ClipSizeUpgradeButton;
import Entity.GunUpgradeButtons.FireRateUpgradeButton;
import Entity.GunUpgradeButtons.GunDamageUpgradeButton;
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
	public static String CLIP_SIZE = "CLIP_SIZE";
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
		showSprite.setPosition(x, y - 30);
	}

	public void setSize(float w, float h)
	{
		showSprite.setSize(w, h);
	}

	public void draw(Batch batch)
	{
		gunSelectorTab.gunBackSprite.setSize(450, 300);

		float w = gunSelectorTab.gunBackSprite.getWidth();
		float h = gunSelectorTab.gunBackSprite.getHeight();

		float shW = showSprite.getWidth();
		float shH = showSprite.getHeight();

		gunSelectorTab.gunBackSprite.setPosition(showSprite.getX() - (w - shW) / 2 + 10, showSprite.getY() - (h - shH) / 2 + 20);
		gunSelectorTab.gunBackSprite.draw(batch);
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
			dis.readLine();//BulletHP
			dis.readLine();//BulletDamage
			dis.readLine();//BulletSpeed
			dis.readLine();//BulletSize
			dis.readLine();//ClipSize
			dis.readLine();//ReloadTime
			dis.readLine();//physic

			dis.readLine();//blank line
			dis.readLine();//UPGRADES

			while (true)
			{
				in = dis.readLine();

				if(in.equals(EOF))
					break;

				String type = BodyStrings.getPartOf(in, 0);
				int price = Integer.valueOf(BodyStrings.getPartOf(in, 1));

				if(type.equals(FIRE_RATE_STRING))
					upgradeButtons.add(new FireRateUpgradeButton(gunSelectorTab, price));

				if(type.equals(DAMAGE_STRING))
					upgradeButtons.add(new GunDamageUpgradeButton(gunSelectorTab, price));

				if(type.equals(CLIP_SIZE))
					upgradeButtons.add(new ClipSizeUpgradeButton(gunSelectorTab, price));

//				if(in.equals(DAMAGE_STRING))
//				{
//				}
			}

			in = dis.readLine();
			float width  = Float.valueOf(BodyStrings.getPartOf(in, 1));
			float height = Float.valueOf(BodyStrings.getPartOf(in, 2));

			showSprite.setSize(width, height);

		}
		catch (IOException e)
		{
			Log.e("Tag", e.toString());
		}//catch

	}

	public void initUpgradeButtons(GunStatData gunStatData)
	{
		float startX = gunSelectorTab.DX + 115;
		float startY = gunSelectorTab.DY + 25;
		float width = 188;
		float height = 50;
		float padding = 10;

		for(int i = 0;i < upgradeButtons.size();i++)
		{
			upgradeButtons.get(i).setGunStatData(gunStatData);
			upgradeButtons.get(i).setPosition(startX + (width + padding) * (i), startY);
		}
	}
}
