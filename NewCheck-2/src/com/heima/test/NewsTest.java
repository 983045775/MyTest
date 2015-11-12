package com.heima.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.heima.demain.Newsbean;
import com.heima.utils.NewsUtils;

public class NewsTest extends AndroidTestCase {

	public void mytest() {

		try {
			List<Newsbean> list = NewsUtils.getNews(getContext());
			for (Newsbean news : list) {
				System.out.println(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
