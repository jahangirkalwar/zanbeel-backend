package com.zanbeel.BeneficiaryService.util;

import java.util.UUID;

public class Util {
    public static String generateOTP(int length) {
        String seq = "0";
        while (seq.startsWith("0")) {
            seq = Long.valueOf(UUID.randomUUID().getLeastSignificantBits()).toString().substring(1, length+1);
        }
        return seq;
    }

}
