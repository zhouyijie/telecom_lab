package com.mcgill;

import java.util.Arrays;

public class DNS_Question {

    private String address;
    private String qType;
    byte[] dataSent = new byte[1024];
    int count = 0;
    private int nameBitLength = 0;

    public DNS_Question(String address, String qType) {
        this.address = address;
        this.qType = qType;
    }

    public byte[] getSendData() {

        int nameBitLength = 0;

        //QName start // tp
        String splittedAddress[] = address.split("\\.");

        for (int i = 0; i < splittedAddress.length; i++) {
            dataSent[count++] = ((byte) splittedAddress[i].length());

            for (int j = 0; j < splittedAddress[i].length(); j++) {
                dataSent[count++] = (byte) splittedAddress[i].charAt(j);
            }
        }
        dataSent[count++] = (byte) 0;


        setNameBitLength(count);
        //QName End
        //starting QType
        dataSent[count++] = (byte) 0;

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

    public int getNameBitLength() {
        return nameBitLength;
    }

    public void setNameBitLength(int nameBitLength) {
        this.nameBitLength = nameBitLength;
    }
}
