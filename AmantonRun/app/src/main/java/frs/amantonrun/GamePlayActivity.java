package frs.amantonrun;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;

public class GamePlayActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView i;
    Animation anim;
    private int largeur;
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

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        move=0;
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
                    if(move<=4)
                    {
                        i.setImageResource(R.mipmap.left);
                        i.invalidate();
                    }
                    else
                    {
                        i.setImageResource(R.mipmap.left2);
                        i.invalidate();
                    }
                Log.i("PERSO",move+"");
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
