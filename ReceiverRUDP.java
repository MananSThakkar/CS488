package Project1_RUDP;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ReceiverRUDP {
	final static int ownPort = 2220;
	final static int targetPort = 1110 ;
	static InetAddress host = null;
	
	static int packetNumber = 0;
    static int lastPacket = 0;
    static boolean isLastPacket = false;
    static boolean isLastMessage = false;
    
	static{
		try {
			host = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
	        File file = new File("./Resource/copy_1.2_rudp.jpg");
	        FileOutputStream fis = new FileOutputStream(file);
	        DatagramSocket datagramSocket = new DatagramSocket(ownPort);
	        System.out.println("Receiver: connection built, about to transfer. ");
	        
	        while(!isLastMessage) {
	        	byte[] bytes = new byte[1024];
	        	byte[] data = new byte[1021];
	        	DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
	        	datagramSocket.setSoTimeout(0);
	        	datagramSocket.receive(receivePacket);
	        	
	        	packetNumber = ((bytes[0]) << 8) + (bytes[1]);
	        	
	        	if(bytes[2] == 255 && bytes[3] == 255) {
	        		isLastPacket = true;
				}else { isLastPacket = false;}
				
				if(packetNumber == (lastPacket + 1)){
					lastPacket = packetNumber;
					
					
					for(int i = 4; i < 1024; i++) {
						data[i-4] = bytes[i];
					}
					fis.write(data);
					System.out.println("Received Packet Number " + lastPacket);
					byte[] acks = new byte[2];
					acks[0] = (byte) (lastPacket >> 8);
					acks[1] = (byte) (lastPacket); 
					DatagramPacket ack = new DatagramPacket(acks, acks.length, host, receivePacket.getPort());
					datagramSocket.send(ack);
					System.out.println("Ack Sent");
	        }else {
	        	System.out.println("Resending Ack " + packetNumber);
	        	byte[] acks = new byte[2];
				acks[0] = (byte) (lastPacket >> 8);
				acks[1] = (byte) (lastPacket); 
				DatagramPacket ack = new DatagramPacket(acks, acks.length, host, receivePacket.getPort());
				datagramSocket.send(ack);
				System.out.println("Ack Sent");  	
	        }
				if(isLastPacket) {
					isLastMessage = false;
					break;
				}
	        
	        }
	        datagramSocket.close();
	        fis.close();
	        System.out.println("File has been received");
	}
}
	        
	        
	        
				/*    
	        
	        byte[] data = new byte[1024];
	       
	        
	        DatagramPacket dataPacket = new DatagramPacket(data, data.length);
	        datagramSocket.receive(dataPacket);
	        String st = new String(dataPacket.getData(),dataPacket.getOffset(), dataPacket.getLength() );
	        int length = Integer.parseInt(st.trim());
	        System.out.println("Size of the incoming file:  " + length + " bytes");
	        
	        int count = 0 ;
	        int seqnum = 0;
	        while(count < ((length/1024)+1) && length > 0) {
	        	try {
	        		byte[] buff = new byte[1024];
	        		DatagramPacket receivePacket = new DatagramPacket(buff, buff.length);
	        		System.out.println("Receiving pakcet...");
	        		datagramSocket.receive(receivePacket);	        		
	        		fis.write(buff);
	        		int lastseqnum = seqnum - 1;
	        		if(receivePacket.getLength() <= 0) {
	        			System.out.println("Packet Lost" + lastseqnum);
	        		}else {
	        		
	        		datagramSocket.setSoTimeout(10000);
	        		
	        		System.out.println("ACK " + count + "Sequence number " + seqnum + " received");
	        		String m = Integer.toString(count);
	        		byte[] acks = m.getBytes();
                    DatagramPacket ackdata = new DatagramPacket(acks, acks.length, receivePacket.getAddress(), receivePacket.getPort());
                    datagramSocket.send(ackdata);
	        		
	        		
	        	}
	        	count++;
	        	seqnum++;
	        	}			
	        	catch(SocketTimeoutException e){
	        		System.out.println("Error!");
	        		
	        		
	        	}
	        	if(count == (length/1024)+1) {
	        		break;
	        	}
	        }
	        
	       // ServerSocket socket = new ServerSocket(ownPort, 1, host);
	       datagramSocket.setSoTimeout(10000);
	        System.out.println("Receiver: connection built, about to transfer. ");
	        while(true) {
	        	try {
	        		//Socket client = socket.accept();
	        		DatagramPacket receivePacket = new DatagramPacket(data, data.length, host, targetPort);
	        		datagramSocket.receive(receivePacket);	        		
	        		fis.write(data);
	        		datagramSocket.send(new DatagramPacket(data, data.length, receivePacket.getAddress(),receivePacket.getPort()));
	        		System.out.println("ACK sent");
	        	}
	        	catch(SocketTimeoutException e){
	        		break;
	        	}
	        }
	        System.out.println("Receiver: transfer completed");
	        datagramSocket.close();
	        fis.close();
	        */
		
