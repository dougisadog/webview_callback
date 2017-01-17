package com.doug.entity;

import com.doug.component.WVInjectManager;
import com.doug.component.WebClientInterface;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 客户端功能抽象类
 * @author Doug
 *
 */
public abstract class BaseFunctionsLoader implements WebClientInterface{

	protected boolean inited = false;
	
	public final static String CALLBACK_NAME = "functionslistner";
	public final static int CONTACT = 1;
	public final static int FILEUPLOAD = 2;
	public final static int RINGTONE = 3;
	public final static int VIBRATOR = 4;
	public final static int CAMERA = 5;
	
	/**
	 * 获取统一环境下的Activity
	 * @return
	 */
	protected Activity getActivity() {
		return WVInjectManager.getInstance().getActivity();
		
	}
	
	/**
	 * 获取统一环境下的WebView
	 * @return
	 */
	protected WebView getWebView() {
		return WVInjectManager.getInstance().getWebView();
		
	}
	
	/**
	 * 初始化 暂未展开
	 */
	public void init() {
		inited = true;
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

    /**
     * js调用客户端功能的启动抽象接口
     * @param params js传递的参数集合
     */
	public abstract void functionsStart(String... params);

	/**
	 * js调用客户端功能的启动抽象接口返回处理，在对应activity中的onActivityResult中执行回调
	 * @param requestCode onActivityResult的requestCode
	 * @param resultCode onActivityResult的resultCode
	 * @param data onActivityResult的requestCode intent
	 */
	public abstract void dealWithActivityResult(int requestCode,
			int resultCode, Intent data);


}
