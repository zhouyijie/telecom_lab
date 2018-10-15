package com.mcgill;

public class DNS_test {
	public static void main(String[] args){
		short ID = 23;
		byte QR = 1;
		byte OPCODE = 1;
		byte AA = 1;
		byte TC = 1;
		byte RD = 1;
		byte RA = 1;
		byte Z = 1;
		byte RCODE = 1;
		short QDCOUNT = 32;
		short ANCOUNT = 42;
		short ARCOUNT = 12;
		short NSCOUNT = 24;
		//DNS_PacketHeaders.packetHeaders dns = new DNS_PacketHeaders.packetHeaders();
		
		DNS_PacketHeaders test = new DNS_PacketHeaders();
		test.packetHeaders(ID,QR,OPCODE,AA,TC,RD,RA,Z,RCODE,QDCOUNT,ANCOUNT,NSCOUNT,ARCOUNT);

		//test.packetHeaders(id, qr, opCode, aa, tc, rd, ra, z, rCode, qdCount, anCount, nsCount, arCount);
		
	}
}
