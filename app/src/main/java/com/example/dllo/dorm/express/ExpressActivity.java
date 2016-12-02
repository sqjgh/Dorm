package com.example.dllo.dorm.express;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.firstpage.swipecards.CardMode;
import com.example.dllo.dorm.tools.ChangeChinese;
import com.example.dllo.dorm.tools.okhttp.ContentBean;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.InfoBean;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by Wanghuan on 16/12/1.
 */

public class ExpressActivity extends BaseActivity {
    private EditText mEditText;
    private TextView mTextView;
    private Button mButton;
    private ListView mListView;
    private ExpressAdapter mExpressAdapter;
    private Context mContext;

    private String COMPANY_INFO;

    private int NUMBER_INFO;
    private ChangeChinese mChangeChinese;
    private Spinner mSpinner;


    @Override
    protected void initData() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeChinese = new ChangeChinese();
                String num = mEditText.getText().toString();
                String compny = mChangeChinese.getSpelling(mSpinner.getSelectedItem().toString());

                initInfo(num,compny);
            }
        });

    }

    private void initInfo(String num,String company) {
        Log.d("ExpressActivity", company);
        HttpUtil.getInfo(company, num, new ResponseCallBack<InfoBean>() {
            @Override
            public void OnResponse(InfoBean infoBean) {
                Log.d("ExpressActivity", infoBean.getReason());

                mExpressAdapter = new ExpressAdapter(mContext);
                mExpressAdapter.setInfoBeen(infoBean);
                mListView.setAdapter(mExpressAdapter);
            }

            @Override
            public void onError(Exception throwable) {
                Toast.makeText(mContext, "请输入正确的数据", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void initViews() {

        mEditText = (EditText) findViewById(R.id.express_id);
        mTextView = (TextView) findViewById(R.id.company);
        mButton = (Button) findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.info_express);
        mSpinner = (Spinner) findViewById(R.id.spinner);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_express;
    }
}
