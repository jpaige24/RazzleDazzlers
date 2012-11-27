package razzleDazzlers.mycafemac;

import razzleDazzlers.ratecafemac.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class WelcomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        welcome();
    }
    
    public void welcome(){
    	new Thread(){
    		public void run(){
    			try{
    				Thread.sleep(3000);
    			}catch(InterruptedException e){
    				e.printStackTrace();
    			}
    			if (!isOnline()){
    	        	error(WelcomeActivity.this);
    	    	}
    			Intent intent = new Intent();
    			intent.setClass(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
    		}	
    	}.start();
    }
    
    public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public static void error(final Context context){
    	new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(context).setTitle("No Internet?").setCancelable(false)
                        .setMessage("Oops. WiFi not connected or server not responding. Please check back later.").setNeutralButton("Ok", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            	System.exit(0);
                            }
                        })
                        .create().show();
                Looper.loop();
            }
        }.start();
    }
}