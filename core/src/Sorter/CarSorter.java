package Sorter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import BaseCar.BaseCar;
import BaseCar.CarLoader;
import Cars.ArmorCar;
import Cars.DenaCar;
import Cars.PeykanOffroad;
import Cars.Toyota;
import Cars.Train;
import Cars.Truck;
import DataStore.CarStatData;
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

					1,//  Dena
					2,//  Toyota
					3,//  Peykan
					4,//  Truck
					5,//  Armor Car


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

		BaseCar retCar = null;
		CarStatData carStatData = gameManager.activity.carStatDatas[selectedCar];

		switch (selectedCar)
		{
			case 1:
				DenaCar carD = new DenaCar(gameManager, carStatData);
				carD.load("gfx/car/1/car.car", gameManager, world, disposeTextureArray);
				retCar = carD;
				break;

			case 2:
				Toyota carT = new Toyota(gameManager, carStatData);
				carT.load("gfx/car/2/car.car", gameManager, world, disposeTextureArray);
				retCar = carT;
				break;

			case 3:
				PeykanOffroad carP = new PeykanOffroad(gameManager, carStatData);
				carP.load("gfx/car/3/car.car", gameManager, world, disposeTextureArray);
				retCar = carP;
				break;

			case 4:
				Truck carTr = new Truck(gameManager, carStatData);
				carTr.load("gfx/car/4/car.car", gameManager, world, disposeTextureArray);
				retCar = carTr;
				break;

			case 5:
				ArmorCar carA = new ArmorCar(gameManager, carStatData);
				carA.load("gfx/car/5/car.car", gameManager, world, disposeTextureArray);
				retCar = carA;
				break;
		}

		return retCar;
	}

}
