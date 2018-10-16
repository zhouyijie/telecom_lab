package com.mcgill;

import java.util.Arrays;

public class DNS_Answer {

    public DNS_Answer() {

    }

    public void answer(byte[] recievedData, int nameSize) {


        int startBitsSize = 12 + nameSize +4;
        
        if (recievedData[startBitsSize] == -64){
        	System.out.println("we found a pointer");
        	//limitation: max index num is 277, and check for one pointer only
        	int pointer = recievedData[startBitsSize++];
        	startBitsSize++;
        }else{
        	/*
        	while(recievedData[startBitsSize] != -64){
        		startBitsSize++;
        		
        	}
        	*/
        }

        //TYPE
        //System.out.println("TYPE");

        byte[] typeByte = new byte[2];

        int i = startBitsSize;
        int c = 0;

        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            //System.out.println(recievedData[i]);
            typeByte[c] = recievedData[i];
            c++;
        }
        
        short type = (short)(typeByte[0]<<8|typeByte[1]);
        
        
        //CLASS
        //System.out.println("CLASS");

        byte[] classByte = new byte[2];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 2; i++) {
            //System.out.println(recievedData[i]);
            classByte[c] = recievedData[i];
            c++;
        }
        
        short classtype = (short)(classByte[0]<<8|classByte[1]);
        
        
        //TTL
        //System.out.println("TTL");
        byte[] ttlByte = new byte[4];
        startBitsSize = i;

        c = 0;
        for (i = startBitsSize; i < startBitsSize + 4; i++) {
            //System.out.println(recievedData[i]);
            ttlByte[c] = recievedData[i];
            c++;
        }
        short ttl = (short)(ttlByte[0]<<24 | ttlByte[1]<<16 | ttlByte[2]<<8 | ttlByte[3]&0xff);
        
        //rdlength
        byte[] lengthByte = new byte[2];
        startBitsSize = i;
        c = 0;
        for(i =startBitsSize;i<startBitsSize+2;i++){
        	lengthByte[c] = recievedData[i];
        	c++;
        }
        short rdlength = (short)(lengthByte[0]<<8|lengthByte[1]);
        
        //rdata
        byte[] rData = new byte[rdlength];
        startBitsSize = i;
        c = 0;
        for(i =startBitsSize;i<startBitsSize+rdlength;i++){
        	rData[c] = recievedData[i];
        	c++;
        }
        

        /*
        if (typeByte[0] == 0 && typeByte[1] == 15) { //MX type
            System.out.println(" MX TYPE preference");

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
        */

        
        if(type == 1){
    		
    		//A ip ttl auth
    		System.out.print("IP: ip adress ");
    		for(int j = 0;j<4;j++){
    			if(rData[j]<0){
    				int ip = 256 + rData[j];
    				System.out.print(ip+".");
    			}else{
    				System.out.print(rData[j]+".");
    			}
    		}
    		System.out.print(" ttl: "+ttl+" ");
        }else if (type == 2){	
    		
    		
    		//NS alias ttl auth
    		System.out.print("NS: alias: ");
    		for(int j=0;j<rdlength;j++){
    			System.out.print(Character.toString((char) rData[j]));
    		}
    		System.out.print(" ttl: "+ttl+" ");
        }else if (type == 15){
    		
    		//MX alias pref ttl auth
        	System.out.print("MX: pref: ");
        	short pref = (short)(rData[0]<<8|rData[1]);
        	System.out.print(pref+" alias: ");
        	for (int j =2;j<rdlength;j++){
        		if (rData[j]<30){
        			System.out.print(".");
        		}else{
        			System.out.print(Character.toString((char) rData[j]));
        		}
        	}
        	System.out.print(" ttl: "+ttl+" ");
        	
        }else if(type == 5){
    		
    		
    		//CNAME alias ttl auth
        	
        	
        }else{
        	System.out.println("invalid type: "+ type);
        }
    		
        
        
        if (classtype != 1){
        	System.out.println("different classtype encountered: "+classtype);
        }


    }

}
