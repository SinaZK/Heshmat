package BaseLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import BaseLevel.Modes.CinematicMode;
import BaseLevel.Modes.DrivingEnemyQuery;
import BaseLevel.Modes.DrivingMode;
import BaseLevel.Modes.FinishMode;
import BaseLevel.Modes.ShootingMode;
import BaseLevel.Modes.StartGameCinematic;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import GameScene.LevelManager;
import Misc.BodyStrings;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 5/16/16.
 * 05:23
 * <p/>
 * a loader who loads .body file(sizakBody)
 */
public class LevelLoader
{
	public static String EOF = "END";
	public static String DrivingTagString   = "DRIVING";
	public static String ShootingTagString  = "SHOOTING";

	public static String CinematicTagString = "CINEMATIC";
	public static String StartGameCinematicTagString = "FIRST_CINEMATIC";

	public static String FinishTagString    = "FINISH";
	public static String TRUE  = "TRUE";
	public static String FALSE = "FALSE";

	float ratio = PhysicsConstant.PIXEL_TO_METER;
	public static void loadLVLFile(BaseLevel normalLevel, GameManager gameManager, String path, World world, ArrayList<Texture> disposableArray)
	{

		FileHandle f = Gdx.files.internal(path + "level.lvl");
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		LevelManager levelManager = gameManager.levelManager;

		String read;
		while (true)
		{
			try
			{
				read = dis.readLine();

				if(read.equals(EOF))
					break;

				if(read.equals(DrivingTagString))
				{
					DrivingMode drivingModePart = new DrivingMode(levelManager);
					read = dis.readLine();

					drivingModePart.distance = Float.valueOf(BodyStrings.getPartOf(read, 1));

					read = dis.readLine();
					drivingModePart.fullTime = Float.valueOf(BodyStrings.getPartOf(read, 1));

					while (true)
					{
						read = dis.readLine();
						if(read.equals(EOF))
							break;

						drivingModePart.queries.add(
								new DrivingEnemyQuery(drivingModePart, EnemyFactory.StringToEnemy(BodyStrings.getPartOf(read, 0)),
										Integer.valueOf(BodyStrings.getPartOf(read, 1)),
										Float.valueOf(BodyStrings.getPartOf(read, 2))));
					}

					normalLevel.levelParts.add(drivingModePart);
					dis.readLine();//ignoring blank line
				}//Driving Tag

				if(read.equals(CinematicTagString))
				{
					read = dis.readLine();
					if(BodyStrings.getPartOf(read, 1).equals(StartGameCinematicTagString))
					{
						StartGameCinematic startGameCinematic = new StartGameCinematic(levelManager);
						read = dis.readLine();
						startGameCinematic.fullTime = Float.valueOf(BodyStrings.getPartOf(read, 1));

						normalLevel.levelParts.add(startGameCinematic);
					}

					dis.readLine();//ignoring blank line
				}//Driving Tag

				if(read.equals(ShootingTagString))
				{
					ShootingMode shootingModePart = new ShootingMode(levelManager);
					read = dis.readLine();

					shootingModePart.numberOfWaves = Integer.valueOf(BodyStrings.getPartOf(read, 1));

					for(int i = 0;i < shootingModePart.numberOfWaves;i++)
					{
						read = dis.readLine();

						shootingModePart.waves.add(new BaseWave(gameManager, shootingModePart));
						shootingModePart.waves.get(shootingModePart.waves.size() - 1).create(
								BodyStrings.getPartOf(read, 0),
								Integer.valueOf(BodyStrings.getPartOf(read, 1)),
								Integer.valueOf(BodyStrings.getPartOf(read, 2)),
								dis);
					}

					normalLevel.levelParts.add(shootingModePart);

					dis.readLine();//ignoring blank line
				}//Shooting Tag

				if(read.equals(FinishTagString))
				{
					FinishMode finishModePart = new FinishMode(levelManager);
					normalLevel.levelParts.add(finishModePart);

					dis.readLine();//ignoring blank line
				}//Shooting Tag

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
