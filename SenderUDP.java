package Project1_RUDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
public class SenderRUDP {
	final static int ownPort = 777;
	final static int targetPort = 888;
	static InetAddress host = null;
	static{
		try {
			host = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
	        File file = new File("./Resource/1.jpg");
	        FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[1024];
			// Socket socket = new Socket(host.getHostAddress(), targetPort);
			DatagramSocket datagramSocket = new DatagramSocket(ownPort);
			System.out.println("Sender: connection built, about to transfer");
			while(fis.read(data) != -1) {
			// 	socket.getOutputStream().write(data);
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, host, targetPort);
				datagramSocket.send(sendPacket);
			}
			System.out.println("Sender: transfer completed");
	        // socket.close();
			datagramSocket.close();
	        fis.close();
	}
		
	}