package frs.amantonrun;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class GamePlayActivity extends AppCompatActivity {

    public static ImageView i;
    private float x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        i = (ImageView) findViewById(R.id.imageView);

        DisplayMetrics ecran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ecran);
        int largeur = ecran.widthPixels;

        Log.i("TAG",largeur+"");

        this.x = largeur/2;
    }

    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TAG",this.x+" "+event.getX()+" "+Math.abs(this.x-event.getX()));

        if(Math.abs(this.x-event.getX()) < 50)
        {
            i.setX(event.getX());
            this.x= event.getX();
            Log.i("TAG","OK");
        }
        else
        {
            Log.i("TAG","!OK");
        }
        return true;
    }
}
