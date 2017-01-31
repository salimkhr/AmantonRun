package frs.amantonrun;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import frs.amantonrun.bdd.DatabaseHandler;
import frs.amantonrun.bdd.Score;

public class tableScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_score);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Score> allscore = db.getAllScores();
        StringBuilder stb = new StringBuilder();
        //TextView tv = (TextView)findViewById(R.id.textViewHG);
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        TableRow.LayoutParams llp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        llp.setMargins(5,5,5,5);//2px right-margin

        boolean b = true;
        for(Score s : allscore) {
            TableRow tr = new TableRow(this);
            if(b)
                tr.setBackgroundColor(Color.GREEN);

            //tr.setBackgroundResource(R.drawable.border);

            tr.setLayoutParams(llp);

            TextView ts = new TextView(this);
            TextView tt = new TextView(this);
            TextView td = new TextView(this);

            ts.setPadding(2, 2, 2, 2);
            tt.setPadding(2, 2, 2, 2);
            td.setPadding(2, 2, 2, 2);

            tr.addView(ts);
            tr.addView(tt);
            tr.addView(td);

            ts.setText(s.getScore()+"");
            tt.setText(s.getTime());
            td.setText(s.getDate());

            tableLayout.addView(tr);

            b=!b;
        }
    }
}
