package com.coolapps.toptube.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
	public List<Content> contents = new ArrayList<Content>();
    public String icon="";
    public String tag="";
    public String type="";
    public String playlist="";
}
