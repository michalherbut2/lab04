package pl.edu.pwr.tkubik.jp.lab04.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherData {
    private List<Measurement> measurements;

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }


}
