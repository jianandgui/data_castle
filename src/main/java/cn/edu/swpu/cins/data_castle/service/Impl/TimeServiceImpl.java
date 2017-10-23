package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.service.TimeService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class TimeServiceImpl implements TimeService{
    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date(
                        this.getCurrentTimeMillis()));
    }

    @Override
    public long getMatchExpireTime() {
        Calendar expireTime = Calendar.getInstance(Locale.CHINA);
        expireTime.set(2017, Calendar.DECEMBER ,9, 24, 0, 0);
        return expireTime.getTimeInMillis();
    }
}
