package wjx.server.operator;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

public class CPS extends BaseOperator{
	private Robot robot;
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		robot = new Robot();
		ArrayList<String> ackMsg = new ArrayList<String>();
	    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//��ȡ���а�
	    Transferable tText = new StringSelection(cmdBody);//cmdBodyΪString�ַ�������Ҫ�����������������
	    clip.setContents(tText, null); //���ü��а�����
	    robot.keyPress(KeyMap.getKey("vk_control"));
	    robot.keyPress(KeyMap.getKey("vk_v"));
	    robot.keyRelease(KeyMap.getKey("vk_control"));
	    robot.keyRelease(KeyMap.getKey("vk_v"));
	    ackMsg.add("cps:" + cmdBody);
	    System.out.println("���а��Ѹ���..");
	    return ackMsg;
	}

}
