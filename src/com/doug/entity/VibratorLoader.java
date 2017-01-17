package com.doug.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class VibratorLoader extends BaseFunctionsLoader {
	// private final static int REQUEST_RV_LOADER = 21002;
	private Vibrator vibrator;

	@Override
	public void functionsStart(String... params) {
		int repeat = -1;// -1为不重复，0为一直震动
		long[] pattern = new long[2];
		if (params.length < 3)
			return;
		long wait;
		long last;
		try {
			if ("repeat".equals(params[0])) {
				repeat = 0;
			}
			wait = Long.parseLong(params[2].toString());
			last = Long.parseLong(params[1].toString());
			pattern[0] = wait;
			pattern[1] = last;
			vibrator(pattern, repeat);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	private void vibrator(long[] pattern, int repeat) {
		vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(pattern, repeat);
	}

	@Override
	public void dealWithActivityResult(int requestCode, int resultCode,
			Intent data) {
		// TODO Auto-generated method stub

	}

}
