package frs.amantonrun.listener;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import frs.amantonrun.GamePlayActivity;

import static frs.amantonrun.R.string.abc_font_family_body_1_material;
import static frs.amantonrun.R.string.perso;

/**
 * Created by salim on 05/01/17.
 */

public class ImgAnimationListener implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private int piege;
    private ImageView img;
    private RelativeLayout rl;

    public ImgAnimationListener(RelativeLayout rl,ImageView img,int piege) {
        this.rl = rl;
        this.img=img;
        this.piege = piege;
    }

    public void onAnimationUpdate(ValueAnimator animation)
    {

        img.setY((Float) animation.getAnimatedValue());
        new Rect();
        if(GamePlayActivity.getPerso().getY() <= (float)animation.getAnimatedValue()) {
            ImageView perso = GamePlayActivity.getPerso();

            Rect rectPerso = new Rect((int)perso.getX(),(int)perso.getY(),(int)perso.getX()+(int)perso.getWidth(),(int)perso.getY()+perso.getHeight());
            Rect rectObj = new Rect((int)img.getX(),(int)img.getY(),(int)img.getX()+img.getWidth(),(int)img.getY()+img.getHeight());

            if(rectPerso.intersect(rectObj)) {

                if (piege == 0)
                    GamePlayActivity.setVie(GamePlayActivity.getVie() - 1);
                else if (piege == 1)
                    GamePlayActivity.setScore(GamePlayActivity.getScore() + 1);
                else if (piege == 2)

                {
                    GamePlayActivity.setRapid(3);
                    GamePlayActivity.playMoz();
                } else if (piege == 3)

                {
                    GamePlayActivity.setRapid(-3);
                    GamePlayActivity.playIe();
                }

                animation.end();
            }
        }
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        rl.removeView(img);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}


