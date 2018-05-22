package wjx.server.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import wjx.server.operator.HOME;
import wjx.server.operator.Operator;

public class CmdServerSocketThread extends Thread {
	int port = 8019;// �Զ���һ���˿ڣ��˿ںž�������ѡһЩ������������ռ�õĶ˿ڣ����
					// http://blog.csdn.net/hsj521li/article/details/7678880
	private BufferedReader bufferedReader;
	private OutputStreamWriter writer;
	private ServerSocket serverSocket;
	
	ArrayList<String> msgBackList = new ArrayList<>();
	

	public CmdServerSocketThread() {
		// TODO Auto-generated constructor stub

	}

	public CmdServerSocketThread(int port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// ע�⣺���� Socket �Ĺ���������ʽ�� Android �� Socket �Ĺ����������µ��߳���ʵ�֣����� UI ���߳��й����ᱨ��
		try {
			serverSocket = new ServerSocket(port);
			doCmdTask(serverSocket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
	}

	public void close() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				System.out.println("The CmdServerSocket is closed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void doCmdTask(ServerSocket serverSocket) throws Exception {
		while (true) {//  ����ѭ����ʹ֮�ܽ�����ǰ socket �����׼����һ�� socket ����
			if (serverSocket.isClosed()) {
				System.out.println("The server socket is closed for ever. Please create another CmdServerSocket!");
				break;
			}
			System.out.println("Waiting client to connect.....");
			Socket socket;
			try {
				socket = serverSocket.accept(); //  ����ʽ��ֱ���пͻ������ӽ������Ż��������ִ�У�����һֱͣ���ڴ˴���
				System.out.println("Client connected from: "+socket.getRemoteSocketAddress().toString());
				// InetAddress inetAddress = socket.getInetAddress();// ��ȡ��ʱ��������������
				// System.out.println("Client connected from: "
				// + inetAddress.getHostName() + "\t"
				// + inetAddress.getHostAddress());//  ��ӡ�ͻ��˵������� IP ��ַ
				try{
					getAndDealCmd(socket);//  �� socket ������������д������Ѵ��������� msgBackList ���� socket д�뷵�ؿͻ�����Ϣ
					//getAndDealCmd �����Լ����п��ܻ����쳣���ֵĵ��ú�������д�� throws Exception ��ʽ���׳��쳣
					}catch(Exception e){
					System.out.println("getAndDealCmd(socket) error:"+e.getMessage());
				}
				writeBackMsg(socket);//  �� msgBackList ��Ϣ��һ��Ԫ��һ�з��ͻؿͻ��ˣ����������Ƿ��͵�������
				bufferedReader.close();//  ���������رգ��������Ҳ��رգ����� socket Ҳ���ر�
				writer.close();
				socket.close();//  �Ѿ�����ǰ�ر�
				System.out.println(" ��ǰ Socket ������� ");
			} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
				close();//serverSocket �رգ��˳���ѭ�����߳̽�����
				System.out.println(e.toString());
			}
		}
	}

	private void writeBackMsg(Socket socket) throws Exception {
		// TODO Auto-generated method stub
		BufferedOutputStream os = new BufferedOutputStream(
				socket.getOutputStream());// socket.getOutputStream() ���������
											// �����װΪ������������
		writer = new OutputStreamWriter(os, "UTF-8");// ���Խ��ַ�����ĳ� "GB2312"
		writer.write("" + msgBackList.size() + "\n");// δ����д�����������������ڴ���
		writer.flush();// д������������������ݴ����ȥ
		for (int i = 0; i < msgBackList.size(); i++) {
			writer.write(msgBackList.get(i) + "\n");
			writer.flush();
		}
	}

	public ArrayList<String> readSocketMsg(Socket socket) throws Exception {
		// �� socket ��������������� socket �������Ѿ����ӳɹ�δ���ڹرյ� socket
		// ���ȶ�ȡһ�У�������ȡ���ַ�������ת��Ϊ int �����ݣ��ѻ�ú�����Ҫ��ȡ������
		ArrayList<String> msgList = new ArrayList<String>();
		InputStream inputStream = socket.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
		bufferedReader = new BufferedReader(reader);
		String lineNumStr = bufferedReader.readLine();
		int lineNum = Integer.parseInt(lineNumStr);
		for (int i = 0; i < lineNum; i++) {
			String str = bufferedReader.readLine();
			msgList.add(str);
		}
		// ��ȡ���������������ܹرգ���ʱ�رգ��Ὣ socket �رգ��Ӷ����º����� socket д�����޷�ʵ��
		return msgList;
	}

	public void getAndDealCmd(Socket socket) throws Exception {
		ArrayList<String> cmdList = readSocketMsg(socket);
		if (cmdList.size() == 1) {// ����ֻ��һ�У��������������ж�
			String cmd = cmdList.get(0);
			processCmd(cmd);
		}
	}

	private void cmdFail() {
		msgBackList.clear();// ���б���Ϊ 0 ����ʾ�����ִ��ʧ��
		// String msgBack="Invalid command";
		// msgBackList.add(msgBack);// ������ϢΪ����ʧ��
	}

	private void processCmd(String cmd) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Client Command:" + cmd);
		int splitIdx = cmd.indexOf(":");
		if (splitIdx < 1) {
			cmdFail();
			return;
		}
		String cmdHead = cmd.substring(0, splitIdx);
		String cmdBody = cmd.substring(splitIdx + 1);
		msgBackList = Operator.exeCmd(cmdHead.toLowerCase(), cmdBody);// ������ǰ׺תΪСд���Ա�֤�жϲ��ִ�Сд
	}
}
