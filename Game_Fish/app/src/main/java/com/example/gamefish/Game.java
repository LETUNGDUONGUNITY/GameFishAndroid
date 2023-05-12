package com.example.gamefish;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
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
    private int numberOfSpellsToCast = 0;
    private int joystickPointerId = 0;
    private  final  GameDisplay gameDisplay;
    private ArrayList<Spell> spellList= new ArrayList<Spell>();
    private ArrayList<Enemy> enemies= new ArrayList<Enemy>();
    public double width;
    public double height;
    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        Log.d("MainActivity.java", "Game Start()");
        gameLoop = new GameLoop(this, surfaceHolder);
        mainContext=context;
        background= new background(context);
        performance= new Performance(context,gameLoop);
        cannon= new Cannon(2*500,500,context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, cannon);
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
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public  void update() {

        cannon.update();
        gameDisplay.update();
        if (Enemy.readyToSpawn()) {
            double posX = width - 300;
            Random random = new Random();
            int position = random.nextInt(500);
            double posY = position;
            enemies.add(new Enemy(getContext(), posX, posY));
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
//        Iterator<Spell> iteratorSpell = spellList.iterator();
//        while (iteratorSpell.hasNext()) {
//            Spell spell = iteratorSpell.next();
//            // Remove enemy if it collides with a spell
//            if (spell.isOutSide(gameDisplay.widthPixels,gameDisplay.heightPixels)) {
//                iteratorSpell.remove();
//                break;
//            }
//        }
        Iterator<Enemy> iteratorEnemy = enemies.iterator();
        while (iteratorEnemy.hasNext()) {
            Enemy enemy = iteratorEnemy.next();
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Spell spell = iteratorSpell.next();
                if (spell.isOutSide(gameDisplay.widthPixels, gameDisplay.heightPixels)) {
                    iteratorSpell.remove();
                    break;

                }
                if(Player.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
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
        cannon.draw(canvas,gameDisplay);
        for (Spell spell : spellList) {
            spell.draw(canvas,gameDisplay);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(canvas, gameDisplay);
        }

    }
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

}
