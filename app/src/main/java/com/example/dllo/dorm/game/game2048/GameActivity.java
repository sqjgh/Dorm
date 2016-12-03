package com.example.dllo.dorm.game.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;

/**
 * Created by Wanghuan on 16/11/29.
 */
public class GameActivity extends BaseActivity implements  Game2048Layout.OnGame2048Listener{
    private Game2048Layout mGame2048Layout;
    private TextView mScore;
    private Button chongzhi;
    private Button fanhui;

    @Override
    protected void initData() {
        mScore = bindView(R.id.id_score);
        mGame2048Layout = bindView(R.id.id_game2048);
        mGame2048Layout.setOnGame2048Listener(this);

    }

    @Override
    protected void initViews() {
        fanhui = bindView(R.id.fanhuishangyibu);
        chongzhi = bindView(R.id.chongzhiyouxi);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame2048Layout.backStep();
            }
        });
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame2048Layout.restart();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_game;
    }

    @Override
    public void onScoreChange(int score) {
        mScore.setText("SCORE: " + score);

    }

    @Override
    public void onGameOver() {
        new AlertDialog.Builder(this).setTitle("GAME OVER")
                .setMessage("YOU HAVE GOT " + mScore.getText())
                .setPositiveButton("RESTART", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mGame2048Layout.restart();
                    }
                }).setNegativeButton("EXIT", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        }).show();
    }


}

