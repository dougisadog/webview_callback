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

/**
 * 文件上传实现
 * @author Doug
 *
 */
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

	
	/**
	 * 通过重写BaseFunctionsLoader中的WebClientInterface的实现方法来进行文件选取的实现
	 * 具体方法结构和返回值参见WebChromeClient的对应结构
	 */
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

	
	/**
	 * 打开文件  
	 */ 
	private void openFiles() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		getActivity().startActivityForResult(
				Intent.createChooser(intent, "File Chooser"),
				REQUEST_FILE_PICKER);
	};

	private String filePhotoPath;

	
	/**
	 * 打开摄像头拍照
	 */ 
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
	 * 显示图片选择方式弹出框（相册/拍照）
	 */
	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("选择方式");
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

	/**
	 * 通过uri返回文件选取结果
	 * @param result
	 */
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
