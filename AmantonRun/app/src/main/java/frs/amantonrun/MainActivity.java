package frs.amantonrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    public void startGame(View view) {
        startActivity(new Intent(this,GamePlayActivity.class));
    }
    public void highScore(View view) { startActivity(new Intent(this,tableScoreActivity.class));
    }
}
