package wjx.server.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class FileDownLoadSocketThread extends Thread {
	private ServerSocket serverSocket;
	private long filePos=0l;
	private File file;
	private String path = ""; //�����ص��ļ�·��
	private static int port = 8099;
	
	public FileDownLoadSocketThread() {
		// TODO Auto-generated constructor stub
		try {
			//serverSocket = new ServerSocket(0);//��̬������ö˿�
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socket;
		System.out.println("Waiting client to load.....");
		try {
			socket = serverSocket.accept();  //����ʽ
			InputStream in= socket.getInputStream();
			Scanner sc = new Scanner(in);
			OutputStream out = socket.getOutputStream();
			while(true){
				String str = sc.nextLine();
				String path = sc.nextLine();
				if(!str.equals(null)){
					 System.out.println("����ļ�����"+str);
					 System.out.println("·��Ϊ��"+path);
					 File f = new File(path+"\\"+str);       
					 FileInputStream fis = new FileInputStream(f);       
					 DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));       
					 byte[] buffer = new byte[8192];       
					 DataOutputStream ps = new DataOutputStream(out);       
					 ps.writeLong((long) f.length());//�����ļ���С

					 ps.flush();       
					 while(true) {       
						 int read = 0;       
						 if(dis!=null){       
							 read = fis.read(buffer);      
						 }        
						 if(read == -1){    
							break;        
						 }        
						 ps.write(buffer,0,read);   
					}       
						 ps.flush();     
						 dis.close();    
						 socket.close();       
						 out.flush();      
						 break;     
					 }    
				}    
			}
		catch(IOException e){  
			e.printStackTrace();   
		}   
	}  
}



