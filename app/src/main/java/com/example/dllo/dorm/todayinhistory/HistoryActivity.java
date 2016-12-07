package com.example.dllo.dorm.todayinhistory;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;

/**
 * Created by Wanghuan on 16/12/6.
 */
public class HistoryActivity extends BaseActivity {
    private ListView mHistory;
    private EditText mInput;
    private ImageView mSearch;
    private HistoryAdapter mHistoryAdapter;



    @Override
    protected int getLayout() {
        return R.layout.history;
    }

    @Override
    protected void initViews() {
        mHistory = (ListView) findViewById(R.id.history_list);
        mInput = (EditText) findViewById(R.id.history_input);
        mSearch = (ImageView) findViewById(R.id.history_search);


    }

    @Override
    protected void initData() {


        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWords = mInput.getText().toString();

                Log.d("HistoryActivity------", keyWords);

              //  Toast.makeText(HistoryActivity.this, "mHistoryBean.getResult():" + mHistoryBean.getResult(), Toast.LENGTH_SHORT).show();
                HttpUtil.getHistory(keyWords, new ResponseCallBack<HistoryBean>() {
                    @Override
                    public void OnResponse(HistoryBean historyBean) {
                        mHistoryAdapter = new HistoryAdapter(HistoryActivity.this);
                        mHistoryAdapter.setHistoryBean(historyBean);
                        mHistory.setAdapter(mHistoryAdapter);

                    }

                    @Override
                    public void onError(Exception throwable) {
                        Toast.makeText(HistoryActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }


//    private String getUTFString(String xml) {
//        Log.d("HistoryActivity", "这里走没走");
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(xml);
//        String xmString = "";
//        String xmlUTF8 = "";
//        try {
//            xmString = new String(stringBuffer.toString().getBytes("UTF-8"));
//            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();大连
//        }
//
//        return xmlUTF8;
//    }
}
