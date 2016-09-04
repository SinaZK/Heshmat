package Sorter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import BaseCar.BaseCar;
import BaseCar.CarLoader;
import Cars.ArmorCar;
import Cars.DenaCar;
import Cars.MiniBus;
import Cars.PeykanOffroad;
import Cars.Tank.Tank;
import Cars.Toyota;
import Cars.Train;
import Cars.Truck;
import Cars.VanTaxi;
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
					6,//  TaxiVan
					3,//  Peykan
					7,//  MiniBus
					4,//  Truck
					5,//  Armor Car
					8,//  Tank


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
				carD.loadCarSound("sfx/car/1.ogg");
				retCar = carD;
				break;

			case 2:
				Toyota carT = new Toyota(gameManager, carStatData);
				carT.load("gfx/car/2/car.car", gameManager, world, disposeTextureArray);
				carT.loadCarSound("sfx/car/2.ogg");
				retCar = carT;
				break;

			case 3:
				PeykanOffroad carP = new PeykanOffroad(gameManager, carStatData);
				carP.load("gfx/car/3/car.car", gameManager, world, disposeTextureArray);
				carP.loadCarSound("sfx/car/3.ogg");
				retCar = carP;
				break;

			case 4:
				Truck carTr = new Truck(gameManager, carStatData);
				carTr.load("gfx/car/4/car.car", gameManager, world, disposeTextureArray);
				carTr.loadCarSound("sfx/car/4.ogg");
				retCar = carTr;
				break;

			case 5:
				ArmorCar carA = new ArmorCar(gameManager, carStatData);
				carA.load("gfx/car/5/car.car", gameManager, world, disposeTextureArray);
				carA.loadCarSound("sfx/car/5.ogg");
				retCar = carA;
				break;

			case 6:
				VanTaxi van = new VanTaxi(gameManager, carStatData);
				van.load("gfx/car/6/car.car", gameManager, world, disposeTextureArray);
				van.loadCarSound("sfx/car/5.ogg");
				retCar = van;
				break;

			case 7:
				MiniBus miniBus = new MiniBus(gameManager, carStatData);
				miniBus.load("gfx/car/7/car.car", gameManager, world, disposeTextureArray);
				miniBus.loadCarSound("sfx/car/5.ogg");
				retCar = miniBus;
				break;

			case 8:
				Tank tank = new Tank(gameManager, carStatData);
				tank.load("gfx/car/8/car.car", gameManager, world, disposeTextureArray);
				tank.loadCarSound("sfx/car/5.ogg");
				retCar = tank;
				break;
		}

		return retCar;
	}

}
