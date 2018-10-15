package com.mcgill;

public class DNS_test {
	public static void main(String[] args){
		byte a = -1;
		byte b = -2;
		System.out.println(Byte.toUnsignedInt(a)+" and "+Byte.toUnsignedInt(b));
		short ab = (short)(a<<8|b);
		
		int i = 0;
		
		System.out.println(Short.toUnsignedInt(ab));
		
	}
}
