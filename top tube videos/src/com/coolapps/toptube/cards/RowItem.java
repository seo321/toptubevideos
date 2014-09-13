package com.coolapps.toptube.cards;

public class RowItem {
    private String imageId;
    private String title;
    private String description,bigdescription;
    private String type;
    private String playlist;
    private boolean news;
   
    
    public String getBigdescription() {
		return bigdescription;
	}



	public void setBigdescription(String bigdescription) {
		this.bigdescription = bigdescription;
	}



	public boolean isNews() {
		return news;
	}



	public void setNews(boolean news) {
		this.news = news;
	}



	public String getPlaylist() {
		return playlist;
	}



	public void setPlaylist(String playlist) {
		this.playlist = playlist;
	}



	public String getDescription() {
		return description;
	}
    
	

	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public RowItem(String imageId, String title, String desc, String bigdesc,String type,String playlist,boolean news) {
        this.imageId = imageId;
        this.title = title;
        this.description = desc;
        this.bigdescription = bigdesc;
        this.type=type;
        this.playlist=playlist;
        this.news=news;
      
    }
   


	public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return description;
    }
    public void setDesc(String desc) {
        this.description = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + description;
    }
}
