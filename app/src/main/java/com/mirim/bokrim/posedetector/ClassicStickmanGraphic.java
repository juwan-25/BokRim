package com.mirim.bokrim.posedetector;

import android.graphics.Canvas;
import android.graphics.Paint;

import  com.mirim.bokrim.GraphicOverlay;

public class ClassicStickmanGraphic extends StickmanGraphic {

    ClassicStickmanGraphic(GraphicOverlay overlay, PosePositions posePositions, int accessoryID, int accessoryType, Paint stickmanPaint) {
        super(overlay, posePositions, accessoryID, accessoryType, stickmanPaint);
    }

    @Override
    public void draw(Canvas canvas) {
        if (posePositions.isEmpty()) {
            return;
        }

        drawAccessory(canvas);
    }
}