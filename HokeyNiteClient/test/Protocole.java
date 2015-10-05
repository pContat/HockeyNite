package test;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousServerSocketChannel;

import utils.Marshallizer;
import protocole.Message;

public class Protocole {
	public static Message craftMessage(){
		Message reply = new Message();
		reply.setType(Message.REQUEST);
		reply.setDestinationPort(6780);
		InetAddress aHost = null;
		try {
			aHost = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reply.setDestination(aHost);
		reply.setSender(aHost);
		reply.setSenderPort(6780);
		//reply.setValue(new MyObject(1, "coucou"));
		return reply;
	}

	public static void respond(Message message,DatagramSocket aSocket ) {
		try {
			
				//Message.getData()
				System.out.println(message.toString());
				byte[] stream = Marshallizer.marshallize(message);
				System.out.println("Stream length " + stream.length);
				DatagramPacket datagram = new DatagramPacket(stream,
						message.getLength(), 
						message.getDestination(),
						message.getDestinationPort());
				aSocket.send(datagram); // �mission non-bloquante
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}