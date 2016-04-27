package sen.com.senboss.tools;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Administrator on 2016/4/27.
 */
public class GetSoftKeyboardUtil {
    private CusOnGlobalLayoutListener mCuslinstener;
    public  void observeSoftKeyboard(Activity activity,  OnSoftKeyboardChangeListener listener) {
         View decorView = activity.getWindow().getDecorView();
        mCuslinstener   = new CusOnGlobalLayoutListener(decorView,listener);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(mCuslinstener);
    }

    private   class CusOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{
        private View mView ;
        private OnSoftKeyboardChangeListener mListener;
        int previousKeyboardHeight = -1;
        public CusOnGlobalLayoutListener(View view,OnSoftKeyboardChangeListener listener){
            this.mListener = listener;
            this.mView = view;
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            mView.getWindowVisibleDisplayFrame(rect);
            int displayHeight = rect.bottom - rect.top;
            int height = mView.getHeight();
            int keyboardHeight = height - displayHeight;
            if (previousKeyboardHeight != keyboardHeight) {
                boolean hide = (double) displayHeight / height > 0.8;
                mListener.onSoftKeyBoardChange(height,keyboardHeight, !hide);
            }

            previousKeyboardHeight = height;
        }
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int rootViewHeight,int softKeybardHeight, boolean visible);
    }

    public void cancleObserve(Activity activity){
        if (mCuslinstener !=null){
            View decorView = activity.getWindow().getDecorView();
            decorView.getViewTreeObserver().removeGlobalOnLayoutListener(mCuslinstener);
        }

    }

    private static volatile GetSoftKeyboardUtil instance = null;
    private GetSoftKeyboardUtil() { }
    public static GetSoftKeyboardUtil getInstance() {
        if(instance == null) {
            synchronized(GetSoftKeyboardUtil.class){
                if(instance == null) {
                    instance = new GetSoftKeyboardUtil();
                }
            }
        }
        return instance;
    }

}
