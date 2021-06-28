package com.example.weatherappmvp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherappmvp.R;
import com.example.weatherappmvp.activities.MainActivity;
import com.example.weatherappmvp.models.DailyItem;
import com.example.weatherappmvp.models.Temp;
import com.example.weatherappmvp.models.WeatherItem;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeatherRecViewAdapter extends RecyclerView.Adapter<WeatherRecViewAdapter.ViewHolder> {

    private List<DailyItem> dailyItems = new ArrayList<>();

    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private final Context context;

    public WeatherRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WeatherRecViewAdapter.ViewHolder holder, int position) {

        String description = dailyItems.get(position).getWeather().get(0).getDescription();
        String weatherDescriptionUpCase = description.substring(0, 1).toUpperCase() + description.substring(1);

        holder.txtWeatherForecastDescription.setText(weatherDescriptionUpCase);
        String temperature = (int) dailyItems.get(position).getTemp().getDay() + "°C";
        holder.txtWeatherTemperature.setText(temperature);

        String iconLink = getIconLink(dailyItems.get(position).getWeather().get(0).getIcon());

        Glide.with(context)
                .asBitmap()
                .load(iconLink)
                .into(holder.imgWeatherForecastIcon);

        String day = getDay(position);
        holder.txtDay.setText(day);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(iconLink, position, day);
            }
        });
    }

    private String getDay(int position) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String dayToday = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        int pos = 0;

        for (int i = 0; i < days.length; i++) {
            if (days[i].equals(dayToday)) {
                pos = i;
                break;
            }
        }
        pos = pos + position + 1;
        if (pos >= 7) {
            pos -= 7;
        }
        String day = days[pos];
        return day;
    }

    private void showDialog(String iconLink, int position, String day) {

        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;

        ImageView imgPopoutIcon;
        TextView txtWindSpeed, txtUvIndex, txtHumidity, txtPressure, txtMorningTemp,
                txtEveningTemp, txtNightTemp, txtMaxTemp, txtMinTemp, txtDayTemp, txtPopoutDescription, txtPopoutDay;

        dialogBuilder = new AlertDialog.Builder(context);
        final View contactPopup = LayoutInflater.from(context).inflate(R.layout.alert_dialog_more_weather_info, null);

        imgPopoutIcon = (ImageView) contactPopup.findViewById(R.id.imgPopoutIcon);


        txtPopoutDescription = (TextView) contactPopup.findViewById(R.id.txtPopoutDescription);
        txtWindSpeed = (TextView) contactPopup.findViewById(R.id.txtWindSpeed);
        txtUvIndex = (TextView) contactPopup.findViewById(R.id.txtUvIndex);
        txtHumidity = (TextView) contactPopup.findViewById(R.id.txtHumidity);
        txtPressure = (TextView) contactPopup.findViewById(R.id.txtPressure);
        txtMorningTemp = (TextView) contactPopup.findViewById(R.id.txtMorningTemp);
        txtEveningTemp = (TextView) contactPopup.findViewById(R.id.txtEveningTemp);
        txtNightTemp = (TextView) contactPopup.findViewById(R.id.txtNightTemp);
        txtMaxTemp = (TextView) contactPopup.findViewById(R.id.txtMaxTemp);
        txtMinTemp = (TextView) contactPopup.findViewById(R.id.txtMinTemp);
        txtDayTemp = (TextView) contactPopup.findViewById(R.id.txtDayTemp);
        txtPopoutDay = (TextView) contactPopup.findViewById(R.id.txtPopoutDay);

        String description = dailyItems.get(position).getWeather().get(0).getDescription();
        String weatherDescriptionUpCase = description.substring(0, 1).toUpperCase() + description.substring(1);

        Temp temp = dailyItems.get(position).getTemp();

        String dayTemp = (int) temp.getDay() + "°C";
        String minTemp = (int) temp.getMin() + "°C";
        String maxTemp = (int) temp.getMax() + "°C";
        String nightTemp = (int) temp.getNight() + "°C";
        String eveningTemp = (int) temp.getEve() + "°C";
        String morningTemp = (int) temp.getMorn() + "°C";

        String pressure = String.valueOf(dailyItems.get(position).getPressure()) + "Pa";
        String humidity = String.valueOf(dailyItems.get(position).getHumidity()) + "%";
        String windSpeed = String.valueOf(dailyItems.get(position).getWindSpeed()) + "km/h";
        String uvIndex = String.valueOf(dailyItems.get(position).getUvi());

        txtPopoutDescription.setText(weatherDescriptionUpCase);
        txtWindSpeed.setText(windSpeed);
        txtUvIndex.setText(uvIndex);
        txtHumidity.setText(humidity);
        txtPressure.setText(pressure);
        txtMorningTemp.setText(morningTemp);
        txtEveningTemp.setText(eveningTemp);
        txtNightTemp.setText(nightTemp);
        txtMaxTemp.setText(maxTemp);
        txtMinTemp.setText(minTemp);
        txtDayTemp.setText(dayTemp);
        txtPopoutDay.setText(day);

        Glide.with(context)
                .asBitmap()
                .load(iconLink)
                .into(imgPopoutIcon);

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private String getIconLink(String iconName) {
        Map<String, String> mapOfIcons = new HashMap<>();
        mapOfIcons.put("01d", "http://openweathermap.org/img/wn/01d@2x.png");
        mapOfIcons.put("02d", "http://openweathermap.org/img/wn/02d@2x.png");
        mapOfIcons.put("03d", "http://openweathermap.org/img/wn/03d@2x.png");
        mapOfIcons.put("04d", "http://openweathermap.org/img/wn/04d@2x.png");
        mapOfIcons.put("09d", "http://openweathermap.org/img/wn/09d@2x.png");
        mapOfIcons.put("10d", "http://openweathermap.org/img/wn/10d@2x.png");
        mapOfIcons.put("11d", "http://openweathermap.org/img/wn/11d@2x.png");
        mapOfIcons.put("13d", "http://openweathermap.org/img/wn/13d@2x.png");
        mapOfIcons.put("50d", "http://openweathermap.org/img/wn/50d@2x.png");
        mapOfIcons.put("01n", "http://openweathermap.org/img/wn/01n@2x.png");
        mapOfIcons.put("02n", "http://openweathermap.org/img/wn/02n@2x.png");
        mapOfIcons.put("03n", "http://openweathermap.org/img/wn/03n@2x.png");
        mapOfIcons.put("04n", "http://openweathermap.org/img/wn/04n@2x.png");
        mapOfIcons.put("09n", "http://openweathermap.org/img/wn/09n@2x.png");
        mapOfIcons.put("10n", "http://openweathermap.org/img/wn/10n@2x.png");
        mapOfIcons.put("11n", "http://openweathermap.org/img/wn/11n@2x.png");
        mapOfIcons.put("13n", "http://openweathermap.org/img/wn/13n@2x.png");
        mapOfIcons.put("50n", "http://openweathermap.org/img/wn/50n@2x.png");

        return mapOfIcons.get(iconName);
    }

    @Override
    public int getItemCount() {
        return 7;
//        return dailyItems.size();
    }

    public void setWeatherItems(List<DailyItem> dailyItems) {
        this.dailyItems = dailyItems;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgWeatherForecastIcon;
        private TextView txtWeatherForecastDescription, txtWeatherTemperature, txtDay;
        private CardView parent;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgWeatherForecastIcon = itemView.findViewById(R.id.imgWeatherForecastIcon);
            txtWeatherForecastDescription = itemView.findViewById(R.id.txtWeatherForecastDescription);
            txtWeatherTemperature = itemView.findViewById(R.id.txtWeatherTemperature);
            txtDay = itemView.findViewById(R.id.txtDay);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
