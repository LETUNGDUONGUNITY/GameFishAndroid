package com.example.gamefish;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.Context;


public class Cannon extends GameObject {


    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;
    private double touchX;
    private double touchY;
    public double angle;
    private Context context;
    private Bitmap bitmap;


    public Cannon(int centerPositionX, int centerPositionY,Context context) {
        this.context=context;
       // bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.my_image);
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cannon2);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int left = (canvasWidth - bitmapWidth) / 2;
        int top = canvasHeight - bitmapHeight;
        angle = (float) Math.toDegrees(Math.atan2(touchY - top - bitmapHeight / 2, touchX - left - bitmapWidth / 2));
        angle+=90;
        double distance=Utils.getDistanceBetweenPoints(left,top,touchX,touchY);
        positionX= left;
        positionY=top;
        directionY= (touchY - top - bitmapHeight / 2)/distance;
        directionX= (touchX - left - bitmapWidth / 2)/distance;
        float centerX = left + bitmapWidth / 2.0f;
        float centerY = top + bitmapHeight / 2.0f;
        canvas.save(); // Lưu trạng thái canvas trước khi xoay
        canvas.rotate((float)angle, centerX, centerY);

        // Vẽ bitmap vào canvas
        canvas.drawBitmap(bitmap, left, top, null);

        // Khôi phục trạng thái canvas sau khi vẽ xong
        canvas.restore();

    }

    public void update()
    {
        UpdateRotate();

    }

   private void UpdateRotate()
   {

   }

    private void updateInnerCirclePosition() {
//        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
//        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);

    }

    public void setActuator(double touchPositionX, double touchPositionY) {
//        double deltaX = touchPositionX - outerCircleCenterPositionX;
//        double deltaY = touchPositionY - outerCircleCenterPositionY;
//        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
//
//        if(deltaDistance < outerCircleRadius) {
//            actuatorX = deltaX/outerCircleRadius;
//            actuatorY = deltaY/outerCircleRadius;
//        } else {
//            actuatorX = deltaX/deltaDistance;
//            actuatorY = deltaY/deltaDistance;
//        }
    }



    public boolean getIsPressed() {
        return isPressed;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
    public void SetRotate(float touchX, float touchY)
    {
        this.touchX=touchX;
        this.touchY=touchY;
    }
    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }
}
