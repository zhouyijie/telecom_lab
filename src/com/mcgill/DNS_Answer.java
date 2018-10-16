package com.mcgill;

import java.util.Arrays;

public class DNS_Answer {

    public DNS_Answer() {

    }

    public void answer(byte[] recievedData, int nameSize) {


        int startBitsSize = 12 + nameSize;

        //TYPE
        byte[] typeByte = new byte[2];

        int i = startBitsSize;
        int c = 0;

        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            System.out.println(recievedData[i]);
            typeByte[c] = recievedData[i];
            c++;
        }
        //CLASS
        byte[] classByte = new byte[2];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            System.out.println(recievedData[i]);
            classByte[c] = recievedData[i];
            c++;
        }
        //TTL
        byte[] ttlByte = new byte[4];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 4; i++) {
            System.out.println(recievedData[i]);
            ttlByte[c] = recievedData[i];
            c++;
        }

        //RDLENGTH
        System.out.println("RDLENGTH");
        byte[] rdByte = new byte[2];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            System.out.println(recievedData[i]);
            rdByte[c] = recievedData[i];
            c++;
        }


        if (typeByte[0] == 0 && typeByte[1] == 15) { //MX type
            System.out.println("preference");

            //RDATA
            byte[] rDataByte = new byte[2];
            startBitsSize = i;

            c = 0;
            for (i = startBitsSize; i < startBitsSize + 2; i++) {
                System.out.println(recievedData[i]);
                rDataByte[c] = recievedData[i];
                c++;
            }


        } else if (typeByte[0] == 0 && typeByte[1] == 2) { //NS type
            //RDATA
            System.out.println("TYPE NS");

            byte[] rDataByte = new byte[nameSize];
            startBitsSize = i;

            c = 0;
            for (i = startBitsSize; i < startBitsSize + nameSize; i++) {
                System.out.println(recievedData[i]);
                rDataByte[c] = recievedData[i];
                c++;
            }


        } else if (typeByte[0] == 0 && typeByte[1] == 1) { //A type
            //RDATA
            System.out.println("TYPE A");

            byte[] rDataByte = new byte[4];
            startBitsSize = i;

            c = 0;
            for (i = startBitsSize; i < startBitsSize + 4; i++) {
                System.out.println(recievedData[i]);
                rDataByte[c] = recievedData[i];
                c++;
            }

        } else {   //CNAME

        }

        if (classByte[0] == 0 && classByte[1] == 1) {

        } else {
            //error
        }


    }

}
