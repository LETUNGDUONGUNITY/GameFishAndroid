package com.example.gamefish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Enemy extends Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    public Context context;
    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;

            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }
    }
    public Enemy(Context context,double posX,double posY) {
        super(context,(double) posX,(double) posY);
        this.context=context;
        velocityX= -1*randomValue()*10;
        velocityY=0;

    }
    double randomValue()
    {
        double min = 0.6;
        double max = 0.7;
        Random random = new Random();
        double number = min + (max - min) * random.nextDouble();
        return  number;
    }
    @Override
    public void draw(Canvas canvas,GameDisplay gameDisplay)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish1);
        int width=bitmap.getWidth()/2;
        int height=bitmap.getHeight()/2;
        Bitmap scaledMap= Bitmap.createScaledBitmap(bitmap,width,height,true);
        canvas.drawBitmap(scaledMap,(float) positionX,(float) positionY,null);
        radius = Math.sqrt(width*width+height*height);
    }
    public Bitmap RandomImage()
    {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        switch (randomNum)
        {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
    @Override
    public void update() {
        positionX = positionX + velocityX;
        positionY = positionY + velocityY;
    }
}
