package com.example.newcheck_2;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.heima.demain.Newsbean;
import com.heima.ui.CleverImageView;
import com.heima.utils.NewsUtils;
import com.loopj.android.image.SmartImageView;

public class MainActivity extends Activity {

	private List<Newsbean> list;
	private ListView lv = null;
	private final int ONE = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ONE:
				lv.setAdapter(new Myadapter());
				break;

			default:
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.news);

		new Thread() {
			public void run() {
				try {
					list = NewsUtils.getNews(MainActivity.this);
					// 进行数据加载 需要hanlder
					Message msg = Message.obtain();
					msg.what = ONE;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	private class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				view = View.inflate(MainActivity.this, R.layout.news, null);
			} else {
				view = convertView;
			}
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView desc = (TextView) view.findViewById(R.id.desc);
			TextView type = (TextView) view.findViewById(R.id.type);
			SmartImageView img = (SmartImageView) view.findViewById(R.id.iv);

			img.setImageUrl(list.get(position).getImage(), R.drawable.error);
			title.setText(list.get(position).getTitle());
			desc.setText(list.get(position).getDescription());
			int count = list.get(position).getType();

			if (count == 1) {
				type.setText("评论数:" + list.get(position).getComment());
			} else if (count == 2) {
				type.setBackgroundColor(Color.CYAN);
				type.setText("视频");
			} else if (count == 3) {
				type.setBackgroundColor(Color.RED);
				type.setText("直播");
			}
			return view;
		}

	}
}
