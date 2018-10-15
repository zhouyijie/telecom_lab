package com.mcgill;

import java.io.IOException;
import java.net.*;
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
        long totalTime = 0;


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

        DNS_PacketHeaders dnsHeader = new DNS_PacketHeaders((short) randomID.nextInt(), (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (short) 1, (short) 0, (short) 0, (short) 0);


        byte[] sendData = null;
        DNS_Question dnsQuestion = new DNS_Question(argAddress, argType);
        sendData = merge(dnsHeader.getHeader(), dnsQuestion.getSendData());
        
        /*
         sending dns request
         */
        InetAddress server = InetAddress.getByAddress(address);

        int argTimeoutInMillis = argTimeout * 1000;

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(argTimeoutInMillis);

        DatagramPacket dnsReqPacket = new DatagramPacket(sendData, sendData.length, server, argPort);

        int countRetries = 0;

        //keep trying until the max retries value is reached
        while (argMaxR > countRetries) {

            try {
                byte[] receiveData = new byte[1024];
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

                long startTime = System.currentTimeMillis();

                socket.send(dnsReqPacket);

                /*
                 * waiting for dns response
                 *
                 */

                socket.receive(packet);

                long endTime = System.currentTimeMillis();

                totalTime = endTime - startTime;

                System.out.println("total time:" + totalTime + "ms");

                System.out.println("");

                System.out.println("Received data: " + Arrays.toString(receiveData));
            } catch (SocketTimeoutException error) {
                countRetries++;
                if (countRetries >= argMaxR) {
                    System.err.println("ERROR: Max timeout reached!");
                    return;
                }
            }
            break;

        }
        System.out.println("sending request for " + argAddress + "\n"
                + "Server: " + Arrays.toString(splittedStringIp)
                + " Request type: " + argType + "\n"
                + "Response received after " + (totalTime) + " milliseconds ("
                + countRetries + " retries (Max retries " + argMaxR
                + ")) \n");

    }

    public static byte[] merge(byte[] a, byte[] b) {

        byte[] c = new byte[a.length + b.length];
        int i;
        for (i = 0; i < a.length; i++) {
            c[i] = a[i];
        }

        for (int j = 0; j < b.length; j++) {
            c[i++] = b[j];
        }
        return c;
    }
}

