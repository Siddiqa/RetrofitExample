package com.app.retrofitdemo.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.adapter.SongAdapter;
import com.app.retrofitdemo.utils.MusicRetriever;
import com.app.retrofitdemo.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
//implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener
public class AndroidBuildingMusicPlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {
    public static ImageView btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageView btnNext;
    private ImageView btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    public static  SeekBar songProgressBar;
    public static TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    // Media Player

    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();
    ;
    //  private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0, playlist_index = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private String song_path;
    private String song_title;
    private ImageView albumart;
    ArrayList<MusicRetriever.Item> songitems_list;
    private int index_pos;
    private boolean isplaying = false;
    private static final int NOTIFY_ME_ID=1337;
    private String TAG="AndroidBuildingMusicPlayerActivity";
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_building_music_player);
        // All player buttons
        btnPlay = (ImageView) findViewById(R.id.btnPlay);
        btnNext = (ImageView) findViewById(R.id.btnNext);
        btnPrevious = (ImageView) findViewById(R.id.btnPrevious);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        albumart = (ImageView) findViewById(R.id.albumart_imgv);
        // Mediaplayer
        mp = Utilities.getMp();
        //songManager = new SongsManager();
        utils = new Utilities();
        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Dismiss Notification
        notificationmanager.cancel(0);
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MusicRetriever musicRetriever = new MusicRetriever(contentResolver, getApplicationContext());
        songitems_list = musicRetriever.prepare();
        Bundle b = getIntent().getExtras();
        if (b != null) {

            index_pos = b.getInt("Index");
            currentSongIndex=index_pos;

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(songitems_list.get(index_pos).getPath());
            byte[] artBytes = mmr.getEmbeddedPicture();
            if (artBytes != null) {
                //     InputStream is = new ByteArrayInputStream(mmr.getEmbeddedPicture());
                Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
                albumart.setImageBitmap(bm);
            } else {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                albumart.setImageBitmap(bm);
            }

            playSong(index_pos);
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing

                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.btn_play);
                    }
                } else {
                    // Resume song
                    if (mp != null) {
                        mp.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSongIndex < (songitems_list.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    // play first song

                    playSong(0);
                    currentSongIndex = 0;
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    // play last song
                    playSong(songitems_list.size() - 1);
                    currentSongIndex = songitems_list.size() - 1;
                }
            }
        });

    }


    //connect to the service
    /*
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"OnService Connected");
            Music.MusicBinder binder = (Music.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songitems_list);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    //start and bind the service when the activity starts
    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, Music.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
            musicSrv.playSong(index_pos);
        }
    }
*/
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.e(TAG,"OnComplete");

        if (SongAdapter.playlistArrayList.size() > 0) {
            Log.e(TAG,"Inside Playlist");
            playlist_index=0;

            playSongfromPlaylist(playlist_index);
            playlist_index++;

        } else {
            // no repeat or shuffle ON - play next song
            if (currentSongIndex < (songitems_list.size() - 1)) {

                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                // play first song
                stopPlayer();
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    public void playSong(int songIndex) {
      //  generatenotification(songIndex);
        // Play song
        try {

            mp.reset();
            mp.setDataSource(songitems_list.get(songIndex).getPath());
            mp.prepare();
            mp.start();
            isplaying = true;
            // Displaying Song title
            String songTitle = songitems_list.get(songIndex).getTitle();
            songTitleLabel.setText(songTitle);

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playSongfromPlaylist(int songIndex) {
        // Play song
        try {
            mp.reset();
            mp.setDataSource(SongAdapter.playlistArrayList.get(songIndex).getPath());
            mp.prepare();
            mp.start();
            isplaying = true;
            // Displaying Song title
            String songTitle = SongAdapter.playlistArrayList.get(songIndex).getTitle();
            songTitleLabel.setText(songTitle);

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatenotification(int songIndex )
    {

        String strtitle = "Playing Song";
//        Intent notIntent = new Intent(this, AndroidBuildingMusicPlayerActivity.class);
//        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
//                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);
// This pending intent will open after notification click
        PendingIntent pendInt=PendingIntent.getActivity(this, 0,
                new Intent(this, AndroidBuildingMusicPlayerActivity.class),
                0);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.img_btn_play)
                .setTicker(strtitle)
                .setOngoing(true)
                .setContentTitle(songitems_list.get(songIndex).getTitle())
                .setContentText(strtitle);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());
    }
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"OnResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"OnRestart");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // mp.stop();
    }

    public void stopPlayer()
    {
        if(mp.isPlaying())
        {
            mp.stop();
            mp.release();
            mp=null;
        }
        else
        {

        }
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e(TAG,"OnPause");
//        // mp.stop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // mp.stop();
//    }
/*
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getDur();
        else return 0;
    }

    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
            return musicSrv.isPng();
        return false;
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    //set the controller up
    private void setController(){
        controller = new MusicController(this);
        //set previous and next button listeners
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        //set and show
        controller.setMediaPlayer(this);
       // controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);
    }

    private void playNext(){
        musicSrv.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
       // stopService(playIntent);
       // musicSrv=null;
        super.onDestroy();
    }*/
}
