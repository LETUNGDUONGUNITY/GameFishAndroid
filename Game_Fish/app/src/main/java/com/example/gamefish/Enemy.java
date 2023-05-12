package com.example.gamefish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

public class Enemy extends Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 100;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    public Context context;
    public int randomNum;
    public int direction;
    public float angle;
    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;

            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }
    }


    public Enemy(Context context,double posX,double posY,double direction) {
        super(context,posX, posY);
        this.context=context;
        velocityX= direction*randomValue()*10;
        velocityY=0;
        this.direction =(int)direction;
        Random random= new Random();
        randomNum = random.nextInt(3) + 1;

    }
    double randomValue()
    {
        double min = 0.6;
        double max = 0.7;
        Random random = new Random();
        double number = min + (max - min) * random.nextDouble();
        return  number;
    }
    int ReturnIdImage()
    {
        if(direction<0)
        switch (randomNum)
        {
            case 1:
                return R.drawable.fish1;
            case 2:
                return R.drawable.fish2;

            case 3:
               return R.drawable.fish3;

        }
        else
        {
            switch (randomNum)
            {
                case 1:
                    return R.drawable.leftfish1;
                case 2:
                    return R.drawable.leftfish2;
                case 3:return R.drawable.leftfish4;
            }
        }
        return R.drawable.fish1;
    }
    @Override
    public void draw(Canvas canvas)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), ReturnIdImage());
        int width=(bitmap.getWidth()<300) ?bitmap.getWidth():bitmap.getWidth()/2;
        int height=(bitmap.getHeight()<300)?bitmap.getHeight():bitmap.getHeight()/2;
        Matrix matrix=new Matrix();
        matrix.setRotate(0);
        Bitmap scaledMap= Bitmap.createScaledBitmap(bitmap,width,height,true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledMap, 0, 0, scaledMap.getWidth(), scaledMap.getHeight(), matrix, true);
        canvas.drawBitmap(rotatedBitmap,(float) positionX,(float) positionY,null);
        radius = Math.sqrt(width*width+height*height);
    }

    @Override
    public void update() {
        positionX = positionX + velocityX;
        positionY = positionY + velocityY;
    }
}
