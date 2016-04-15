/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.klower.twodimensioncode;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.klower.R;
import com.klower.interfaces.OnTwodimensioncodeListener;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class CaptureActivityHandler extends Handler {

	private static final String TAG = CaptureActivityHandler.class
			.getSimpleName();

	private final DecodeThread decodeThread;
	private State state;

	private ViewfinderView viewfinderView;

	private OnTwodimensioncodeListener onTwodimensioncodeListener;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(Vector<BarcodeFormat> decodeFormats,
			String characterSet, ViewfinderView view,
			OnTwodimensioncodeListener listener) {
		this.viewfinderView = view;
		this.onTwodimensioncodeListener = listener;
		decodeThread = new DecodeThread(this, decodeFormats, characterSet,
				new ViewfinderResultPointCallback(view));
		decodeThread.start();
		state = State.SUCCESS;

		// Start ourselves capturing previews and decoding.
		CameraManager.get().startPreview();
		restartPreviewAndDecode(viewfinderView);
	}

	@Override
	public void handleMessage(Message message) {

		if (message.what == R.id.auto_focus) {
			// Log.d(TAG, "Got auto-focus message");
			// When one auto focus pass finishes, start another. This is the
			// closest thing to
			// continuous AF. It does seem to hunt a bit, but I'm not sure what
			// else to do.
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			}
		} else if (message.what == R.id.restart_preview) {
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode(viewfinderView);
		} else if (message.what == R.id.decode_succeeded) {
			Log.d(TAG, "Got decode succeeded message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			Bitmap barcode = bundle == null ? null : (Bitmap) bundle
					.getParcelable(DecodeThread.BARCODE_BITMAP);
			String str_result = ((Result) message.obj).getText();
			// activity.handleDecode((Result) message.obj, barcode);
			onTwodimensioncodeListener.handleDecode((Result) message.obj,
					barcode);
			// Intent intent=new Intent(activity,MainActivity.class);
			// intent.putExtra("code", str_result);
			// activity.startActivity(intent);
			// activity.finish();
		} else if (message.what == R.id.decode_failed) {
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
		} else if (message.what == R.id.return_scan_result) {
			Log.d(TAG, "Got return scan result message");
			// activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
			// activity.finish();
			onTwodimensioncodeListener.setCodeResult(Activity.RESULT_OK,
					(Intent) message.obj);
		}

		//
		// switch (message.what) {
		// case R.id.auto_focus:
		// //Log.d(TAG, "Got auto-focus message");
		// // When one auto focus pass finishes, start another. This is the
		// closest thing to
		// // continuous AF. It does seem to hunt a bit, but I'm not sure what
		// else to do.
		// if (state == State.PREVIEW) {
		// CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
		// }
		// break;
		// case R.id.restart_preview:
		// Log.d(TAG, "Got restart preview message");
		// restartPreviewAndDecode(viewfinderView);
		// break;
		// case R.id.decode_succeeded:
		// Log.d(TAG, "Got decode succeeded message");
		// state = State.SUCCESS;
		// Bundle bundle = message.getData();
		// Bitmap barcode = bundle == null ? null :
		// (Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);
		// String str_result=((Result) message.obj).getText();
		// // activity.handleDecode((Result) message.obj, barcode);
		// onTwodimensioncodeListener.handleDecode((Result) message.obj,
		// barcode);
		// // Intent intent=new Intent(activity,MainActivity.class);
		// // intent.putExtra("code", str_result);
		// // activity.startActivity(intent);
		// // activity.finish();
		//
		// break;
		// case R.id.decode_failed:
		// // We're decoding as fast as possible, so when one decode fails,
		// start another.
		// state = State.PREVIEW;
		// CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
		// R.id.decode);
		// break;
		// case R.id.return_scan_result:
		// Log.d(TAG, "Got return scan result message");
		// // activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
		// // activity.finish();
		// onTwodimensioncodeListener.setCodeResult(Activity.RESULT_OK, (Intent)
		// message.obj);
		// break;
		// }
	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
		quit.sendToTarget();
		try {
			decodeThread.join();
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(R.id.decode_succeeded);
		// removeMessages(R.id.return_scan_result);
		removeMessages(R.id.decode_failed);
	}

	private void restartPreviewAndDecode(ViewfinderView viewfinderView) {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
			CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			viewfinderView.drawViewfinder();
		}
	}

}
