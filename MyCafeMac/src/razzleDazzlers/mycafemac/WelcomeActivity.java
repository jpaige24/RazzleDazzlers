package razzleDazzlers.mycafemac;

import razzleDazzlers.ratecafemac.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    			Intent intent = new Intent();
    			intent.setClass(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
    		}	
    	}.start();
    }
}