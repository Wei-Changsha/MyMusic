package com.example.musicandroid.play;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.musicandroid.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    /*操作指令*/
    public static final String ACTION_OPT_MUSIC_PLAY = "ACTION_OPT_MUSIC_PLAY";
    public static final String ACTION_OPT_MUSIC_PAUSE = "ACTION_OPT_MUSIC_PAUSE";
    public static final String ACTION_OPT_MUSIC_NEXT = "ACTION_OPT_MUSIC_NEXT";
    public static final String ACTION_OPT_MUSIC_LAST = "ACTION_OPT_MUSIC_LAST";
    public static final String ACTION_OPT_MUSIC_SEEK_TO = "ACTION_OPT_MUSIC_SEEK_TO";

    /*状态指令*/
    public static final String ACTION_STATUS_MUSIC_PLAY = "ACTION_STATUS_MUSIC_PLAY";
    public static final String ACTION_STATUS_MUSIC_PAUSE = "ACTION_STATUS_MUSIC_PAUSE";
    public static final String ACTION_STATUS_MUSIC_COMPLETE = "ACTION_STATUS_MUSIC_COMPLETE";
    public static final String ACTION_STATUS_MUSIC_DURATION = "ACTION_STATUS_MUSIC_DURATION";

    public static final String PARAM_MUSIC_DURATION = "PARAM_MUSIC_DURATION";
    public static final String PARAM_MUSIC_SEEK_TO = "PARAM_MUSIC_SEEK_TO";
    public static final String PARAM_MUSIC_CURRENT_POSITION = "PARAM_MUSIC_CURRENT_POSITION";
    public static final String PARAM_MUSIC_IS_OVER = "PARAM_MUSIC_IS_OVER";

    private int mCurrentMusicIndex = 0;
    private boolean mIsMusicPause = false;
    private List<MusicData> mMusicDatas = new ArrayList<>();

    private MusicReceiver mMusicReceiver = new MusicReceiver();
    private MediaPlayer mMediaPlayer = new MediaPlayer();



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initMusicDatas(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBoardCastReceiver();
    }

    private void initMusicDatas(Intent intent) {
        if (intent == null) return;

        List<MusicData> musicDatas = (List<MusicData>) intent.getSerializableExtra(PlayActivity.PARAM_MUSIC_LIST);
        mMusicDatas.addAll(musicDatas);

    }

    private void initBoardCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ACTION_OPT_MUSIC_PLAY);
        intentFilter.addAction(ACTION_OPT_MUSIC_PAUSE);
        intentFilter.addAction(ACTION_OPT_MUSIC_NEXT);
        intentFilter.addAction(ACTION_OPT_MUSIC_LAST);
        intentFilter.addAction(ACTION_OPT_MUSIC_SEEK_TO);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver);

//        MainActivity.globalSongName = mMusicDatas.get(index).getMusicName();
//        MainActivity.globalSinger = mMusicDatas.get(index).getMusicAuthor();
        //MainActivity.globalIsplaying
    }

    //在这里获取当前播放的歌曲  保存到数据库记忆
    private void play(final int index) {
       // Log.d("ddd001", "name = "+mMusicDatas.get(index).getMusicName()+"  index= "+ index );
        if (index >= mMusicDatas.size()) return;
        if (mCurrentMusicIndex == index && mIsMusicPause) {
            mMediaPlayer.start();
            //Log.d("ddd002", "name = "+mMusicDatas.get(index).getMusicName()+"  index= "+ index );
        } else {
            mMediaPlayer.stop();
            mMediaPlayer = null;

            mMediaPlayer = MediaPlayer.create(getApplicationContext(), mMusicDatas.get(index)
                    .getMusicRes());
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(this);
            mCurrentMusicIndex = index;
            mIsMusicPause = false;


            int duration = mMediaPlayer.getDuration();
            MainActivity.globalAllProgress = duration;
            sendMusicDurationBroadCast(duration);
            MainActivity.globalAllplayTime = duration2Time(duration);
        }
        sendMusicStatusBroadCast(ACTION_STATUS_MUSIC_PLAY);

        MainActivity.globalSongName = mMusicDatas.get(index).getMusicName();
        MainActivity.globalSinger = mMusicDatas.get(index).getMusicAuthor();
        MainActivity.globalIsplaying = true;
        //MainActivity.globalAvatar = mMusicDatas.get(index).getMusicPicRes();
        //MainActivity.globalPlayTime =
        //MainActivity.globalUnplayTime =

        //Log.d("ttt8", "  000 time = "+ MainActivity.globalAllplayTime );

        mMediaPlayer.getCurrentPosition();

        MainActivity.globalCurrentPosition = mMediaPlayer.getCurrentPosition();
        //Log.d("fff", "  000 time = "+ mMediaPlayer.getCurrentPosition() );
    }

    private void pause() {
        mMediaPlayer.pause();
        mIsMusicPause = true;
        sendMusicStatusBroadCast(ACTION_STATUS_MUSIC_PAUSE);

//        MainActivity.globalSongName = mMusicDatas.get(index).getMusicName();
//        MainActivity.globalSinger = mMusicDatas.get(index).getMusicAuthor();

        MainActivity.globalIsplaying = false;




    }

    private void stop() {
        mMediaPlayer.stop();
    }

    private void next() {
        if (mCurrentMusicIndex + 1 < mMusicDatas.size()) {
            play(mCurrentMusicIndex + 1);
        } else {
            stop();
        }

    }

    private void last() {
        if (mCurrentMusicIndex != 0) {
            play(mCurrentMusicIndex - 1);
        }
    }

    private void seekTo(Intent intent) {
        if (mMediaPlayer.isPlaying()) {
            int position = intent.getIntExtra(PARAM_MUSIC_SEEK_TO, 0);
            mMediaPlayer.seekTo(position);
            MainActivity.globalCurrentPosition = mMediaPlayer.getCurrentPosition();
            //Log.d("ttt88", "  000 globalCurrentPosition = "+ MainActivity.globalCurrentPosition );

        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        sendMusicCompleteBroadCast();
    }

    class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_OPT_MUSIC_PLAY)) {
                play(mCurrentMusicIndex);
            } else if (action.equals(ACTION_OPT_MUSIC_PAUSE)) {
                pause();
            } else if (action.equals(ACTION_OPT_MUSIC_LAST)) {
                last();
            } else if (action.equals(ACTION_OPT_MUSIC_NEXT)) {
                next();
            } else if (action.equals(ACTION_OPT_MUSIC_SEEK_TO)) {
                seekTo(intent);
            }
        }
    }

    private void sendMusicCompleteBroadCast() {
        Intent intent = new Intent(ACTION_STATUS_MUSIC_COMPLETE);
        intent.putExtra(PARAM_MUSIC_IS_OVER, (mCurrentMusicIndex == mMusicDatas.size() - 1));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMusicDurationBroadCast(int duration) {
        Intent intent = new Intent(ACTION_STATUS_MUSIC_DURATION);
        intent.putExtra(PARAM_MUSIC_DURATION, duration);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMusicStatusBroadCast(String action) {
        Intent intent = new Intent(action);
        if (action.equals(ACTION_STATUS_MUSIC_PLAY)) {
            intent.putExtra(PARAM_MUSIC_CURRENT_POSITION,mMediaPlayer.getCurrentPosition());

        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    /*根据时长格式化称时间文本*/
    private String duration2Time(int duration) {
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        return (min < 10 ? "0" + min : min + "") + ":" + (sec < 10 ? "0" + sec : sec + "");
    }
}