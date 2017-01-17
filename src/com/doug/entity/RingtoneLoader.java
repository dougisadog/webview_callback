package com.doug.entity;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class RingtoneLoader extends BaseFunctionsLoader{
//	private final static int REQUEST_RV_LOADER = 21002;
	private MediaPlayer mMediaPlayer;
	private Vibrator vibrator;
	
	@Override
	public void functionsStart(String... params) {
		 ringtone();
	}
	
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
	
	private void vibrator() {
		vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);  
		vibrator.vibrate(new long[]{3000, 3000}, 0);  
	}

	@Override
	public void dealWithActivityResult(int requestCode, int resultCode,
			Intent data) {
		// TODO Auto-generated method stub
		
	}
	
}
