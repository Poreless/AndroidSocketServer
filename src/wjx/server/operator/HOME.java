package wjx.server.operator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HOME {
	public static ArrayList<String> GetPathHost(){
		ArrayList<String> HomePath = new ArrayList<String>();
		File[] listFiles=File.listRoots();
		HomePath.add("");
		for (File mfile : listFiles) {
			String fileName = mfile.getName();
			String fileDate = "";// 取得文件最后修改时间，并按格式转为字符串
			String fileSize = "0";
			String isDir = "1";
			HomePath.add(fileName + ">" + fileDate + ">" + fileSize + ">" + isDir
					+ ">");
		}
		return HomePath;
	}
}
