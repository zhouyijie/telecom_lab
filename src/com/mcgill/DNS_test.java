package com.mcgill;

public class DNS_test {
	
	public static void main(String[] args){
		byte ttlByte[] = new byte[4];
		ttlByte[0] = 0;
		ttlByte[1] = 0;
		ttlByte[2] = -3;
		ttlByte[3] = -64;
		long ttl = (long) (ttlByte[0] << 24 & 0xffffffff | ttlByte[1] << 16 & 0xffffff | ttlByte[2] << 8 & 0xffff | ttlByte[3] & 0xff);
		System.out.println(ttl);
	}
}
