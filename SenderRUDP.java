package Project1_RUDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class SenderRUDP {
	final static int ownPort = 1247;
	final static int targetPort = 2147;
	static InetAddress host = null;
	
	static int fileLength;
    static int packetNumber = 0;
    static int ackPacketNo = 0;
    static boolean LastPacket = false;

    static boolean AckReceived = false;  //Ack received from receiver.
	
	static{
		try {
			host = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		File file = new File("./Resource/1.jpg");
        FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		System.out.println(data);
		byte[] copyData = new byte[1024];
		// Socket socket = new Socket(host.getHostAddress(), targetPort);
		DatagramSocket datagramSocket = new DatagramSocket(ownPort);
		int packetNumber = 0;
			
	while(fis.read(copyData) != -1) {
		CircularQueue<Byte> DataByte = new CircularQueue<Byte>();
		
			for(int j = 0; j < 16; j++) {
			DataByte.enqueue(copyData[j]);
			
			
       
        //4. Set every pieces as datagram package
        DatagramPacket sendPacket = new DatagramPacket(copyData, copyData.length, host, targetPort);
        //5. Send a package
        datagramSocket.send(sendPacket); 
        System.out.println("Sending Packet... Packet number is " + packetNumber);
       
        boolean isAckReceivedCorrect = false;
        while (!isAckReceivedCorrect) {
        	DataByte.dequeue();
        byte[] Ack = new byte[2];
		DatagramPacket Acks = new DatagramPacket(Ack, Ack.length);
		datagramSocket.receive(Acks);
		if(isAckReceivedCorrect=true) {
			
			System.out.println("Ack received " + packetNumber);
			packetNumber++;
		}else if(isAckReceivedCorrect){
			datagramSocket.send(sendPacket);
			System.out.println("Resending the packet lost (number) " + packetNumber);
			packetNumber++;
		}
		
        }
	}
			 
	}
    //8. Display “file has been sent messages” after all parts are transferred.
	datagramSocket.close();
    fis.close();
    System.out.println("File has been sent.");

}
}
	