package frs.amantonrun;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GamePlayActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView i;
    Animation anim;
    private int largeur;
    private int hauteur;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        i = (ImageView) findViewById(R.id.imageView);

        DisplayMetrics ecran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ecran);
        largeur = ecran.widthPixels;
        hauteur = ecran.heightPixels;

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        move=0;

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(1000);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ImageView iv = new ImageView(getApplicationContext());

                                iv.setImageResource(R.mipmap.ic_launcher);
                                RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);
                                rl.addView(iv);
                                final int randX = (int)(Math.random()*largeur);
                                Log.i("Perso",randX+"");
                                TranslateAnimation anim = new TranslateAnimation(randX,randX,0,hauteur);
                                anim.setFillAfter(true);
                                anim.setDuration(1000);

                                anim.setAnimationListener(new Animation.AnimationListener(){
                                    @Override
                                    public void onAnimationStart(Animation arg0) {
                                    }
                                    @Override
                                    public void onAnimationRepeat(Animation arg0) {
                                    }
                                    @Override
                                    public void onAnimationEnd(Animation arg0) {
                                        Log.d("Perso",(i.getX()>randX && i.getX()<randX+i.getWidth())+"");
                                    }
                                });

                                iv.startAnimation(anim);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, accelerometer);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            if(Math.abs(event.values[0]) > 1 && i.getX() - (event.values[0]*5)>0 && i.getX() - (event.values[0]*5)+(i.getWidth())<largeur)
            {
                if(event.values[0]<0)
                    if(move<4)
                    {
                        i.setImageResource(R.mipmap.right);
                        i.invalidate();
                    }

                    else
                    {
                        i.setImageResource(R.mipmap.right2);
                        i.invalidate();
                    }
                else
                    if(move>=4)
                    {
                        i.setImageResource(R.mipmap.left);
                        i.invalidate();
                    }
                    else
                    {
                        i.setImageResource(R.mipmap.left2);
                        i.invalidate();
                    }
                move = (move+1)%8;//0 1 2 3
                i.setX(i.getX() - event.values[0]*5);
            }
        else
            {
                i.setImageResource(R.mipmap.center);
                i.invalidate();
            }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
