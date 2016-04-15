package com.klowerbase.test;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.klower.animation.Animation;
import com.klower.animation.AnimationListener;
import com.klower.animation.BlindAnimation;
import com.klower.animation.BlinkAnimation;
import com.klower.animation.BounceAnimation;
import com.klower.animation.ExplodeAnimation;
import com.klower.animation.FadeInAnimation;
import com.klower.animation.FadeOutAnimation;
import com.klower.animation.FlipHorizontalAnimation;
import com.klower.animation.FlipHorizontalToAnimation;
import com.klower.animation.FlipVerticalAnimation;
import com.klower.animation.FlipVerticalToAnimation;
import com.klower.animation.FoldAnimation;
import com.klower.animation.FoldLayout.Orientation;
import com.klower.animation.HighlightAnimation;
import com.klower.animation.ParallelAnimator;
import com.klower.animation.ParallelAnimatorListener;
import com.klower.animation.PathAnimation;
import com.klower.animation.PuffInAnimation;
import com.klower.animation.PuffOutAnimation;
import com.klower.animation.RotationAnimation;
import com.klower.animation.ScaleInAnimation;
import com.klower.animation.ScaleOutAnimation;
import com.klower.animation.ShakeAnimation;
import com.klower.animation.SlideInAnimation;
import com.klower.animation.SlideInUnderneathAnimation;
import com.klower.animation.SlideOutAnimation;
import com.klower.animation.SlideOutUnderneathAnimation;
import com.klower.animation.TransferAnimation;
import com.klower.animation.UnfoldAnimation;

public class AnimationListActivity extends Activity {

