package wjx.server.operator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import wjx.server.socket.CmdServerSocketThread;
import wjx.server.socket.FileDownLoadSocketThread;

public class DLF {
	public static ArrayList<String> exe(String cmdBody) {
		ArrayList<String> ackMsg = new ArrayList<String>();// ִ����Ϸ��ص���Ϣ������ն���
			File file = new File(cmdBody);
			String fileName = file.getName();

			try {
				FileDownLoadSocketThread load = new FileDownLoadSocketThread();
				load.start();    //���߳�Ϊ��������
				load.join();
				System.out.println("�����½���..");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return ackMsg;
	}
}
