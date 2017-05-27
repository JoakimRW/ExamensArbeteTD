package com.mygdx.game.view.tooltip;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.entites.entityinformation.EntityMapper;
import com.mygdx.game.utils.Assets;

public abstract class TooltipTable extends Table {
	
	protected Tooltip<Table> tooltip;
	protected double fireRate , damage , range , price;
	protected String special , description , towerName;
	protected Skin skin;
	protected TooltipManager manager;
	protected EntityInformation info;
	
	
	public TooltipTable(Skin skin , TowerType type) {
		this.skin = skin;
		EntityMapper mapper = new EntityMapper();
		info = mapper.getTowerInformation(type);
		setFireRate(info.getFireRate());
		setDamage(info.getDamage());
		setRange(info.getRange());
		setPrice(info.getCost());
		setSpecial("");
		setDescription(info.getDescription());
		setTowerName(info.getName());
		createTooltipTable();
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTowerName() {
		return towerName;
	}

	public void setTowerName(String towerName) {
		this.towerName = towerName;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}
	
	public double getFireRate() {
		return fireRate;
	}

	public void setFireRate(double fireRate) {
		this.fireRate = fireRate;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTooltip(Tooltip<Table> tooltip) {
		this.tooltip = tooltip;
	}
	
	public void createTooltipTable(){
		 	this.setSkin(skin);
	        this.background(skin.getDrawable("black-rectangle-hover"));
	        tooltip = new Tooltip<Table>(this);
	        setManager(this.getTooltip().getManager());
	        this.getTooltip().getManager().instant();
	        this.getTooltip().getManager().offsetX = 0;
	        this.getTooltip().getManager().offsetY = 0;
	        this.getTooltip().getManager().animations = false;
	        this.getTooltip().setInstant(true);

	        // labels for tooltip
	        Label towerNameLbl = new Label(towerName, skin , "font24" , "white");
	        towerNameLbl.setColor(Assets.greenColor);
	        Label fireRateText = new Label("Fire rate:", skin , "default-font" , "white");
	        Label fireRateLbl = new Label(String.format("%.1f/S",fireRate) , skin , "default-font" , "white");
	        Label damageText = new Label("Damage:", skin , "default-font" , "white");
	        Label damageLbl = new Label(String.format("%.0f",damage),skin , "default-font" , "white");
	        Label rangeText = new Label("Range:", skin , "default-font" , "white");
	        Label rangeLbl = new Label(String.format("%.0f",range) ,skin , "default-font" , "white");
	        Label descriptionLbl = new Label(description , skin , "default-font" , "white");
	  
	        descriptionLbl.setColor(Assets.greenColor);
	        descriptionLbl.setWrap(true);
	        Label specialText = new Label("Special:", skin , "default-font" , "white");
	        Label specialLbl = new Label(special,skin , "default-font" , "white");
	        Label priceText = new Label("Price:", skin , "default-font" , "white");
	        Label priceLbl = new Label( String.format( "%.0f" , price ),skin , "default-font" , "white");
	        // Add labels to tooltip table
	        this.defaults().grow();
	        this.add(towerNameLbl).align(Align.left);
	        this.row();
	        this.add(fireRateText).align(Align.left);
	        this.add(fireRateLbl).align(Align.right);
	        this.row();
	        this.add(damageText).align(Align.left);
	        this.add(damageLbl).align(Align.right);
	        this.row();
	        this.add(rangeText).align(Align.left);
	        this.add(rangeLbl).align(Align.right);
	        this.row();
	        this.add(specialText).align(Align.left);
	        this.add(specialLbl).align(Align.right);
	        this.row();
	        this.add(descriptionLbl).align(Align.left).colspan(2);
	        this.row();
	        this.add(priceText).align(Align.left);
	        this.add(priceLbl).align(Align.right);
	        this.pad(8);
	        this.align(Align.left);
	}
	
	public Tooltip<Table> getTooltip(){
		return tooltip;
	}
	
	public TooltipManager getManager() {
		return manager;
	}

	public void setManager(TooltipManager manager) {
		this.manager = manager;
	}
}
