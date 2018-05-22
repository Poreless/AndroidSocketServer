package wjx.server.operator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DIRS{

	private static File file = new File("c:/");// 默认让 file 指向 C 盘

	public static ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		File[] listFiles;
		boolean isRoot = false;
		ArrayList<String> ackMsg = new ArrayList<String>();
		if (cmdBody.equals("...")) {
			ackMsg.add("");
			isRoot = true;
		} else if (cmdBody.equals("..")) {
			file = file.getParentFile();
		} else {
			file = new File(cmdBody);
		}
		if (isRoot) {
			listFiles = File.listRoots();
			for (File mfile : listFiles) {
				if (!mfile.canRead()) {
					continue;
				}
				String fileName = mfile.getPath();
				long lastModified = mfile.lastModified();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 对日期进行格式化
				String fileDate = dateFormat.format(new Date(lastModified));// 取得文件最后修改时间，并按格式转为字符串
				String fileSize = "0";
				String isDir = "2";
				ackMsg.add(fileName + ">" + fileDate + ">" + fileSize + ">"
						+ isDir + ">");
			}
		} else {
			String pwd = "";
			try {
				pwd = file.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("pwd=" + pwd);
			ackMsg.add(pwd);// 首行为路径
			ackMsg.add("..." + ">" + "" + ">" + "0" + ">" + "1" + ">");// 若不是显示盘符的根目录，则显示
																		// "..."
																		// 用于切换至根目录的操作
			String[] pwdSplits = pwd.split("/");
			String[] pwdSplits2 = pwd.split("\\\\");
			if (pwdSplits.length > 1 | pwdSplits2.length > 1) {// 判断是否是一级目录，若是二级以上目录，显示
																// ".."
																// 用于切换至上一级目录
				ackMsg.add(".." + ">" + "" + ">" + "0" + ">" + "1" + ">");
			}
			listFiles = file.listFiles();
			if (listFiles != null) {
				for (File mfile : listFiles) {
					if (!mfile.canRead()) {
						continue;
					}
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
					ackMsg.add(fileName + ">" + fileDate + ">" + fileSize + ">"
							+ isDir + ">");
				}
			}
		}
		return ackMsg;
	}

	public DIRS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean checkCanAcess(File f) {
		if (f.canRead()) {
			return false;
		}
		if (f.isHidden()) {
			return false;
		}
		return true;
	}
}
