package frs.amantonrun.bdd;

/**
 * Created by salim on 31/01/17.
 */

public class Score {
    private String date;
    private String time;
    private int score;

    public Score(int score , String time, String date) {
        this.score = score;
        this.time = time;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }
}
