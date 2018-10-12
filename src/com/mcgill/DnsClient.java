package com.mcgill;

public class DnsClient {
    public static void main(String[] args) {
        String argIp = "";
        String argAddress = "";
        int splittedIntIp[] = new int[4];
        byte[] address = new byte[4];

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '@') {
                argIp = args[i];
                argIp = argIp.substring(1);
                argAddress = args[i++];
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

    }
}
