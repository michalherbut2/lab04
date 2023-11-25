package pl.edu.pwr.tkubik.jp.lab04.client.model;

import com.google.gson.annotations.SerializedName;

public class Measurement {
    @SerializedName("id_stacji")
    private String stationId;

    private String stacja;
    private String data_pomiaru;
    private String godzina_pomiaru;
    private float temperatura;

    @SerializedName("predkosc_wiatru")
    private float windSpeed;

    @SerializedName("kierunek_wiatru")
    private float windDirection;

    @SerializedName("wilgotnosc_wzgledna")
    private float relativeHumidity;

    @SerializedName("suma_opadu")
    private float precipitation;

    private float cisnienie;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStacja() {
        return stacja;
    }

    public void setStacja(String stacja) {
        this.stacja = stacja;
    }

    public String getData_pomiaru() {
        return data_pomiaru;
    }

    public void setData_pomiaru(String data_pomiaru) {
        this.data_pomiaru = data_pomiaru;
    }

    public String getGodzina_pomiaru() {
        return godzina_pomiaru;
    }

    public void setGodzina_pomiaru(String godzina_pomiaru) {
        this.godzina_pomiaru = godzina_pomiaru;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Float windDirection) {
        this.windDirection = windDirection;
    }

    public float getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(float relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getCisnienie() {
        return cisnienie;
    }

    public void setCisnienie(float cisnienie) {
        this.cisnienie = cisnienie;
    }
}