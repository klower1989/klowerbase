package com.klowerbase.test;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.klower.interfaces.OnCusDialogInterface;
import com.klower.interfaces.OnTwodimensioncodeListener;
import com.klower.model.GoodsInfo;
import com.klower.model.GoodsPayload;
import com.klower.twodimensioncode.CameraManager;
import com.klower.twodimensioncode.CaptureActivityHandler;
import com.klower.twodimensioncode.InactivityTimer;
import com.klower.twodimensioncode.ViewfinderView;
import com.klower.utilities.DialogUtils;
import com.klower.utilities.LogTool;

public class MYCaptureActivity extends Activity implements Callback,
		OnClickListener, OnTwodimensioncodeListener {

	private CaptureActivityHandler handler;

	private ViewfinderView viewfinderView;

	private boolean hasSurface;

	private Vector<BarcodeFormat> decodeFormats;

	private String characterSet;

	private InactivityTimer inactivityTimer;

	private MediaPlayer mediaPlayer;

	private boolean playBeep;

	private static final float BEEP_VOLUME = 0.10f;

	private boolean vibrate;

	private boolean isFlashOn = false;

	/**
	 * Called when the activity is first created.
	 * 
	 * @throws HMException
	 */

	private ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CameraManager.init(this);
		setContentView(R.layout.ui_twodimensioncode);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isFlashOn) {
			openFlashLight();
		}
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(decodeFormats, characterSet,
					viewfinderView, this);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		playBeepSoundAndVibrate();
		inactivityTimer.onActivity();
		final String code = obj.getText();

		DialogUtils.showDialog(MYCaptureActivity.this, null, "Code: " + code,
				"查询", "取消", false, new OnCusDialogInterface() {

					@Override
					public void onConfirmClick() {
						if (!code.contains("http")) {
//							getGoodsInfo(code);
						} else {
							Intent fcintent = new Intent();
							fcintent.setAction("android.intent.action.VIEW");
							Uri fcurl = Uri.parse(code);
							fcintent.setData(fcurl);
							startActivity(fcintent);
						}

					}

					@Override
					public void onCancelClick() {
						handler.sendEmptyMessage(R.id.restart_preview);
					}
				});

		return;

	}

	private String getId(String code) {
		String id = "";
		if (code != null) {
			int start = code.lastIndexOf("/") + 1;
			int end = code.lastIndexOf("?");
			if (start > end) {
				return "";
			}
			id = code.substring(start, end);
		}
		LogTool.d("Capture", "------------>id:" + id);
		return id;
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	private void openFlashLight() {
		Camera camera = CameraManager.get().getCamara();
		camera.startPreview();
		Parameters parameter = camera.getParameters();
		if (isFlashOn) {
			isFlashOn = false;
			parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
		} else {
			isFlashOn = true;
			parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
		}
		camera.setParameters(parameter);
	}

	@Override
	public void setCodeResult(int resultCode, Intent intent) {
		// TODO Auto-generated method stub

	}

//	public static RequestParams getSearchParams(String code) {
//		RequestParams params = new RequestParams(ParamsType.OTHER);
//		params.put("code", code);
//		params.put("showapi_appid", "929");
//		params.put("showapi_sign", "simple_5db4a36afa2a488cb2ab03c35a3e1a3e");
//		params.put("goodsName", "");
//		params.put("showapi_timestamp", "20150619093138");
//		return params;
//	}

//	private void getGoodsInfo(String code) {
//		mProgressDialog = DialogUtils.showProgressDialog(this, null,
//				"正在查询商品信息...", false);
//		mAsyncHttpClient.get(this, "http://route.showapi.com/66-22", null,
//				getSearchParams(code), new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, String content) {
//						// TODO Auto-generated method stub
//						super.onSuccess(statusCode, content);
//						if (mProgressDialog != null) {
//							mProgressDialog.dismiss();
//						}
//						try {
//							JSONObject jsonObject = new JSONObject(content);
//							if (jsonObject.getInt("showapi_res_code") == 0) {
//								GoodsPayload goodsPayload = new Gson()
//										.fromJson(content, GoodsPayload.class);
//								if (goodsPayload.getShowapi_res_body()
//										.getCodeData() == null) {
//									Toast.makeText(MYCaptureActivity.this,
//											"未查询到结果！", Toast.LENGTH_SHORT)
//											.show();
//									return;
//								}
//								GoodsInfo goodsInfo = goodsPayload
//										.getShowapi_res_body().getCodeData()
//										.get(0);
//								String msg = "商品Code: " + goodsInfo.getCode()
//										+ "\n" + "商品名称: "
//										+ goodsInfo.getGoodsName() + "\n商品价格: "
//										+ goodsInfo.getInPrice() + "~"
//										+ goodsInfo.getPrice();
//								DialogUtils.showDialog(MYCaptureActivity.this,
//										null, msg, "确定", null, true, null);
//							} else {
//								Toast.makeText(MYCaptureActivity.this, "查询失败！",
//										Toast.LENGTH_SHORT).show();
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onFailure(Throwable error, String content) {
//						// TODO Auto-generated method stub
//						super.onFailure(error, content);
//						if (mProgressDialog != null) {
//							mProgressDialog.dismiss();
//						}
//						Toast.makeText(MYCaptureActivity.this, "查询失败！",
//								Toast.LENGTH_SHORT).show();
//					}
//				});
//	}

}
