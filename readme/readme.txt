webview_callback.jar 现支持webview调用文件上传（支持拍照和相册） 震动 响铃 读取手机联系人
将webview_callback.jar 拷贝至android项目的libs下

按照所需功能 添加权限  并确保 使用功能的activity 在AndroidManifest的配置android:launchMode="standard"

	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!-- 联系人 -->
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <!-- 震动 -->
    <uses-permission android:name="android.permission.VIBRATE"/> 

    在调用的activity中调用  具体参见FunctionsActivity.java
    Set<Integer> functions = new HashSet<Integer>();
		functions.add(BaseFunctionsLoader.CONTACT); //联系人
		functions.add(BaseFunctionsLoader.RINGTONE); //响铃
		functions.add(BaseFunctionsLoader.VIBRATOR); //震动
		functions.add(BaseFunctionsLoader.FILEUPLOAD); //图片上传
		//webView 为调用的webview this为当前activity  注意此方法会覆盖原有webView.setWebChromeClient
		WVInjectManager.getInstance().init(webView, this).initFunctions(functions);

		并在当前activity 中实现onActivityResult并调用
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		WVInjectManager.getInstance().dealWithActivityResult(requestCode, resultCode, data);
	}

	js 端调用方式见welcome.jsp