package com.example.gamefish;

import android.content.Context;
import android.graphics.Canvas;

public class Player extends GameObject {
    public double radius;
    public double getRadius()
    {
        return  radius;
    }
    public Player(Context context, double positionX, double positionY)
    {
        super(positionX,positionY);
    }

    public static boolean isColliding(Spell spell, Enemy enemy) {
        double distance = getDistanceBetweenObjects(spell, enemy);
        if (distance < 100)
            return true;
        else
            return false;
    }

    public void draw(Canvas canvas,GameDisplay gameDisplay)
    {

    }

    @Override
    public void update() {

    }
}
