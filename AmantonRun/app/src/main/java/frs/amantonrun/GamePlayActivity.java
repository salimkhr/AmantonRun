package frs.amantonrun;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import frs.amantonrun.listener.ImgAnimationListener;
import frs.amantonrun.sound.SoundManager;

public class GamePlayActivity extends AppCompatActivity implements SensorEventListener {

    private static ImageView i;
    private static SoundManager sound;
    private int largeur;
    private static int hauteur;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int move;
    private static TextView tvScore;
    private static int score = 0;
    private static int nbVie;
    private Thread thread;
    private static ArrayList<ImageView> arrayImg;
    private boolean running;
    private static Vibrator vib;
    private static int rapid = 0;
    private static MediaPlayer mpIe,mpMoz;
    private long time;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sound = new SoundManager(this);
        sound.addSound(0,R.raw.paspiege);
        sound.addSound(1,R.raw.piege);
        sound.addSound(2,R.raw.gameover);
        MediaPlayer.OnCompletionListener mpListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                rapid = 0;
            }
        };


        mpIe =  MediaPlayer.create(getApplicationContext(), R.raw.ie);
        mpIe.setOnCompletionListener(mpListener);

        mpMoz = MediaPlayer.create(getApplicationContext(), R.raw.moz);
        mpMoz.setOnCompletionListener(mpListener);

        score = 0;
        nbVie = 5;
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

        i.setMinimumWidth(largeur/8);

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        move=0;

        vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        running = true;

        thread = new Thread() {

            @Override
            public void run() {
                try {
                    while(nbVie > 0) {

                        int slip = 500-(score*2);
                        sleep((slip>200)?slip:200);
                        Log.d("perso",running+"");
                        if(running)
                            runOnUiThread(new Runnable() {
                            public void run() {

                                int rand  = (int)(Math.random()*100);
                                int piege;

                                ImageView iv = new ImageView(getApplicationContext());
                                if(rand<45)
                                {
                                    if(Math.random()>0.5)
                                        iv.setImageResource(R.mipmap.windows);
                                    else
                                        iv.setImageResource(R.mipmap.java);

                                    piege=0;
                                }
                                else if (rand<90)
                                {
                                    if(Math.random()>0.5)
                                        iv.setImageResource(R.mipmap.linux);
                                    else
                                        iv.setImageResource(R.mipmap.c);

                                    piege=1;
                                }
                                else
                                {
                                    if(Math.random()>0.5)
                                    {
                                        iv.setImageResource(R.mipmap.moz);
                                        piege = 2;
                                    }
                                    else
                                    {
                                        iv.setImageResource(R.mipmap.ie);
                                        piege = 3;
                                    }

                                }

                                int width = largeur/12;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,width);
                                iv.setLayoutParams(parms);

                                RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);
                                rl.addView(iv);

                                final int randX = (int)(Math.random()*(largeur-width));
                                iv.setX(randX);

                                ValueAnimator anim = ValueAnimator.ofFloat(0,hauteur);
                                int duration = 3000-(score*5);
                                anim.setDuration((duration>1000)?duration:1000);

                                //anim.setInterpolator(new AnticipateInterpolator());
                                //anim.setInterpolator(new OvershootInterpolator());
                                //anim.setInterpolator(new AnticipateOvershootInterpolator());
                                //anim.setInterpolator(new CycleInterpolator(1));
                                //anim.setInterpolator(new BounceInterpolator());
                                anim.setInterpolator(new AccelerateInterpolator());
                                //anim.setInterpolator(new DecelerateInterpolator());
                                //anim.setInterpolator(new AccelerateDecelerateInterpolator());

                                ImgAnimationListener imgl = new ImgAnimationListener(rl,iv,piege);

                                anim.addUpdateListener(imgl);
                                anim.addListener(imgl);

                                anim.start();
                            }
                        });
                    }
                    Intent i =  new Intent(getApplicationContext(),GameOverActivity.class);
                    i.putExtra("score",score);
                    long time2 = new Date().getTime();
                    Log.d("scoreSQL",time2+" time2");

                    long timeDiff = time2-time;

                    Log.d("scoreSQL",timeDiff+" timeDiff");


                    i.putExtra("time",timeDiff);
                    mpMoz.stop();
                    mpIe.stop();
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        
        time = new Date().getTime();

        Log.d("scoreSQL",time+" time");
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, accelerometer);
        running = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        running = true;
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sensorManager.unregisterListener(this, accelerometer);
        running = false;
        super.onDestroy();
    }

    public void onSensorChanged(SensorEvent event) {

        int coef = 5+rapid;
        Log.d("coef",coef+" "+rapid);
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            if(Math.abs(event.values[0]) > 1 && i.getX() - (event.values[0]*coef)>0 && i.getX() - (event.values[0]*coef)+(i.getWidth())<largeur)
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
                i.setX(i.getX() - event.values[0]*coef);
            }
        else
            {
                i.setImageResource(R.mipmap.center);
                i.invalidate();
            }

    }

    public static void setScore(int score){
        sound.playSound(0);
        GamePlayActivity.score= score;
        tvScore.setText("score : "+score);
    }

    public static int getScore(){
        return score;
    }

    public static void setVie(int nbVie){
        sound.playSound(1);
        vib.vibrate(new long[]{500,100,500},-1);
        GamePlayActivity.nbVie= nbVie;
        if(nbVie >= 0)
            arrayImg.get(nbVie).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public static int getVie(){
        return nbVie;
    }

    public static ImageView getPerso()
    {
        return i;
    }
    
    public static SoundManager getSound() {
        return sound;
    }

    public static void playIe(){mpIe.start();Log.d("Piege",rapid+"");}

    public static void playMoz(){mpMoz.start();Log.d("Piege",rapid+"");}

    public static void setRapid(int rapid) {
        GamePlayActivity.rapid = rapid;
    }

}
