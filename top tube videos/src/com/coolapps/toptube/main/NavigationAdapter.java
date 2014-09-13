package com.coolapps.toptube.main;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coolapps.toptube.main.Main.OnBadgeUpdateListener;
import com.coolapps.toptube.utils.BadgeView;
import com.coolapps.toptube.utils.Configuration;
import com.coolapps.toptube.utils.DataBase;
import com.coolapps.toptube.R;

public class NavigationAdapter extends BaseAdapter {
	private Activity activity;

	ArrayList<Item_objct> arrayitms = new ArrayList<Item_objct>();
	DataBase db;
	BadgeView badge;

	public NavigationAdapter(Activity activity, ArrayList<Item_objct> listarry) {
		super();
		this.activity = activity;
		this.arrayitms = listarry;
		db = new DataBase(activity);
	}

	// Retorna objeto Item_objct del array list
	@Override
	public Object getItem(int position) {
		return arrayitms.get(position);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return arrayitms.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// Declaramos clase estatica la cual representa a la fila
	public static class Fila {
		TextView titulo_itm;
		ImageView icono;
		BadgeView badge;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Fila view;
		LayoutInflater inflator = activity.getLayoutInflater();
		//if (convertView == null) {
			view = new Fila();
			// Creo objeto item y lo obtengo del array

			convertView = inflator.inflate(R.layout.itm, null);
			// Titulo
			view.titulo_itm = new TextView(activity.getBaseContext());
			view.titulo_itm = (TextView) convertView
					.findViewById(R.id.title_item);
			// Seteo en el campo titulo el nombre correspondiente obtenido del
			// objeto
			view.titulo_itm.setTextColor(Color
					.parseColor(Configuration.TEXT_COLOR_CATEGORY));
			view.titulo_itm.setText(arrayitms.get(position).getTitulo());

			if (position == 0 && db.NovistosNews() > 0) {
				// view.badge = new BadgeView(activity, view.titulo_itm);
				badge = new BadgeView(activity, view.titulo_itm);
				// view.badge.setBadgeBackgroundColor(droidGreen);
				badge.setTextColor(Color.WHITE);
				Log.e("test4",
						"el tamaño en la lista de los no vistos "
								+ db.NovistosNews());
				badge.setText(String.valueOf(db.NovistosNews()));
				Main.addOnBadgeUpdateListener(new OnBadgeUpdateListener() {

					@Override
					public void OnBadgeUpdate() {
						// TODO Auto-generated method stub
						try {
							Log.e("test6",
									"el tamaño en la lista de los no vistos "
											+ db.NovistosNews()
											+ "despues de OnBadgeUpdate");
							if (db.NovistosNews() > 0) {
								badge.setText(String.valueOf(db.NovistosNews()));
								badge.show();
							} else {
								badge.hide();
							}
						} catch (Exception e) {

						}
					}
				});
				badge.show();
			} else {
			} // view.badge.hide();
			// Icono
			view.icono = new ImageView(activity.getBaseContext());
			view.icono = (ImageView) convertView.findViewById(R.id.icon);
			// Seteo el icono
			view.icono.setImageResource(arrayitms.get(position).getIcono());
			convertView.setTag(view);
//		//} else {
//			view = new Fila();
//			view = (Fila) convertView.getTag();
//		}
		Log.w("getView", view.titulo_itm.getText().toString() + " pos :"
				+ position + " item " + arrayitms.get(position).getTitulo());
		return convertView;
	}
}
