package com.coolapps.toptube.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class XmlDownloader {
	OnDownloadedListener mOnDownloadedListener;
	public void addOnDownloadedListener( OnDownloadedListener onDownloadedListener){
		mOnDownloadedListener=onDownloadedListener;
	}
	ProgressBar barra;
	public interface OnDownloadedListener{
	void	OnDownloaded(String succes);
		
	};	

	
	public XmlDownloader(Context context ,ProgressBar barra){
		this.barra=barra;
		final DownloadTask downloadTask = new DownloadTask(context);
		downloadTask.execute(Configuration.URL);
		
	}
	
	
	
	private class DownloadTask extends AsyncTask<String, Integer, String> {

	    private Context context;
	     

	    public DownloadTask(Context context) {
	        this.context = context;
	    }
	    
		@Override
		protected void onPreExecute() {
			if(barra!=null){
			barra.setMax(100);
			barra.setProgress(0);
			}
		}
	    @Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
	    	Log.w("test","descargando "+values[0].intValue());
	    	if(barra!=null)
	    	barra.setProgress(values[0].intValue());
			super.onProgressUpdate(values);
		}
          
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try{
				if(barra!=null)
				barra.setProgress(50);
			mOnDownloadedListener.OnDownloaded(result);
			}catch(Exception e){
				
			}
			super.onPostExecute(result);
		}

		@Override
	    protected String doInBackground(String... sUrl) {
	        InputStream input = null;
	        OutputStream output = null;
	        HttpURLConnection connection = null;
	        try {
	            URL url = new URL(sUrl[0]);
	            connection = (HttpURLConnection) url.openConnection();
	            connection.connect();

	            // expect HTTP 200 OK, so we don't mistakenly save error report
	            // instead of the file
	            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
	                return "Server returned HTTP " + connection.getResponseCode()
	                        + " " + connection.getResponseMessage();
	            }

	            // this will be useful to display download percentage
	            // might be -1: server did not report the length
	            int fileLength = connection.getContentLength();

	            // download the file
	            input = connection.getInputStream();
	            output = new FileOutputStream(Configuration.URI_STORAGE);

	            byte data[] = new byte[4096];
	            long total = 0;
	            int count;
	            while ((count = input.read(data)) != -1) {
	                // allow canceling with back button
	                if (isCancelled()) {
	                    input.close();
	                    return "canceled";
	                }
	                total += count;
	                // publishing the progress....
	               // publishProgress((int) (total * 50 / 100));
	                if (fileLength > 0) // only if total length is known
	                    publishProgress((int) (total * 50 / fileLength));
	                output.write(data, 0, count);
	            }
	        } catch (Exception e) {
	            return e.toString();
	        } finally {
	            try {
	                if (output != null)
	                    output.close();
	                if (input != null)
	                    input.close();
	            } catch (IOException ignored) {
	            }

	            if (connection != null)
	                connection.disconnect();
	        }
	        return "succes";
	    }	
	}
}
