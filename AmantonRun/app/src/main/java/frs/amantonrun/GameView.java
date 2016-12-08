package frs.amantonrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindness on 08/12/2016.
 */

public class GameView extends SurfaceView{
    private GameLoop gameLoop;
    private SurfaceHolder holder;

    Bitmap playerbmp;
    private List<Player> player = new ArrayList<>();

    public GameView(Context context) {
        super(context);

    gameLoop = new GameLoop(this);
    holder=getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoop.setRun(true);
                gameLoop.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        playerbmp = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        player.add(new Player(this,playerbmp,50,50));
    }
    @Override
    public  boolean onTouchEvent(MotionEvent e){
        for(Player pplayer: player){
            pplayer.onTouch();
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas c){
        for(Player pplayer: player){
            pplayer.onDraw(c);
        }
    }
}
