package com.doug.component;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public interface WebClientInterface {

    public void openFileChooser(ValueCallback<Uri> filePathCallback);

	public void openFileChooser(ValueCallback filePathCallback, String acceptType);

    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture);

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);
}
