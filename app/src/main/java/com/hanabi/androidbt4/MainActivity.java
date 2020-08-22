package com.hanabi.androidbt4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgvPlay;
    private int[] tvShows, btnSelects;
    private int[] images = new int[]{
            R.drawable.baocao, R.drawable.aomua, R.drawable.canthiep,
            R.drawable.xedapdien, R.drawable.xaphong, R.drawable.xakep,
            R.drawable.vuonbachthu, R.drawable.vuaphaluoi, R.drawable.tranhthu,
            R.drawable.totien, R.drawable.tohoai, R.drawable.tichphan,
            R.drawable.thothe, R.drawable.thattinh, R.drawable.songsong
    };
    private Random random = new Random();
    private TextView tvShow, tvHeart, tvScore, btnSelect;
    private GridLayout glShow, glSelector;
    private int heart = 5, count = 0, score, id;
    private String nameImage;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        createId();
    }

    private void initViews() {
        tvHeart = findViewById(R.id.tv_heart);
        tvScore = findViewById(R.id.tv_score);
        imgvPlay = findViewById(R.id.imgv_play);
        glShow = findViewById(R.id.gr_btn_show);
        glSelector = findViewById(R.id.gr_btn_select);
        nameImage = this.getResources().getResourceEntryName(images[id]);
        tvShows = new int[nameImage.length()];
        btnSelects = new int[15];

        Glide.with(this).load(images[id]).into(imgvPlay);

        String[] chars = nameImage.replaceAll("", " ").trim().toLowerCase().split(" ");
        ArrayList<String> list = new ArrayList(Arrays.asList(chars));
        Collections.shuffle(list);

        for (int j = 0; j < nameImage.length(); j++) {
            setStylesTxt(tvShow, tvShows, glShow, j, 999900);
        }

        for (int j = 0; j < 12; j++) {
            setStylesBtn(btnSelect, btnSelects, glSelector, j, 988800, list);
        }

    }

    private void setStylesTxt(View view, int[] arrayId, GridLayout gridLayout, int j, int idCode) {
        view = new TextView(glShow.getContext());
        view.setId(idCode + j);
        view.setBackgroundResource(R.drawable.ic_tile_hover);
        ((TextView) view).setTextColor(Color.WHITE);
        ((TextView) view).setGravity(Gravity.CENTER);
        ((TextView) view).setTextSize(25f);
        arrayId[j] = view.getId();
        gridLayout.addView(view);
    }

    private void setStylesBtn(View view, int[] arrayId, GridLayout gridLayout, int j, int idCode, ArrayList chars) {

        view = new TextView(glShow.getContext());
        view.setId(idCode + j);
        view.setTag(1);
        view.setBackgroundResource(R.drawable.ic_tile_hover);
        if (j < chars.size()) {
            ((TextView) view).setText(chars.get(j).toString());
        } else {
            ((TextView) view).setText(chars.get(random.nextInt(chars.size())).toString());
        }
        ((TextView) view).setTextColor(Color.WHITE);
        ((TextView) view).setGravity(Gravity.CENTER);
        ((TextView) view).setTextSize(25f);
        arrayId[j] = view.getId();
        view.setOnClickListener(this);
        gridLayout.addView(view);
    }


    @Override
    public void onClick(View view) {
        TextView button = findViewById(view.getId());
        if (count < nameImage.length() && button.getTag().toString().equals("1")) {
            button.setVisibility(View.INVISIBLE);
            TextView tv = findViewById(tvShows[count]);
            tv.setText(button.getText().toString());
            result += button.getText().toString();
            count++;
            if (count == nameImage.length()) {
                isWin();
            }
        }
    }

    private void isWin() {
        if (result.equals(nameImage)) {
            score += 100;
            tvScore.setText(score + "");
        } else {
            heart--;
            tvHeart.setText(heart + "");
        }

        if (heart > 0) {
            reset();
            initViews();
        }
    }

    private void reset() {
        count = 0;
        createId();
        result = "";
        glSelector.removeAllViews();
        glShow.removeAllViews();
    }

    private void createId() {
        int id = random.nextInt(images.length);
        while (id == this.id) {
            id = random.nextInt(images.length);
        }
        this.id = id;
    }
}