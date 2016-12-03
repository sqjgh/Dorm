package com.example.dllo.dorm.welcome.loginmvp;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseFragment;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.toast.ToastUtil;

import static com.example.dllo.dorm.R.id.btn_login;
import static com.example.dllo.dorm.R.id.et_id_login;
import static com.example.dllo.dorm.R.id.et_password_login;

/**
 * Created by dllo on 16/11/22.
 * V层
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener, LoginContract.View {
    private EditText usernameEt, passwordEt;
    private Button loginBtn;
    private LoginContract.Presenter mPresenter;
    private ProgressDialog progressDialog;



    private EditText idRegister;
    private Button btnRegister;
    private TextView registerTv;
    private boolean vis = false;
    private ImageView back;
    private String name;


    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        usernameEt = bindView(et_id_login);
        passwordEt = bindView(et_password_login);

//        warnId = bindView(R.id.warn_id);
//        warnPassWord = bindView(R.id.warn_password);
        back = bindView(R.id.back_login);
        idRegister = bindView(R.id.et_id_register);
        // 注册, 取消注册文字
        registerTv = bindView(R.id.code_login);
        // 登录按钮
        loginBtn = bindView(btn_login);
        // 注册按钮
        btnRegister = bindView(R.id.btn_register);

        setClick(this, loginBtn, back, idRegister, registerTv, btnRegister);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                name = usernameEt.getText().toString().trim();
                String password = usernameEt.getText().toString().trim();
                mPresenter.login(name, password); // 登录
                break;
        }


    }

    /**
     * 把P层放到V层里面
     *
     * @param presenter
     */
    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * 显示出用户名/ 密码 为空的提示信息
     */
    @Override
    public void showEmptyMsg() {
        Toast.makeText(getActivity(), "用户名/密码为空", Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示正在登录的画面
     */
    @Override
    public void showLoading() {
        // TODO 这里可能会有问题
        progressDialog = ProgressDialog.show(getContext(), "正在登录标题", "正在登录内容");


    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {
        progressDialog.dismiss();
//        ToastUtil.showShortToast("登录成功");
        Values.USER_NAME = name;
    }


    /**
     * 登录失败, 显示失败信息
     *
     * @param msg 失败信息
     */
    @Override
    public void loginError(String msg) {
        progressDialog.dismiss();
        ToastUtil.showShortToast(msg);

    }



}
