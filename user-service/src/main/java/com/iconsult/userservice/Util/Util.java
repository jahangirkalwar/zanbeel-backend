package com.iconsult.userservice.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Util {

    public static String generateOTP(int length) {
        String seq = "0";
        while (seq.startsWith("0")) {
            seq = Long.valueOf(UUID.randomUUID().getLeastSignificantBits()).toString().substring(1, length+1);
        }
        return seq;
    }

    public static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String transdatetimeFormat = "yyyyMMddHHmmss"; //length 14
    public static DateFormat TransDateTimeFormat = new SimpleDateFormat(transdatetimeFormat);
}
