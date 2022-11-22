package com.mirim.bokrim;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import  com.mirim.bokrim.GraphicOverlay.Graphic;

/**
 * Draw camera image to background.
 */
public class ImageGraphic extends Graphic {

    private final Bitmap bitmap;

    public ImageGraphic( com.mirim.bokrim.GraphicOverlay overlay, Bitmap bitmap) {
        super(overlay);
        this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getTransformationMatrix(), null);
    }
}
