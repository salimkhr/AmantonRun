package frs.amantonrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        int score =  getIntent().getExtras().getInt("score");
        Log.d("score",score+"");

        TextView tv = (TextView) findViewById(R.id.textViewScore);
        tv.setText(tv.getText()+" "+score);
    }
}
