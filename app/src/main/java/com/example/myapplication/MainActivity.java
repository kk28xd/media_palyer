package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageButton imageButton;
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;


    ImageButton front;
    ImageButton back;
    boolean isSelected = false;

    double d;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound1);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.sound2);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.sound3);
        ArrayList<MediaPlayer> arr = new ArrayList<>();
        arr.add(mediaPlayer);
        arr.add(mediaPlayer2);
        arr.add(mediaPlayer3);
        imageButton = findViewById(R.id.stop_or_play);
        front = findViewById(R.id.front);
        back = findViewById(R.id.back);
        imageButton.setOnClickListener(
                v -> {
                    if (imageButton.isPressed()) {
                        if (isSelected) {
                            imageButton.setImageResource(R.drawable.play);
                            arr.get(index).pause();
                        } else {
                            imageButton.setImageResource(R.drawable.stop);
                            arr.get(index).start();
                        }
                        isSelected = !isSelected;
                    }
                }
        );
        front.setOnClickListener(v -> {
            arr.get(index).stop();
            try {
                arr.get(index).prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            index++;
            if (index > arr.size() - 1) index = 0;
            Toast.makeText(this, "song" + index, Toast.LENGTH_SHORT).show();
            if (isSelected) {
                arr.get(index).start();
            }

        });
        back.setOnClickListener(v -> {
            arr.get(index).stop();
            try {
                arr.get(index).prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            index--;
            if (index < 0) index = arr.size() - 1;
            Toast.makeText(this, "song" + index, Toast.LENGTH_SHORT).show();
            if (isSelected) {
                arr.get(index).start();
            }
        });
        mediaPlayer.setOnCompletionListener(mp -> {
            imageButton.setImageResource(R.drawable.play);
            isSelected = !isSelected;
        });
    }

    String getD(MediaPlayer m) {
        d = (double) m.getDuration();
        d = d / (1000 * 60);
        double sec = Math.floor(d);
        sec = d - sec;
        sec *= 60;
        int second = (int) Math.round(sec);
        int mi = (int) d;
        return mi + ":" + second;
    }

    int getMaxx(MediaPlayer m) {
        d = (double) m.getDuration();
        d = d / (1000 * 60);
        double sec = Math.floor(d);
        sec = d - sec;
        sec *= 60;
        int second = (int) Math.round(sec);
        int mi = (int) d;
        return (mi * 60) + second;
    }
}