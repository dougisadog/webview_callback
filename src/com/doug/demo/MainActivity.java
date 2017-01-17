package com.doug.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.doug.component.WVInjectManager;
import com.doug.entity.BaseFunctionsLoader;
import com.doug.entity.FileUploader;
import com.example.webviewcallback_project.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity{

	private WebView web;
	private FileUploader fl;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);
		web = (WebView) findViewById(R.id.webview);
		WebSettings ws = web.getSettings();
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// 设置支持缩放
		ws.setSupportZoom(true);
		ws.setUseWideViewPort(true);
		ws.setJavaScriptEnabled(true);
		ws.setLoadWithOverviewMode(true);
		web.loadUrl("http://www.chuantu.biz");
		
		Set<Integer> functions = new HashSet<Integer>();
		functions.add(BaseFunctionsLoader.FILEUPLOAD);
		WVInjectManager.getInstance().init(web, this).initFunctions(functions);
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		WVInjectManager.getInstance().dealWithActivityResult(requestCode, resultCode, intent);
	}

}
