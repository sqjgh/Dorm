package com.example.dllo.dorm.right.weather;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;

/**
 * Created by Wanghuan on 16/12/7.
 */
public class WeatherActivity extends BaseActivity {
    private TextView city,time,weather,temperature,sport,uv,code,wear,pm,tip;
    private EditText mEditText;
    private ImageView mImageView;
    private WeatherBean mWeatherBean;

    @Override
    protected int getLayout() {
        return R.layout.weather;
    }

    @Override
    protected void initViews() {

        mEditText = (EditText) findViewById(R.id.weather_city);
        mImageView = (ImageView) findViewById(R.id.weather_search);
        city = (TextView) findViewById(R.id.city);
        time = (TextView) findViewById(R.id.time_weather);
        weather = (TextView) findViewById(R.id.weather);
        temperature = (TextView) findViewById(R.id.temperature);
        sport = (TextView) findViewById(R.id.sport);
        uv = (TextView) findViewById(R.id.UV);
        code = (TextView) findViewById(R.id.code);
        wear = (TextView) findViewById(R.id.wear);
        pm = (TextView) findViewById(R.id.quality);
        tip = (TextView) findViewById(R.id.tip);

    }

    @Override
    protected void initData() {

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWords = mEditText.getText().toString();
                HttpUtil.getWeather(keyWords, new ResponseCallBack<WeatherBean>() {
                    @Override
                    public void OnResponse(WeatherBean weatherBean) {
                        Log.d("WeatherActivity-------", weatherBean.getResult().getRealtime().getCity_name());
                        city.setText(weatherBean.getResult().getRealtime().getCity_name());
                        time.setText(weatherBean.getResult().getRealtime().getDate());
                        weather.setText(weatherBean.getResult().getRealtime().getWeather().getInfo());
                        temperature.setText(weatherBean.getResult().getRealtime().getWeather().getTemperature());
                        sport.setText(weatherBean.getResult().getLife().getInfo().getYundong().toString());
                        uv.setText(weatherBean.getResult().getLife().getInfo().getZiwaixian().toString());
                        code.setText(weatherBean.getResult().getLife().getInfo().getGanmao().toString());
                        wear.setText(weatherBean.getResult().getLife().getInfo().getChuanyi().toString());
                        pm.setText(weatherBean.getResult().getPm25().getPm25().getPm25());
                        tip.setText(weatherBean.getResult().getPm25().getPm25().getDes());

                    }

                    @Override
                    public void onError(Exception throwable) {

                    }
                });
            }
        });

    }
}
