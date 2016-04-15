/**
 * All rights reserved
 */

package com.klower.communications;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-4-16
 */
public class DownLoadClient {

	public final static int DOWNLOAD_FINISHED = 2;

	public final static int DOWNLOAD_DOING = 1;

	public final static int DOWNLOAD_START = 0;

	public final static int DOWNLOAD_NO_NETWORK = -1;

	public final static int DOWNLOAD_READY = -2;

	public final static int DOWNLOAD_ERROR = -3;

	private Context mContext;

	private Handler mTaskHandler;

	private int fileSize;

	private int downLoadFileSize;

	private DownloadMachine downLoader;

	public DownLoadClient(Context context, Handler handle) {
		this.mContext = context;
		this.mTaskHandler = handle;
	}

	private void createDownloaderClient() {
		if (downLoader == null) {
			downLoader = new DownloadMachine();
		}
	}

	public void startDownLoad(String url, String savePathStr, String fileName) {
		Message msg = null;
		if (isConnect(mContext)) {
			msg = mTaskHandler.obtainMessage(DOWNLOAD_READY, null);
			mTaskHandler.sendMessage(msg);
			createDownloaderClient();
			downLoader.execute(savePathStr, fileName, url);
			downLoader.start();
		} else {
			msg = mTaskHandler.obtainMessage(DOWNLOAD_NO_NETWORK,
					null);
			mTaskHandler.sendMessage(msg);
		}
	}

	public void stopDownLoad() {
		// downLoader.cancel(true);
		mTaskHandler = new Handler();
		// mContext = null;
		downLoader = null;
	}

	public int getFileSize() {
		return fileSize;
	}

	public int getDownLoadSize() {
		return downLoadFileSize;
	}

	private class DownloadMachine extends Thread {

		// private AtomicBoolean isCancel = new AtomicBoolean(false);
		private FileOutputStream fos;

		private String mSavePath, mFileName, mUrlString;

		public void execute(String savePath, String fileName, String urlString) {
			mSavePath = savePath;
			mFileName = fileName;
			mUrlString = urlString;
		}

		@Override
		public void run() {

			Log.v("DownLoader", "savePath: " + mSavePath);
			Log.v("DownLoader", "fileName: " + mFileName);
			Log.v("DownLoader", "urlString: " + mUrlString);

			if (null != mTaskHandler) {
				Message msg = null;
				if (mFileName == null || mUrlString == null) {
					mTaskHandler.sendEmptyMessage(DOWNLOAD_ERROR);
					return;
				}
				File file = downLoadFile(mUrlString, mSavePath, mFileName);
				// msg =
				// mTaskHandler.obtainMessage(DOWNLOAD_FINISHED,
				// file);
				if (null == file) {
					downLoadFileSize = 0;
					mTaskHandler.sendEmptyMessage(DOWNLOAD_ERROR);
				} else {
					msg = mTaskHandler.obtainMessage(
							DOWNLOAD_FINISHED, file);
					mTaskHandler.sendMessage(msg);
				}

			}
		}

		private File downLoadFile(String httpUrl, String filePath,
				String fileName) {
			// final String fileName = "UBI.apk";
			// "/data/data" + mContext.getPackageName() + "/update"
			File tmpFile = new File(filePath);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			final File file = new File(filePath + "/" + fileName);
			try {
				URL url = new URL(httpUrl);
				try {
					URLConnection conn = (URLConnection) url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					fileSize = conn.getContentLength();
					Log.d("DownLoadClient", "fileSize: " + fileSize);
					// if (fileSize <= 0) {
					// // throw new RuntimeException("Unknown file size.");
					// }
					if (is == null) {
						// throw new RuntimeException("Stream is null");
					}
					fos = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					// double count = 0;
					downLoadFileSize = 0;
					// send msg
					sendMsg(DOWNLOAD_START);
					do {
						int numread = is.read(buf);
						if (numread == -1) {
							break;
						}
						fos.write(buf, 0, numread);
						downLoadFileSize += numread;
						sendMsg(DOWNLOAD_DOING);
					} while (true);

					// String permission = "666";
					//
					// String command = "chmod " + permission + " " + filePath +
					// "/" + fileName;
					// Runtime runtime = Runtime.getRuntime();
					// runtime.exec(command);

					// sendMsg(DOWNLOAD_FINISHED);

					is.close();
				} catch (IOException e) {
					if (mTaskHandler != null) {
						mTaskHandler
								.sendEmptyMessage(DOWNLOAD_ERROR);
					}
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				if (mTaskHandler != null) {
					mTaskHandler.sendEmptyMessage(DOWNLOAD_ERROR);
				}
				e.printStackTrace();
			}
			return file;
		}

		private void sendMsg(int flag) {
			Message msg = null;
			if (null != mTaskHandler) {
				if (flag == DOWNLOAD_START) {
					msg = mTaskHandler.obtainMessage(flag, fileSize);
				} else {
					msg = mTaskHandler.obtainMessage(flag, downLoadFileSize);
				}
				mTaskHandler.sendMessage(msg);
			}
		}

	}

	private boolean isConnect(Context context) {
		NetworkInfo info = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		boolean bIsConnect = false;
		if (info != null && info.isConnected() == true) {
			bIsConnect = true;
		}
		return bIsConnect;
	}
}
