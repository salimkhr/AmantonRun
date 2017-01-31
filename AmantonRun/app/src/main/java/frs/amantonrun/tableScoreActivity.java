package frs.amantonrun;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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

        TableRow.LayoutParams llp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        llp.setMargins(5,5,5,5);//2px right-margin
        tlp.setMargins(1,1,1,1);
        boolean b = true;
        for(Score s : allscore) {
            TableRow tr = new TableRow(this);

            TextView ts = new TextView(this);
            TextView tt = new TextView(this);
            TextView td = new TextView(this);

            if(b)
            {
                ts.setBackgroundColor(Color.WHITE);
                tt.setBackgroundColor(Color.WHITE);
                td.setBackgroundColor(Color.WHITE);
            }
            else
            {
                ts.setBackgroundColor(Color.GRAY);
                tt.setBackgroundColor(Color.GRAY);
                td.setBackgroundColor(Color.GRAY);
            }


            tr.setLayoutParams(llp);

            ts.setLayoutParams(tlp);
            tt.setLayoutParams(tlp);
            td.setLayoutParams(tlp);

            ts.setPadding(10,10,10,10);
            tt.setPadding(10,10,10,10);
            td.setPadding(10,10,10,10);

            ts.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tt.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            td.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

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
