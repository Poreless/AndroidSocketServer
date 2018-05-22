package wjx.server.operator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DIR {// 提供 public 的静态方法，使之不用 new 出对象即可直接调用
	public static ArrayList<String> exe(String cmdBody) {
		ArrayList<String> ackMsg = new ArrayList<String>();// 执行完毕返回的信息，避免空对象
			File file = new File(cmdBody);
			File[] listFiles = file.listFiles();
			ackMsg.add(cmdBody);// 首行为路径，后续才是文件的列表
			for (File mfile : listFiles) {
				String fileName = mfile.getName();
				long lastModified = mfile.lastModified();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String fileDate = dateFormat.format(new Date(lastModified));// 取得文件最后修改时间，并按格式转为字符串
				String fileSize = "0";
				String isDir = "1";
				if (!mfile.isDirectory()) {
					isDir = "0";
					fileSize = "" + mfile.length();
				}
				ackMsg.add(fileName + ">" + fileDate + ">" + fileSize + ">" + isDir
						+ ">");
			}

		return ackMsg;
	}
}