package frs.amantonrun.sound;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class SoundManager {

    private  SoundPool mSoundPool;
    private  HashMap mSoundPoolMap;
    private  AudioManager  mAudioManager;
    private  Context mContext;

    //Initialisation
    public SoundManager(Context theContext){
        mContext = theContext;
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new HashMap();
        mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);

    }

    //On ajoute un son a la liste
    public void addSound(int index, int SoundID){
        mSoundPoolMap.put(index, mSoundPool.load(mContext, SoundID, 1));
    }

    //On joue le son une fois
    public void playSound(int index){
        float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
        Log.d("sound","play");
    }

    //On joue le son en replay
    public void playLoopedSound(int index){
        float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
    }
}