package com.doug.entity;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

/**
 * 默认响铃实现类 js具体参数待优化
 * @author Doug
 *
 */
public class RingtoneLoader extends BaseFunctionsLoader{
	
	private MediaPlayer mMediaPlayer;
	
	@Override
	public void functionsStart(String... params) {
		 ringtone();
	}
	
	/**
	 * 调起系统默认响铃
	 */
	private void ringtone() {
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);   
		// 如果为空，才构造，不为空，说明之前有构造过  
		if(mMediaPlayer == null)  
		    mMediaPlayer = new MediaPlayer();  
		try {
			mMediaPlayer.setDataSource(getActivity(), uri);
			mMediaPlayer.setLooping(true); //循环播放  
			mMediaPlayer.prepare();  
			mMediaPlayer.start();  
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	@Override
	public void dealWithActivityResult(int requestCode, int resultCode,
			Intent data) {
		// TODO Auto-generated method stub
		
	}
	
}
