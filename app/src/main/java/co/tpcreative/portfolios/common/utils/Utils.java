package co.tpcreative.portfolios.common.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;

public class Utils {


    public static void printTime(List<CObject>list){
        for (CPortfolios index :list.get(0).navs){
            Calendar calendar2 = Calendar.getInstance();
            DateTime date  = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            calendar2.setTime(date.parse(index.date,formatter).toDate());
        }
    }


    public static HashMap<Integer,String> getSpecificTime(String date){
        if (date != null) {
            HashMap<Integer, String> hashMap = new HashMap<>();
            Calendar calendar2 = Calendar.getInstance();
            DateTime dates = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            calendar2.setTime(dates.parse(date, formatter).toDate());
            hashMap.put(Calendar.DAY_OF_MONTH, (calendar2.get(Calendar.DAY_OF_MONTH) > 9 ? calendar2.get(Calendar.DAY_OF_MONTH) : "0" + calendar2.get(Calendar.DAY_OF_MONTH)).toString());
            // hashMap.put (Calendar.MONTH, ((calendar2.get(Calendar.MONTH) + 1) > 9 ? (calendar2.get(Calendar.MONTH) + 1) : "0"+(calendar2.get(Calendar.MONTH) + 1)).toString());
            hashMap.put(Calendar.MONTH, calendar2.get(Calendar.MONTH) + "");
            hashMap.put(Calendar.YEAR, calendar2.get(Calendar.YEAR) + "");
            return hashMap ;
        }
        return null;

    }

}
