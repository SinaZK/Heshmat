package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Sorter.GunSorter;
import WeaponBase.BaseGun;
import WeaponBase.NormalBullet;
import heshmat.MainActivity;

public class EnemyGun extends NormalGun
{
	public EnemyGun(MainActivity a, GameManager gm, int posID, GunSorter.GunType gunType)
	{
		super(a, gm, posID, gunType);
	}

	@Override
	public void loadResources(String path, ArrayList<Texture> disposalArray)
	{
		{
			FileHandle f = Gdx.files.internal(path + "gun.gun");
			InputStream inputStream = f.read();
			BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

			try {

				String [] val;
				String in;

				dis.readLine();//price

				in = dis.readLine();//size
				float width = Float.valueOf(BodyStrings.getPartOf(in, 1));
				float height = Float.valueOf(BodyStrings.getPartOf(in, 2));

				in = dis.readLine();//HumanPos
				float hX = Float.valueOf(BodyStrings.getPartOf(in, 1));
				float hY = Float.valueOf(BodyStrings.getPartOf(in, 2));
				humanPos.set(hX, hY);

				in = dis.readLine();//origin
				float oX = Float.valueOf(BodyStrings.getPartOf(in, 1));
				float oY = Float.valueOf(BodyStrings.getPartOf(in, 2));

				in = dis.readLine();//shootingPos
				float shootX = Float.valueOf(BodyStrings.getPartOf(in, 1));
				float shootY = Float.valueOf(BodyStrings.getPartOf(in, 2));

				in = dis.readLine();
				rateOfFire = Float.valueOf(BodyStrings.getPartOf(in, 1));

				in = dis.readLine();
				bulletHP = Float.valueOf(BodyStrings.getPartOf(in, 1));

				in = dis.readLine();
				bulletDamage = Float.valueOf(BodyStrings.getPartOf(in, 1));

				in = dis.readLine();
				bulletSpeed = Float.valueOf(BodyStrings.getPartOf(in, 1));

				in = dis.readLine();
				bulletSize = new Vector2(Float.valueOf(BodyStrings.getPartOf(in, 1)), Float.valueOf(BodyStrings.getPartOf(in, 2)));

				in = dis.readLine();
				clipSize = Float.valueOf(BodyStrings.getPartOf(in, 1));
				ammo = clipSize;

				in = dis.readLine();
				reloadTime = Float.valueOf(BodyStrings.getPartOf(in, 1));

				float scaleX = image.getWidth() / width;
				float scaleY = image.getHeight() / height;

				image.setSize(width, height);
				image.setOrigin(oX / scaleX, oY / scaleY);

				shootingPoint.set(shootX / scaleX, shootY / scaleY);

				in = dis.readLine();
				bodyDensity = Float.valueOf(BodyStrings.getPartOf(in, 1));
				bodyElasticity = Float.valueOf(BodyStrings.getPartOf(in, 2));
				bodyFriction = Float.valueOf(BodyStrings.getPartOf(in, 3));
			}
			catch (IOException e)
			{
				Log.e("Tag", e.toString());
			}//catch

		}
	}
}