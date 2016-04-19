package sen.com.senboss.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import sen.com.senboss.R;
import sen.com.senboss.base.BaseFragment;

/**
 * Created by Administrator on 2016/4/16.
 */
public class FragmentGoodsDetails extends BaseFragment {

    private View rootView;
//    @Bind(R.id.root_layout)
//    LinearLayout root_layout;
//    @Bind(R.id.tv_submit_order)
//    AppCompatTextView tv_submit_order;
//    @Bind(R.id.tv_check_order)
//    AppCompatTextView tv_check_order;
//    @Bind(R.id.tv_check_truefalse)
//    AppCompatTextView tv_check_truefalse;
    private Activity mActivity;

    @Override
    protected void dealAdaptationToPhone() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_goodsdes_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {

    }





}
