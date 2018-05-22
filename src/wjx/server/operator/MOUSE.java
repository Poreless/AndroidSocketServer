package wjx.server.operator;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Point;
import java.awt.Robot;
import java.awt.MouseInfo;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.tools.JavaFileManager.Location;

public class MOUSE extends BaseOperator {
	private Robot robot;
	public int left = KeyEvent.BUTTON1_MASK;
	public int right = KeyEvent.BUTTON3_MASK;
	
	ArrayList<String> mva(String cmdBody) throws AWTException {

		ArrayList<String> ackMsg = new ArrayList<String>();
		robot = new Robot();
		int splitIdx = cmdBody.indexOf(",");
		int ValueX = Integer.parseInt(cmdBody.substring(0, splitIdx));
		int ValueY = Integer.parseInt(cmdBody.substring(splitIdx + 1));
		if(ValueX>=0&&ValueY>=0){
			//System.out.println("X:"+ValueX+"Y:"+ValueY);
			robot.mouseMove(ValueX,ValueY);
			//robot.delay(100);//停顿100毫秒,即0.1秒
			//robot.setAutoDelay(200);
			System.out.println("鼠标已移动至"+"(X:"+ValueX+", Y:"+ValueY+")");
		}
		ackMsg.add("mva:" + cmdBody);
		return ackMsg;		
	}
	ArrayList<String> mov(String cmdBody) throws Exception {
		ArrayList<String> ackMsg = new ArrayList<String>();
		robot = new Robot();
		Point point = MouseInfo.getPointerInfo().getLocation();
		int splitIdx = cmdBody.indexOf(",");
		int ValueX = Integer.parseInt(cmdBody.substring(0, splitIdx));
		int ValueY = Integer.parseInt(cmdBody.substring(splitIdx + 1));
		point.x = point.x+ValueX;
		point.y = point.y+ValueY;
		if(point.x<0)
			point.x=0;
		if(point.y<0)
			point.y=0;
		robot.mouseMove(point.x,point.y);
		System.out.println("鼠标已移动至"+"(X:"+point.x+", Y:"+point.y+")");
/*		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Point point = MouseInfo.getPointerInfo().getLocation();
				System.out.println("Location:x="+point.x+",y="+point.y);
			}
		}, 100,100);*/
		ackMsg.add("mov:" + cmdBody);
		return ackMsg;
	}
	
	ArrayList<String> rol(String cmdBody) throws Exception {

		ArrayList<String> ackMsg = new ArrayList<String>();
		robot = new Robot();
		int ValueX = Integer.parseInt(cmdBody);
		//System.out.println("X:"+ValueX+"Y:"+ValueY);
		robot.mouseWheel(ValueX);
		System.out.println("鼠标已向下滚动"+ValueX+"格");
		ackMsg.add("rol:" + cmdBody);
		return ackMsg;		
	}
	
	ArrayList<String> clk(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg = new ArrayList<String>();
		robot = new Robot();
		switch(cmdBody){
			case "left":
				robot.mousePress(left);
				robot.mouseRelease(left);
				break;
			case "right":
				robot.mousePress(right);
				robot.mouseRelease(right);
				break;
			case "left_press":
				robot.mousePress(left);
				break;
			case "left_release":
				robot.mouseRelease(left);
				break;				
			case "right_press":
				robot.mousePress(right);
				break;
			case "right_release":
				robot.mouseRelease(right);
				break;
			default:
				System.out.println("无匹配....");
				break;
					
	}
		ackMsg.add("clk:" + cmdBody);
		//System.out.println("已执行"+cmdBody);
		return ackMsg;
	}
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
