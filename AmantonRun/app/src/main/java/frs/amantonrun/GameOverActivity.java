package frs.amantonrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import frs.amantonrun.bdd.DatabaseHandler;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        DatabaseHandler db = new DatabaseHandler(this);

        GamePlayActivity.getSound().playSound(2);

        int score =  getIntent().getExtras().getInt("score");
        long time =  getIntent().getExtras().getLong("time");

        Log.d("scoreSQL",time+" time");

        db.addScore(score,time,new Date());

        TextView tv = (TextView) findViewById(R.id.textViewScore);
        tv.setText(tv.getText()+" "+score);

        View myView = findViewById(R.id.activity_game_over);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent i =  new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

                return false;
            }
        });
    }
}
