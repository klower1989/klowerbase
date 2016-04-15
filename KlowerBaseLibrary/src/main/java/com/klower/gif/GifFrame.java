/**
 * All rights reserved
 */

package com.klower.gif;

import android.graphics.Bitmap;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2012-5-17
 */

public class GifFrame {
    public Bitmap image;

    public int delay;
    
    public int frameIndex;

    public GifFrame nextFrame = null;

    public GifFrame(Bitmap im, int del, int index) {
        this.image = im;
        this.delay = del;
        this.frameIndex = index;
    }
}
