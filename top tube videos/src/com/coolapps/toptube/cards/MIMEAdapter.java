package com.coolapps.toptube.cards;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.coolapps.toptube.main.Main;
import com.coolapps.toptube.script.network.UrlImageDownloader;
import com.coolapps.toptube.script.network.AbstractImageDownloader.ProgressListener;
import com.coolapps.toptube.utils.*;
import com.coolapps.toptube.*;
import com.google.android.youtube.player.YouTubeThumbnailView;


public class MIMEAdapter extends ArrayAdapter<RowItem> {
	public static final String API_KEY = "AIzaSyCe6tORd9Ch4lx-9Ku5SQ476uS9OtZYsWA";
	Context context;
	private UrlImageDownloader mImageDownloader;
	List<RowItem> lista;
	public YTPlayer youtube;
	boolean favorits[];
	DataBase db;
	private boolean OK = false;
	private static final int REQ_START_STANDALONE_PLAYER = 1;
	private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
    public boolean modeFavorit=false;
	public MIMEAdapter(Context context, int resourceId, List<RowItem> items,boolean favorit) {
		super(context, resourceId, items);
		this.context = context;
		mImageDownloader = new UrlImageDownloader(context);
		favorits = new boolean[items.size()];
		for (int i = 0; i < items.size(); i++) {
			favorits[i] = false;
		}
		lista = items;
		db = new DataBase(context);
		this.modeFavorit=favorit;
	}

	public class ViewHolder {
		ImageView image, share, favorit, type, news;
		LinearLayout lprogress;
		YouTubeThumbnailView youTubeThumbnailView;
		TextView title;
		TextView description;
		LinearLayout card;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		int pos = position;
		RowItem rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			if(modeFavorit){
				convertView = mInflater.inflate(R.layout.favorit_item, null);	
			}else{
			convertView = mInflater.inflate(R.layout.cards_item, null);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			holder = new ViewHolder();
			holder.card = (LinearLayout) convertView.findViewById(R.id.card);
			holder.lprogress = (LinearLayout) convertView
					.findViewById(R.id.lprogress);
			holder.lprogress.setVisibility(View.VISIBLE);
			if(!modeFavorit){	
			holder.news = (ImageView) convertView.findViewById(R.id.newimage);
			}
			holder.share = (ImageView) convertView.findViewById(R.id.share);
			holder.type = (ImageView) convertView.findViewById(R.id.type);
			holder.favorit = (ImageView) convertView.findViewById(R.id.favorit);

			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.description = (TextView) convertView
					.findViewById(R.id.description);
  
			holder.favorit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = Integer.parseInt(v.getContentDescription()
							.toString());
					ImageView fav = (ImageView) v;
					switchFavorit(pos, fav, true);
				}
			});
			holder.share.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					share(lista.get(
							Integer.parseInt(v
									.getContentDescription()
									.toString())).getImageId(),lista.get(
											Integer.parseInt(v
													.getContentDescription()
													.toString())).getTitle(),lista.get(
							Integer.parseInt(v
									.getContentDescription()
									.toString())).getType());
				}
			});

			convertView.setTag(holder);
	
		switchFavorit(pos, holder.favorit, false);
//		holder.youTubeThumbnailView = (YouTubeThumbnailView) convertView
//				.findViewById(R.id.list_imageYT);
		holder.image = (ImageView) convertView
				.findViewById(R.id.list_image);
		// holder.image.setImageResource(rowItem.getImageId());
		if (rowItem.getType().contains("vide")) {
			// ///es un video
            Log.e("test","mostrando video en la position "+position);
		
			// holder.youTubeThumbnailView.setVisibility(View.VISIBLE);
			// holder.lprogress.setVisibility(View.GONE);
			Log.e("sofiware", "idVideo " + rowItem.getImageId());
			
			holder.image.setVisibility(View.GONE);
			/////
			String thumbnail = "http://img.youtube.com/vi/"
					+ lista.get(position).getImageId() + "/0.jpg";
			mostrarImageUrl(holder.image, thumbnail, holder.lprogress);
			// youtube = new YTPlayer(Main.ac, youTubeThumbnailView,
			// null, lista.get(position).getImageId());
			holder.image.setContentDescription("" + position);
			holder.image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Log.w("sofiwar2", "es video se ha pulsado el play");
					YTPlayer.OpenYT(
							lista.get(
									Integer.parseInt(arg0
											.getContentDescription().toString()))
									.getImageId(), false, 0);
				}
			});
			/////
//			holder.youTubeThumbnailView.setVisibility(View.GONE);
//			holder.youTubeThumbnailView.setContentDescription("" + position);
//			youtube = new YTPlayer(context, holder.youTubeThumbnailView,
//					holder.lprogress,holder.image, rowItem.getImageId());
//			holder.youTubeThumbnailView
//					.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View arg0) {
//							// TODO Auto-generated method stub
//							YTPlayer.OpenYT(
//									lista.get(
//											Integer.parseInt(arg0
//													.getContentDescription()
//													.toString())).getImageId(),
//									false, 0);
//						}
//					});

			holder.type.setImageResource(R.drawable.ic_action_video);
			holder.type.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					YTPlayer.OpenYT(
							lista.get(
									Integer.parseInt(v.getContentDescription()
											.toString())).getPlaylist(), true,
							Integer.parseInt(v.getContentDescription()
									.toString()));

				}
			});
		} else {
			// / es una imagen
		     Log.e("test","mostrando imagen en la position "+position);
		
			holder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					customDialogImage(lista.get(
							Integer.parseInt(v.getContentDescription()
									.toString())).getImageId(),v,lista.get(
											Integer.parseInt(v.getContentDescription()
													.toString())).getTitle());
				}
			});
