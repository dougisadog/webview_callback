package com.doug.entity;

import com.doug.component.WVInjectManager;
import com.doug.component.WVInjectManager.WebClientCallback;
import com.doug.component.WebClientInterface;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public abstract class BaseFunctionsLoader implements WebClientInterface{

	protected boolean inited = false;
	
	protected WebClientCallback webClientCallback;
	
	public final static String CALLBACK_NAME = "functionslistner";
	public final static int CONTACT = 1;
	public final static int FILEUPLOAD = 2;
	public final static int RINGTONE = 3;
	public final static int VIBRATOR = 4;
	public final static int CAMERA = 5;
	
	protected Activity getActivity() {
		return WVInjectManager.getInstance().getActivity();
		
	}
	
	protected WebView getWebView() {
		return WVInjectManager.getInstance().getWebView();
		
	}
	
	public void init(WebClientCallback webClientCallback) {
		this.webClientCallback = webClientCallback;
		inited = true;
	}
	
	public void init() {
		WebClientCallback webClientCallback = new WebClientCallback() {
			
			@Override
			public void onFileChoose() {
			}
		};
		init(webClientCallback);
	}
	
    public void openFileChooser(ValueCallback<Uri> filePathCallback)
    {
    }

	public void openFileChooser(ValueCallback filePathCallback, String acceptType)
    {
    }

    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture)
    {
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
    {
        return true;
    }

	public abstract void functionsStart(String... params);

	public abstract void dealWithActivityResult(int requestCode,
			int resultCode, Intent data);


}
