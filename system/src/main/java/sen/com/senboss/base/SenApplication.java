package sen.com.senboss.base;

import android.app.Application;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class SenApplication extends Application {
	/**
	 * SDK初始化也可以放到Application中
	 */
	public static String APPID = "49ca4fc05d3d69e251deb0e21fe1349c";

	@Override
	public void onCreate() {
		super.onCreate();
		//设置BmobConfig
		BmobConfig config =new BmobConfig.Builder()
				//请求超时时间（单位为秒）：默认15s
				.setConnectTimeout(30)
				//文件分片上传时每片的大小（单位字节），默认512*1024
				.setBlockSize(500*1024)
				.build();
		Bmob.getInstance().initConfig(config);
		//Bmob初始化
		Bmob.initialize(this, APPID);
	}

}
