package sen.com.senboss.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sen.com.senboss.R;
import sen.com.senboss.activity.LoginActivity;
import sen.com.senboss.base.BaseFragment;
import sen.com.senboss.tools.ResourcesUtils;

/**
 * Created by Administrator on 2016/4/16.
 */
public class FragmentGift extends BaseFragment {

    private View rootView;
    @Bind(R.id.root_layout)
    LinearLayout root_layout;
    @Bind(R.id.tv_submit_order)
    AppCompatTextView tv_submit_order;
    @Bind(R.id.tv_check_order)
    AppCompatTextView tv_check_order;
    @Bind(R.id.tv_check_truefalse)
    AppCompatTextView tv_check_truefalse;
    private Activity mActivity;

    @Override
    protected void dealAdaptationToPhone() {
        //动态设置coordinatorLayout 的marginTop,因为在小米的手机xml 的24dp中显示不正常
        FrameLayout.LayoutParams rootParams = (FrameLayout.LayoutParams) root_layout.getLayoutParams();
        rootParams.setMargins(0, ResourcesUtils.getStatusBarHeight(getContext()), 0, 0);
        root_layout.setLayoutParams(rootParams);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gift_layout, container, false);
        ButterKnife.bind(this, rootView);
        setOnclickAble(false);
        return rootView;
    }

    @Override
    protected void initData() {
        jumpTV();
    }

    private void jumpTV() {
        TranslateAnimation down = new TranslateAnimation(0, 0, 300, 0);//位移动画，从button的上方300像素位置开始
        down.setFillAfter(true);
        down.setInterpolator(new BounceInterpolator());//弹跳动画,要其它效果的当然也可以设置为其它的值
        down.setDuration(1800);//持续时间
        final TranslateAnimation anima = new TranslateAnimation(0, 0, 300, 0);//位移动画，从button的上方300像素位置开始
        anima.setFillAfter(true);
        anima.setInterpolator(new BounceInterpolator());//弹跳动画,要其它效果的当然也可以设置为其它的值
        anima.setDuration(1800);//持续时间

        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //btn 可点击
                setOnclickAble(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (tv_check_order != null && tv_check_truefalse != null && tv_submit_order != null) {

            down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    tv_check_truefalse.startAnimation(anima);
                    tv_check_order.startAnimation(anima);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tv_submit_order.startAnimation(down);//设置按钮运行该动画效果


        }
    }

    //动画完毕之后能点击
    private void setOnclickAble(boolean isCan) {
        tv_submit_order.setClickable(isCan);
        tv_check_order.setClickable(isCan);
        tv_check_truefalse.setClickable(isCan);

    }

    //申请提货
    @OnClick(R.id.tv_submit_order)
    public void onSumbitOrder() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_check_order)
    public void onCheckOrder() {

    }

    @OnClick(R.id.tv_check_truefalse)
    public void onCheckTrueFalse() {

    }
}
