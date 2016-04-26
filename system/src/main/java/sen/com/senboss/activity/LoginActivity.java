package sen.com.senboss.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import sen.com.senboss.R;
import sen.com.senboss.base.BaseActivity;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_login);
      //  StatusBarCompat.compat(this, ResourcesUtils.getResColor(this, R.color.colorPrimaryDark));
        ButterKnife.bind(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }
}
