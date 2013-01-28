package com.hyperactivity.android_app;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @SuppressLint("NewApi")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.menu_settings:
        	if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB){
        		startActivity(new Intent(this, SettingsActivity.class));
        	}else{
        		 getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        	}
                return true;
        default:
            return super.onOptionsItemSelected(item);
    	}
    }
}
