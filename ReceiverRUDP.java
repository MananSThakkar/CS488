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
	final static int ownPort = 2147;
	final static int targetPort = 1247;
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
		File file = new File("./Resource/copy_2_rudp.jpg");
        FileOutputStream fis = new FileOutputStream(file);
        System.out.println("Waiting to receive...");
        DatagramSocket socket = new DatagramSocket(ownPort);
        
        int packetNumberExpected = 0;
        int packetNumber = 0;
        
        byte[] copyData = new byte[1024];
	
		//  ServerSocket socket = new ServerSocket(ownPort, 1, host);
		//DatagramSocket.setSoTimeout(10000);
		System.out.println("Receiver: connection built. About to receive");
				
		while(true)
		{
			try {
				//CircularQueue<Byte> DataByte = new CircularQueue<Byte>();
				
				//Socket client = socket.accept();
				//do we need the IP and port of the sender? 
				DatagramPacket receivePacket = new DatagramPacket (copyData, copyData.length, host, targetPort);
				socket.receive(receivePacket);
				for(int i = 0; i < 16; i++) {
					
    				// DataByte.enqueue(copyData[i]);
    				
    				
    				fis.write(copyData);
				
				
    				if(packetNumber == packetNumberExpected) {
    					//3. Send Acknowledgement message
    					byte[] ackArray = new byte[2];
    					DatagramPacket ack = new DatagramPacket(ackArray, ackArray.length, host, targetPort);
    					socket.send(ack);
    					System.out.println("SENT ACK." + packetNumber);
    					// DataByte.dequeue();
    					packetNumber++;
    					packetNumberExpected++;
	            } 
    				else if(packetNumber != packetNumberExpected){
    					System.out.println("RESENDING ACK " + packetNumber);
    					
    					byte[] ackArray = new byte[2];
    					DatagramPacket ack = new DatagramPacket(ackArray, ackArray.length, host, targetPort);
    					socket.send(ack);
    					System.out.println("SENT ACK.");
    					// DataByte.dequeue();
    					packetNumber++;
    					packetNumberExpected++;
	            }
	            }
			}	 
					
			catch(SocketTimeoutException e)	{
				break;
			}
		}
		System.out.println("Receiver: finished");
       
            
        //6. Display “file has been received” after all parts are transferred.
        socket.close();
        fis.close();
        System.out.println("File has been received.");
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
		
