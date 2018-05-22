package wjx.server.operator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import wjx.server.socket.CmdServerSocketThread;
import wjx.server.socket.FileDownLoadSocketThread;

public class DLF {
	public static ArrayList<String> exe(String cmdBody) {
		ArrayList<String> ackMsg = new ArrayList<String>();// 执行完毕返回的信息，避免空对象
			File file = new File(cmdBody);
			String fileName = file.getName();

			try {
				FileDownLoadSocketThread load = new FileDownLoadSocketThread();
				load.start();    //此线程为被动启动
				load.join();
				System.out.println("启动新进程..");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return ackMsg;
	}
}
