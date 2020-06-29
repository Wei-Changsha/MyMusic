package com.example.musicandroid.play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.R;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.util.DisplayUtil;
import com.example.musicandroid.util.FastBlurUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.musicandroid.play.DiscView.DURATION_NEEDLE_ANIAMTOR;

public class PlayActivity extends AppCompatActivity implements DiscView.IPlayInfo,View.OnClickListener {

//    private DiscView disc;
//    private ImageView lastSongIV;
//    private ImageView nextSongIV;
//    private ImageView playOrPauseIV;
//    private ViewPager vpDisk;//唱片 底盘
//
//    private Toolbar toolbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play);
//
//        init();
//
//    }
//
//
//    private void init(){
//        disc = findViewById(R.id.disc_view);
//        lastSongIV = findViewById(R.id.iv_last);
//        nextSongIV = findViewById(R.id.iv_next);
//        playOrPauseIV = findViewById(R.id.iv_play_or_pause);
//        toolbar =findViewById(R.id.home_toolbar);
//
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null){
//            actionBar.hide();
//        }
//
//
//        setSupportActionBar(toolbar);
//        //显示应用的logo
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_back);
//
//        toolbar.setClickable(true);
//
//        //显示应用的标题和子标题
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        toolbar.setTitle("song");
//        toolbar.setSubtitle("singer");
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.iv_last:
//                disc.last();
//                break;
//            case  R.id.iv_play_or_pause:
//                disc.playOrPause();
//                break;
//            case R.id.iv_next:
//                disc.next();
//            case android.R.id.home:
//                Toast.makeText(this,"返回",Toast.LENGTH_SHORT).show();
//                break;
//
//        }
////
////        if (view == playOrPauseIV){
////            disc.playOrPause();
////            //Toast.makeText("success",)
////        }else if (view == lastSongIV){
////            disc.last();
////        }else if (view == nextSongIV){
////            disc.next();
////        }
////        Log.d("ddd",view.getId() + "");
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//
//
//        switch (item.getItemId()){
//
//            case android.R.id.home:{
//
//                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
//                break;
//            }
//            default:
//
//        }
//
//        return true;
//    }


    private DiscView mDisc;
    private Toolbar mToolbar;
    private SeekBar mSeekBar;
    private ImageView mIvPlayOrPause, mIvNext, mIvLast;
    private ImageView playHeart, playDownload, playLyric, playComment, playMore;
    private TextView mTvMusicDuration,mTvTotalMusicDuration;
    private BackgourndAnimationRelativeLayout mRootLayout;
    public static final int MUSIC_MESSAGE = 0;

    public static final String PARAM_MUSIC_LIST = "PARAM_MUSIC_LIST";



    private  Handler mMusicHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSeekBar.setProgress(mSeekBar.getProgress() + 1000);
            mTvMusicDuration.setText(duration2Time(mSeekBar.getProgress()));
            startUpdateSeekBarProgress();


        }
    };



    private MusicReceiver mMusicReceiver = new MusicReceiver();
    private List<MusicData> mMusicDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        UserSongListBean.SongBean song = (UserSongListBean.SongBean) getIntent().getSerializableExtra("song");
        List<UserSongListBean.SongBean> songList = ( List<UserSongListBean.SongBean>) getIntent().getSerializableExtra("songList");


        //TODO 应该是在initMusicdatas里面添加播放歌单
        initMusicDatas();
        initView();
        initMusicReceiver();
        makeStatusBarTransparent();
    }

    public void addToPlayList(UserSongListBean.SongBean song){
        //song.getUrl()   song.getPic()
        MusicData music = new MusicData(R.raw.ic_music1,R.raw.ic_music1,song.getName(),"iu");
        mMusicDatas.add(0,music);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:{
                finish();
                break;
            }
            default:
        }
        return true;
    }

    private void initMusicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PLAY);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PAUSE);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_DURATION);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_COMPLETE);
        /*注册本地广播*/
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicReceiver,intentFilter);
    }

    private void initView() {
        mDisc = findViewById(R.id.disc_view);
        mIvNext = findViewById(R.id.iv_next);
        mIvLast = findViewById(R.id.iv_last);
        mIvPlayOrPause = findViewById(R.id.iv_play_or_pause);
        mTvMusicDuration = findViewById(R.id.tvCurrentTime);
        mTvTotalMusicDuration = findViewById(R.id.tv_total_time);
        mSeekBar = findViewById(R.id.music_seek_bar);
        mRootLayout = findViewById(R.id.rootLayout);

        playHeart = findViewById(R.id.play_heart);
        playDownload = findViewById(R.id.play_download);
        playLyric = findViewById(R.id.play_lyric);
        playComment = findViewById(R.id.play_comment);
        playMore = findViewById(R.id.play_more);


        mToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back0);
        }

        mDisc.setPlayInfoListener(this);
        mIvLast.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mTvMusicDuration.setText(duration2Time( progress ));

                //记录音乐播放时间
                //MainActivity.globalPlayTime = duration2Time(progress);
                MainActivity.globalProgress = progress;
                MainActivity.globalPlayTime = duration2Time(progress);
