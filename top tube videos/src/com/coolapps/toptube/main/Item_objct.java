package com.coolapps.toptube.main;

public class Item_objct {
	private String titulo;
	private int icono;
	private int size;
	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}
	private String image;
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Item_objct(String title, int icon,String url,int size) {
		  this.titulo = title;
	      this.icono = icon;
	      this.image=url;
	      this.size=size;
	}	
	
	
    public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getIcono() {
		return icono;
	}
	public void setIcono(int icono) {
		this.icono = icono;
	}   
}
