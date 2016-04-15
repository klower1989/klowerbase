

package com.klower.richedittext;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;

public class AgsUnderlineSpan extends UnderlineSpan implements UpdateAppearance, ParcelableSpan {
    public AgsUnderlineSpan() {
    }

    public AgsUnderlineSpan(Parcel src) {
    }

    public int getSpanTypeId() {
        return 6;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(true);
    }
}
