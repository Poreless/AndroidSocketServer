package wjx.server.operator;

import java.util.ArrayList;

public class Operator {
	public static String Back_cmdHead;
	public static String Back_cmdBody;
	public static ArrayList<String> exeCmd(String cmdHead, String cmdBody)
			throws Exception {
		// ���е���������ڴ˾�̬�������жϼ����÷���
		// ���������������жϲ����ڴ˺��������
		ArrayList<String> msgBackList = new ArrayList<String>();
		if (cmdHead.equals("dir")) {
			msgBackList = DIRS.exe(cmdBody);
			//msgBackList = DIR.exe(cmdBody);
			Back_cmdHead = cmdHead;
			Back_cmdBody = cmdBody;
			return msgBackList;		
		}
		
		if (cmdHead.equals("open")) {
			new OPEN().exe(cmdBody);
			msgBackList = DIRS.exe(Back_cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("HOME")) {
			msgBackList = new HOME().GetPathHost();
			return msgBackList;
		}
		
		if (cmdHead.equals("key")) {
			msgBackList = new KEY().exe(cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("clk")) {
			msgBackList = new MOUSE().clk(cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("mov")) {
			System.out.println("����ִ��mov....");
			msgBackList = new MOUSE().mov(cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("mva")) {
			System.out.println("����ִ��mva....");
			msgBackList = new MOUSE().mva(cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("rol")) {
			System.out.println("����ִ��rol....");
			msgBackList = new MOUSE().rol(cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("cmd")) {
			System.out.println("����ִ��cmd....");
			msgBackList = new CMD().exe(cmdBody);
			return msgBackList;
		}
		
		if (cmdHead.equals("cps")) {
			System.out.println("����ִ��cps....");
			msgBackList = new CPS().exe(cmdBody);
			return msgBackList;
		}
		if (cmdHead.equals("dlf")) {
			System.out.println("���ڻ������ط���....");
			msgBackList = new DLF().exe(cmdBody);
			return msgBackList;
		}
		return msgBackList;
	}
}
