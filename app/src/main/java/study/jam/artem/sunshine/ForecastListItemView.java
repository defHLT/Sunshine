package study.jam.artem.sunshine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import study.jam.artem.sunshine.data.Forecast;


public class ForecastListItemView extends RelativeLayout {

    private static final String DEGREE_CELCIUS = "°C";
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final double minLow;
    private final double maxHigh;
    private TextView tvTempLo;
    private TextView tvTempHi;
    private TextView tvDate;
    private View placeHolderLeft;
    private View placeHolderRight;
    private View placeHolderBar;
    private TextView tvDay;

    public ForecastListItemView(Context context, int minLow, int maxHigh) {
        super(context);
        init(context);
        this.minLow = minLow;
        this.maxHigh = maxHigh;
    }

    public ForecastListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        throw new UnsupportedOperationException();
    }

    public ForecastListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        throw new UnsupportedOperationException();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.forecast_list_item_view, this, true);
        tvTempLo = (TextView) findViewById(R.id.tv_temp_lo);
        tvTempHi = (TextView) findViewById(R.id.tv_temp_hi);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDay = (TextView) findViewById(R.id.tv_day);

        placeHolderLeft = findViewById(R.id.left_praceholder);
        placeHolderRight = findViewById(R.id.right_placeholder);
        placeHolderBar = findViewById(R.id.temperature_bar);
    }

    public void showData(Forecast forecast) {
        tvDate.setText(forecast.getDate().replaceAll(" \\d{4}", "")); // Remove year from date
        tvDay.setText(forecast.getDay());
        tvTempLo.setText(String.valueOf(forecast.getLow()) + DEGREE_CELCIUS);
        tvTempHi.setText(String.valueOf(forecast.getHigh()) + DEGREE_CELCIUS);

        double itemLo = forecast.getLow();
        double itemHi = forecast.getHigh();

        float barWeight = (float) ((itemHi - itemLo) / (maxHigh - minLow ));
        float tempLoWeight = (float) ((itemLo - minLow) / (maxHigh - minLow));

        setViewWeightInLinearLayout(placeHolderBar, barWeight);
        setViewWeightInLinearLayout(placeHolderLeft, tempLoWeight);
        setViewWeightInLinearLayout(placeHolderRight, 1.0f - tempLoWeight - barWeight);
    }

    private void setViewWeightInLinearLayout(View view, float weight) {
        LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams) view.getLayoutParams();
        layout.weight = weight;
        view.setLayoutParams(layout);
    }

}