//			holder.youTubeThumbnailView.setVisibility(View.GONE);
			holder.image.setVisibility(View.GONE);
			mostrarImageUrl(holder.image, rowItem.getImageId(),
					holder.lprogress,null);
			holder.image.setContentDescription("" + position);
			holder.type.setImageResource(R.drawable.ic_action_picture_1);
		}
		holder.title.setText(rowItem.getTitle());
		holder.description.setOnClickListener(new OnClickListener() {

			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pos = Integer
						.parseInt(v.getContentDescription().toString());
				if (!lista.get(pos).getBigdescription().equals("")) {
					Log.w("sofiware1",lista.get(pos).getDescription()+" "+lista
							.get(pos).getBigdescription());
					customDialog(lista.get(pos).getTitle(),lista.get(pos).getDescription()+" "+ lista
							.get(pos).getBigdescription(), v);
				}
			}
		});
		holder.description.setText(rowItem.getDesc());

		holder.title.setContentDescription("" + position);
		holder.description.setContentDescription("" + position);
		holder.share.setContentDescription("" + position);
		holder.favorit.setContentDescription("" + position);
		holder.type.setContentDescription("" + position);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.card_animation);
		holder.card.startAnimation(animation);
		if (rowItem.isNews() && !modeFavorit) {
			holder.news.setVisibility(View.VISIBLE);
		} else if(!rowItem.isNews() && !modeFavorit){
			holder.news.setVisibility(View.GONE);
		}
		return convertView;
	}

	public void switchFavorit(int pos, ImageView fav, boolean switsh) {
		if (switsh) {
			if (!db.isExisteFavorit(lista.get(pos).getImageId())) {
				fav.setImageResource(R.drawable.ic_action_important);
				
				db.insertFavorit(lista.get(pos).getImageId(), lista.get(pos).getTitle(),lista.get(pos).getDescription(), lista.get(pos).getBigdescription(), lista.get(pos).getType());
			} else {
				fav.setImageResource(R.drawable.ic_action_not_important);
				db.deleteFavorit(lista.get(pos).getImageId());
				
			}
		} else {
			if (!db.isExisteFavorit(lista.get(pos).getImageId())) {
				fav.setImageResource(R.drawable.ic_action_not_important);

			} else {
				fav.setImageResource(R.drawable.ic_action_important);
			}
		}
	}

	public void mostrarImageUrl(final ImageView imageView, String url,
			final LinearLayout lprogress,final YouTubeThumbnailView tyTh) {

		mImageDownloader.download(url, imageView, new ProgressListener() {
			@Override
			public void onProgressUpdated(int progressPercent,
					long timeElapsedMilli) {
				Log.w("sofiware", "descargando " + progressPercent);
				if (progressPercent == 100) {
					Log.w("sofiware", "descarga Completa " + progressPercent);
					if(tyTh!=null){
						tyTh.setVisibility(View.GONE);	
					}
				
					lprogress.setVisibility(View.GONE);
					imageView.setVisibility(View.VISIBLE);
				}
			}
		});

	}
/////////////////////////////////// Popups/////////////////////////////
	public void customDialog(String title, String desc, View v) {
		LayoutInflater layoutInflater = (LayoutInflater) Main.ac
				.getBaseContext().getSystemService(
						Main.ac.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.custom, null);
	
		final PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		TextView titleD = (TextView) popupView.findViewById(R.id.texttitle);
		TextView descr = (TextView) popupView
				.findViewById(R.id.textDescription);
		ImageView cancel =(ImageView) popupView.findViewById(R.id.exitImage);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		titleD.setText(title);
		descr.setText(desc);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

	}
	
	public void customDialogImage(String url,  View v ,String title) {
		LayoutInflater layoutInflater = (LayoutInflater) Main.ac
				.getBaseContext().getSystemService(
						Main.ac.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.custom_image, null);

		final PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		ImageView im = (ImageView) popupView.findViewById(R.id.image);
		Display display = Main.ac.getWindowManager().getDefaultDisplay();
		
		int width = (int) (display.getWidth()*0.9); // ((display.getWidth()*20)/100)
		int height = (int) (display.getHeight()*0.6);// ((display.getHeight()*30)/100)
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
		im.setLayoutParams(parms);
		TextView textv= (TextView) popupView.findViewById(R.id.titleImage);
		textv.setText(title);
		LinearLayout lprogr =  (LinearLayout) popupView.findViewById(R.id.lprogress);
		ImageView cancel=(ImageView) popupView.findViewById(R.id.exitImage);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		mostrarImageUrl(im, url, lprogr, null);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

	}

	public void share(String share,String title,String type){
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		 sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Configuration.SHARE);
		if(type.contains("vid")){
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v="+share);
		}else sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share);
		sharingIntent.setType("text/plain");
		Main.ac.startActivity(Intent.createChooser(sharingIntent,title));
	}
	
	public void mostrarImageUrl(final ImageView imageView, String url,
			final LinearLayout lprogress) {

		mImageDownloader.download(url, imageView, new ProgressListener() {
			@Override
			public void onProgressUpdated(int progressPercent,
					long timeElapsedMilli) {
				Log.w("sofiware", "descargando " + progressPercent);
				if (progressPercent == 100) {
					Log.w("sofiware", "descarga Completa " + progressPercent);
					lprogress.setVisibility(View.GONE);
					imageView.setVisibility(View.VISIBLE);
				}
			}
		});

	}
	
}