	ListView mListView;
	String[] strs = new String[] { "nothing", "Blind", "Blink", "Bounce",
			"Explode", "Fade In", "Fade Out", "Flip Horizontal",
			"Flip Horizontal To", "Flip Vertical", "Flip Vertical To",
			"Fold In", "Fold Out", "Highlight", "Path", "Puff In", "Puff Out",
			"Rotate", "Scale In", "Scale Out", "Shake", "Slide In",
			"Slide In Underneath", "Slide Out", "Slide Out Underneath",
			"Transfer", "Parallel Animator" };
	private ImageView card, card2;
	private FrameLayout mFrameLayout;
	private boolean isFinished = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animationtest);
		card = (ImageView) findViewById(R.id.imgTarget);
		card2 = (ImageView) findViewById(R.id.imgBehind);
		mFrameLayout = (FrameLayout) findViewById(R.id.animation_layout);

		mListView = (ListView) findViewById(R.id.function_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1);
		for (int i = 0; i < strs.length; i++) {
			adapter.add(strs[i]);
		}
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				mFrameLayout.setVisibility(View.VISIBLE);
				card.setVisibility(View.VISIBLE);
				card.clearAnimation();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						switch (position) {
						case 1:
							new BlindAnimation(card).animate();
							isFinished = true;
							break;
						case 2:
							new BlinkAnimation(card).setListener(
									new AnimationListener() {
										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;
						case 3:
							new BounceAnimation(card).setNumOfBounces(3)
									.setDuration(Animation.DURATION_SHORT)
									.setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;
						case 4:
							new ExplodeAnimation(card).setListener(new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									
								}
							}).animate();
							isFinished = true;
							break;
						case 5:
							new FadeInAnimation(card).setListener(new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									
								}
							}).animate();
							isFinished = true;
							break;
						case 6:
							new FadeOutAnimation(card).setListener(new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									
								}
							}).animate();
							isFinished = true;
							break;
						case 7:
							new FlipHorizontalAnimation(card).setListener(
									new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;
						case 8:
							new FlipHorizontalToAnimation(card).setFlipToView(card2)
									.setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).setInterpolator(new LinearInterpolator())
									.animate();
							isFinished = true;
							break;
						case 9:
							new FlipVerticalAnimation(card).setListener(
									new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;
						case 10:
							new FlipVerticalToAnimation(card).setFlipToView(card2)
									.setInterpolator(new LinearInterpolator())
									.setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							isFinished = true;
							break;
						case 11:
							card2.setVisibility(View.VISIBLE);
							new UnfoldAnimation(card).setNumOfFolds(10)
									.setDuration(1000)
									.setOrientation(Orientation.HORIZONTAL).setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							isFinished = true;
							break;
						case 12:
							card2.setVisibility(View.VISIBLE);
							new FoldAnimation(card).setNumOfFolds(10).setDuration(1000)
									.setOrientation(Orientation.VERTICAL).setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							isFinished = true;
							break;
						case 13:
							new HighlightAnimation(card).setListener(
									new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;
						case 14:
							ArrayList<Point> points = new ArrayList<Point>();
							points.add(new Point(0, 100));
							points.add(new Point(50, 0));
							points.add(new Point(100, 100));
							points.add(new Point(0, 50));
							points.add(new Point(100, 50));
							points.add(new Point(0, 100));
							points.add(new Point(50, 50));
							new PathAnimation(card).setPoints(points).setDuration(2000)
									.setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;

						case 15:
							new PuffInAnimation(card).setListener(new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									
								}
							}).animate();
							isFinished = true;
							break;
						case 16:
							new PuffOutAnimation(card).setListener(new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									
								}
							}).animate();
							isFinished = true;
							break;
						case 17:
							new RotationAnimation(card)
									.setPivot(RotationAnimation.PIVOT_TOP_LEFT)
									.setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;

						case 18:
							new ScaleInAnimation(card).animate();
							isFinished = true;
							break;
						case 19:
							new ScaleOutAnimation(card).animate();
							isFinished = true;
							break;
						case 20:
							new ShakeAnimation(card).setNumOfShakes(3)
									.setDuration(Animation.DURATION_SHORT)
									.setListener(new AnimationListener() {

										@Override
										public void onAnimationEnd(Animation animation) {
											
										}
									}).animate();
							break;

						case 21:
							new SlideInAnimation(card).setDirection(
									Animation.DIRECTION_UP).animate();
							isFinished = true;
							break;
						case 22:
							new SlideInUnderneathAnimation(card).setDirection(
									Animation.DIRECTION_DOWN).animate();
							isFinished = true;
							break;
						case 23:
							new SlideOutAnimation(card).setDirection(
									Animation.DIRECTION_LEFT).animate();
							isFinished = true;
							break;
						case 24:
							new SlideOutUnderneathAnimation(card).setDirection(
									Animation.DIRECTION_RIGHT).animate();
							isFinished = true;
							break;
						case 25:
							new TransferAnimation(card).setDestinationView(card2).animate();
							break;
						case 26:

							final AnimationListener explodeAnimListener = new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									card.setVisibility(View.INVISIBLE);
									
								}
							};

							final AnimationListener bounceAnimListener = new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation animation) {
									new ExplodeAnimation(card2).setListener(
											explodeAnimListener).animate();
								}
							};

							final ParallelAnimatorListener slideFadeInAnimListener = new ParallelAnimatorListener() {

								@Override
								public void onAnimationEnd(
										ParallelAnimator parallelAnimator) {
									BounceAnimation bounceAnim = new BounceAnimation(
											card2);
									bounceAnim.setNumOfBounces(10);
									bounceAnim.setListener(bounceAnimListener);
									bounceAnim.animate();
								}
							};

							final ParallelAnimatorListener slideFadeOutAnimListener = new ParallelAnimatorListener() {

								@Override
								public void onAnimationEnd(
										ParallelAnimator parallelAnimator) {
									ParallelAnimator slideFadeInAnim = new ParallelAnimator();
									slideFadeInAnim.add(new SlideInAnimation(card2)
											.setDirection(Animation.DIRECTION_RIGHT));
									slideFadeInAnim.add(new FadeInAnimation(card2));
									slideFadeInAnim.setDuration(1000);
									slideFadeInAnim
											.setListener(slideFadeInAnimListener);
									slideFadeInAnim.animate();
								}
							};

							final ParallelAnimatorListener rotatePathAnimListener = new ParallelAnimatorListener() {

								@Override
								public void onAnimationEnd(
										ParallelAnimator parallelAnimator) {
									ParallelAnimator slideFadeOutAnim = new ParallelAnimator();
									slideFadeOutAnim.add(new SlideOutAnimation(card)
											.setDirection(Animation.DIRECTION_RIGHT));
									slideFadeOutAnim.add(new FadeOutAnimation(card));
									slideFadeOutAnim
											.setInterpolator(new LinearInterpolator());
									slideFadeOutAnim.setDuration(1000);
									slideFadeOutAnim
											.setListener(slideFadeOutAnimListener);
									slideFadeOutAnim.animate();
								}
							};

							final ParallelAnimatorListener scaleFlipAnimListener = new ParallelAnimatorListener() {

								@Override
								public void onAnimationEnd(
										ParallelAnimator parallelAnimator) {
									ArrayList<Point> parallelPoints = new ArrayList<Point>();
									parallelPoints.add(new Point(50, 0));
									parallelPoints.add(new Point(100, 50));
									parallelPoints.add(new Point(50, 100));
									parallelPoints.add(new Point(0, 50));
									parallelPoints.add(new Point(50, 50));
									ParallelAnimator rotatePathAnim = new ParallelAnimator();
									rotatePathAnim.add(new PathAnimation(card)
											.setPoints(parallelPoints));
									rotatePathAnim.add(new RotationAnimation(card));
									rotatePathAnim
											.setInterpolator(new LinearInterpolator());
									rotatePathAnim.setDuration(2000);
									rotatePathAnim.setListener(rotatePathAnimListener);
									rotatePathAnim.animate();
								}
							};

							ParallelAnimator scaleFlipAnim = new ParallelAnimator();
							scaleFlipAnim.add(new ScaleInAnimation(card));
							scaleFlipAnim.add(new FlipHorizontalAnimation(card));
							scaleFlipAnim.add(new FlipVerticalAnimation(card));
							scaleFlipAnim.setDuration(2000);
							scaleFlipAnim.setListener(scaleFlipAnimListener);
							scaleFlipAnim.animate();
							break;
						default:
							break;

						}
					}
				}, 500);
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						
					}
				}, 10000);
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		if(mFrameLayout.getVisibility() == View.VISIBLE){
			mFrameLayout.setVisibility(View.GONE);
			return;
		}
		super.onBackPressed();
	}

}
