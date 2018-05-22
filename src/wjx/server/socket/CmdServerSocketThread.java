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
	int port = 8019;// 自定义一个端口，端口号尽可能挑选一些不被其他服务占用的端口，祥见
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
		// 注意：由于 Socket 的工作是阻塞式， Android 端 Socket 的工作必须在新的线程中实现，若在 UI 主线程中工作会报错
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
		while (true) {//  无限循环，使之能结束当前 socket 服务后，准备下一次 socket 服务
			if (serverSocket.isClosed()) {
				System.out.println("The server socket is closed for ever. Please create another CmdServerSocket!");
				break;
			}
			System.out.println("Waiting client to connect.....");
			Socket socket;
			try {
				socket = serverSocket.accept(); //  阻塞式，直到有客户端连接进来，才会继续往下执行，否则一直停留在此代码
				System.out.println("Client connected from: "+socket.getRemoteSocketAddress().toString());
				// InetAddress inetAddress = socket.getInetAddress();// 获取的时间很慢，建议禁掉
				// System.out.println("Client connected from: "
				// + inetAddress.getHostName() + "\t"
				// + inetAddress.getHostAddress());//  打印客户端的域名和 IP 地址
				try{
					getAndDealCmd(socket);//  从 socket 接收命令，并进行处理，并把处理结果存于 msgBackList ，供 socket 写入返回客户端信息
					//getAndDealCmd 函数以及所有可能会有异常出现的调用函数均需写成 throws Exception 形式，抛出异常
					}catch(Exception e){
					System.out.println("getAndDealCmd(socket) error:"+e.getMessage());
				}
				writeBackMsg(socket);//  将 msgBackList 信息按一个元素一行发送回客户端，其中首行是发送的总行数
				bufferedReader.close();//  最外层的流关闭，里面的流也会关闭，包括 socket 也被关闭
				writer.close();
				socket.close();//  已经被提前关闭
				System.out.println(" 当前 Socket 服务结束 ");
			} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
				close();//serverSocket 关闭，退出死循环，线程结束。
				System.out.println(e.toString());
			}
		}
	}

	private void writeBackMsg(Socket socket) throws Exception {
		// TODO Auto-generated method stub
		BufferedOutputStream os = new BufferedOutputStream(
				socket.getOutputStream());// socket.getOutputStream() 是输出流，
											// 则将其封装为带缓冲的输出流
		writer = new OutputStreamWriter(os, "UTF-8");// 尝试将字符编码改成 "GB2312"
		writer.write("" + msgBackList.size() + "\n");// 未真正写入的输出流，仅仅在内存中
		writer.flush();// 写入输出流，真正将数据传输出去
		for (int i = 0; i < msgBackList.size(); i++) {
			writer.write(msgBackList.get(i) + "\n");
			writer.flush();
		}
	}

	public ArrayList<String> readSocketMsg(Socket socket) throws Exception {
		// 读 socket 的输入流，传入的 socket 参数是已经连接成功未处于关闭的 socket
		// 首先读取一行，并将读取的字符串内容转换为 int 型数据，已获得后续需要读取的行数
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
		// 读取结束后，输入流不能关闭，此时关闭，会将 socket 关闭，从而导致后续对 socket 写操作无法实现
		return msgList;
	}

	public void getAndDealCmd(Socket socket) throws Exception {
		ArrayList<String> cmdList = readSocketMsg(socket);
		if (cmdList.size() == 1) {// 命令只有一行，按此条件进行判断
			String cmd = cmdList.get(0);
			processCmd(cmd);
		}
	}

	private void cmdFail() {
		msgBackList.clear();// 若列表长度为 0 ，表示服务端执行失败
		// String msgBack="Invalid command";
		// msgBackList.add(msgBack);// 返回信息为任务失败
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
		msgBackList = Operator.exeCmd(cmdHead.toLowerCase(), cmdBody);// 把命令前缀转为小写，以保证判断不分大小写
	}
}
