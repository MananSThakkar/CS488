package Project1_RUDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class SenderRUDP {
	final static int ownPort = 1110;
	final static int targetPort = 2220;
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
	        fileLength = (int) file.length();
	       
	        byte[] data = new byte[fileLength];
	        fis.read(data);
	        
	        DatagramSocket datagramSocket = new DatagramSocket(ownPort);
			System.out.println("Sender: connection built, about to transfer");
			System.out.println("Sending the file");
	       
	        for(int byteInPackets=0; byteInPackets<data.length; byteInPackets+=1020) {
	        	packetNumber++;
	        	
	        	byte[] bytes = new byte[1024]; //
	        	bytes[0] = (byte) (packetNumber >> 8);// 1 byte that is 8 bits for the head
	        	bytes[1] = (byte) (packetNumber); //Second byte is for SEQ number
	        	
	        	
	        	if((byteInPackets+1021) >= data.length) {
	        		LastPacket = true; //Last packet 
	        		bytes[2] = (byte) 255;
	        		bytes[3] = (byte) 255;
	        	}else {
	        		LastPacket = false;
	        		bytes[2] = (byte) 0;
	        		bytes[3] = (byte) 0;
	        	}
	        	
	        	if(!LastPacket) {
	        		for(int index = 0; index <= 1019; index++)
	        		bytes[index + 4] = data[byteInPackets+index];
	        	}else if(LastPacket){
	        		for(int index = 0; index < (data.length-byteInPackets); index++)
	        		bytes[index + 4] = data[byteInPackets+index];
	        	}
	        	
	        	DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, host, targetPort);
				datagramSocket.send(sendPacket);
	        	
				System.out.println("Sending Packet no " + packetNumber);
				
				boolean isAckReceivedCorrect = false;
				while(!isAckReceivedCorrect ) { //check the received ack from reciever is correct or not 
												//if not then resend the packet
					if(CheckArk(packetNumber, isAckReceivedCorrect, AckReceived, datagramSocket, sendPacket)==-1) break;
					
				}
	        }
				
	        	datagramSocket.close();
	            System.out.println("File has been sent.");
	        
	}

	private static int CheckArk(int packetNumber, boolean isAckReceivedCorrect, boolean AckReceived,
			DatagramSocket datagramSocket, DatagramPacket sendPacket) throws IOException {
		byte[] Ack = new byte[2];
		DatagramPacket Acks = new DatagramPacket(Ack, Ack.length);
		datagramSocket.receive(Acks);
		if(AckReceived=true) {
			isAckReceivedCorrect = true;
			System.out.println("Ack received!");
			return -1;
		}else {
			datagramSocket.send(sendPacket);
			System.out.println("Resending the packet lost (number) " + packetNumber);
		}
		return 0;
	}
}