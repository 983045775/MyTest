package com.heima.ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CleverImageView extends ImageView {

	protected static final int ONE = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ONE:
				Bitmap map = (Bitmap) msg.obj;
				setImageBitmap(map);
				break;
			default:
				break;
			}

		}
	};

	public CleverImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CleverImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CleverImageView(Context context) {
		super(context);
	}

	public void setImageURL(final String path) {
		// 主线程不支持做这个
		new Thread() {
			public void run() {

				// 创建连接
				try {
					URL url = new URL(path);
					// 建立远程访问连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					// 设置超时
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(10000);

					// 提交方式
					conn.setRequestMethod("GET");
					InputStream in = conn.getInputStream();
					// 读到位图里
					Bitmap map = BitmapFactory.decodeStream(in);
					// 需要助手更新主线程的ui了
					Message mes = Message.obtain();
					mes.obj = map;
					mes.what = ONE;
					handler.sendMessage(mes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
