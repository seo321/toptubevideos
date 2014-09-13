package com.coolapps.toptube.utils;







import com.coolapps.toptube.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DialogCustom extends AlertDialog.Builder{
	View customTitle;
	public DialogCustom(Context arg0,String title,String Description) {
		super(arg0);
		// TODO Auto-generated constructor stub
		customTitle = View.inflate(arg0,R.layout.custom, null);
		TextView text = (TextView) customTitle.findViewById(R.id.texttitle);
		 text.setText(title);
		 TextView descr = (TextView) customTitle.findViewById(R.id.textDescription);
		 descr.setText(Description);
		 setCustomTitle(customTitle);
	}

}
