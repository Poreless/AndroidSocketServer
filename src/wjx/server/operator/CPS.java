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
	    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切板
	    Transferable tText = new StringSelection(cmdBody);//cmdBody为String字符串，需要拷贝进剪贴板的内容
	    clip.setContents(tText, null); //设置剪切板内容
	    robot.keyPress(KeyMap.getKey("vk_control"));
	    robot.keyPress(KeyMap.getKey("vk_v"));
	    robot.keyRelease(KeyMap.getKey("vk_control"));
	    robot.keyRelease(KeyMap.getKey("vk_v"));
	    ackMsg.add("cps:" + cmdBody);
	    System.out.println("剪切板已更新..");
	    return ackMsg;
	}

}
