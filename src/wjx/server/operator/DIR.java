package wjx.server.operator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DIR {// �ṩ public �ľ�̬������ʹ֮���� new �����󼴿�ֱ�ӵ���
	public static ArrayList<String> exe(String cmdBody) {
		ArrayList<String> ackMsg = new ArrayList<String>();// ִ����Ϸ��ص���Ϣ������ն���
			File file = new File(cmdBody);
			File[] listFiles = file.listFiles();
			ackMsg.add(cmdBody);// ����Ϊ·�������������ļ����б�
			for (File mfile : listFiles) {
				String fileName = mfile.getName();
				long lastModified = mfile.lastModified();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String fileDate = dateFormat.format(new Date(lastModified));// ȡ���ļ�����޸�ʱ�䣬������ʽתΪ�ַ���
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