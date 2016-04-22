package WeaponBase;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import Physics.CzakBody;
import PhysicsFactory.PhysicsFactory;

public class ThrowBullet extends BaseBullet 
{
	CzakBody body;
	int size;
	
	public ThrowBullet(BulletFactory bf, int sz) 
	{
		size = sz;
		bulletFactory = bf;
	}
	
	@Override
	public void create()
	{
		body = new CzakBody(PhysicsFactory.createBoxBody(bulletFactory.mScene.world, 0, 0, size, size, BodyType.DynamicBody), null);
	}

	@Override
	public void shoot(float x, float y)
	{
		body.setPosition(x, y);
	}
	
}
