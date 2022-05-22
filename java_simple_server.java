package simple_web_server_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception{
		// Start receiving messages - ready to receive messages
		try (ServerSocket serverSocket = new ServerSocket(8080)){
			System.out.println("Server started.\n Listening to messages.");
			
			while(true) {
				// Handle a new incoming message
				try(Socket client = serverSocket.accept()){ // accept message
					// Messages queued up in the client
					System.out.println("Debug: got new message" + client.toString());
					
					
					
				// GET REQUESTS
					
					// Read the request - listen to the message
					// You can read multiple messages and scroll back (stream of all messages)
					InputStreamReader isr = new InputStreamReader(client.getInputStream());
					
					// Get messages from the screen one at a time
					BufferedReader br = new BufferedReader(isr);
					
					
					// Read the first request from client
					// Store each message into a readable file
					StringBuilder request = new StringBuilder(); // creates an updatable string
					String line = br.readLine(); // temporary variable (holds one line of message at a time!)
					
					
					// Each request is separated by a blank line (ignore blank lines in order to read requests)
					while(!line.isBlank()){
						request.append(line + "\r\n");
						line = br.readLine(); // read a new non-blank line
					}
					
					System.out.println("--REQUEST--");
					System.out.println(request);
					
					
				// Decide how to respond based on route
					// Get the first line of the request
					String srequest = request.toString();
					String firstLine = srequest.split("\n")[0]; // "GET / HTTP/1.1"
					
					// Get the second resource from the first line
					String resource = firstLine.split(" ")[1]; // "/"
					System.out.println(resource);
					
					
				// SEND RESPONSES (change response based on route)
					
					// Compare resource with a list of options
					if(resource.equals("/pic")) {
						// Send multiple responses at the same time
						OutputStream clientOutput = client.getOutputStream();
						// Reply with an image
						// Load the image
						FileInputStream image = new FileInputStream("your-image-here.jpg");
						System.out.println(image.toString());
						// Turn the image into bytes
						clientOutput.write(image.readAllBytes());
						// Send the message
						clientOutput.flush();
						
						
					} else if (resource.equals("/hello")) {
						// Send multiple responses at the same time
						OutputStream clientOutput = client.getOutputStream();
						// Reply with a simple string	
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
						clientOutput.write(("\r\n").getBytes()); // blank line
						clientOutput.write(("Hello!").getBytes()); // message content
						// Send the message
						clientOutput.flush();
						
					} else {
						OutputStream clientOutput = client.getOutputStream();
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
						clientOutput.write(("\r\n").getBytes());
						clientOutput.write(("Looking for something?").getBytes());
						clientOutput.flush();
						
					}
					
				
					// Get ready for the next message
					client.close(); // close the output stream
				}
				
				
				
			}
		}
		
		

	}

}
