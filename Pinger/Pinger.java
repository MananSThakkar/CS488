import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Random;

public class Pinger {
	
	static InetAddress server_hostname, client_hostname;
	static int server_port;
	static int packets;
	
	private static void PingerServer(int server_port) throws IOException {
		long startTime = 0;
	    long endTime = 0;
	    byte[] dataReceived = new byte[1024];
	    Random r = new Random();
	    DatagramSocket datagramSocket = new DatagramSocket(server_port);
	   
	    
	    while(true) {
			try {
				//datagramSocket.setSoTimeout(1000);
				DatagramPacket datagramPacketReceive = new DatagramPacket(dataReceived, 1024);
				datagramSocket.receive(datagramPacketReceive);
				datagramPacketReceive.getData();
				client_hostname = datagramPacketReceive.getAddress();
				ByteArrayInputStream B = new ByteArrayInputStream(dataReceived);
				InputStreamReader I = new InputStreamReader(B);
				BufferedReader BR = new BufferedReader(I);
				String s = BR.readLine();
				
				System.out.println("Received from " + client_hostname + ": " + new String(s));
				
			/*	int rand = r.nextInt(10) + 1;
				if(rand <= 4) {
					System.out.println("Not Sent!");
					continue;
				}  */
					
				byte[] dataSend = new byte[1024];
				DatagramPacket datagramPacketSend = new DatagramPacket(dataSend, dataSend.length, client_hostname, server_port);
				datagramSocket.send(datagramPacketSend);
				
			} 
			catch (SocketException e) {
				e.printStackTrace();
			}
		
		}		
	}



	private static void PingerClient(InetAddress server_hostname, int server_port, int packets) throws Exception {
		DatagramSocket datagramSocket = new DatagramSocket(server_port);
		int seq_num = 1;
		long startTime = 0, endTime = 0, currentTime = 0, avgRTT = 0, max = 0, min = Integer.MAX_VALUE, totalTime = 0;
		
		while(seq_num <= packets) {
			startTime = System.currentTimeMillis();
			String send = "Ping " + seq_num + "\n";
			byte[] dataSend = new byte[1024];
			dataSend = send.getBytes();
			DatagramPacket datagramPacketSend = new DatagramPacket(dataSend, dataSend.length, server_hostname, server_port );
			datagramSocket.send(datagramPacketSend);
			
			try {
				byte[] dataReceived = new byte[1024];
				datagramSocket.setSoTimeout(1000);
				DatagramPacket datagramPacketReceive = new DatagramPacket(dataReceived, 1024);
				datagramSocket.receive(datagramPacketReceive);
				endTime = System.currentTimeMillis();
				currentTime = 1000*(endTime - startTime);
				/* ByteArrayInputStream B = new ByteArrayInputStream(dataReceived);
				InputStreamReader I = new InputStreamReader(B);
				BufferedReader BR = new BufferedReader(I);
				String receive = BR.readLine(); */
				totalTime = totalTime + currentTime;
				if(currentTime > max) {
					max = currentTime;
				}else if(currentTime < min) {
					min = currentTime;
				}
				System.out.println("Received from " + server_hostname + ": " + "time: " + currentTime);
				
			}catch (SocketTimeoutException e) {
				break;
			}
			seq_num++;
		}
		avgRTT = totalTime / packets;
		System.out.println("Average RTT: " + avgRTT + " Max RTT: " + max + " Min RTT: " + min + "\n");
		
	}
	

	public static void main(String[] args) throws Exception {
		if (args[0].equals("-c")) {
			if(args.length != 7) {
				System.out.println("Error: Missing or Additional arguments.");
				System.exit(1);
			}
			if(!args[1].equals("-h")){  
				System.out.println("Error in Host: Missing or Additional arguments.");
				System.exit(1);
			}
			else if(!args[3].equals("-p")){
				System.out.println("Error in Port: Missing or Additional arguments.");
				System.exit(1);
			}
			else if(!args[5].equals("-n")){
				System.out.println("Error in Time: Missing or Additional arguments.");
				System.exit(1);		
			}
			
            server_hostname = InetAddress.getByName(args[2]);
            
            server_port = Integer.parseInt(args[4]);
            if(server_port <= 1024 || server_port >= 65535){  // check port number
				System.out.println("Error: Port number must be in the range 1024 to 65535");
				System.exit(1);
            }
            
            packets = Integer.parseInt(args[6]); 
			
			
            PingerClient(server_hostname, server_port, packets);
        }
        else if (args[0].equals("-s")) {
        	if(args.length != 3) {
				System.out.println("Error: Missing or Additional arguments.");
				System.exit(1);
			}
            if (!args[1].equals("-p")) {
                System.out.println("Error: command not found");
                return;
            }
            server_port = Integer.parseInt(args[2]);
            if(server_port <= 1024 || server_port >= 65535){  // check port number
				System.out.println("Error: Port number must be in the range 1024 to 65535");
				System.exit(1);
            }
  
           PingerServer(server_port);
            
        }
	}
}
