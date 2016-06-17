package Sorter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import BaseCar.BaseCar;
import BaseCar.CarLoader;
import Cars.Train;
import GameScene.GameManager;
import Misc.Log;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 6/7/16.
 * 19:40
 */
public class CarSorter
{

	public static int carPos[] =
			{
					0,//   NULL

					1,//  Train
					2,//  Neysan


			};

	public static int getFromCarPos(int id)
	{
		for(int i = 1;i <= SceneManager.CAR_NUM;i++)
			if(carPos[i] == id)
				return i;

		return -1;
	}

	public static BaseCar createSelectedCar(GameManager gameManager, World world, ArrayList<Texture> disposeTextureArray, int pos)
	{
		int selectedCar = carPos[pos];

		switch (selectedCar)
		{
			case 1:
//				Log.e("CarSorter.java", "Selecting Train");
				return CarLoader.loadTrainFile(gameManager, "gfx/car/1/car.car", world, disposeTextureArray, gameManager.activity.carStatDatas[selectedCar]);

			case 2:
//				Log.e("CarSorter.java", "Selecting Neysan");
				return CarLoader.loadCarFile(gameManager, "gfx/car/2/car.car", world, disposeTextureArray, gameManager.activity.carStatDatas[selectedCar]);
		}

		return null;
	}

}
