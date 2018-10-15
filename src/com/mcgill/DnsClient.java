package com.mcgill;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Random;

public class DnsClient {

    public static void main(String[] args) throws IOException {
        String argIp = "";
        String argAddress = "";
        String argType = "A";
        int argPort = 53;
        int argMaxR = 3;
        int argTimeout = 5;

        int splittedIntIp[] = new int[4];
        byte[] address = new byte[4];
        //short ID = new byte[2];

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '@') {
                argIp = args[i];
                argIp = argIp.substring(1);
                argAddress = args[i + 1];
            } else if (args[i].charAt(0) == '-') {
                if (args[i].charAt(1) == 't') {
                    argTimeout = Integer.parseInt(args[i + 1]);

                } else if (args[i].charAt(1) == 'r') {
                    argMaxR = Integer.parseInt(args[i + 1]);

                } else if (args[i].charAt(1) == 'p') {
                    argPort = Integer.parseInt(args[i + 1]);
                } else if (args[i].charAt(1) == 'm') {
                    argType = "MX";
                } else if (args[i].charAt(1) == 'n') {
                    argType = "NS";
                }

            }
        }
        System.out.println("t:" + argType);

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

        Random randomID = new Random(Short.MAX_VALUE + 1);
        //random.nextBytes(ID);


        DNS_PacketHeaders dnsHeader = new DNS_PacketHeaders((short) randomID.nextInt(), (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (short) 0, (short) 0, (short) 0, (short) 0);

        System.out.println("sending request for " + argAddress + "\n"
                + "Server: " + Arrays.toString(splittedStringIp));
        
        /*
         sending dns request
         */
        InetAddress server = InetAddress.getByAddress(address);

        DatagramSocket socket = new DatagramSocket();


        byte[] sendData = new byte[]{1};
        DatagramPacket dnsReqPacket = new DatagramPacket(sendData, sendData.length, server, argPort);

        long startTime = System.currentTimeMillis();

//        socket.send(dnsReqPacket);

        /*
         * waiting for dns response
         *
         */


        byte[] receiveData = new byte[]{1};
        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

        socket.receive(packet);

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;

        System.out.println("");

        System.out.println("Received data: " + Arrays.toString(receiveData));


    }
}
