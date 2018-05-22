package wjx.server.operator;

import java.awt.Robot;
import java.util.ArrayList;

public class KEY extends BaseOperator {
	private Robot robot;

	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		// cmdBody �����ж��ţ���ʾ��ϼ�������Ϊ����
		// ��ϼ�ʾ���� VK_ALT+VK_TAB,VK_TAB+VK_ALT ��ʾ�Ȱ��� alt �����ٰ��� tab �������ͷ� tab
		// �������ͷ� alt ��
		// ����ǰ��� + ��ʾ���¼���˳�򣬶��ź���� + ��ʾ�ͷż���˳��
		ArrayList<String> ackMsg = new ArrayList<String>();
		robot = new Robot();
		int splitIdx = cmdBody.indexOf(",");
		if (splitIdx < 1) {
			int splitIdx2 = cmdBody.indexOf("+");
			if (splitIdx2 < 1) {
				singleKeyPress(cmdBody);
			} else {
				simpleComboKeyPress(cmdBody);
			}
		} else {
			String keyPressStr = cmdBody.substring(0, splitIdx);
			String keyReleaseStr = cmdBody.substring(splitIdx + 1);
			comboKeyPress(keyPressStr, keyReleaseStr);
		}
		ackMsg.add("key:" + cmdBody);
		return ackMsg;
	}

	private void simpleComboKeyPress(String keyPressStr) {
		String[] keyPressArray = keyPressStr.split("\\+");
														
		for (int i = 0; i < keyPressArray.length; i++) {
			int keycode = KeyMap.getKey(keyPressArray[i]);
			robot.keyPress(keycode);
			System.out.println("������"+keyPressArray[i]);
		}
		for (int i = keyPressArray.length - 1; i >= 0; i--) {
			int keycode = KeyMap.getKey(keyPressArray[i]);
			robot.keyRelease(keycode);
			System.out.println("�ͷ���"+keyPressArray[i]);
		}
	}

	private void comboKeyPress(String keyPressStr, String keyReleaseStr) {
		// TODO Auto-generated method stub
		String[] keyPressArray = keyPressStr.split("\\+");
		String[] keyReleaseArray = keyReleaseStr.split("\\+");
		for (int i = 0; i < keyPressArray.length; i++) {
			int keycode = KeyMap.getKey(keyPressArray[i]);
			robot.keyPress(keycode);
			System.out.println("������"+keyPressArray[i]);
		}
		for (int i = 0; i < keyReleaseArray.length; i++) {
			int keycode = KeyMap.getKey(keyReleaseArray[i]);
			robot.keyRelease(keycode);
			System.out.println("�ͷ���"+keyPressArray[i]);
			
		}
	}

	private void singleKeyPress(String cmdBody) {
		// TODO Auto-generated method stub
		int keycode = KeyMap.getKey(cmdBody);
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
	}
	
}