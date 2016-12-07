package com.example.dllo.dorm.game.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.game.ChartAdapter;
import com.example.dllo.dorm.game.ChartBean;
import com.example.dllo.dorm.tools.liteorm.BaseSingleton;
import com.example.dllo.dorm.tools.timeform.TimeUtil;
import com.example.dllo.dorm.tools.toast.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Wanghuan on 16/11/29.
 */
public class GameActivity extends BaseActivity implements Game2048Layout.OnGame2048Listener, View.OnClickListener {
    private Game2048Layout mGame2048Layout;
    private TextView mScore;
    private Button gameBack;
    private Button gameHistory;
    private ListView lvChart;

    @Override
    protected void initData() {
        mScore = bindView(R.id.id_score);
        mGame2048Layout = bindView(R.id.id_game2048);
        mGame2048Layout.setOnGame2048Listener(this);

    }


    @Override
    protected void initViews() {
        gameBack = bindView(R.id.game_back);
        gameHistory = bindView(R.id.game_history);
        gameBack.setOnClickListener(this);
        gameHistory.setOnClickListener(this);

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_game;
    }

    @Override
    public void onScoreChange(int score) {
        mScore.setText(String.valueOf(score));
    }

    String instertTime = "";
    @Override
    public void onGameOver() {

        String date = TimeUtil.getDate();
        instertTime = date;

        //每次GameOver时候的评分
        int chart = Integer.valueOf(mScore.getText().toString());

        //每次的评分加到集合
        List<ChartBean> chartBeanList = new ArrayList<>();
        ChartBean bean = new ChartBean();
        bean.setChart(chart);
        chartBeanList.add(bean);
        BaseSingleton.getIntstance().insertData(chartBeanList);

        new AlertDialog.Builder(this).setTitle("GAME OVER")
                .setMessage("YOU HAVE GOT  SCORE :" + mScore.getText())
                .setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGame2048Layout.restart();
                    }
                }).setNegativeButton("EXIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setCancelable(false).show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_back:
                mGame2048Layout.backStep();
                break;
            case R.id.game_history:
                showChart();
                break;
        }
    }



    //显示历史评分
    private void showChart() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最高评分").setIcon(R.mipmap.ic_launcher);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chart_view, null);
        builder.setView(view);
        lvChart = (ListView) view.findViewById(R.id.lv_dialog_chart);

        BaseSingleton.getIntstance().queryAllData(new BaseSingleton.OnQueryListenerAll<ChartBean>() {
            @Override
            public void onQuery(List<ChartBean> chartBeen) {

                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < chartBeen.size(); i++) {
                    int chart = chartBeen.get(i).getChart();

                    arrayList.add(chart);
                }
                Collections.sort(arrayList);  //集合排序
                Collections.reverse(arrayList); //集合倒置
                ChartAdapter chartAdapter = new ChartAdapter();
                chartAdapter.setArrayList(arrayList);
                lvChart.setAdapter(chartAdapter);
            }
        }, ChartBean.class);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtil.showShortToast("确定");
            }
        }).setNegativeButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtil.showShortToast("清空");
                BaseSingleton.getIntstance().deleteAllData();
            }
        });

        builder.show();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}

