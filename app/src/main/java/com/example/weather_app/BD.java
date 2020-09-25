package com.example.weather_app;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

class BD extends BitmapDrawable {
    private static final String TAG = "";
    private Rect mSelection;

    public BD(Resources res, Bitmap bitmap) {
        super(res, bitmap);
        mSelection = new Rect(20, 20, 60, 60);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG, "draw " + canvas.getMatrix());
        canvas.clipRect(mSelection, Region.Op.DIFFERENCE);
        canvas.drawColor(0x66000000);
    }
}