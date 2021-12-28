package com.xmu.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public class DateTimeUtil {

    public static String between(LocalDateTime in,LocalDateTime out){
        Date inTemp=Date.from(in.atZone(ZoneId.systemDefault()).toInstant());
        Date outTemp = Date.from(out.atZone(ZoneId.systemDefault()).toInstant());
        long day= DateUtil.between(inTemp,outTemp, DateUnit.DAY);
        long hour= DateUtil.between(inTemp,outTemp, DateUnit.HOUR)-day*24;
        long minute= DateUtil.between(inTemp,outTemp, DateUnit.MINUTE)-hour*60;
        return day+" day "+hour+" hour "+minute+" minutes";
    }
}
