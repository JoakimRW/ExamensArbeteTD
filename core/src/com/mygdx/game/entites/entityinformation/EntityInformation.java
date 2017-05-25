package com.mygdx.game.entites.entityinformation;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;

public class EntityInformation {

	private double _fireRate;
	private double _damage;
	private double _range;
	private double _hp;
	private double _velocity;
	private boolean _isFlying;
	private String _animationName;
	private Skeleton _skeleton;
	private double cost;
	private String _name;
	private float _offsetX;
	private float _offsetY;
	private AnimationStateData _animationStateData;
	private String _description;
	private Sound _soundEffect;
	private Sprite _projectileSprite;
	private boolean _isSplash;
	private float _splashRadius;
	private boolean _isMultiTarget;
	private int _maxTargets;
	
	

	public double getRange() {
		return _range;
	}

	protected void setRange(double range) {
		_range = range;
	}

	public double getDamage() {
		return _damage;
	}

	protected void setDamage(double damage) {
		_damage = damage;
	}

	public double getFireRate() {
		return _fireRate;
	}

	protected void setFireRate(double fireRate) {
		_fireRate = fireRate;
	}

	public double getHp() {
		return _hp;
	}

	protected void setHp(double hp) {
		_hp = hp;
	}

	public double getVelocity() {
		return _velocity;
	}

	protected void setVelocity(double velocity) {
		_velocity = velocity;
	}

	public boolean isFlying() {
		return _isFlying;
	}

	protected void setFlying(boolean isFlying) {
		_isFlying = isFlying;
	}

	public String getAnimationName() {
		return _animationName;
	}

	protected void setAnimationName(String animationName) {
		_animationName = animationName;
	}

	public Skeleton getSkeleton() {
		return _skeleton;
	}

	protected void setSkeleton(Skeleton skeleton) {
		_skeleton = skeleton;
	}

	public double getCost() {
		return cost;
	}

	protected void setCost(double cost) {
		this.cost = cost;
	}

	public String getName() {
		return _name;
	}

	protected void setName(String name) {
		_name = name;
	}

	public float getOffsetX() {
		return _offsetX;
	}

	protected void setOffsetX(float offsetX) {
		_offsetX = offsetX;
	}

	public float getOffsetY() {
		return _offsetY;
	}

	protected void setOffsetY(float offsetY) {
		_offsetY = offsetY;
	}

	public AnimationStateData getAnimationStateData() {
		return _animationStateData;
	}

	protected void setAnimationStateData(AnimationStateData animationStateData) {
		_animationStateData = animationStateData;
	}

	public String getDescription() {
		return _description;
	}
	
	public boolean  isSplash() {
		return _isSplash;
	}
	
	public void  setIsSplash(boolean isSplash) {
		_isSplash = isSplash;
	}
	
	protected void setDescription(String description) {
		_description = description;
	}

	public Sound getSoundEffect() {
		return _soundEffect;
	}

	protected void setSoundEffect(Sound soundEffect) {
		_soundEffect = soundEffect;
	}

	public Sprite getProjectileSprite() {
		return _projectileSprite;
	}

	protected void setProjectileSprite(Sprite laserSprite) {
		_projectileSprite = laserSprite;
	}

	public float getSplashRadius() {
		return _splashRadius;
	}
	
	protected void setSplashRadius(float splashRadius) {
		_splashRadius = splashRadius;
	}

	public boolean isMultiTarget() {
		return _isMultiTarget;
	}

	protected void setMultiTarget(boolean isMultiTarget) {
		_isMultiTarget = isMultiTarget;
	}

	public int getMaxTargets() {
		// TODO Auto-generated method stub
		return _maxTargets;
	}
}
