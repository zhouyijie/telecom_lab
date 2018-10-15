package com.mcgill;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Random;

public class DnsClient {
    private static int port;

	public static void main(String[] args) throws IOException {
        /*
         * parse command line
         * read IP address
         * read labels
         */
		
		String argIp = "";
        String argAddress = "";
        int splittedIntIp[] = new int[4];
        byte[] address = new byte[4];
        //short ID = new byte[2];

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '@') {
                argIp = args[i];
                argIp = argIp.substring(1);
                argAddress = args[i+1];
            }
        }

        String splittedStringIp[] = argIp.split("\\.");
        if (splittedStringIp.length != 4) {
            System.out.println("Invalid IP");
            System.exit(1);
        }
        splittedIntIp[0] = Integer.parseInt(splittedStringIp[0]);
        splittedIntIp[1] = Integer.parseInt(splittedStringIp[1]);
        splittedIntIp[2] = Integer.parseInt(splittedStringIp[2]);
        splittedIntIp[3] = Integer.parseInt(splittedStringIp[3]);

        address[0] = (byte) splittedIntIp[0];
        address[1] = (byte) splittedIntIp[1];
        address[2] = (byte) splittedIntIp[2];
        address[3] = (byte) splittedIntIp[3];
        
        /*
         * packet headers
         * ID random
         * QR 0 (query)
         * OPCODE 0 (standard)
         * AA 0 (only in response, authority(1) or not(0))
         * TC 0 (not truncated)
         * RD 1 (desire recursion)
         * RA 0
         * Z 0
         * RCODE 0 (no error condition)
         * QD 1
         * AN 0 
         * NS 0 (ignored)
         * AR 0
         */
        //generate random ID
        Random randomID = new Random(Short.MAX_VALUE + 1);
        //random.nextBytes(ID);
        
        
        DNS_PacketHeaders dnsHeader = new DNS_PacketHeaders((short)randomID.nextInt(), (byte)0, (byte)0, (byte)0, (byte)0, (byte)1, (byte)0, (byte)0, (byte)0, (short)1, (short)0, (short)0, (short)0);

        System.out.println("sending request for "  +argAddress+ "\n"
                + "Server: " + Arrays.toString(splittedStringIp));
        
        byte[] sendData = null;
        DNS_Question dnsQuestion = new DNS_Question(argAddress,"","");
        sendData = merge(dnsHeader.getHeader(),DNS_Question.getQuestion());
        
        /*
         sending dns request
         */
        InetAddress server = InetAddress.getByAddress(address);

        DatagramSocket socket = new DatagramSocket();

        
		
		DatagramPacket dnsReqPacket = new DatagramPacket(sendData ,sendData.length,server,port);

        long startTime = System.currentTimeMillis();

        socket.send(dnsReqPacket);
        
        /*
         * waiting for dns response
         * 
         */
        

        byte[] receiveData = new byte[] {1};
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

        socket.receive(packet);

        long endTime   = System.currentTimeMillis();

        long totalTime = endTime - startTime;

        System.out.println("");

        System.out.println("Received data: " + Arrays.toString(receiveData));
        
        


    }
	public static byte[]merge(byte[]a, byte[]b){
		
		byte[]c = new byte[a.length+b.length];
		int i;
		for(i=0; i<a.length; i++){
			c[i] = a[i];
		}

		for(int j=0; j<b.length; j++){
			c[i++]=b[j];
		}
		return c;
		  
		     
		       
	}
}

