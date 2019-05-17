import java.io.*;
import java.net.*;

public class Iperfer {

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
		   
		     }
		     socket.close();
		     System.out.println();
		     System.out.println("Sending Data completed.");
		     long noofKB = accumulatorBytes / 1000; //convert to KB's for output
		     long Throughput = (noofKB * 8)/ (endTime-startTime); //calculate bandwith
		     System.out.println("Sent = " + noofKB + " KB " + "rate = " + Throughput + " Mbps.");  //output sent KB's and rate that it transferred in Mbps.
		     System.out.println();

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void Server(int listen_port) throws IOException{
		long dataSize = 0;
		long accumulatorBytes = 0;
		long startTime = 0;
	    long endTime = 0;
	    
		try{
			ServerSocket serverSocket = new ServerSocket(server_port);
			Socket socket = serverSocket.accept();
			InputStream IS = socket.getInputStream();
			byte[] data = new byte[1000];
			System.out.println("Server: connection built, Receiving data" );
			startTime = System.currentTimeMillis();
			while((dataSize = IS.read(data)) != -1) {
				//IS.read(data);
				accumulatorBytes += dataSize;  //accumulate all the data
		    	endTime = System.currentTimeMillis();
			}
			serverSocket.close();
			socket.close();
			System.out.println();
			System.out.println("Receiving Data Completed");
			long noofKB = accumulatorBytes / 1000; //convert to KB's for output
			long Throughput = (noofKB * 8)/ (endTime-startTime); //calculate bandwith
			System.out.println("Received = " + noofKB + " KB " + "rate = " + Throughput + " Mbps.");  //input received KB's and rate that it transferred in Mbps.
			System.out.println();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws IOException {
		
		if (args[0].equals("-c")) {
			if(!args[1].equals("-h")){  
				System.out.println("Error in Host: Missing or Additional arguments_.");
				System.exit(1);
			}
			else if(!args[3].equals("-p")){
				System.out.println("Error in Port: Missing or Additional arguments.");
				System.exit(1);
			}
			else if(!args[5].equals("-t")){
				System.out.println("Error in Time: Missing or Additional arguments.");
				System.exit(1);		
			}
			
            server_hostname = args[2];
            
            server_port = Integer.parseInt(args[4]);
            if(server_port <= 1024 || server_port >= 65535){  // check port number
				System.out.println("Error: Port number must be in the range 1024 to 65535");
				System.exit(1);
            }
            
            time = Integer.parseInt(args[6]); 
			if(time < 1){                  //check time is > 0
				System.out.println("Error: Time should be greater then '0'");
				System.exit(1);
			}
			
            Client(server_hostname, server_port, time);
        }
        else if (args[0].equals("-s")) {
            if (!args[1].equals("-p")) {
                System.out.println("Error: command not found");
                return;
            }
            server_port = Integer.parseInt(args[2]);
            if(server_port <= 1024 || server_port >= 65535){  // check port number
				System.out.println("Error: Port number must be in the range 1024 to 65535");
				System.exit(1);
            }
  
            System.out.println("---------------------------------------------------------------");
			System.out.println("Server listening on TCP port " + server_port);
			System.out.println("---------------------------------------------------------------");
			Server(server_port);
            
        }
	}
}
