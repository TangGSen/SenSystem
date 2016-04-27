package sen.com.senboss.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.blurry.Blurry;
import sen.com.senboss.R;
import sen.com.senboss.base.BaseActivity;
import sen.com.senboss.tools.GetSoftKeyboardUtil;
import sen.com.senboss.tools.ResourcesUtils;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "sen";
    @Bind(R.id.login_layot)
    LinearLayout login_layot;
    @Bind(R.id.login_root_view)
    RelativeLayout login_root_view;
    @Bind(R.id.login_user_input_layout)
    TextInputLayout login_user_input_layout;
    @Bind(R.id.login_password_input_layout)
    TextInputLayout login_password_input_layout;
    @Bind(R.id.user_name)
    AppCompatEditText user_name;
 @Bind(R.id.password)
    AppCompatEditText password;


    @Bind(R.id.bt_login)
    AppCompatButton bt_login;

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
        GetSoftKeyboardUtil.getInstance().observeSoftKeyboard(this, new GetSoftKeyboardUtil.OnSoftKeyboardChangeListener() {

            @Override
            public void onSoftKeyBoardChange(int rootViewHeight, int softKeybardHeight, boolean visible) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (visible) {
//                    lp.setMargins(0, rootViewHeight - softKeybardHeight - login_layot.getHeight(), 0, 0);
                    Log.e(TAG, "onSoftKeyBoardChange: " + rootViewHeight + "___" + softKeybardHeight);
                    lp.setMargins(0, 0, 0, softKeybardHeight - ResourcesUtils.getStatusBarHeight(LoginActivity.this));
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                } else {
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                }
                login_layot.setLayoutParams(lp);
            }
        });


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected void initListener() {
        super.initListener();
        Blurry.with(this)
                .radius(50)
                .sampling(20)
                .color(Color.argb(66, 255, 255, 0))
                .async()
                .animate(500)
                .onto(login_root_view);
        // 那个获取焦点的无效，就这个好使
        user_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_user_input_layout.setErrorEnabled(false);
                login_password_input_layout.setErrorEnabled(false);
                return false;
            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                login_user_input_layout.setErrorEnabled(false);
                login_password_input_layout.setErrorEnabled(false);
                return false;
            }
        });

    }

    @OnClick(R.id.bt_login)
    public void checkLogin() {
        String userName = login_user_input_layout.getEditText().getText().toString();
        String password = login_password_input_layout.getEditText().getText().toString();

        if (userName.length() == 0) {
            login_user_input_layout.setErrorEnabled(true);
            login_user_input_layout.setError(getString(R.string.login_number_null));
        } else {
            login_user_input_layout.setErrorEnabled(false);
        }
        if (password.length() == 0) {
            login_password_input_layout.setErrorEnabled(true);
            login_password_input_layout.setError(getString(R.string.login_password_null));
        } else {
            login_password_input_layout.setErrorEnabled(false);
        }

        //然后全部判断是否通过
        if (userName.length() != 0 && password.length() != 0) {
            login(userName, password);

        }
    }

    private void login(String userName, String password) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetSoftKeyboardUtil.getInstance().cancleObserve(this);
    }
}
