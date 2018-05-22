 package wjx.server.app;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import wjx.server.socket.CmdServerSocketThread;

public class ServerSocketApp {
	/**
	 * 服务程序入口，直接在 main 函数里调用相关应用
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cmdPort = 8019;
		boolean isFail = true;
		try {
			System.out.println("本机IP="+Inet4Address.getLocalHost());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {// 解决服务出现问题，不能重启服务的问题
			try {
				if (isFail) {// 当程序出错， isFail=true, 则重新 new 出一个 ServerSocket
								// 线程进行服务
					isFail = false;
					CmdServerSocketThread cmdServerSocket = new CmdServerSocketThread(
							cmdPort);
					cmdServerSocket.start();// 若一切工作正常，该线程永远不会结束
					cmdServerSocket.join();// 等待线程执行结束，线程中有使用 try catch
											// ，当捕捉到异常，线程结束
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