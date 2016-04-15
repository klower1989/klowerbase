package com.klowerbase.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.klower.communications.DownloadService;
import com.klower.downloadmanager.DownloadListener;
import com.klower.downloadmanager.DownloadManager;
import com.klower.downloadmanager.DownloadTask;
import com.klower.utilities.LogTool;

import java.io.File;

public class DownloadActivity extends Activity {

	private DownloadManager downloadManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		downloadManager = new DownloadManager(this);
		findViewById(R.id.download).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(DownloadActivity.this,
						DownloadService.class);
				intent.putExtra("url", "http://txl114.com/txl.apk");
				intent.putExtra("fileName", getString(R.string.app_name));
				intent.putExtra("fileType", ".apk");
				startService(intent);

				final DownloadTask task = new DownloadTask(
						DownloadActivity.this);
				task.setUrl("http://txl114.com/txl.apk");
				// downloadManager.add(task, listener);
				downloadManager.start(task,
						new DownloadListener<Integer, DownloadTask>() {
							@Override
							public void onProgressUpdate(Integer... values) {
								super.onProgressUpdate(values);
								long filesize = 0l;
								File file = new File(task.getPath());
								if (file.exists()) {
									filesize = file.length();
								}
								int progress = 0;
								if (task.getSize() > 0) {
									progress = (int) (filesize * 100 / task
											.getSize());
								}
								LogTool.d("DownloadManager", "Progress: "
										+ progress + "path: " + task.getPath());
							}

							@Override
							public void onFinish() {
								super.onFinish();
								LogTool.d("DownloadManager",
										"Progress: Finish: " + task.getPath());
							}

							@Override
							public void onError(Throwable thr) {
								super.onError(thr);
							}

							@Override
							public void onStop(DownloadTask task) {
								// TODO Auto-generated method stub
								super.onStop(task);
							}
						});
			}
		});
	}

	private DownloadListener listener = new DownloadListener<Integer, DownloadTask>() {
		/**
		 * The download task has been added to the sqlite.
		 * <p/>
		 * operation of UI allowed.
		 * 
		 * @param downloadTask
		 *            the download task which has been added to the sqlite.
		 */
		@Override
		public void onAdd(DownloadTask downloadTask) {
			super.onAdd(downloadTask);
			LogTool.i("onAdd()");
			LogTool.i("" + downloadTask);
		}

		/**
		 * The download task has been delete from the sqlite
		 * <p/>
		 * operation of UI allowed.
		 * 
		 * @param downloadTask
		 *            the download task which has been deleted to the sqlite.
		 */
		@Override
		public void onDelete(DownloadTask downloadTask) {
			super.onDelete(downloadTask);
			LogTool.i("onDelete()");
		}

		/**
		 * The download task is stop
		 * <p/>
		 * operation of UI allowed.
		 * 
		 * @param downloadTask
		 *            the download task which has been stopped.
		 */
		@Override
		public void onStop(DownloadTask downloadTask) {
			super.onStop(downloadTask);
			LogTool.i("onStop()");
		}

		/**
		 * Runs on the UI thread before doInBackground(Params...).
		 */
		@Override
		public void onStart() {
			super.onStart();
			LogTool.i("onStart()");
		}

		/**
		 * Runs on the UI thread after publishProgress(Progress...) is invoked.
		 * The specified values are the values passed to
		 * publishProgress(Progress...).
		 * 
		 * @param values
		 *            The values indicating progress.
		 */
		@Override
		public void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			// ((DownloadTaskAdapter) getListAdapter()).notifyDataSetChanged();
			LogTool.i("onProgressUpdate");
		}

		/**
		 * Runs on the UI thread after doInBackground(Params...). The specified
		 * result is the value returned by doInBackground(Params...). This
		 * method won't be invoked if the task was cancelled.
		 * 
		 * @param downloadTask
		 *            The result of the operation computed by
		 *            doInBackground(Params...).
		 */
		@Override
		public void onSuccess(DownloadTask downloadTask) {
			super.onSuccess(downloadTask);
			LogTool.i("onSuccess()");
		}

		/**
		 * Applications should preferably override onCancelled(Object). This
		 * method is invoked by the default implementation of
		 * onCancelled(Object). Runs on the UI thread after cancel(boolean) is
		 * invoked and doInBackground(Object[]) has finished.
		 */
		@Override
		public void onCancelled() {
			super.onCancelled();
			LogTool.i("onCancelled()");
		}

		@Override
		public void onError(Throwable thr) {
			super.onError(thr);
			LogTool.i("onError()");
		}

		/**
		 * Runs on the UI thread after doInBackground(Params...) when the task
		 * is finished or cancelled.
		 */
		@Override
		public void onFinish() {
			super.onFinish();
			LogTool.i("onFinish()");
		}
	};
}
