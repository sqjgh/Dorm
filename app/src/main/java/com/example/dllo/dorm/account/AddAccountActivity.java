package com.example.dllo.dorm.account;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.timeform.TimeUtil;

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
    private TextView money;
    private Button btnBack;
    private Button btnTime;
    private RadioButton rbCall;
    private RadioButton rbHappy;
    private RadioButton rbBuy;
    private RadioButton rbTraffic;
    private RadioButton rbFood;
    private TextView tvUseBy;
    private RadioButton moneyOut;
    private RadioButton moneyIn;
    private EditText etAddMoney;

    @Override
    protected int getLayout() {
        return R.layout.add_account_pop;
    }

    @Override
    protected void initViews() {

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
        money = bindView(R.id.tv_money);

        rbFood = bindView(R.id.rb_food);
        rbTraffic = bindView(R.id.rb_traffic);
        rbBuy = bindView(R.id.rb_buy);
        rbHappy = bindView(R.id.rb_happy);
        rbCall = bindView(R.id.rb_call);

        etAddMoney = bindView(R.id.et_money_add);

        tvUseBy = bindView(R.id.tv_money_use_by);
        btnTime = bindView(R.id.btn_now_time);

        setClick(this, moneyOut, moneyIn,
                btn0, btn1, btn2, btn3, btn4, btn5,
                btn6, btn7, btn8, btn9,
                btnPoint, btnOk, btnBack,
                rbBuy, rbCall, rbFood, rbHappy, rbTraffic,
                btnTime);
    }

    @Override
    protected void initData() {

    }

    String str = "¥ :";
    String useBy = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_money_out:
                rbBuy.setVisibility(View.VISIBLE);
                rbTraffic.setVisibility(View.VISIBLE);
                rbFood.setVisibility(View.VISIBLE);
                rbHappy.setVisibility(View.VISIBLE);
                rbCall.setVisibility(View.VISIBLE);

                etAddMoney.setVisibility(View.INVISIBLE);

                break;
            case R.id.rb_money_in:
                etAddMoney.setVisibility(View.VISIBLE);
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
                tvUseBy.getText();   //获取当前消费的项目
                money.getText();     //获取当前消费金额
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

            case R.id.btn_now_time:
                btnTime.setText(TimeUtil.getTime());
                break;
            case R.id.rb_food:
                useBy += "餐饮";
                break;
            case R.id.rb_traffic:
                useBy += "交通";
                break;
            case R.id.rb_buy:
                useBy += "购物";
                break;
            case R.id.rb_happy:
                useBy += "娱乐";
                break;
            case R.id.rb_call:
                useBy += "通讯";
                break;
        }
        tvUseBy.setText(useBy);
        money.setText(str);
    }
}
