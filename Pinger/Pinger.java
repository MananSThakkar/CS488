import java.io.IOException;
import java.net.InetAddress;

public class Pinger {
	
	static InetAddress server_hostname;
	static int server_port;
	static int packets;
	
	private static void PingerServer(int server_port) {
		// TODO Auto-generated method stub
		
	}



	private static void PingerClient(InetAddress server_hostname, int server_port, int packets) {
		// TODO Auto-generated method stub
		
	}
	

	public static void main(String[] args) throws IOException {
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
