package com.coolapps.toptube.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;




import com.coolapps.toptube.main.Main;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class YTPlayer implements
YouTubePlayer.OnInitializedListener, YouTubeThumbnailView.OnInitializedListener{
	 public static final String API_KEY = "AIzaSyCe6tORd9Ch4lx-9Ku5SQ476uS9OtZYsWA";
	  private static final int REQ_START_STANDALONE_PLAYER = 1;
	  private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
	 Context context;
	 private YouTubePlayer youTubePlayer;
	// private YouTubePlayerView youTubePlayerView;
	 private YouTubeThumbnailView youTubeThumbnailView;
	 private YouTubeThumbnailLoader youTubeThumbnailLoader;
	 private String id_VIDEO;
	 private LinearLayout lprogress;
	 private ImageView image;
	 public YTPlayer(Context cnt,YouTubeThumbnailView thumbnail,LinearLayout lprogress,ImageView image,String idVideo){
		 context=cnt;
		 this.lprogress=lprogress;
		 this.image=image;
		 id_VIDEO=idVideo;
		 youTubeThumbnailView=new YouTubeThumbnailView(context);
		 youTubeThumbnailView=thumbnail;
		 youTubeThumbnailView.initialize(API_KEY, this); 
	 }
	@Override
	public void onInitializationFailure(YouTubeThumbnailView arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitializationSuccess(YouTubeThumbnailView arg0,
			YouTubeThumbnailLoader arg1) {
		// TODO Auto-generated method stub
		  youTubeThumbnailLoader = arg1;
		  arg1.setOnThumbnailLoadedListener(new ThumbnailLoadedListener());
		       
		  youTubeThumbnailLoader.setVideo(id_VIDEO);
	}
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		
	}
	
	 private final class ThumbnailLoadedListener implements
	    YouTubeThumbnailLoader.OnThumbnailLoadedListener {

	  @Override
	  public void onThumbnailError(YouTubeThumbnailView arg0, ErrorReason arg1) {
	
	  }

	  @Override
	  public void onThumbnailLoaded(YouTubeThumbnailView arg0, String arg1) {
		  lprogress.setVisibility(View.GONE);
		  if(image!=null)
		  image.setVisibility(View.GONE);
		  youTubeThumbnailView.setVisibility(View.VISIBLE);

	   
	  }
	  
	 } 
	 
	 public static void  OpenYT(String idVideo,boolean listv,int pos){
			Intent intent = null;
			try{
				if(!idVideo.equals("")){
			if(!listv){
		      intent = YouTubeStandalonePlayer.createVideoIntent(
		         Main.ac, API_KEY, idVideo, 0, false, true);
			}else{
				 intent = YouTubeStandalonePlayer.createPlaylistIntent(Main.ac, API_KEY,
						 idVideo, pos,0, false, true);		      }
			
		      if (intent != null) {
		          if (canResolveIntent(intent)) {
		        	  Main.ac.startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
		          } else {
		            // Could not resolve the intent - must need to install or update the YouTube API service.
		            YouTubeInitializationResult.SERVICE_MISSING
		           .getErrorDialog(Main.ac, REQ_RESOLVE_SERVICE_MISSING).show();
		          }
		        } 
				}
			}catch(Exception e){
				
			}
	 }
	  private static boolean canResolveIntent(Intent intent) {
		    List<ResolveInfo> resolveInfo = Main.ac.getPackageManager().queryIntentActivities(intent, 0);
		    return resolveInfo != null && !resolveInfo.isEmpty();
		  }
}
