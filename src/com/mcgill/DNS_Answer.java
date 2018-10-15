package com.mcgill;

import java.util.Arrays;

public class DNS_Answer {

    public DNS_Answer() {

    }

    public void answer(byte[] recievedData) {
        System.out.println(Arrays.toString(recievedData));

    }

}
