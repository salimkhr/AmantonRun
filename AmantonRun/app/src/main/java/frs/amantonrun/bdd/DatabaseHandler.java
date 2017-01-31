package frs.amantonrun.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "game";

    // Table name
    private static final String TABLE_SCORE = "score";

    // Score Table Columns names
    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_SCORE = "score_value";
    private static final String KEY_TIME = "score_duree";
    private static final String KEY_DAY = "score_date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORE + "("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SCORE + " TEXT ,"
                + KEY_TIME + " TEXT ,"
                + KEY_DAY + " TEXT" + ")";

        Log.d("scoreSQL","oncreate");

        db.execSQL(CREATE_SCORE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if(oldVersion<newVersion)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        // Create tables again
        onCreate(db);
    }

    // Adding new score
    public void addScore(int score, long time, Date date) {

        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,0,0);
        ContentValues values = new ContentValues();

        values.put(KEY_SCORE, score); // score value
        values.put(KEY_TIME, time); // time value
        values.put(KEY_DAY, DateFormat.format("yyyy.MM.dd HH:mm:ss",date).toString()); // day value

        // Inserting Values
        db.insert(TABLE_SCORE, null, values);

        db.close();

    }

    // Getting All Scores
    public ArrayList<Score> getAllScores() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE + " LIMIT 10";

        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,0,0);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        int i = 0;

        ArrayList<Score> data = new ArrayList<>();

        while (cursor.moveToNext()) {

            data.add(new Score(Integer.parseInt(cursor.getString(1)),String.format("%dmin, %02dsec",
                    TimeUnit.MILLISECONDS.toMinutes(Integer.parseInt(cursor.getString(2))),
                    TimeUnit.MILLISECONDS.toSeconds(Integer.parseInt(cursor.getString(2)) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Integer.parseInt(cursor.getString(2)))
                            ))),cursor.getString(3)));

            Log.d("scoreSQL"," "+cursor.getString(2));

            i = i++;

        }
        cursor.close();
        db.close();
        // return score array
        return data;
    }
}