package wjx.server.operator;

import java.awt.Robot;
import java.util.ArrayList;

public class CMD extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg = new ArrayList<String>();
		Runtime.getRuntime().exec("cmd /c start "+cmdBody);
		ackMsg.add("cmd:" + cmdBody);
		return ackMsg;	
	}

}
