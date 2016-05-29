package Physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

import Entity.AnimatedSpriteSheet;
import Misc.GUI;
import PhysicsFactory.PhysicsConstant;

public class CzakBody 
{
	Body mBody;
	ArrayList<Sprite> mSprite;
	public AnimatedSpriteSheet spriteSheet;
	public String bodyName;

	public CzakBody() {mSprite = new ArrayList<Sprite>();}

	public CzakBody(Body pBody, Sprite pSprite)
	{
		mBody = pBody;

		mSprite = new ArrayList<Sprite>();
		
		if(pSprite != null)
			mSprite.add(pSprite);
	}

	public CzakBody(Body pBody, AnimatedSpriteSheet pSprite)
	{
		mBody = pBody;

		mSprite = new ArrayList<Sprite>();

		spriteSheet = pSprite;
	}

	public CzakBody(Body pBody, Texture pTexture)
	{
		mBody = pBody;

		mSprite = new ArrayList<Sprite>();

		if(pTexture != null)
			mSprite.add(new Sprite(pTexture));
	}

	public void draw(Batch batch)
	{
		for(int i = 0;i < mSprite.size();i++)
		{
			batch.setColor(mSprite.get(i).getColor());
			mSprite.get(i).setPosition( 
					mBody.getPosition().x * PhysicsConstant.PIXEL_TO_METER - mSprite.get(i).getWidth() / 2,
					mBody.getPosition().y * PhysicsConstant.PIXEL_TO_METER - mSprite.get(i).getHeight() / 2);

			mSprite.get(i).setRotation((float) (mBody.getAngle() * 180f / Math.PI));
			mSprite.get(i).setOrigin(mSprite.get(i).getWidth() / 2, mSprite.get(i).getHeight() / 2);

			GUI.draw(batch, mSprite.get(i));

			batch.setColor(Color.WHITE);
		}

		if(spriteSheet != null)
		{
			spriteSheet.setPosition(mBody.getPosition().x * PhysicsConstant.PIXEL_TO_METER - spriteSheet.getWidth() / 2,
					mBody.getPosition().y * PhysicsConstant.PIXEL_TO_METER - spriteSheet.getHeight() / 2);
			spriteSheet.draw(batch);
		}
	}

	public void draw(Batch batch, float stateTime, int selectedAnim)
	{
		for(int i = 0;i < mSprite.size();i++)
		{
			batch.setColor(mSprite.get(i).getColor());
			mSprite.get(i).setPosition(
					mBody.getPosition().x * PhysicsConstant.PIXEL_TO_METER - mSprite.get(i).getWidth() / 2,
					mBody.getPosition().y * PhysicsConstant.PIXEL_TO_METER - mSprite.get(i).getHeight() / 2);

			mSprite.get(i).setRotation((float) (mBody.getAngle() * 180f / Math.PI));
			mSprite.get(i).setOrigin(mSprite.get(i).getWidth() / 2, mSprite.get(i).getHeight() / 2);

			GUI.draw(batch, mSprite.get(i));

			batch.setColor(Color.WHITE);
		}

		if(spriteSheet != null)
		{
			spriteSheet.setPosition(mBody.getPosition().x * PhysicsConstant.PIXEL_TO_METER - spriteSheet.getWidth() / 2,
					mBody.getPosition().y * PhysicsConstant.PIXEL_TO_METER - spriteSheet.getHeight() / 2);
			spriteSheet.draw(batch, stateTime, selectedAnim);
		}
	}

	public void draw(Batch batch, int i)
	{
		batch.setColor(mSprite.get(i).getColor());
		mSprite.get(i).setPosition( 
				mBody.getPosition().x * PhysicsConstant.PIXEL_TO_METER - mSprite.get(i).getWidth() / 2,
				mBody.getPosition().y * PhysicsConstant.PIXEL_TO_METER - mSprite.get(i).getHeight() / 2);

		mSprite.get(i).setRotation((float) (mBody.getAngle() * 180f / Math.PI));
		mSprite.get(i).setOrigin(mSprite.get(i).getWidth() / 2, mSprite.get(i).getHeight() / 2);

		GUI.draw(batch, mSprite.get(i));

		batch.setColor(Color.WHITE);
	}
	
	public void setPosition(float x, float y)
	{
		mBody.setTransform(x, y, mBody.getAngle());
	}

	public void addSprite(Sprite s)
	{
		mSprite.add(s);
	}

	public void setBody(Body b)
	{
		mBody = b;
	}

	public float getWidth()
	{
		return mSprite.get(0).getWidth();
	}

	public float getHeight()
	{
		return mSprite.get(0).getHeight();
	}

	public void setUserData(String s)
	{
		mBody.setUserData(s);
	}

	public Body getmBody() {
		return mBody;
	}

	public ArrayList<Sprite> getmSprite() {
		return mSprite;
	}
}
