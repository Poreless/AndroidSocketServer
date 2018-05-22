package wjx.server.operator;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;

public class OPEN extends BaseOperator {
	public ArrayList<String> exe(String cmdBody) throws Exception{
		Desktop desk=Desktop.getDesktop();  
		ArrayList<String> ackMsg = new ArrayList<String>();
	    File file=new File(cmdBody);//创建一个java文件系统  
	    desk.open(file); //调用open（File f）方法打开文件   
	    ackMsg.add("OPN:"+cmdBody);
	    return null;
	}

}