//                Log.d("ttt20", "  000 globalPlayTime = "+ MainActivity.globalPlayTime );
//                Log.d("ttt21", "  000 progress = "+ progress );
//                Log.d("ttt21", "  000 MainActivity.globalProgress = "+ MainActivity.globalProgress );


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdateSeekBarProgree();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(seekBar.getProgress());
                //seekTo(MainActivity.globalProgress);
                startUpdateSeekBarProgress();
            }
        });

        mTvMusicDuration.setText(MainActivity.globalPlayTime);
        mTvTotalMusicDuration.setText(MainActivity.globalAllplayTime);
        mDisc.setMusicDataList(mMusicDatas);

        MainActivity.globalPlayTime = duration2Time(MainActivity.globalProgress);
//        Log.d("ttt66", "  000 time = "+ MainActivity.globalPlayTime );
//        Log.d("ttt67", "  000 time = "+ MainActivity.globalProgress );

    }

    private void stopUpdateSeekBarProgree() {
        mMusicHandler.removeMessages(MUSIC_MESSAGE);
    }

    /*设置透明状态栏*/
    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initMusicDatas() {
        MusicData musicData1 = new MusicData(R.raw.music1, R.raw.ic_music1, "寻", "三亩地");
        MusicData musicData2 = new MusicData(R.raw.music2, R.raw.ic_music2, "Nightingale", "YANI");
        MusicData musicData3 = new MusicData(R.raw.music3, R.raw.ic_music3, "Cornfield Chase", "Hans Zimmer");

        mMusicDatas.add(musicData1);
        mMusicDatas.add(musicData2);
        mMusicDatas.add(musicData3);

        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(PARAM_MUSIC_LIST, (Serializable) mMusicDatas);

        Log.d("ddd004", "size = "+ mMusicDatas.size() );
        startService(intent);
    }

    private void try2UpdateMusicPicBackground(final int musicPicRes) {
        if (mRootLayout.isNeed2UpdateBackground(musicPicRes)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Drawable foregroundDrawable = getForegroundDrawable(musicPicRes);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRootLayout.setForeground(foregroundDrawable);
                            mRootLayout.beginAnimation();
                        }
                    });
                }
            }).start();
        }
    }

    private Drawable getForegroundDrawable(int musicPicRes) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (DisplayUtil.getScreenWidth(PlayActivity.this)
                * 1.0 / DisplayUtil.getScreenHeight(this) * 1.0);

        Bitmap bitmap = getForegroundBitmap(musicPicRes);
        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }

    private Bitmap getForegroundBitmap(int musicPicRes) {
        int screenWidth = DisplayUtil.getScreenWidth(this);
        int screenHeight = DisplayUtil.getScreenHeight(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / DisplayUtil.getScreenWidth(this);
        int sampleY = imageHeight / DisplayUtil.getScreenHeight(this);

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), musicPicRes, options);
    }

    @Override
    public void onMusicInfoChanged(String musicName, String musicAuthor) {
        getSupportActionBar().setTitle(musicName);
        getSupportActionBar().setSubtitle(musicAuthor);
    }

    @Override
    public void onMusicPicChanged(int musicPicRes) {
        try2UpdateMusicPicBackground(musicPicRes);
    }

    @Override
    public void onMusicChanged(DiscView.MusicChangedStatus musicChangedStatus) {
        switch (musicChangedStatus) {
            case PLAY:{
                play();
                break;
            }
            case PAUSE:{
                pause();
                break;
            }
            case NEXT:{
                next();
                break;
            }
            case LAST:{
                last();
                break;
            }
            case STOP:{
                stop();
                break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mIvPlayOrPause) {
            mDisc.playOrPause();
        } else if (v == mIvNext) {
            mDisc.next();
        } else if (v == mIvLast) {
            mDisc.last();
        }

        switch (v.getId()){
            case R.id.play_heart:
                break;
            case R.id.play_download:
                break;
            case R.id.play_lyric:
                break;
            case R.id.play_comment:
                break;
            case R.id.play_more:
                break;

        }
    }

    private void play() {
        optMusic(MusicService.ACTION_OPT_MUSIC_PLAY);
        startUpdateSeekBarProgress();
    }

    private void pause() {
        optMusic(MusicService.ACTION_OPT_MUSIC_PAUSE);
        stopUpdateSeekBarProgree();
    }

    private void stop() {
        stopUpdateSeekBarProgree();
        mIvPlayOrPause.setImageResource(R.drawable.ic_play);
        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));
        mSeekBar.setProgress(0);
    }

    private void next() {
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicService.ACTION_OPT_MUSIC_NEXT);
            }
        }, DURATION_NEEDLE_ANIAMTOR);
        stopUpdateSeekBarProgree();
        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));

    }

    private void last() {
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicService.ACTION_OPT_MUSIC_LAST);
            }
        }, DURATION_NEEDLE_ANIAMTOR);
        stopUpdateSeekBarProgree();
        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));

    }

    private void complete(boolean isOver) {
        if (isOver) {
            mDisc.stop();
        } else {
            mDisc.next();
        }
    }

    private void optMusic(final String action) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
    }

    private void seekTo(int position) {
        Intent intent = new Intent(MusicService.ACTION_OPT_MUSIC_SEEK_TO);
        intent.putExtra(MusicService.PARAM_MUSIC_SEEK_TO,position);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startUpdateSeekBarProgress() {
        /*避免重复发送Message*/
        stopUpdateSeekBarProgree();
        mMusicHandler.sendEmptyMessageDelayed(0,1000);
    }

    /*根据时长格式化称时间文本*/
    public String duration2Time(int duration) {
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        return (min < 10 ? "0" + min : min + "") + ":" + (sec < 10 ? "0" + sec : sec + "");
    }

    private void updateMusicDurationInfo(int totalDuration) {

//        int pos = MainActivity.globalProgress * 100 / MainActivity.globalAllProgress ;
//
//        Log.d("ttt1111", "  000 pos = "+ pos );
//        Log.d("ttt1110", "  000 MainActivity.globalProgress = "+ MainActivity.globalProgress );
//        mSeekBar.setProgress(MainActivity.globalProgress);
        mSeekBar.setProgress(MainActivity.globalCurrentPosition);
        mSeekBar.setMax(totalDuration);

        Log.d("fffq1", "  000 totalDuration = "+ totalDuration);
        mTvTotalMusicDuration.setText(duration2Time(totalDuration));
        mTvMusicDuration.setText(MainActivity.globalPlayTime);
        startUpdateSeekBarProgress();

//        //记录音乐播放时间
//        MainActivity.globalPlayTime = duration2Time(0);
//        Log.d("ttt1", "  000 time = "+ MainActivity.globalPlayTime );

    }

    class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicService.ACTION_STATUS_MUSIC_PLAY)) {
                mIvPlayOrPause.setImageResource(R.drawable.ic_pause);
                int currentPosition = intent.getIntExtra(MusicService.PARAM_MUSIC_CURRENT_POSITION, 0);

                Log.d("fff0", "  000 currentPosition = "+ currentPosition );
                mSeekBar.setProgress(currentPosition);
                if(!mDisc.isPlaying()){
                    mDisc.playOrPause();
                }
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_PAUSE)) {
                mIvPlayOrPause.setImageResource(R.drawable.ic_play);
                if (mDisc.isPlaying()) {
                    mDisc.playOrPause();
                }
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_DURATION)) {
                int duration = intent.getIntExtra(MusicService.PARAM_MUSIC_DURATION, 0);
                updateMusicDurationInfo(duration);
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_COMPLETE)) {
                boolean isOver = intent.getBooleanExtra(MusicService.PARAM_MUSIC_IS_OVER, true);
                complete(isOver);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
