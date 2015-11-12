package com.heima.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.util.Base64;

import com.example.newcheck_2.R;
import com.heima.demain.Newsbean;

import con.heima.service.NewsParser;

/**
 * ���ŵ�ҵ����,���н�����ַ
 * 
 * 
 */
public class NewsUtils {

	public static List<Newsbean> getNews(Context context) throws Exception {
		// ��ȡ��ַ
		String net = context.getResources().getString(R.string.news);
		// ��������
		URL url = new URL(net);
		// �򿪷���
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// ��ӷ��ʷ�ʽ
		conn.setRequestMethod("GET");

		// ��ʱ
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(10000);
		// �鿴����Դ
		int code = conn.getResponseCode();
		if (code == 200) {
			InputStream stream = conn.getInputStream();
			File file = new File(context.getFilesDir(), Base64.encodeToString(
					net.getBytes(), Base64.DEFAULT));
			OutputStream out = new FileOutputStream(file);
			int len = -1;
			byte[] by = new byte[1024];

			while ((len = stream.read(by)) != -1) {
				out.write(by, 0, len);
			}
			stream.close();
			out.close();
			// ��������Դ
			return NewsParser.parser(file);
		}
		return null;
	}
}
