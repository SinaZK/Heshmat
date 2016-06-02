package Human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import GameScene.GameManager;
import GameScene.GameScene;
import Misc.Log;


public class HumanResizer 
{
	GameManager gameManager;
	GameScene gameScene;

	//FULL
	public float fullDX, fullDY;
	
	//Head
	public float headRadius;
	public float headDX, headDY;
	
	//Neck
	public float neckWidth, neckHeight;
	
	//MainBody
	public float bodyWidth, bodyHeight;
	
	//Hand
	public float handWidth, handHeight;
	public float hand1Arm1R, hand1Arm2R;
	public float hand2Arm1R, hand2Arm2R;
	public float hand1DX, hand1DY, hand1Arm2DX, hand1Arm2DY;
	public float hand2DX, hand2DY, hand2Arm2DX, hand2Arm2DY;
	
	
	//Leg
	public float legWidth, legHeight;
	public float leg1Arm1R, leg1Arm2R;
	public float leg2Arm1R, leg2Arm2R;
	public float leg1DX, leg1DY, leg1Arm2DX, leg1Arm2DY;
	public float leg2DX, leg2DY, leg2Arm2DX, leg2Arm2DY;
	
	public HumanResizer(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
	}
	
	public void loadATT(String add)
	{
		FileHandle f = Gdx.files.internal(add);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		try {

			String [] val;
			String in;
			
			
			dis.readLine();//fullDXY
			
			in = dis.readLine();
			val = in.split(" ");
			fullDX  = Float.valueOf(val[0]);
			fullDY  = Float.valueOf(val[1]);
			
			dis.readLine();//Head
			
			in = dis.readLine();
			headRadius = Float.valueOf(in);
			in = dis.readLine();
			val = in.split(" ");
			headDX  = Float.valueOf(val[0]);
			headDY = Float.valueOf(val[1]);
			
			dis.readLine();//Neck
			
			in = dis.readLine();
			val = in.split(" ");
			neckWidth  = Float.valueOf(val[0]);
			neckHeight = Float.valueOf(val[1]);
			
			dis.readLine();//MainBody
			
			in = dis.readLine();
			val = in.split(" ");
			bodyWidth= Float.valueOf(val[0]);
			bodyHeight = Float.valueOf(val[1]);
			
			dis.readLine();//Hand1
			
			//w, h
			in = dis.readLine();
			val = in.split(" ");
			handWidth  = Float.valueOf(val[0]);
			handHeight = Float.valueOf(val[1]);
			//dx, dy
			in = dis.readLine();
			val = in.split(" ");
			hand1DX  = Float.valueOf(val[0]);
			hand1DY  = -Float.valueOf(val[1]);
			//arm1Teta, arm2Teta
			in = dis.readLine();
			val = in.split(" ");
			hand1Arm1R  = Float.valueOf(val[0]);
			hand1Arm2R  = Float.valueOf(val[1]);
			//arm2DX, arm2DY
			in = dis.readLine();
			val = in.split(" ");
			hand1Arm2DX  = Float.valueOf(val[0]);
			hand1Arm2DY  = -Float.valueOf(val[1]);
			
			dis.readLine();//Hand2
			
			//dx, dy
			in = dis.readLine();
			val = in.split(" ");
			hand2DX  = Float.valueOf(val[0]);
			hand2DY  = -Float.valueOf(val[1]);
			//arm1Teta, arm2Teta
			in = dis.readLine();
			val = in.split(" ");
			hand2Arm1R  = Float.valueOf(val[0]);
			hand2Arm2R  = Float.valueOf(val[1]);
			//arm2DX, arm2DY
			in = dis.readLine();
			val = in.split(" ");
			hand2Arm2DX  = Float.valueOf(val[0]);
			hand2Arm2DY  = -Float.valueOf(val[1]);
			
			dis.readLine();//Leg1
			
			//w, h
			in = dis.readLine();
			val = in.split(" ");
			legWidth  = Float.valueOf(val[0]);
			legHeight = Float.valueOf(val[1]);
			//dx, dy
			in = dis.readLine();
			val = in.split(" ");
			leg1DX  = Float.valueOf(val[0]);
			leg1DY  = -Float.valueOf(val[1]);
			//arm1Teta, arm2Teta
			in = dis.readLine();
			val = in.split(" ");
			leg1Arm1R  = Float.valueOf(val[0]);
			leg1Arm2R  = -Float.valueOf(val[1]);
			//arm2DX, arm2DY
			in = dis.readLine();
			val = in.split(" ");
			leg1Arm2DX  = Float.valueOf(val[0]);
			leg1Arm2DY  = -Float.valueOf(val[1]);
			
			dis.readLine();//Leg2
			
			//dx, dy
			in = dis.readLine();
			val = in.split(" ");
			leg2DX  = Float.valueOf(val[0]);
			leg2DY  = -Float.valueOf(val[1]);
			//arm1Teta, arm2Teta
			in = dis.readLine();
			val = in.split(" ");
			leg2Arm1R  = Float.valueOf(val[0]);
			leg2Arm2R  = -Float.valueOf(val[1]);
			//arm2DX, arm2DY
			in = dis.readLine();
			val = in.split(" ");
			leg2Arm2DX  = Float.valueOf(val[0]);
			leg2Arm2DY  = -Float.valueOf(val[1]);
		} 
		catch (IOException e) 
		{
			Log.e("Tag", e.toString());
		}//catch
		
	}//loadATT
}//class
