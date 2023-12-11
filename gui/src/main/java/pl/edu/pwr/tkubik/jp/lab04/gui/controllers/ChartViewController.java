package pl.edu.pwr.tkubik.jp.lab04.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import pl.edu.pwr.tkubik.jp.lab04.client.Client;
import pl.edu.pwr.tkubik.jp.lab04.client.model.Measurement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChartViewController {
    @FXML
    public ComboBox stationComboBox;
    @FXML
    public LineChart lineChart;
    public ComboBox parametrComboBox;

        List<Measurement> allData = Client.getAllDataFromDatabase();
    @FXML
    public void loadStation() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(stationComboBox.getValue().toString());
        for (Measurement measurement : allData) {
            if (Objects.equals(measurement.getStacja(), stationComboBox.getValue().toString())){
                float parameter;
                String text = parametrComboBox.getValue().toString();
                lineChart.setTitle(text);
                switch (text){
                    case "Temperatura":
                        parameter = measurement.getTemperatura();
                        break;
                    case "Predkość wiatru":
                        parameter = measurement.getWindSpeed();
                        break;
                    case "Kierunek wiatru":
                        parameter = measurement.getWindDirection();
                        break;
                    case "Wilgotnosc wzgledna":
                        parameter = measurement.getRelativeHumidity();
                        break;
                    case "Suma opadu":
                        parameter = measurement.getPrecipitation();
                        break;
                    case "Cisnienie":
                        parameter = measurement.getCisnienie();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + parametrComboBox.getValue().toString());
                }
                System.out.println(parameter + " " + measurement.getGodzina_pomiaru());
                String date = measurement.getData_pomiaru() + " " + measurement.getGodzina_pomiaru();
                series.getData().add(new XYChart.Data<>(date, parameter));

            }
        }
        lineChart.getData().clear();
        lineChart.getData().add(series);
        lineChart.requestLayout();
    }

    public void initialize(){
        stationComboBox.setItems(getStationNames());
        parametrComboBox.setItems(getParameterNames());
        stationComboBox.setValue("Białystok");
        parametrComboBox.setValue("Temperatura");
        loadStation();

    }

    private ObservableList<String> getStationNames(){
        List<Measurement> allData = Client.getAllDataFromDatabase();
        List<String> stationStrings = allData.stream().map(m -> m.getStacja()).collect(Collectors.toList());

        ObservableList<String> stationNames = FXCollections.observableArrayList(stationStrings);
        return stationNames;
    }
    private ObservableList<String> getParameterNames(){

        String[] stationStrings = {"Temperatura", "Predkość wiatru", "Kierunek wiatru", "Wilgotnosc wzgledna", "Suma opadu", "Cisnienie"};

        ObservableList<String> stationNames = FXCollections.observableArrayList(stationStrings);
        return stationNames;
    }
}
