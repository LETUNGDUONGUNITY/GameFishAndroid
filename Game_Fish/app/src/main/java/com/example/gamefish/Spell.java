package com.example.gamefish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Spell extends Player {
    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public Context context;
    public double angle;
    public Spell(Context context, Cannon cannon) {
        super(
                context,
                cannon.getPositionX(),
                cannon.getPositionY()
        );
        this.context=context;
        positionX+=cannon.directionX*10;
        positionY+=cannon.directionY*10;
        velocityX= cannon.directionX*MAX_SPEED;
        velocityY=cannon.directionY*MAX_SPEED;
        angle= cannon.angle;
    }

    @Override
    public void update() {
        positionX = positionX + velocityX;
        positionY = positionY + velocityY;
    }
    @Override
    public void draw(Canvas canvas)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet3);
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        Bitmap scaledMap= Bitmap.createScaledBitmap(bitmap,width,height,true);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);
        radius=width*width+height*height;

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledMap, 0, 0, scaledMap.getWidth(), scaledMap.getHeight(), matrix, true);
        canvas.drawBitmap(rotatedBitmap,
                (float) positionX,
                (float) positionY,
                null);
    }
    public boolean isOutSide(int width, int height)
    {
        return positionX<0 || positionX>width ||positionY<0 ||positionY>height;
    }

}