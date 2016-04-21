package sen.com.senboss.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sen.com.senboss.R;
import sen.com.senboss.base.BaseFragment;
import sen.com.senboss.calender.CalendarDialog;

/**
 * Created by Administrator on 2016/4/16.
 * 填写收货信息
 */
public class FragmentEidtOrderReceiver extends BaseFragment {

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
    @Bind(R.id.sp_province)
    AppCompatSpinner mSpProvince;//省下拉控件
    @Bind(R.id.sp_city)
    AppCompatSpinner mSpCity;//城市下拉控件
    @Bind(R.id.sp_arear)
    AppCompatSpinner mSpArea;//地区下拉控件
    @Bind(R.id.choose_date)
    AppCompatTextView choose_date;//地区下拉控件

    private JSONObject mJsonObj;//把全国的省市区的信息以json的格式保存，解析完成后赋值为null

    private String[] mProvinceDatas;//所有省
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();//key - 省 value - 市s
    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();//key - 市 values - 区s

    private String mCurrentProviceName;//当前省的名称
    private String mCurrentAreaName = "";//当前区的名称

    private ArrayAdapter<String> mProvinceAdapter;//省份数据适配器
    private ArrayAdapter<String> mCityAdapter;//城市数据适配器
    private ArrayAdapter<String> mAreaAdapter;//省份数据适配器

    private String mAddress;//原有的地址
    private String[] mAddressList;//原有地址，用空格进行切分地址

    private Boolean isFirstLord = true;//判断是不是最近进入对话框
    private Boolean ifSetFirstAddress = true;//判断是否已经设置了，初始的详细地址

    private AlertDialog dialog;

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
        rootView = inflater.inflate(R.layout.fragment_eidt_address_msg, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    public void initCanlenderView() {
        final Calendar calendar = Calendar.getInstance();
        dialog = new CalendarDialog().getCalendarDialog(mActivity, true, true, Calendar.getInstance().get(Calendar.YEAR) + 1, 1950, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new CalendarDialog.OnSelectDateListener() {
                    @Override
                    public void onSelectDate(long time, int year, int month, int day, boolean isLunar) {
                        // calendar.setTimeInMillis(time);
                        Date d = new Date(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (choose_date != null) {
                            choose_date.setText(sdf.format(d) + "");
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        initChooseData();
        initJsonData();
        parseJsonData();
        int selectPro = 0;//有传输数据时
        /**
         * 设置，省，市，县，的适配器，进行动态设置其中的值  begin
         */
        mProvinceAdapter = new ArrayAdapter<String>(mActivity, R.layout.customer_simp_spinner_item);
        for (int i = 0; i < mProvinceDatas.length; i++) {
            //当有传入值时，进行判断选中的条目，设置默认值
            if (mAddress != null && !mAddress.equals("") && mAddressList.length > 0 && mAddressList[0].equals(mProvinceDatas[i])) {
                selectPro = i;
            }
            mProvinceAdapter.add(mProvinceDatas[i]);
        }
        mProvinceAdapter.setDropDownViewResource(R.layout.cumstomer_spinner_dropdown_item);
        mSpProvince.setAdapter(mProvinceAdapter);
        mSpProvince.setSelection(selectPro);
        mCityAdapter = new ArrayAdapter<String>(mActivity, R.layout.customer_simp_spinner_item);
        mCityAdapter.setDropDownViewResource(R.layout.cumstomer_spinner_dropdown_item);
        mSpCity.setAdapter(mCityAdapter);
        mAreaAdapter = new ArrayAdapter<String>(mActivity, R.layout.customer_simp_spinner_item);
        mAreaAdapter.setDropDownViewResource(R.layout.cumstomer_spinner_dropdown_item);
        mSpArea.setAdapter(mAreaAdapter);
        /**
         * 设置，省，市，县，的适配器，进行动态设置其中的值  end
         */
        setupViewsListener();
        initCanlenderView();
    }

    private void initChooseData() {
        if (choose_date != null) {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            choose_date.setText(format.format(date));
        }
    }

    /**
     * 设置控件点击选择监听
     */
    private void setupViewsListener() {
        mSpProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                mCurrentProviceName = arg0.getSelectedItem() + "";
                if (isFirstLord) {
                    if (mAddress != null && !mAddress.equals("") && mAddressList.length > 1 && mAddressList.length < 3) {
                        updateCitiesAndAreas(mCurrentProviceName, mAddressList[1], null);
                    } else if (mAddress != null && !mAddress.equals("") && mAddressList.length >= 3) {
                        updateCitiesAndAreas(mCurrentProviceName, mAddressList[1], mAddressList[2]);
                    } else {
                        updateCitiesAndAreas(mCurrentProviceName, null, null);
                    }
                } else {
                    updateCitiesAndAreas(mCurrentProviceName, null, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        mSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (!isFirstLord) {
                    updateAreas(arg0.getSelectedItem(), null);
                } else {
                    if (mAddress != null && !mAddress.equals("") && mAddressList.length == 4) {
                        //  mEtDetailAddre.setText(mAddressList[3]);
                    }
                }
                isFirstLord = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        mSpArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mCurrentAreaName = arg0.getSelectedItem() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 根据当前的省，更新市和地区信息
     */
    private void updateCitiesAndAreas(Object object, Object city, Object myArea) {
        int selectPosition = 0;//当有数据时，进行匹配城市，默认选中
        String[] cities = mCitisDatasMap.get(object);
        mCityAdapter.clear();
        for (int i = 0; i < cities.length; i++) {
            if (city != null && city.toString().equals(cities[i])) {
                selectPosition = i;
            }
            mCityAdapter.add(cities[i]);
        }
        mCityAdapter.notifyDataSetChanged();
        if (city == null) {
            updateAreas(cities[0], null);
        } else {
            mSpCity.setSelection(selectPosition);
            updateAreas(city, myArea);
        }
    }


    /**
     * 根据当前的市，更新地区信息
     */
    private void updateAreas(Object object, Object myArea) {
        boolean isAreas = false;//判断第三个地址是地区还是详细地址
        int selectPosition = 0;//当有数据时，进行匹配地区，默认选中
        String[] area = mAreaDatasMap.get(object);
        mAreaAdapter.clear();
        if (area != null) {
            for (int i = 0; i < area.length; i++) {
                if (myArea != null && myArea.toString().equals(area[i])) {
                    selectPosition = i;
                    isAreas = true;
                }
                mAreaAdapter.add(area[i]);
            }
            mAreaAdapter.notifyDataSetChanged();
            mSpArea.setSelection(selectPosition);
        }
        //第三个地址是详细地址，并且是第一次设置edtext值，正好，地址的长度为3的时候，设置详细地址
        if (!isAreas && ifSetFirstAddress && mAddress != null && !mAddress.equals("") && mAddressList.length == 3) {
            // mEtDetailAddre.setText(mAddressList[2]);
            ifSetFirstAddress = false;
        }
    }


    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = mActivity.getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void parseJsonData() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceDatas = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字

                mProvinceDatas[i] = province;

                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字
                    mCitiesDatas[j] = city;
                    JSONArray jsonAreas = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("a");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
                        mAreasDatas[k] = area;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                }

                mCitisDatasMap.put(province, mCitiesDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }


    @OnClick(R.id.choose_date)
    public void chooseDate() {
        if (dialog==null){
            initCanlenderView();
        }

        dialog.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog!=null && dialog.isShowing())
            dialog.dismiss();
    }
}
