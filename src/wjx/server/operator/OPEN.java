package wjx.server.operator;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;

public class OPEN extends BaseOperator {
	public ArrayList<String> exe(String cmdBody) throws Exception{
		Desktop desk=Desktop.getDesktop();  
		ArrayList<String> ackMsg = new ArrayList<String>();
	    File file=new File(cmdBody);//����һ��java�ļ�ϵͳ  
	    desk.open(file); //����open��File f���������ļ�   
	    ackMsg.add("OPN:"+cmdBody);
	    return null;
	}

}
