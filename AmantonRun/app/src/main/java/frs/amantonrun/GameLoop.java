package frs.amantonrun;

import android.graphics.Canvas;
import android.provider.Settings;

/**
 * Created by mindness on 08/12/2016.
 */

public class GameLoop extends Thread {
    private GameView view;
    static final long FPS =30;
    boolean run;

    public GameLoop(GameView view) {
        this.view =view;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void run(){
        long ticksPS = 1000/FPS;
        long startTime = 0;
        long sleepTime =0;

        while(run){
            Canvas c = null;
            // permet grace a synchronized d'utiuliser un seul thread a la fois
            //lock canvas fonctionne comme un verrou (pareil que les mutex ?)
            try{
                c=view.getHolder().lockCanvas();
                synchronized(view.getHolder()){ //
                    view.onDraw(c);
                }
            }finally {
                if(c != null){
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
                sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime > 0) {
                        sleep(sleepTime);
                    } else {
                        sleep(25);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
        }
    }
}
