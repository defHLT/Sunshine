package study.jam.artem.sunshine;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import study.jam.artem.sunshine.data.Forecast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchForecastService extends IntentService {
    public static final String ACTION_FETCH_FORECAST = "study.jam.artem.sunshine.action.FETCH_FORECAST";
    private static final String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Dnipropetrovsk,%20Ukraine&mode=json&units=metric&cnt=16&lang=en";
    private final String LOG_TAG = this.getClass().getSimpleName();

    public FetchForecastService() {
        super("FetchForecastService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_FORECAST.equals(action)) {
                fetchForecast();
            }
        }
    }

    private void fetchForecast() {
        Log.d(LOG_TAG, "Trying to fetch forecast...");
        RequestQueue queue = Volley.newRequestQueue(this);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.d(LOG_TAG, "got response");
                try {
                    final JSONArray jForecast = response
                            .getJSONArray("list");
                    Realm realm = Realm.getInstance(getBaseContext());

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(Forecast.class).findAll().clear();
                            realm.createAllFromJson(Forecast.class, jForecast);
                        }
                    });

                    realm.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "can't parse JSON");
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(LOG_TAG, "Cannot update forecast: " + error.getLocalizedMessage());
            }
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
               url, null, listener, errorListener);

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(1000, 5, 1.5f));
        queue.start();
        queue.add(jsonObjReq);
    }

}
