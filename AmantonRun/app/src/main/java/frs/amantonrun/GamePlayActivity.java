package frs.amantonrun;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePlayActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView i;
    private int largeur;
    private int hauteur;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int move;
    private static TextView tvScore;
    private static int score = 0;
    private static int nbVie = 5;
    private Thread thread;
    private static ArrayList<ImageView> arrayImg;
    private static AlertDialog dialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        i = (ImageView) findViewById(R.id.imageView);
        tvScore = (TextView) findViewById(R.id.textView);

        arrayImg = new ArrayList<>(5);

        arrayImg.add((ImageView) findViewById(R.id.life1));
        arrayImg.add((ImageView) findViewById(R.id.life2));
        arrayImg.add((ImageView) findViewById(R.id.life3));
        arrayImg.add((ImageView) findViewById(R.id.life4));
        arrayImg.add((ImageView) findViewById(R.id.life5));

        DisplayMetrics ecran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ecran);
        largeur = ecran.widthPixels;
        hauteur = ecran.heightPixels;

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        move=0;

        dialog = new AlertDialog.Builder(GamePlayActivity.this).create();
        dialog.setTitle("Alert");
        dialog.setMessage("Alert message to be shown");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(nbVie > 0) {
                        sleep(500);
                        runOnUiThread(new Runnable() {
                            public void run() {

                                Boolean piege = Math.random()>0.5;

                                ImageView iv = new ImageView(getApplicationContext());

                                if(piege)
                                    if(Math.random()>0.5)
                                        iv.setImageResource(R.mipmap.windows);
                                    else
                                        iv.setImageResource(R.mipmap.java);
                                else
                                    if(Math.random()>0.5)
                                        iv.setImageResource(R.mipmap.linux);
                                    else
                                        iv.setImageResource(R.mipmap.c);

                                int width = 60;
                                int height = 60;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                                iv.setLayoutParams(parms);

                                RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);
                                rl.addView(iv);
                                final int randX = (int)(Math.random()*(largeur-iv.getWidth()));
                                //Log.i("Perso",randX+"");
                                TranslateAnimation anim = new TranslateAnimation(randX,randX,0,i.getY());
                                anim.setFillAfter(true);
                                anim.setDuration(1000);
                                anim.setInterpolator(new LinearInterpolator());

                                ImgAnimationListener imgl = new ImgAnimationListener(iv,i,rl,randX,hauteur,score,piege);

                                anim.setAnimationListener(imgl);

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

    public static void setScore(int score){
        GamePlayActivity.score= score;
        tvScore.setText("score : "+score);
    }

    public static int getScore(){
        return GamePlayActivity.score;
    }

    public static void setVie(int score){
        GamePlayActivity.nbVie= score;
        if(score >= 0)
            GamePlayActivity.arrayImg.get(score).setVisibility(View.INVISIBLE);
        else
            dialog.show();
    }

    public static int getVie(){
        return GamePlayActivity.nbVie;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
