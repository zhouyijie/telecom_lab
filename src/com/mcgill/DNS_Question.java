package com.mcgill;

import java.util.Arrays;

public class DNS_Question {

    private String address;
    private String qType;
    private String qClass;
    byte[] dataSent = new byte[1024];
    int count = 0;

    public DNS_Question(String address, String qType) {
        this.address = address;
        this.qType = qType;
        this.qClass = qClass;
    }

    public byte[] getSendData() {

        //QName start
        String splittedAddress[] = address.split("\\.");

        for (int i = 0; i < splittedAddress.length; i++) {
            dataSent[count++] = ((byte) splittedAddress[i].length());

            for (int j = 0; j < splittedAddress[i].length(); j++) {
                dataSent[count++] = (byte) splittedAddress[i].charAt(j);
            }
        }
        dataSent[count++] = (byte) 0;
        dataSent[count++] = (byte) 0;
        //QName End
        //starting QType

        if (qType.equals("MX")) {
            dataSent[count++] = (byte) 15;

        } else if (qType.equals("NS")) {
            dataSent[count++] = (byte) 2;
        } else {
            dataSent[count++] = (byte) 1;

        }
        //QType end

        //QClass start
        dataSent[count++] = (byte) 0;
        dataSent[count++] = (byte) 1;

        return Arrays.copyOf(dataSent, count);
    }
}
