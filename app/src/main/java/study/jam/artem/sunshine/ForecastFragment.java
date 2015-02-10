package study.jam.artem.sunshine;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import study.jam.artem.sunshine.data.Forecast;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private final String LOG_TAG = ForecastFragment.class.getSimpleName();
    private ForecastAdapter mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Realm realm = Realm.getInstance(getActivity());
        RealmResults<Forecast> realmResults = realm.where(Forecast.class).findAll();
        mForecastAdapter = new ForecastAdapter(getActivity(), realmResults, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView forecastListView = (ListView) rootView.findViewById(R.id.forecast_listview);
        forecastListView.setAdapter(mForecastAdapter);

        TextView tvEmptyView = new TextView(getActivity());
        tvEmptyView.setText(getResources().getString(R.string.no_forecast_emptyview));
        forecastListView.setEmptyView(tvEmptyView);

        return rootView;
    }
}
