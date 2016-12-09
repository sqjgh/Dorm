package com.example.dllo.dorm.right.account;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.timeform.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import static com.example.dllo.dorm.R.id.btn_0;
import static com.example.dllo.dorm.R.id.btn_1;
import static com.example.dllo.dorm.R.id.btn_2;
import static com.example.dllo.dorm.R.id.btn_3;
import static com.example.dllo.dorm.R.id.btn_4;
import static com.example.dllo.dorm.R.id.btn_5;
import static com.example.dllo.dorm.R.id.btn_6;
import static com.example.dllo.dorm.R.id.btn_7;
import static com.example.dllo.dorm.R.id.btn_8;
import static com.example.dllo.dorm.R.id.btn_9;
import static com.example.dllo.dorm.R.id.btn_money_add_logo;


/**
 * Created by zhaojun on 16/12/4.
 */
public class AddAccountActivity extends BaseActivity implements View.OnClickListener {

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnPoint;
    private Button btnHied;
    private Button btnOk;
    private TextView showMoney;
    private Button btnBack;
    private Button btnTime;
    private RadioButton rbCall;
    private RadioButton rbHappy;
    private RadioButton rbBuy;
    private RadioButton rbTraffic;
    private RadioButton rbFood;
    private TextView accountBack;
    private TextView tvUseBy;
    private RadioButton moneyOut;
    private RadioButton moneyIn;
    private Button addMoneyLogo;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_account;
    }

    @Override
    protected void initViews() {
        accountBack = bindView(R.id.tv_account_back);

        moneyOut = bindView(R.id.rb_money_out);
        moneyIn = bindView(R.id.rb_money_in);

        btn0 = bindView(btn_0);
        btn1 = bindView(btn_1);
        btn2 = bindView(btn_2);
        btn3 = bindView(btn_3);
        btn4 = bindView(btn_4);
        btn5 = bindView(btn_5);
        btn6 = bindView(btn_6);
        btn7 = bindView(btn_7);
        btn8 = bindView(btn_8);
        btn9 = bindView(btn_9);
        btnBack = bindView(R.id.btn_back);
        btnPoint = bindView(R.id.btn_point);
        btnOk = bindView(R.id.btn_ok);
        showMoney = bindView(R.id.tv_money);

        rbFood = bindView(R.id.rb_food);
        rbTraffic = bindView(R.id.rb_traffic);
        rbBuy = bindView(R.id.rb_buy);
        rbHappy = bindView(R.id.rb_happy);
        rbCall = bindView(R.id.rb_call);

        addMoneyLogo = bindView(btn_money_add_logo);

        tvUseBy = bindView(R.id.tv_money_use_by);
        btnTime = bindView(R.id.btn_now_time);

        setClick(this,accountBack,
                moneyOut, moneyIn,
                btn0, btn1, btn2, btn3, btn4, btn5,
                btn6, btn7, btn8, btn9,
                btnPoint, btnOk, btnBack,
                rbBuy, rbCall, rbFood, rbHappy, rbTraffic,
                addMoneyLogo,
                btnTime);
    }

    @Override
    protected void initData() {
        btnTime.setText(TimeUtil.getTime());  //显示一下当前时间
    }

    String str = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_account_back:
                onBackPressed();
                break;
            case R.id.rb_money_out:
                rbBuy.setVisibility(View.VISIBLE);
                rbTraffic.setVisibility(View.VISIBLE);
                rbFood.setVisibility(View.VISIBLE);
                rbHappy.setVisibility(View.VISIBLE);
                rbCall.setVisibility(View.VISIBLE);

                addMoneyLogo.setVisibility(View.INVISIBLE);

                showMoney.setText("");
                break;

            case R.id.rb_money_in:
                addMoneyLogo.setVisibility(View.VISIBLE);

                rbBuy.setVisibility(View.INVISIBLE);
                rbTraffic.setVisibility(View.INVISIBLE);
                rbFood.setVisibility(View.INVISIBLE);
                rbHappy.setVisibility(View.INVISIBLE);
                rbCall.setVisibility(View.INVISIBLE);

                showMoney.setText("");
                break;
            case R.id.btn_0:
                str += 0;
                break;
            case R.id.btn_1:
                str += 1;
                break;
            case R.id.btn_2:
                str += 2;
                break;
            case R.id.btn_3:
                str += 3;
                break;
            case R.id.btn_4:
                str += 4;
                break;
            case R.id.btn_5:
                str += 5;
                break;
            case R.id.btn_6:
                str += 6;
                break;
            case R.id.btn_7:
                str += 7;
                break;
            case R.id.btn_8:
                str += 8;
                break;
            case R.id.btn_9:
                str += 9;
                break;
            case R.id.btn_ok:


                AccountBean bean = new AccountBean();

                bean.setUseBy((String) tvUseBy.getText());
                bean.setUseMoney((String) showMoney.getText());
                bean.setTabTime((String) btnTime.getText());

                EventBus.getDefault().post(bean);

                onBackPressed();
                break;
            case R.id.btn_back:
                if (str.length() == 0) {
                    break;
                } else {
                    str = str.substring(0, str.length() - 1);
                }
                break;
            case R.id.btn_point:
                str += ".";
                break;
            case R.id.btn_money_add_logo:
                Log.d("aaaaaAddAccountActivity", "舍费收取");
                tvUseBy.setText("舍费收取");
                break;
            case R.id.rb_food:
                tvUseBy.setText("餐饮");
                break;
            case R.id.rb_traffic:
                tvUseBy.setText("交通");
                break;
            case R.id.rb_buy:
                tvUseBy.setText("购物");
                break;
            case R.id.rb_happy:
                tvUseBy.setText("娱乐");
                break;
            case R.id.rb_call:
                tvUseBy.setText("通讯");
                break;
        }

        showMoney.setText(str);
    }
}
