package con.heima.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;
import com.heima.demain.Newsbean;

public class NewsParser {

	public static List<Newsbean> parser(File file) throws Exception {
		// 解析一个Xml文件
		XmlPullParser pull = Xml.newPullParser();
		InputStream in = new FileInputStream(file);
		pull.setInput(in, "utf-8");
		// 开始解析
		int type = pull.getEventType();
		boolean flag = false;
		Newsbean news = null;
		List<Newsbean> list = new ArrayList<Newsbean>();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG: // 标签开始了
				if (pull.getName().equals("item")) {
					flag = true;
				}
				if (flag) {
					if (pull.getName().equals("item")) { // 新闻标签开始了
						news = new Newsbean();
					} else if (pull.getName().equals("title")) {
						String title = pull.nextText();
						news.setTitle(title);
					} else if (pull.getName().equals("image")) {
						String image = pull.nextText();
						news.setImage(image);
					} else if (pull.getName().equals("type")) {
						String newstype = pull.nextText();
						news.setType(Integer.parseInt(newstype));
					} else if (pull.getName().equals("description")) {
						String description = pull.nextText();
						news.setDescription(description);
					} else if (pull.getName().equals("comment")) {
						String comment = pull.nextText();
						news.setComment(Integer.parseInt(comment));
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (pull.getName().equals("item")) {
					list.add(news);
				}
				break;
			}
			type = pull.next();
		}
		return list;
	}

}
