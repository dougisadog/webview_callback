package com.doug.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.doug.entity.BaseFunctionsLoader;
import com.doug.entity.ContactLoader;
import com.doug.entity.FileUploader;
import com.doug.entity.RingtoneLoader;
import com.doug.entity.VibratorLoader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 统管所有loader
 * @author doug
 *
 */
public class WVInjectManager {
	
private static WVInjectManager instance = null;

	
	private Map<Integer, BaseFunctionsLoader> loaders; //功能loader列表
	private WebView webView;						   //注册的webview
	private Activity activity;						   //注册的activity
	
	public Activity getActivity() {
		return activity;
	}
	public WebView getWebView() {
		return webView;
	}
	
	public static interface WebClientCallback {
		public void onFileChoose();
	}

	
	private Handler mHandler;
	
	
	public static WVInjectManager getInstance() {
		if (null == instance) {
			instance = new WVInjectManager();
		}
		return instance;
	}
	


	private WVInjectManager() {
		loaders = new HashMap<Integer, BaseFunctionsLoader>();
		mHandler = new Handler();
	}
	
	/**
	 * 初始化 统一注册JavascriptInterface接口
	 * 将所有WebChromeClient 功能当做接口交给BaseFunctionsLoader的子类去实现 并轮询完成回调
	 * @param webView
	 * @param activity
	 * @return
	 */
	public WVInjectManager init(WebView webView, Activity activity) {
		this.webView = webView;
		this.activity = activity;
		if (null == loaders) loaders = new HashMap<Integer, BaseFunctionsLoader>();
		loaders.clear();
		webView.setWebChromeClient(new WebChromeClient()
		{
		    public void openFileChooser(ValueCallback<Uri> filePathCallback)
		    {
		    	for (BaseFunctionsLoader loader : loaders.values()) {
		    		loader.openFileChooser(filePathCallback);
				}
		    }

			public void openFileChooser(ValueCallback filePathCallback, String acceptType)
		    {
				for (BaseFunctionsLoader loader : loaders.values()) {
		    		loader.openFileChooser(filePathCallback, acceptType);
				}
		    }

		    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture)
		    {
		    	for (BaseFunctionsLoader loader : loaders.values()) {
		    		loader.openFileChooser(filePathCallback, acceptType, capture);
				}
		    }

		    @Override
		    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
		    {
		    	for (BaseFunctionsLoader loader : loaders.values()) {
		    		loader.onShowFileChooser(webView, filePathCallback, fileChooserParams);
				}
		        return true;
		    }
		});
		webView.addJavascriptInterface(new JavascriptInterface(),
				"functionslistner");
		return this;
	}
	
	/**
	 *注册相应功能
	 * @param functions set去重
	 */
	public void initFunctions(Set<Integer> functions) {
		for (Integer integer : functions) {
			if (null != integer && integer == BaseFunctionsLoader.CONTACT) {
				ContactLoader cl = new ContactLoader();
				cl.init();
				loaders.put(BaseFunctionsLoader.CONTACT, cl);
			}
			if (null != integer && integer == BaseFunctionsLoader.FILEUPLOAD) {
				FileUploader fu = new FileUploader();
				fu.init();
				loaders.put(BaseFunctionsLoader.FILEUPLOAD, fu);
			}
			if (null != integer && integer == BaseFunctionsLoader.RINGTONE) {
				RingtoneLoader rl = new RingtoneLoader();
				rl.init();
				loaders.put(BaseFunctionsLoader.RINGTONE,rl);
			}
			if (null != integer && integer == BaseFunctionsLoader.VIBRATOR) {
				VibratorLoader vl = new VibratorLoader();
				vl.init();
				loaders.put(BaseFunctionsLoader.VIBRATOR,vl);
			}
		}
	
	}
	
	/**
	 * 统一管理所有onactivityresult的处理回调
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void dealWithActivityResult(int requestCode,
			int resultCode, Intent data) {
		for (BaseFunctionsLoader loader : loaders.values()) {
			loader.dealWithActivityResult(requestCode, resultCode, data);
		}
	}
	
	/**
	 * 接口中添加功能
	 * @author doug
	 *
	 */
	public class JavascriptInterface {

		// 一定要声明该函数是JavascriptInterface
		@android.webkit.JavascriptInterface
		public void openContactsList() {

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					BaseFunctionsLoader loader = loaders.get(BaseFunctionsLoader.CONTACT);
					loader.functionsStart();
				}
			});

		}
		
		@android.webkit.JavascriptInterface
		public void ringtone() {

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					BaseFunctionsLoader loader = loaders.get(BaseFunctionsLoader.RINGTONE);
					loader.functionsStart();
				}
			});

		}
		
		@android.webkit.JavascriptInterface
		public void vibrator(final String repeat, final String wait, final String last) {

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					String[] parmas = new String[3];
					parmas[0] = repeat;
					parmas[1] = wait;
					parmas[2] = last;
					BaseFunctionsLoader loader = loaders.get(BaseFunctionsLoader.VIBRATOR);
					loader.functionsStart(parmas);
				}
			});

		}
		
	}
}
