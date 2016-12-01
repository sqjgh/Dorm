package com.example.dllo.dorm.firstpage.chat;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;

/**
 * Created by Wanghuan on 16/11/24.
 */
public class ChatInfoActivity extends BaseActivity {
    FloatingActionButton mFloatingActionButton;
    TextView mTextView;
    ImageView mIVClose;
    RelativeLayout mRelativeLayout;

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            initEnterData();
            initExitData();

        } else {

            initWork();
        }


    }

    private void initWork() {

        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(300);
        mTextView.startAnimation(animation);
        mIVClose.setAnimation(animation);
        mTextView.setVisibility(View.VISIBLE);
        //mIVClose.setVisibility(View.VISIBLE);

    }

    // 退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initExitData() {
        Fade fade = new Fade();
        getWindow().setReenterTransition(fade);
        fade.setDuration(300);

    }

    // 入场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initEnterData() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);

//        Scene scene = getContentScene();
//        transition = TransitionInflater.from(this).inflateTransitionManager()

        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animShow();

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });


    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animShow() {

        GuiUtils.animateRevealShow(this, mRelativeLayout, mFloatingActionButton.getWidth() / 2, R.color.colorPrimaryDark,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {

                    }
                });

    }


    @Override
    protected void initViews() {


        mFloatingActionButton = bindView(R.id.other_fab_circle);
//        mTextView = bindView(R.id.other_rl_container);
        mIVClose = bindView(R.id.other_iv_close);
        mRelativeLayout = bindView(R.id.other_rl_container);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    onBackPressed();
                } else {
                    defaultBackPressed();
                }
            }
        });
        mIVClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    onBackPressed();
                } else {
                    defaultBackPressed();
                }

            }
        });
    }



    @Override
    protected int getLayout() {
        return R.layout.im_info;
    }

    // 退出事件
    @Override
    public void onBackPressed() {
        GuiUtils.animateRevealHide(
                this, mRelativeLayout,
                mFloatingActionButton.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        defaultBackPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    private void defaultBackPressed() {
        super.onBackPressed();
    }

}
