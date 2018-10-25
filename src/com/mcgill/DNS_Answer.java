package com.mcgill;

public class DNS_Answer {

    private int startBitsSize = 0;

    public DNS_Answer() {

    }

    public void answer(byte[] recievedData, int nameSize) {

        if (startBitsSize == 0) {
            //reading first response

            startBitsSize = 12 + nameSize + 4;
        }

        if (recievedData[startBitsSize] == -64) {
            //System.out.println("we found a pointer");
            //limitation: max index num is 277, and check for one pointer only
            int pointer = recievedData[startBitsSize++];
            startBitsSize++;
        } else {
            //System.out.println("curser is at the wrong place");
            while (recievedData[startBitsSize] != -64) {
                startBitsSize++;

            }

        }

        //TYPE
        byte[] typeByte = new byte[2];

        int i = startBitsSize;
        int c = 0;

        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            //System.out.println(recievedData[i]);
            typeByte[c] = recievedData[i];
            c++;
        }

        short type = (short) (typeByte[0] << 8 | typeByte[1]);


        //CLASS

        byte[] classByte = new byte[2];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            //System.out.println(recievedData[i]);
            classByte[c] = recievedData[i];
            c++;
        }

        short classtype = (short) (classByte[0] << 8 | classByte[1]);


        //TTL
        byte[] ttlByte = new byte[4];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 4; i++) {
            //System.out.println(recievedData[i]);
            ttlByte[c] = recievedData[i];
            c++;
        }
        short ttl = (short) (ttlByte[0] << 24 | ttlByte[1] << 16 | ttlByte[2] << 8 | ttlByte[3] & 0xff);

        //rdlength
        byte[] lengthByte = new byte[2];
        startBitsSize = i;
        c = 0;
        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            lengthByte[c] = recievedData[i];
            c++;
        }
        short rdlength = (short) (lengthByte[0] << 8 | lengthByte[1]);

        //rdata
        byte[] rData = new byte[rdlength];
        startBitsSize = i;
        c = 0;
        for (i = startBitsSize; i < startBitsSize + rdlength; i++) {
            rData[c] = recievedData[i];
            c++;
        }
        startBitsSize = i;

        //rdata
        if (type == 1) {

            //A ip ttl auth
            System.out.print("IP\t");
            for (int j = 0; j < 4; j++) {
                if (rData[j] < 0) {
                    int ip = 256 + rData[j];

                    if (j == 3) {
                        System.out.print(ip);
                    } else {
                        System.out.print(ip + ".");
                    }

                } else {
                    if (j == 3) {
                        System.out.print(rData[j]);
                    } else {
                        System.out.print(rData[j] + ".");
                    }
                }
            }

            System.out.print("\t" + ttl + "\t");
        } else if (type == 2) {


            //NS alias ttl auth
            System.out.print("NS\t");
            int count = 0;
            for (int j = 0; j < rdlength; j++) {
                if (rData[j] == -64) {
                    j++;
                    int ptr;
                    if (rData[j] < 0) {
                        ptr = rData[j] + 256;
                    } else {
                        ptr = rData[j];
                    }
                    // System.out.print("(jump to pointer " + ptr + " )");
                    while (recievedData[ptr] != 0) {
                        if (recievedData[ptr] < 30) {
                            System.out.print(".");
                        } else {
                            System.out.print(Character.toString((char) recievedData[ptr]));
                        }
                        ptr++;
                    }
                } else if (rData[j] < 30) {
                    if (count > 0) {
                        System.out.print(".");
                    }
                    count++;
                } else {
                    System.out.print(Character.toString((char) rData[j]));
                }
            }
            System.out.print("\t" + ttl + "\t");
        } else if (type == 15) {

            //MX alias pref ttl auth
            System.out.print("MX\t");
            short pref = (short) (rData[0] << 8 | rData[1]);
            System.out.print(pref + "\t");
            int count = 0;
            for (int j = 2; j < rdlength; j++) {
                if (rData[j] == -64) {
                    j++;
                    int ptr;
                    if (rData[j] < 0) {
                        ptr = rData[j] + 256;
                    } else {
                        ptr = rData[j];
                    }
                    //System.out.print("(jump to pointer " + ptr + " )");
                    while (recievedData[ptr] != 0) {
                        if (recievedData[ptr] < 30) {
                            System.out.print(".");
                        } else {
                            System.out.print(Character.toString((char) recievedData[ptr]));
                        }
                        ptr++;
                    }
                } else if (rData[j] < 30) {
                    if (count > 0) {
                        System.out.print(".");
                    }
                    count++;
                } else {
                    System.out.print(Character.toString((char) rData[j]));
                }
            }
            System.out.print("\t" + ttl + "\t");

        } else if (type == 5) {


            //CNAME alias ttl auth
            System.out.print("CNAME\t");
            int count = 0;
            for (int j = 0; j < rdlength; j++) {
                if (rData[j] == -64) {
                    j++;
                    int ptr;
                    if (rData[j] < 0) {
                        ptr = rData[j] + 256;
                    } else {
                        ptr = rData[j];
                    }
                    //System.out.print("(jump to pointer " + ptr + " )");
                    while (recievedData[ptr] != 0) {
                        if (recievedData[ptr] < 30) {
                            System.out.print(".");
                        } else {
                            System.out.print(Character.toString((char) recievedData[ptr]));
                        }
                        ptr++;
                    }
                } else if (rData[j] < 30) {
                    if (count > 0) {
                        System.out.print(".");
                    }
                    count++;
                } else {
                    System.out.print(Character.toString((char) rData[j]));
                }
            }
            System.out.print("\t" + ttl + "\t");

        } else {
            System.out.println("invalid type: " + type);
        }


        if (classtype != 1) {
            System.out.println("different classtype encountered: " + classtype);
        }


    }

}
