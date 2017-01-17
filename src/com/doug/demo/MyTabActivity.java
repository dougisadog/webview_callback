package com.doug.demo;

import com.example.webviewcallback_project.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MyTabActivity extends Activity implements OnClickListener{

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_my_tab);
		findViewById(R.id.fileUpload).setOnClickListener(this);
		findViewById(R.id.functions).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		if (R.id.fileUpload == v.getId()) {
			startActivity(new Intent(this, MainActivity.class));
		}
		else if (R.id.functions == v.getId()){
			startActivity(new Intent(this, FunctionsActivity.class));
		}
		
	}

}
