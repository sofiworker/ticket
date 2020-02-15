package com.dm.ticket.util;

import java.sql.Timestamp;


public class TimeUtil {

    public static Timestamp nowTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
