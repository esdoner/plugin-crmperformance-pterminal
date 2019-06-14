package com.fr.plugin.performance.util.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by yuwh on 2019/03/18
 * Description:none
 */
public class TimeFormat {
    private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);;

    public String date2String(Date date){ return sdf.format(date); }

    public Date string2Date(String date){
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TimeFormat setSDF(String format){
        sdf= new SimpleDateFormat(format, Locale.ENGLISH);
        return this;
    }
}
