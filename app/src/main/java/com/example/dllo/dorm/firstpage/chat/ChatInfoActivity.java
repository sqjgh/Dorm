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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;

/**
 * Created by Wanghuan on 16/11/24.
 */
public class ChatInfoActivity extends BaseActivity {
    private RelativeLayout otherRlContainer;
    private FloatingActionButton otherFabCircle;
    private TextView otherTvContainer;

    @Override
    protected int getLayout() {
        return R.layout.im_info;
    }

    @Override
    protected void initViews() {
        otherRlContainer = bindView(R.id.other_rl_container);
        otherFabCircle = bindView(R.id.other_fab_circle);
        otherTvContainer = bindView(R.id.other_tv_container);
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        }else {
            otherFabCircle.setVisibility(View.INVISIBLE);
        }

    }

    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(0);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(
                this, otherRlContainer,
                otherFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initTheViews();
                    }
                });


    }

    private void initTheViews() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(300);
        otherTvContainer.startAnimation(animation);
        otherTvContainer.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GuiUtils.animateRevealHide(
                    this, otherRlContainer,
                    otherFabCircle.getWidth() / 2, R.color.colorAccent,
                    new GuiUtils.OnRevealAnimationListener() {
                        @Override
                        public void onRevealHide() {
                            defaultBackPressed();
                        }

                        @Override
                        public void onRevealShow() {

                        }
                    });
        }else {
            defaultBackPressed();
        }
    }

    private void defaultBackPressed() {
        super.onBackPressed();

    }


}
