package WeaponBase;

public abstract class BaseBullet 
{
	public BulletFactory bulletFactory;
	public float mDamage;
	
	public void create(){};
	public abstract void shoot(float x, float y);
}
