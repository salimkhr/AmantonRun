package frs.amantonrun;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by salim on 05/01/17.
 */

    public class ImgAnimationListener implements AnimationListener {

    private final int score;
    private final Boolean piege;
    private int randX,hauteur;
    private ImageView img,perso;
    private RelativeLayout rl;

    public ImgAnimationListener(ImageView img, ImageView perso, RelativeLayout rl, int randX, int hauteur, int score, Boolean piege) {
        this.img = img;
        this.perso = perso;
        this.rl = rl;
        this.randX = randX;
        this.hauteur = hauteur;
        this.score=score;
        this.piege = piege;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

       if(perso.getX()+perso.getWidth()/2>randX && perso.getX()<randX+perso.getWidth()/2) {
           if(piege)
               GamePlayActivity.setVie(GamePlayActivity.getVie()-1);
           else
               GamePlayActivity.setScore(GamePlayActivity.getScore()+1);
           rl.removeView(img);
       }
       else
       {

           TranslateAnimation anim = new TranslateAnimation(randX,randX,perso.getY(),hauteur);
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
