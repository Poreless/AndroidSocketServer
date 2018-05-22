 package wjx.server.app;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import wjx.server.socket.CmdServerSocketThread;

public class ServerSocketApp {
	/**
	 * ���������ڣ�ֱ���� main ������������Ӧ��
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cmdPort = 8019;
		boolean isFail = true;
		try {
			System.out.println("����IP="+Inet4Address.getLocalHost());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {// �������������⣬�����������������
			try {
				if (isFail) {// ��������� isFail=true, ������ new ��һ�� ServerSocket
								// �߳̽��з���
					isFail = false;
					CmdServerSocketThread cmdServerSocket = new CmdServerSocketThread(
							cmdPort);
					cmdServerSocket.start();// ��һ�й������������߳���Զ�������
					cmdServerSocket.join();// �ȴ��߳�ִ�н������߳�����ʹ�� try catch
											// ������׽���쳣���߳̽���
					System.out.println("The CmdServerSocketThread is finished!");
				}
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				isFail = true;
			}
		}
	}
}