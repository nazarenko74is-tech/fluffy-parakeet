package com.segamaster.desktop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public final class SegaMasterAsset {
    private SegaMasterAsset() {}
    private static final String PNG_B64 = "iVBORw0KGgoAAAANSUhEUgAAAgsAAALECAYAAABnYk4YAAAgAElEQVR4nOzdeZRcZZ3v//e5J0kz...";
    public static Bitmap get(Context context) {
        byte[] bytes = Base64.decode(PNG_B64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
