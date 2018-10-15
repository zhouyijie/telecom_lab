package com.mcgill;

public class DNS_PacketHeaders {
    // private variables

    private short ID;

    private byte QR;

    private byte OPCODE;

    private byte AA;

    private byte TC, RD, RA, Z, RCODE;

    private short QDCOUNT;

    private short ANCOUNT;

    private short NSCOUNT;

    private short ARCOUNT;
    private byte[] packetHeader;

    public DNS_PacketHeaders(short id, byte qr, byte opCode, byte aa, byte tc, byte rd, byte ra, byte z, byte rCode,
                             short qdCount, short anCount, short nsCount, short arCount) {
        ID = id;
        QR = qr;
        OPCODE = opCode;
        AA = aa;
        TC = tc;
        RD = rd;
        RA = ra;
        Z = z;
        RCODE = rCode;
        QDCOUNT = qdCount;
        ANCOUNT = anCount;
        ARCOUNT = arCount;
        NSCOUNT = nsCount;
        byte[] packetHeader = new byte[12];
        // big endian for id address
        packetHeader[0] = (byte) (ID >>> 8);
        packetHeader[1] = (byte) (ID);
        packetHeader[2] = (byte) ((QR << 7) | (OPCODE << 3) | (AA << 2) | (TC << 1) | RD);
        packetHeader[3] = (byte) ((RA << 7) | (Z << 4) | RCODE);
        packetHeader[4] = (byte) (QDCOUNT >>> 8);
        packetHeader[5] = (byte) QDCOUNT;
        packetHeader[6] = (byte) (ARCOUNT >>> 8);
        packetHeader[7] = (byte) ARCOUNT;
        packetHeader[8] = (byte) (ANCOUNT >>> 8);
        packetHeader[9] = (byte) ANCOUNT;
        packetHeader[10] = (byte) (NSCOUNT >>> 8);
        packetHeader[11] = (byte) NSCOUNT;
        this.packetHeader = packetHeader;

    }

    public void readHeader(byte[] header) throws Exception {

        //System.out.println("read headers");
        this.ID = (short) ((header[0] << 8) | header[1]);

        String flag1 = byteToBin(header[2]);
        String flag2 = byteToBin(header[3]);

        this.QR = binToByte(flag1.substring(0, 1));

        this.OPCODE = binToByte(flag1.substring(1, 5));

        this.AA = binToByte(flag1.substring(5, 6));

        this.TC = binToByte(flag1.substring(6, 7));

        this.RD = binToByte(flag1.substring(7, 8));

        this.RA = binToByte(flag2.substring(0, 1));

        this.Z = binToByte(flag2.substring(1, 4));

        this.RCODE = binToByte(flag2.substring(4, 8));

        this.QDCOUNT = (short) ((header[4] << 8) | header[5]);

        this.ANCOUNT = (short) ((header[6] << 8) | header[7]);

        this.NSCOUNT = (short) ((header[8] << 8) | header[9]);

        this.ARCOUNT = (short) ((header[10] << 8) | header[11]);

        if (RA == 1) {

            System.out.println("server does not support recursive queries.");
        }

        switch (RCODE) {

            case 0:
                break;// zero error condition

            case 1:
                System.out.println("format error: the name server was unable to interpret the query");

            case 2:
                System.out.println("failure: the name server was unable to process this query due to a problem with the name server");

            case 3:
                System.out.println("name error: domain name referenced in the query does not exist");

            case 4:
                System.out.println("not implemented: the name server does not support the requested kind of query");

            case 5:
                System.out.println("refused: The name server refuses to perform the requested operation for policy reasons");
        }
    }

    private byte binToByte(String substring) {
        byte intOut = (byte) Integer.parseInt(substring, 2);

        return intOut;
    }

    public byte[] getHeader() {
        return packetHeader;
    }

    public static String byteToBin(byte byteIn) {

        String binOut = "";

        if (byteIn < 0) {

            int intIn = byteIn;

            intIn = intIn + 256;
            binOut = String.format("%8s", Integer.toBinaryString(intIn & 0xFF)).replace(' ', '0');

        } else {
            binOut = String.format("%8s", Integer.toBinaryString(byteIn & 0xFF)).replace(' ', '0');
        }

        return binOut;

    }

}
