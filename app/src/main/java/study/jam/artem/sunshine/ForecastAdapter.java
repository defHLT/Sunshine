package study.jam.artem.sunshine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import study.jam.artem.sunshine.data.Forecast;

public class ForecastAdapter extends RealmBaseAdapter<Forecast> implements ListAdapter {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private Integer maxHight;
    private Integer minLow;

    public ForecastAdapter(Context context, RealmResults<Forecast> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public void updateRealmResults(RealmResults<Forecast> realmResults) {
        super.updateRealmResults(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == minLow || null == maxHight) {
            minLow = realmResults.min("low").intValue();
            maxHight = realmResults.max("high").intValue();
        }

        if (null == convertView) {
            convertView = new ForecastListItemView(parent.getContext(), minLow, maxHight);
        }

        Forecast forecast = realmResults.get(position);
        ((ForecastListItemView)convertView).showData(forecast);

        return convertView;
    }
}
