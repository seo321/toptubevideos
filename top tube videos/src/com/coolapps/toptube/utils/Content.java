package com.coolapps.toptube.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Content implements Serializable{
	public int index;
	public String smalltext="";
	public String bigtext="";
	public String title="";
	public String url="";
    public String type="";
    public String email="";
    public String playlist="";
    public String favorit="";
    public Calendar date;
    public boolean news=false;

		
}
