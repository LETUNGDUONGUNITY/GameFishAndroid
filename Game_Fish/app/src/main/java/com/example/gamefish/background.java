package com.example.gamefish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class background {
    public Canvas _canvas;
    public Context _context;
    public  background(Context context)
    {
        this._context=context;
    }
    public background(Canvas canvas,Context context)
    {
        _canvas=canvas;
        _context=context;
    }
    public void draw(Canvas canvas)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(_context.getResources(), R.drawable.bg);

// vẽ bitmap full màn hình
        Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(bitmap, null, rect, null);
    }
}
