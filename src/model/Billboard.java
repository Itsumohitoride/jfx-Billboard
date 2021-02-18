package model;

import java.io.Serializable;

public class Billboard implements Serializable{
	
	private static final long serialVersionUID = 1;
	private Double width;
	private Double heigth;
	private boolean inUse;
	private String brand;

	public Billboard(Double width, Double heigth, boolean inUse, String brand) {

		this.width = width;
		this.heigth = heigth;
		this.inUse = inUse;
		this.brand = brand;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeigth() {
		return heigth;
	}

	public void setHeigth(Double heigth) {
		this.heigth = heigth;
	}

	public boolean getInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public Double calculateArea() {
		
		Double area = 0.0;
		
		area = getWidth()*getHeigth();
		
		return area;
	}
}
