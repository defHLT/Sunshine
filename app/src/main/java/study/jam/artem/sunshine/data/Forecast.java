package study.jam.artem.sunshine.data;

import io.realm.RealmObject;

/**
 * Created by ice on 2/9/15.
 */
public class Forecast extends RealmObject {
    private int dt;
    private Temp temp;

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }
}
