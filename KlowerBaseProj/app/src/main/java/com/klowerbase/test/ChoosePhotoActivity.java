package com.klowerbase.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.klower.adapter.GridViewPicAdapter;
import com.klower.adapter.GridViewPicsAdapter;
import com.klower.mutiphotochooser.ChoosePhotoAlbumListActivity;
import com.klower.titlebar.BaseException;
import com.klower.utilities.LogTool;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ChoosePhotoActivity extends ActionBarActivity {

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    GridView mPhotoGridView;

    GridViewPicsAdapter mAdapter;

//	GridViewPicAdapter mGridViewPicAdapter;

    @Override
    protected void onCreateEqually(Bundle savedInstanceState)
            throws BaseException {
        // TODO Auto-generated method stub
        super.onCreateEqually(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        options = Utils.getNormalDisplayImageOptions(R.drawable.empty_photo);
        setCustomContentView(R.layout.choose_photo);
        // photo = (ImageView) findViewById(R.id.Photo);
        // photo.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View arg0) {
        // showAddPhotoDialog();
        // }
        // });
//		mGridViewPicAdapter = new GridViewPicAdapter(this, 5,R.drawable.lh_advice_default);

        mPhotoGridView = (GridView) findViewById(R.id.gridview);
        mAdapter = new GridViewPicsAdapter(this);
        mAdapter.setLimitSize(7);
        mPhotoGridView.setAdapter(mAdapter);
        mAdapter.addData(null);
        mPhotoGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == mAdapter.getCount() - 1
                        && mAdapter.getData().get(arg2) == null) {
                    showAddPhotoDialog();
                }
            }
        });
    }

    private void showAddPhotoDialog() {
        String[] numbers = new String[]{"Take a photo", "Choose a photo from Local",
                "ChooseMutiPhoto", "Choose a photo", "Cancel"};
        new AlertDialog.Builder(ChoosePhotoActivity.this).setSingleChoiceItems(
                numbers, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                startTakePhoto(0);
                                break;
                            case 1:
                                startGetPhoto(1);
                                break;
                            case 2:
                                Intent intent = new Intent(
                                        ChoosePhotoActivity.this,
                                        ChoosePhotoAlbumListActivity.class);
                                intent.putExtra("choosePhotoType", 2);
                                startActivityForResult(intent, 2);
                                break;
                            case 3:
                                Intent intent2 = new Intent(
                                        ChoosePhotoActivity.this,
                                        ChoosePhotoAlbumListActivity.class);
                                intent2.putExtra("choosePhotoType", 1);
                                startActivityForResult(intent2, 2);
                                break;
                        }
                    }
                }).show();

    }

    private void processCameraPhoto(final String path, final int requestCode,
                                    Bitmap bitmap) {
        final int degree = readPictureDegree(path);
        ImageSize imageSize = new ImageSize(
                Utils.getScreenWidth(ChoosePhotoActivity.this) / 4,
                Utils.getScreenHeight(ChoosePhotoActivity.this) / 4, degree);
        imageLoader.loadImage("file://" + path, imageSize, options,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        mAdapter.addData(loadedImage);
                        // sendFileUpload(loadedImage);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        // dialog.dismiss();
                    }
                });
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;

    }

    private Uri outputFileUri;

    String path = Environment.getExternalStorageDirectory() + "/smallisto";

    private void startTakePhoto(int requestCode) {
        File photoFile = new File(path);
        if (!photoFile.exists()) {
            photoFile.mkdirs();
        }
        String photoName = path + "/" + System.currentTimeMillis() + ".png";
        File file = new File(photoName);
        if (file.exists()) {
            file.delete();
        }
        outputFileUri = Uri.fromFile(file);

        Intent intentFromCapture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intentFromCapture, requestCode);
    }

    private void startGetPhoto(int requestCode) {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候 if (resultCode != RESULT_CANCELED) {
        switch (requestCode) {
            case 0:
                // if (data != null) {
                if (resultCode == -1) {
                    processCameraPhoto(Utils.getFliePath(ChoosePhotoActivity.this,
                            outputFileUri, false), requestCode, null);
                }
                break;
            case 1:
                if (data != null) {
                    if (data.getData() != null) {
                        processCameraPhoto(
                                Utils.getFliePath(ChoosePhotoActivity.this,
                                        data.getData(), true), requestCode, null);
                    } else if (data.getExtras() != null) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        processCameraPhoto(
                                Utils.getFliePath(ChoosePhotoActivity.this,
                                        data.getData(), true), requestCode, photo);
                    }
                    // startPhotoZoom(data.getData());
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        ArrayList<String> photos = data
                                .getStringArrayListExtra("photos");
//					ArrayList<String> newPhotos = new ArrayList<String>();
                        for (int i = 0; i < photos.size(); i++) {
//						newPhotos.add("file://" +photos.get(i));
                            LogTool.d("Photos", "photo: " + photos.get(i));
                            processCameraPhoto(photos.get(i), requestCode, null);
                        }
//					mPhotoGridView.setAdapter(mGridViewPicAdapter);
//					mGridViewPicAdapter.addImgsData(newPhotos);
//					mPhotoGridView.setOnItemClickListener(new OnItemClickListener() {
//
//						@Override
//						public void onItemClick(AdapterView<?> arg0, View arg1,
//								int arg2, long arg3) {
//							
//						}
//					});


                    }
                }
                break;
        }
    }
}
