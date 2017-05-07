package com.bob.gank_client.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bob on 17-5-4.
 */

public class DateUtil {

        public DateUtil() {
        }

        public static String toDateTimeStr(Date date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return year + "-" + month + "-" + day;
        }

}
