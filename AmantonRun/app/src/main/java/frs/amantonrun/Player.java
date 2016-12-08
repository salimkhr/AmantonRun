package frs.amantonrun;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by mindness on 08/12/2016.
 */

public class Player {
    static int x;
    static int y;
    static int gravity =1;
    static int speed =1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    static int playerheight;
    static int playerwidth;
    static int jumppower = -15;
    Bitmap bmp;
    GameView gameview;

    public Player(GameView gameview, Bitmap bmp, int x,int y) {
        this.x=x;
        this.y=y;
        this.bmp = bmp;
        this.gameview = gameview;
        playerheight = bmp.getHeight();
    }

    public void update(){
        checkground();
    }

    private void checkground() {
        if (y < gameview.getHeight()-64-playerheight){
            speed += gravity;
            if(y > gameview.getHeight()-64-playerheight+speed){
                speed = gameview.getHeight()-64-y-playerheight;
            }
        }else if (speed != 0)
        {
            speed =0;
            y = gameview.getHeight()-64-playerheight;
        }

        y += speed;
    }

    public void onTouch(){
        if(y >= gameview.getHeight()-64-playerheight){
            speed = jumppower;
        }
    }
    public void onDraw(Canvas c){
        update();
        c.drawBitmap(bmp,x,y,null);
    }

}
