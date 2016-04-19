package sen.com.senboss.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sen.com.senboss.R;
import sen.com.senboss.base.BaseFragment;
import sen.com.senboss.tools.ResourcesUtils;

/**
 * Created by Administrator on 2016/4/16.
 */
public class FragmentService extends BaseFragment {
    private View rootView;

    @Bind(R.id.root_layout_service)
    LinearLayout root_layout_service;

    @Bind(R.id.tv_after_service)
    AppCompatTextView tv_after_service;
    @Bind(R.id.tv_contact)
    AppCompatTextView tv_contact;


    @Override
    protected void dealAdaptationToPhone() {
        //动态设置coordinatorLayout 的marginTop,因为在小米的手机xml 的24dp中显示不正常
        FrameLayout.LayoutParams rootParams = (FrameLayout.LayoutParams) root_layout_service.getLayoutParams();
        rootParams.setMargins(0, ResourcesUtils.getStatusBarHeight(getContext()), 0, 0);
        root_layout_service.setLayoutParams(rootParams);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service_layout, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv_after_service)
    public void onAfterService() {
        call();

    }

    @OnClick(R.id.tv_contact)
    public void onContact() {
        call();
    }

    private void call() {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15510474794"));
        startActivity(intent);
    }

}
