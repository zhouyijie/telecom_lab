package com.mcgill;

public class DNS_Question {

    private String address;
    private String qType;
    private String qClass;
    byte[] dataSent = new byte[1024];
    int count = 0;

    public DNS_Question(String address, String qType, String qClass) {
        this.address = address;
        this.qType = qType;
        this.qClass = qClass;
        parseQName();
    }

    public void parseQName() {


        String s[] = address.split("\\.");

        for (int i = 0; i < s.length; i++) {
            dataSent[count++] = ((byte) s[i].length());

            for (int j = 0; j < s[i].length(); j++) {
                dataSent[count++] = (byte) s[i].charAt(j);
            }
        }
        dataSent[count++] = (byte) 0;
    }

	public static byte[] getQuestion() {
		// TODO Auto-generated method stub
		return null;
	}
}
