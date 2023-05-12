package com.example.gamefish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class GameUI {
    public Context context;
    public int score;
    public GameUI(Context context)
    {
        this.context=context;
    }
    public void update()
    {

    }
    public void draw(Canvas canvas)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.point);
        Bitmap bitmap1= BitmapFactory.decodeResource(context.getResources(),R.drawable.anhsang);
        canvas.drawBitmap(bitmap,50,50,null);
        canvas.drawBitmap(bitmap1,60,50,null);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, androidx.cardview.R.color.cardview_light_background);
        paint.setColor(color);
        paint.setTextSize(60);
        canvas.drawText(""+score+"           POINT",
                100, 50+bitmap.getHeight()/2+10, paint);
    }
}
