package com.doug.demo;

import java.util.HashSet;
import java.util.Set;

import com.doug.component.WVInjectManager;
import com.doug.entity.BaseFunctionsLoader;
import com.example.webviewcallback_project.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class FunctionsActivity extends Activity{

	private WebView webView;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);
		webView = (WebView) findViewById(R.id.webview);
		WebSettings ws = webView.getSettings();
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// 设置支持缩放
		ws.setSupportZoom(true);
		ws.setUseWideViewPort(true);
		ws.setJavaScriptEnabled(true);
		ws.setLoadWithOverviewMode(true);
		webView.loadUrl("http://www.nangua.webok.net:9973/jsp");
		
		Set<Integer> functions = new HashSet<Integer>();
		functions.add(BaseFunctionsLoader.CONTACT);
		functions.add(BaseFunctionsLoader.RINGTONE);
		functions.add(BaseFunctionsLoader.VIBRATOR);
		functions.add(BaseFunctionsLoader.FILEUPLOAD);
		WVInjectManager.getInstance().init(webView, this).initFunctions(functions);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		WVInjectManager.getInstance().dealWithActivityResult(requestCode, resultCode, data);
	}

}
