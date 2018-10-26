package com.mcgill;

public class DNS_Answer {

    private int startBitsSize = 0;
    private byte[] recievedData;
    //private int previousEnd = 0;

    public DNS_Answer(byte[] data) {
    	recievedData = data;
    }

    public void answer(int nameSize) {

        if (startBitsSize == 0) {
            //reading first response

            startBitsSize = 12 + nameSize + 4;
        }
        //previousEnd = startBitsSize;

        if (recievedData[startBitsSize] == -64) {
            //System.out.println("we found a pointer");
            //limitation: max index num is 277, and check for one pointer only
        	recievedData[startBitsSize] = 0;
            int pointer = recievedData[startBitsSize++];
            startBitsSize++;
        } else {
            //System.out.println("curser is at the wrong place");
            while (recievedData[startBitsSize] != -64) {
                startBitsSize++;

            }
            recievedData[startBitsSize] = 0;

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
        long ttl = (long) (ttlByte[0] << 24 & 0xff000000 | ttlByte[1] << 16 & 0xff0000 | ttlByte[2] << 8 & 0xff00 | ttlByte[3] & 0xff);

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
            readLabels(0,rData,rdlength);
            System.out.print("\t" + ttl + "\t");
        } else if (type == 15) {

            //MX alias pref ttl auth
            System.out.print("MX\t");
            short pref = (short) (rData[0] << 8 | rData[1]);
            System.out.print(pref + "\t");
            
            readLabels(2,rData,rdlength);
            
            System.out.print("\t" + ttl + "\t");

        } else if (type == 5) {


            //CNAME alias ttl auth
            System.out.print("CNAME\t");
            
            readLabels(0,rData,rdlength);
            System.out.print("\t" + ttl + "\t");

        } else {
            System.out.print("ERROR\t"+"unexpected response type: " + type);
        }


        if (classtype != 1) {
            System.out.println("different classtype encountered: " + classtype);
        }
    }

	private void readLabels(int rd, byte[] data, short length ) {
		
        while(rd < length){
        	if(data[rd] == -64){
        		rd++;
                int ptr;
                if (data[rd] < 0) {
                    ptr = data[rd] + 256;
                } else {
                    ptr = data[rd];
                }
                int ptrLength;
                ptrLength = ptr;
          
                while(recievedData[ptrLength] != 0){
                	ptrLength++;
                	
                }
                
                readLabels(ptr,recievedData,(short)ptrLength);
        		rd++;
        		if(rd<length){
        			System.out.print(".");
        		}
        	}else{
        		for(int j = 0;j<data[rd];j++){
        			System.out.print(Character.toString((char) data[rd+1+j]));
        		}
        		rd = rd + data[rd];
        		rd++;
        		if(rd<length && data[rd] != 0){
        			System.out.print(".");
        		}
        	}
        }
	}

}
