package frs.amantonrun;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static frs.amantonrun.R.string.perso;

/**
 * Created by salim on 05/01/17.
 */

    public class ImgAnimationListener implements AnimationListener {

    private final Boolean piege;
    private final ImageView img;
    private int randX;
    private RelativeLayout rl;

    public ImgAnimationListener(ImageView img, RelativeLayout rl, int randX, Boolean piege) {
        this.img=img;
        this.rl = rl;
        this.randX = randX;
        this.piege = piege;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

       if(GamePlayActivity.getPerso().getX()+GamePlayActivity.getPerso().getWidth()/2>randX && GamePlayActivity.getPerso().getX()<randX+GamePlayActivity.getPerso().getWidth()/2) {
           if(piege)
               GamePlayActivity.setVie(GamePlayActivity.getVie()-1);
           else
               GamePlayActivity.setScore(GamePlayActivity.getScore()+1);
           rl.removeView(img);
       }
       else
       {

           TranslateAnimation anim = new TranslateAnimation(randX,randX,GamePlayActivity.getPerso().getY(),GamePlayActivity.getHauteur());
           anim.setFillAfter(false);
           anim.setFillBefore(true);
           anim.setDuration(300);
           anim.setInterpolator(new LinearInterpolator());

           anim.setAnimationListener(new AnimationListener() {
               @Override
               public void onAnimationStart(Animation animation) {

               }

               @Override
               public void onAnimationEnd(Animation animation) {

                   rl.removeView(img);
               }

               @Override
               public void onAnimationRepeat(Animation animation) {

               }
           });
           img.clearAnimation();
           img.startAnimation(anim);
       }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
