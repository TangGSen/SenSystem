package sen.com.senboss.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sen.com.senboss.R;
import sen.com.senboss.base.BaseActivity;
import sen.com.senboss.fragment.FragmentEidtOrderReceiver;
import sen.com.senboss.fragment.FragmentGoodsDetails;
import sen.com.senboss.tools.ResourcesUtils;
import sen.com.senboss.tools.StatusBarCompat;

/**
 * Created by Administrator on 2016/4/16.
 */
public class SubmitOrderActivity extends BaseActivity {
    @Bind(R.id.layout_content)
    FrameLayout layout_content;
    @Bind(R.id.root_submit_layout)
    LinearLayout root_submit_layout;
    @Bind(R.id.btn_eidt)
    AppCompatTextView btn_eidt;
    @Bind(R.id.btn_goods_des)
    AppCompatTextView btn_goods_des;
    @Bind(R.id.down_imgbtn_close)
    AppCompatTextView down_imgbtn_close;

    FragmentManager mFragmentManager;

    private FragmentGoodsDetails mGoodsDetail;
    private FragmentEidtOrderReceiver mEidtOrderReceiver;
    private static final String TAG_GOODSDETAILS = "FragmentGoodsDetails";
    private static final String TAG_EIDTORDERRECEIVER = "FragmentEidtOrderReceiver";

    private int currentFragPosition = 0;
    private final String FRAG_POSITION = "currentFragPosition";


    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void dealAdaptationToPhone() {
        //动态设置coordinatorLayout 的marginTop,因为在小米的手机xml 的24dp中显示不正常
        FrameLayout.LayoutParams rootParams = (FrameLayout.LayoutParams) root_submit_layout.getLayoutParams();
        rootParams.setMargins(0, ResourcesUtils.getStatusBarHeight(this), 0, 0);
        root_submit_layout.setLayoutParams(rootParams);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        StatusBarCompat.compat(this, ResourcesUtils.getResColor(this, R.color.colorPrimaryDark));
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            //取出上一次保存的数据
            currentFragPosition = savedInstanceState.getInt(FRAG_POSITION, 0);
            Log.e("sen", "恢复的状态" + currentFragPosition);
            mGoodsDetail = (FragmentGoodsDetails) mFragmentManager.findFragmentByTag(TAG_GOODSDETAILS);
            mEidtOrderReceiver = (FragmentEidtOrderReceiver) mFragmentManager.findFragmentByTag(TAG_EIDTORDERRECEIVER);

        }
        changeIndexColor(currentFragPosition);
        setSelectedFragment(currentFragPosition);
    }

    private void setSelectedFragment(int position) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragments(transaction);
        switch (position) {
            case 0:
                if (mGoodsDetail == null) {
                    mGoodsDetail = new FragmentGoodsDetails();
                    transaction.add(R.id.layout_content, mGoodsDetail, TAG_GOODSDETAILS);
                } else {
                    transaction.show(mGoodsDetail);
                }

                break;
            case 1:
                if (mEidtOrderReceiver == null) {
                    mEidtOrderReceiver = new FragmentEidtOrderReceiver();
                    transaction.add(R.id.layout_content, mEidtOrderReceiver, TAG_EIDTORDERRECEIVER);
                } else {
                    transaction.show(mEidtOrderReceiver);
                }
                break;


        }
        currentFragPosition = position;
        transaction.commit();
    }

    private void hideAllFragments(FragmentTransaction transaction) {
        if (mGoodsDetail != null) {
            transaction.hide(mGoodsDetail);
        }
        if (mEidtOrderReceiver != null) {
            transaction.hide(mEidtOrderReceiver);
        }


    }

    private void changeIndexColor(int item) {

        switch (item) {
            case 0:
                btn_goods_des.setTextColor(ResourcesUtils.getResColor(this, R.color.tab_bgcolor));
                btn_eidt.setTextColor(ResourcesUtils.getResColor(this, R.color.primary_text));
                btn_goods_des.setBackgroundColor(ResourcesUtils.getResColor(this, R.color.colorPrimaryDark));
                btn_eidt.setBackgroundColor(ResourcesUtils.getResColor(this, R.color.tab_bgcolor));
                changeSelecteTabIcon(btn_goods_des, R.drawable.ic_gift_while);
                changeSelecteTabIcon(btn_eidt, R.drawable.ic_add_address);
                break;
            case 1:
                btn_goods_des.setTextColor(ResourcesUtils.getResColor(this, R.color.primary_text));
                btn_eidt.setTextColor(ResourcesUtils.getResColor(this, R.color.tab_bgcolor));
                btn_goods_des.setBackgroundColor(ResourcesUtils.getResColor(this, R.color.tab_bgcolor));
                btn_eidt.setBackgroundColor(ResourcesUtils.getResColor(this, R.color.colorPrimaryDark));
                changeSelecteTabIcon(btn_goods_des, R.drawable.ic_gift);
                changeSelecteTabIcon(btn_eidt, R.drawable.ic_add_address_while);
                break;
        }


    }


    public void changeSelecteTabIcon(AppCompatTextView textView, int drawableId) {
        Drawable topDrawable = ResourcesUtils.getResDrawable(this, drawableId);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        textView.setCompoundDrawables(topDrawable, null, null, null);

    }


    //系统销毁Activity 的时候保存Fragment 的状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(FRAG_POSITION, currentFragPosition);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.btn_goods_des)
    public void onGoodsDes() {
        if (currentFragPosition == 1) {
            setSelectedFragment(0);
            changeIndexColor(0);
        }
    }

    @OnClick(R.id.btn_eidt)
    public void onEidt() {
        if (currentFragPosition == 0) {
            setSelectedFragment(1);
            changeIndexColor(1);
        }
    }@OnClick(R.id.down_imgbtn_close)
    public void onClose() {
        finish();
    }

}