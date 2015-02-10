package study.jam.artem.sunshine.data;

import io.realm.RealmObject;

/**
 * Created by ice on 2/9/15.
 */
public class Forecast extends RealmObject {
    private int high;
    private int low;
    private String date;
    private String code;
    private String day;


    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
