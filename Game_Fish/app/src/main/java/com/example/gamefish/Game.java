package com.example.gamefish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private final Cannon cannon;
    public Context mainContext;
    public Performance performance;
    public background background;
    public GameUI gameUI;
    private int numberOfSpellsToCast = 0;
    private int joystickPointerId = 0;
    private ArrayList<Spell> spellList= new ArrayList<Spell>();
    private ArrayList<Enemy> enemies= new ArrayList<Enemy>();
    public double width;
    public double height;
    public double leftConstraint;
    public double rightConstraint;
    public int velocity;
    public double randomX;
    public double randomY;
    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        Log.d("MainActivity.java", "Game Start()");
        gameLoop = new GameLoop(this, surfaceHolder);
        mainContext=context;
        gameUI=new GameUI(context);
        background= new background(context);
        performance= new Performance(context,gameLoop);
           cannon= new Cannon(2*500,500,context);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, cannon);
        setFocusable(true);
    }

    public void pause() {
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        width= getWidth();
        height=getHeight();
        gameLoop.startLoop();
        leftConstraint = 300;
        rightConstraint=width-300;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public  void update() {

        cannon.update();
        if (Enemy.readyToSpawn()) {
            double posX = getRandomX();
            double posY =getRandomY();
            double direction;
            direction = (posX == leftConstraint) ? 1 : -1;
            Enemy enemy= new Enemy(getContext(),posX,posY,direction);
            enemies.add(enemy);
        }
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        while (numberOfSpellsToCast > 0) {
            spellList.add(new Spell(getContext(), cannon));
            numberOfSpellsToCast--;
        }
        for (Spell spell : spellList) {
            spell.update();
        }

        Iterator<Enemy> iteratorEnemy = enemies.iterator();
        while (iteratorEnemy.hasNext()) {
            Enemy enemy = iteratorEnemy.next();
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Spell spell = iteratorSpell.next();
                if (spell.isOutSide((int)width, (int)height)) {
                    iteratorSpell.remove();
                    break;

                }
                if(Player.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    gameUI.score+=1;
                    break;
                }
            }
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        performance.draw(canvas);
        background.draw(canvas);
        gameUI.draw(canvas);
        for (Spell spell : spellList) {
            spell.draw(canvas);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
        cannon.draw(canvas);

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                numberOfSpellsToCast++;
                cannon.SetRotate(event.getX(),event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                numberOfSpellsToCast++;
                cannon.SetRotate(event.getX(),event.getY());
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    cannon.setIsPressed(false);
                    cannon.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    public double getRandomX()
    {
        Random random = new Random();
        int value = random.nextInt(2);

        if(value==0)
        {
            return rightConstraint;

        }
        else {
            return leftConstraint;
        }
    }
    public double getRandomY()
    {
        Random random =new Random();
        return random.nextDouble()*500;
    }
}
