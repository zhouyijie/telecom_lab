package com.mcgill;

public class DNS_PacketHeaders {
	//private variables
	
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

	public DNS_PacketHeaders(short id, byte qr, byte opCode, byte aa, byte tc, byte rd, byte ra, byte z, byte rCode, short qdCount, short anCount, short nsCount, short arCount){
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
		//big endian for id address
		packetHeader[0] = (byte) (ID >> 8);
		packetHeader[1] = (byte) (ID);
		packetHeader[2] = (byte) ((QR << 7) | (OPCODE << 3) | (AA << 2)| (TC << 1) | RD);
		packetHeader[3] = (byte) ((RA << 7) | (Z << 4) | RCODE);
		packetHeader[4] = (byte) (QDCOUNT >> 8);
		packetHeader[5] = (byte) QDCOUNT;
		packetHeader[6] = (byte) (ARCOUNT >> 8);
		packetHeader[7] = (byte) ARCOUNT;
		packetHeader[8] = (byte) (ANCOUNT >> 8);
		packetHeader[9] = (byte) ANCOUNT;
		packetHeader[10] = (byte) (NSCOUNT >> 8);
		packetHeader[11] = (byte) NSCOUNT;
		this.packetHeader = packetHeader;
		
		
	}
	public void readHeader(byte[] header){
		
	}
	public byte[] getHeader(){
		return packetHeader;
		
	}
}
