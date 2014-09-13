package com.coolapps.toptube.main;



import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coolapps.toptube.main.Main.OnEventSearchListener;
import com.coolapps.toptube.R;

/*  Fragment para seccion perfil */ 
@SuppressLint("NewApi")
public class ProfileFragment extends Fragment {
     
    public ProfileFragment(){
     Main.addOnSearchListener(new OnEventSearchListener() {
			
			@Override
			public void onSearch(String text) {
				// TODO Auto-generated method stub
				Log.w("test1","Listeners yes home "+text);
			}
		}); 
    }
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.profile, container, false);
          
        return rootView;
    }
}