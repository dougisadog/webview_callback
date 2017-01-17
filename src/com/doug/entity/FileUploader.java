package com.doug.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class FileUploader extends BaseFunctionsLoader {
	private final static int REQUEST_FILE_PICKER = 21000;
	private final static int REQUEST_CAMERA_PICKER = 20999;

	@Override
	public void openFileChooser(ValueCallback<Uri> filePathCallback) {
		mFilePathCallback4 = filePathCallback;
		showNoticeDialog();
	}
	@Override
	public void openFileChooser(ValueCallback filePathCallback,
			String acceptType) {
		mFilePathCallback4 = filePathCallback;
		showNoticeDialog();
	}
	@Override
	public void openFileChooser(ValueCallback<Uri> filePathCallback,
			String acceptType, String capture) {
		mFilePathCallback4 = filePathCallback;
		showNoticeDialog();
	}

	@Override
	public boolean onShowFileChooser(WebView webView,
			ValueCallback<Uri[]> filePathCallback,
			WebChromeClient.FileChooserParams fileChooserParams) {
		mFilePathCallback5 = filePathCallback;
		showNoticeDialog();
		return true;
	}

	/**
	 * 检查SD卡是否存在
	 * 
	 * @return
	 */
	public final boolean checkSDcard() {
		boolean flag = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);
		if (!flag) {
			Toast.makeText(getActivity(), "请插入手机存储卡再使用本功能", Toast.LENGTH_SHORT)
					.show();
		}
		return flag;
	}

	private void openFiles() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		getActivity().startActivityForResult(
				Intent.createChooser(intent, "File Chooser"),
				REQUEST_FILE_PICKER);
	};

	private String filePhotoPath;

	private void openCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		filePhotoPath = Environment.getExternalStorageDirectory().getPath()
				+ "/temp/" + (System.currentTimeMillis() + ".jpg");
		File pre = new File(filePhotoPath);
		// 下面这句指定调用相机拍照后的照片存储的路径
		if (pre.exists()) {
			pre.delete();
		}
		try {
			pre.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pre));
		getActivity().startActivityForResult(cameraIntent,
				REQUEST_CAMERA_PICKER);
	};

	private AlertDialog chooseDialog;

	/**
	 * 显示版本更新通知对话框
	 */
	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("选择方式");
		StringBuffer sb = new StringBuffer();
		// builder.setMessage(sb.toString());
		builder.setPositiveButton("相册", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				openFiles();
			}
		});
		builder.setNegativeButton("拍照", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				openCamera();
			}
		});

		chooseDialog = builder.create();
		chooseDialog.setCancelable(false);
		chooseDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					dialog.dismiss();
				}
				return false;
			}
		});
		chooseDialog.show();
	}

	private ValueCallback<Uri> mFilePathCallback4;
	private ValueCallback<Uri[]> mFilePathCallback5;

	private void uploadPic(Uri result) {
		if (mFilePathCallback4 != null) {
			mFilePathCallback4.onReceiveValue(result);
		}
		if (mFilePathCallback5 != null) {
			if (result != null) {
				mFilePathCallback5.onReceiveValue(new Uri[]{result});
			}
			else {
				mFilePathCallback5.onReceiveValue(null);
			}
		}
		mFilePathCallback4 = null;
		mFilePathCallback5 = null;
	}

	public void dealWithActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (!inited || resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_FILE_PICKER) {
			if (null != intent)
				uploadPic(intent.getData());
		}
		else if (requestCode == REQUEST_CAMERA_PICKER) {
			try {
				if (TextUtils.isEmpty(filePhotoPath))
					return;
				File filename = new File(filePhotoPath);
				Uri uri = Uri.fromFile(filename);
				uploadPic(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void functionsStart(String... params) {
		showNoticeDialog();
	}

}
