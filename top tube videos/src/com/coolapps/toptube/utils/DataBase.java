package com.coolapps.toptube.utils;

import java.util.ArrayList;
import java.util.List;

import com.coolapps.toptube.cards.RowItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {
	private static final int VERSION_DATABAE = 1;

	// Nombre de nuestro archivo de base de datos
	private static final String NOMBRE_BASEDATOS = "database.db";

	// Sentencia SQL para la creación de una tabla
	private static final String TABLA_FAVORIT = "CREATE TABLE favorit"
			+ "(url TEXT PRIMARY KEY, smalltext TEXT, bigtext TEXT, type TEXT,title TEXT)";

	// Sentencia SQL para la creación de una tabla
	private static final String TABLA_NEWS = "CREATE TABLE news"
			+ "(url TEXT PRIMARY KEY, visto TEXT)";

	// constructor
	public DataBase(Context context) {
		super(context, NOMBRE_BASEDATOS, null, VERSION_DATABAE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLA_FAVORIT);
		db.execSQL(TABLA_NEWS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLA_FAVORIT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLA_NEWS);
		onCreate(db);
	}

	public void insertFavorit(String url, String title, String smalltext,
			String bigtext, String type) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			ContentValues valores = new ContentValues();
			valores.put("url", url);
			valores.put("title", title);
			valores.put("smalltext", smalltext);
			Log.w("sofiware", " BD type" + type);
			valores.put("type", type);
			valores.put("bigtext", bigtext);
			db.insert("favorit", null, valores);
			db.close();
		}
	}

	public void updateFavorit(String url, String title, String smalltext,
			String bigtext, String type) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			ContentValues valores = new ContentValues();
			valores.put("url", url);
			valores.put("title", title);
			valores.put("smalltext", smalltext);
			valores.put("type", type);
			valores.put("bigtext", bigtext);
			db.update("favorit", valores, "url='" + url + "'", null);
			db.close();
		}
	}

	public void deleteFavorit(String url) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete("favorit", "url= '" + url + "'", null);
		db.close();
	}

	public List<RowItem> selectFavorit() {
		SQLiteDatabase db = getReadableDatabase();
		List<RowItem> lista = new ArrayList<RowItem>();
		String[] valores = { "url", "title", "smalltext", "bigtext", "type" };
		Cursor c = db.query("favorit", valores, null, null, null, null, null,
				null);
		if (c != null) {
			if (c.getCount() > 0) {
				c.moveToFirst();
				do {

					RowItem content = new RowItem(c.getString(0),
							c.getString(1), c.getString(2), c.getString(3),
							c.getString(4), "", false);
					lista.add(content);
				} while (c.moveToNext());
			}
		}
		db.close();
		c.close();
		return lista;
	}

	public boolean isExisteFavorit(String url) {
		boolean ok = false;
		SQLiteDatabase db = getReadableDatabase();
		String[] valores_recuperar = { "url", "type" };
		Cursor c = db.query("favorit", valores_recuperar, "url='" + url + "'",
				null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			if (c.getCount() == 0) {
				ok = false;
			} else {
				ok = true;
			}
		}
		db.close();
		c.close();
		return ok;
	}

	public boolean isExistNews(String url) {
		boolean ok = false;
		SQLiteDatabase db = getReadableDatabase();
		String[] valores_recuperar = { "url", "visto" };
		Cursor c = db.query("news", valores_recuperar, "url='" + url + "'",
				null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			if (c.getCount() == 0) {
				ok = false;
			} else {
				ok = true;
			}
		}
		db.close();
		c.close();
		return ok;
	}
	public void insertnews(String url) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			ContentValues valores = new ContentValues();
			valores.put("url", url);
			valores.put("visto", "false");
			db.insert("news", null, valores);
			db.close();
		}
	}

	public void vistoNews(String url) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			ContentValues valores = new ContentValues();
			valores.put("url", url);
			valores.put("visto", "true");

			db.update("news", valores, "url='" + url + "'", null);
			db.close();
		}
	}
	
	public boolean isNotNews(String url) {
		boolean ok = false;
		SQLiteDatabase db = getReadableDatabase();
		String[] valores_recuperar = { "url", "visto" };
		Cursor c = db.query("news", valores_recuperar, "url='" + url + "'",
				null, null, null, null, null);
		if (c != null) {
			if (c.getCount() > 0) {
				c.moveToFirst();
				ok=	Boolean.parseBoolean(c.getString(1));	
			}
		}
		db.close();
		c.close();
		return ok;
	}
	
	
	public int NovistosNews() {
		int count =0;
		SQLiteDatabase db = getReadableDatabase();
		String[] valores_recuperar = { "url", "visto" };
		Cursor c = db.query("news", valores_recuperar,"visto='false'",
				null, null, null, null, null);
		if (c != null) {
			if (c.getCount() > 0) {
				c.moveToFirst();
				do{
				count++;	
				}while(c.moveToNext());
			}
		}
		db.close();
		c.close();
		return count;
	}

}
