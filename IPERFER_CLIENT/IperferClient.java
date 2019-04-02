import java.io.*;
import java.net.*;

public class IperferClient {
	
	// static InetAddress server_hostname;
	static private String server_hostname;
	static int server_port;
	static long time;

	 public static void Client(String server_hostname, int server_port, long time) throws IOException{
		long accumulatorBytes = 0;
		long startTime = 0;
	    long endTime = 0;
	    long currentTime = 0 ;
		
		try{
			 Socket socket = new Socket(server_hostname, server_port);
			 byte[] data = new byte[1000];
		     System.out.println("Client: connection built, sending data:" );
		     OutputStream OS = socket.getOutputStream();
		     startTime = System.currentTimeMillis();
		     while(currentTime <= time * 1000){  //while the current time is less then the time from args (converted to seconds).
		    	 OS.write(data);
		    	 accumulatorBytes += 1000;  //accumulate all the data 
		    	 endTime = System.currentTimeMillis();
		    	 currentTime = endTime-startTime;
		   // 	 if(currentTime >= time * 1000){
		   // 		 break;
		    //	 }
		     }
		     socket.close();
		System.out.println("Sending Data completed.");
		long noofKB = accumulatorBytes / 1000; //convert to KB's for output
		long Throughput = (noofKB * 8)/ (endTime-startTime); //calculate bandwith
		System.out.println("Sent = " + noofKB + " KB " + "rate = " + Throughput + " Mbps.");  //output sent KB's and rate that it transferred in Mbps.
		
		}catch(IOException e){
			e.printStackTrace();
		}  
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws IOException {
			
			if(!args[0].equals("-h")){  
				System.out.println("Error in Host: Missing or Additional arguments_.");
				System.exit(1);
			}
			else if(!args[2].equals("-p")){
				System.out.println("Error in Port: Missing or Additional arguments.");
				System.exit(1);
			}
			else if(!args[4].equals("-t")){
				System.out.println("Error in Time: Missing or Additional arguments.");
				System.exit(1);		
			}
			
			server_hostname = args[1]; // check the host server name
			
			server_port = Integer.parseInt(args[3]);  //Parse through the port number
			if(server_port <= 1024 || server_port >= 65535){  // check port number
				System.out.println("Error: Port number must be in the range 1024 to 65535");
				System.exit(1);
			}
			
			time = Integer.parseInt(args[5]); 
			if(time < 1){                  //check time is > 0
				System.out.println("Error: Time should be greater then '0'");
				System.exit(1);
			}
				Client(server_hostname, server_port, time);  //if all argument are correct proceed to connection 
	}
}
