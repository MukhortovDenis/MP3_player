package com.example.mp3_player;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class MainActivity extends Activity implements OnPreparedListener, UpdateTrack{

    private RecyclerView numbersList;
    private NumbersAdapter numbersAdapter;
    final String LOG_TAG = "myLogs";
    final String DATA_STREAM = "http://air.radiorecord.ru:805/rock_320";
    final String DATA_SD = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            + "/music.mp3";
    private Uri DATA_URI = Uri.parse("/storage/self/primary/Music/DeadMansBones-MyBodysaZombieForYou.mp3");


    MediaPlayer mediaPlayer;
    AudioManager am;
    CheckBox chbLoop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbersList = findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        numbersList.setLayoutManager(layoutManager);

        numbersList.setHasFixedSize(true);
        numbersAdapter = new NumbersAdapter(getFiles(), this);
        numbersList.setAdapter(numbersAdapter);
        ///
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        chbLoop = findViewById(R.id.chbLoop);
        chbLoop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (mediaPlayer != null)
                    mediaPlayer.setLooping(isChecked);
            }
        });

    }

    private String[] getFiles(){
        String dir = "/storage/self/primary/Music/";
        File test = new File(dir);
        System.out.println(test.list().toString());
        return test.list() ;
    }

    public void onClickStart(View view) {
        releaseMP();

        try {
            switch (view.getId()) {
                case R.id.btnStartStream:
                    Log.d(LOG_TAG, "start Stream");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_STREAM);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d(LOG_TAG, "prepareAsync");
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;
                case R.id.btnStartSD:
                    Log.d(LOG_TAG, "start SD");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_SD);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    break;
                case R.id.btnStartUri:
                    Log.d(LOG_TAG, "start Uri");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(this, DATA_URI);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    break;
                case R.id.btnStartRaw:
                    Log.d(LOG_TAG, "start Raw");
                    mediaPlayer = MediaPlayer.create(this, R.raw.glass_animals_pork_soda);
                    mediaPlayer.start();
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mediaPlayer == null)
            return;

        mediaPlayer.setLooping(chbLoop.isChecked());

    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        if (mediaPlayer == null)
            return;
        switch (view.getId()) {
            case R.id.btnPause:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case R.id.btnResume:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;
            case R.id.btnStop:
                mediaPlayer.stop();
                break;
            case R.id.btnBackward:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 3000);
                break;
            case R.id.btnForward:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 3000);
                break;


        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "onPrepared");
        mp.start();
    }

    public void onCompletion(MediaPlayer mp) {
        Log.d(LOG_TAG, "onCompletion");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    @Override
    public void update(String pathInMusic) {
        this.DATA_URI =
                Uri.parse("/storage/self/primary/Music/" + pathInMusic);
        System.out.println("file:///storage/self/primary/Music/" + pathInMusic);
    }
}