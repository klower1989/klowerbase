/**
 * All rights reserved
 */

package com.klower.gif;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2012-5-17
 */
public class GifView extends View implements GifAction {
	private GifDecoder gifDecoder = null;

	private Bitmap currentImage = null;

	private boolean pause = false;

	private int showWidth = -1;

	private Rect rect = null;

	private DrawThread drawThread = null;

	private GifAnimationListener gifAnimationListener = null;

	private GifImageType animationType = GifImageType.SYNC_DECODER;

	static public int K_PLUG = 0;

	static public int K_AC = 1;

	private int fragmeCount;

	private Handler redrawHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d("Gif view", "Gif frame index: " + msg.arg1 + "count: "
					+ gifDecoder.getFrameCount());
			if (msg.arg1 == gifDecoder.getFrameCount()) {
				gifAnimationListener.onGifAnimationEnd();
			}
			GifView.this.invalidate();
		}
	};

	public GifView(Context context) {
		super(context);
		destroyBitmap();
		gifAnimationListener.onGifAnimationEnd();

	}

	public GifView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private void setGifDecoderImage(byte[] gif) {
		if (this.gifDecoder != null) {
			this.gifDecoder.free();
			this.gifDecoder = null;
		}
		this.gifDecoder = new GifDecoder(gif, this);
		this.gifDecoder.start();
	}

	private void setGifDecoderImage(InputStream is, int key) {
		/*
		 * if (this.gifDecoder != null) { this.gifDecoder.free();
		 * this.gifDecoder = null; } this.gifDecoder = new GifDecoder(is, this);
		 * this.gifDecoder.start();
		 */
		if (key == K_PLUG) {
			if (this.gifDecoder == null)
				this.gifDecoder = GifDecoder.getPlugInstance(is, this);
		} else if (key == K_AC) {
			if (this.gifDecoder == null)
				this.gifDecoder = GifDecoder.getClimateInstance(is, this);
		}

	}

	public void setGifImage(byte[] gif) {
		setGifDecoderImage(gif);
	}

	public void setGifImage(InputStream is, int key) {
		setGifDecoderImage(is, key);
	}

	public void setGifImage(int resId, int key) {
		Resources r = getResources();
		InputStream is = null;
		// try {
		is = r.openRawResource(resId);
		setGifDecoderImage(is, key);
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// is.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

	}

	public void setGifAnimationListener(GifAnimationListener listener) {
		gifAnimationListener = listener;
	}

	private void destroyBitmap() {
		if (null != this.currentImage && !this.currentImage.isRecycled()) {
			this.currentImage.recycle();
		}
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (this.gifDecoder == null)
			return;
		if (this.currentImage == null) {
			this.currentImage = this.gifDecoder.getImage();
		}
		if (this.currentImage == null) {
			return;
		}
		int saveCount = canvas.getSaveCount();
		canvas.save();
		canvas.translate(getPaddingLeft(), getPaddingTop());
		if (this.showWidth == -1)
			canvas.drawBitmap(this.currentImage, 0.0F, 0.0F, null);
		else {
			canvas.drawBitmap(this.currentImage, null, this.rect, null);
		}
		canvas.restoreToCount(saveCount);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int pleft = getPaddingLeft();
		int pright = getPaddingRight();
		int ptop = getPaddingTop();
		int pbottom = getPaddingBottom();
		int h;
		int w;
		if (this.gifDecoder == null) {
			w = 1;
			h = 1;
		} else {
			w = this.gifDecoder.width;
			h = this.gifDecoder.height;
		}

		w += pleft + pright;
		h += ptop + pbottom;

		w = Math.max(w, getSuggestedMinimumWidth());
		h = Math.max(h, getSuggestedMinimumHeight());

		int widthSize = resolveSize(w, widthMeasureSpec);
		int heightSize = resolveSize(h, heightMeasureSpec);

		setMeasuredDimension(widthSize, heightSize);
	}

	public void showCover() {
		if (this.gifDecoder == null)
			return;
		this.pause = true;
		this.currentImage = this.gifDecoder.getImage();
		invalidate();
	}

	public void showAnimation() {
		if (this.pause)
			this.pause = false;
	}

	public void setGifImageType(GifImageType type) {
		if (this.gifDecoder == null)
			this.animationType = type;
	}

	public void setShowDimension(int width, int height) {
		if ((width > 0) && (height > 0)) {
			this.showWidth = width;
			this.rect = new Rect();
			this.rect.left = 0;
			this.rect.top = 0;
			this.rect.right = width;
			this.rect.bottom = height;
		}
	}

	public void startGifAnimation() {
		if (this.gifDecoder.getFrameCount() > 1) {

			if (drawThread == null) {
				drawThread = new DrawThread();
				drawThread.start();
			}

		}
	}

	public void stopGifAnimation() {
		if (this.gifDecoder.getFrameCount() > 1) {
			Log.d(this.getClass().getName(), "stopGifAnimation:" + drawThread
					+ "currentclass:" + this);
			if (drawThread != null) {
				drawThread.stopThread();
				drawThread = null;
			}
		}
	}

	public void parseOk(boolean parseStatus, int frameIndex) {
		if (parseStatus)
			if (this.gifDecoder != null)
				switch (this.animationType.ordinal()) {
				case 1:
					if (frameIndex != -1)
						break;
					if (this.gifDecoder.getFrameCount() == 1) {
						GifView.this.currentImage = this.gifDecoder.getImage();
					}
					/*
					 * if (this.gifDecoder.getFrameCount() > 1) { DrawThread dt
					 * = new DrawThread(); dt.start(); break; }
					 */
					reDraw();

					break;
				case 3:
					if (frameIndex == 1) {
						this.currentImage = this.gifDecoder.getImage();
						reDraw();
						break;
					}
					if (frameIndex != -1)
						break;
					/*
					 * if (this.gifDecoder.getFrameCount() > 1) { if
					 * (this.drawThread != null) break; this.drawThread = new
					 * DrawThread(); this.drawThread.start(); break; }
					 */

					reDraw();

					break;
				case 2:
					if (frameIndex == 1) {
						this.currentImage = this.gifDecoder.getImage();
						reDraw();
						break;
					}
					if (frameIndex == -1) {
						reDraw();
						break;
					}
					/*
					 * if (this.drawThread != null) break; this.drawThread = new
					 * DrawThread(); this.drawThread.start();
					 */

					break;
				default:
					break;
				}
	}

	private void reDraw() {
		if (this.redrawHandler != null) {
			Message msg = this.redrawHandler.obtainMessage();
			this.redrawHandler.sendMessage(msg);
		}
	}

	private class DrawThread extends Thread {
		public boolean isRun = false;

		private DrawThread() {
			GifView.this.gifDecoder.reset();
		}

		@Override
		public void run() {
			if (GifView.this.gifDecoder == null) {
				return;
			}
			while (isRun) {
				if (!GifView.this.pause) {
					GifFrame frame = GifView.this.gifDecoder.next();
					if (frame != null) {
						GifView.this.currentImage = frame.image;
						long sp = frame.delay;
						if (GifView.this.redrawHandler == null)
							break;
						Message msg = GifView.this.redrawHandler
								.obtainMessage();
						msg.arg1 = frame.frameIndex;
						GifView.this.redrawHandler.sendMessage(msg);
						SystemClock.sleep(sp);
					}
				} else {
					SystemClock.sleep(10L);
				}
			}
		}

		@Override
		public void start() {
			isRun = true;
			super.start();
		}

		public void stopThread() {
			isRun = false;
		}

	}

	public static enum GifImageType {
		WAIT_FINISH(0),

		SYNC_DECODER(1),

		COVER(2);

		final int nativeInt;

		private GifImageType(int i) {
			this.nativeInt = i;
		}
	}
}
