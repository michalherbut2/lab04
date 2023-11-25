package pl.edu.pwr.tkubik.jp.lab04.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.pwr.tkubik.jp.lab04.client.Api;
import pl.edu.pwr.tkubik.jp.lab04.client.model.Measurement;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    private ObservableList<String> stationNames =
//            getStationNames();
            FXCollections.observableArrayList(
            "Station 1", "Station 2", "Station 3" // Dodaj nazwy stacji zgodnie z Twoimi danymi
    );

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chart-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        /////////////////////////////
//        stage.setTitle("Temperature Chart App");
//        ObservableList<String> stationNames2 = getStationNames();
//        ComboBox<String> stationComboBox = new ComboBox<>(stationNames2);
////        stationComboBox.
//        stationComboBox.setPromptText("Select Station");
//
//        NumberAxis yAxis = new NumberAxis();
//        yAxis.setLabel("Temperature (°C)");
//
//        CategoryAxis xAxis = new CategoryAxis();
////        xAxis.setCategories();
//        xAxis.setLabel("Data");
//
//        LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), yAxis);
//        lineChart.setTitle("Temperature Chart");
//
//        stationComboBox.setOnAction(e -> {
//            String selectedStation = stationComboBox.getSelectionModel().getSelectedItem();
//            updateChart(lineChart, selectedStation);
//        });
//
//        VBox vbox = new VBox(stationComboBox, lineChart);
//        Scene scene = new Scene(vbox, 800, 600);
//
//        stage.setScene(scene);
//        stage.show();
    }

    private void updateChart(LineChart<String, Number> lineChart, String selectedStation) {
        // Tu powinieneś dodać logikę do pobierania danych z bazy danych dla wybranej stacji
        // i zaktualizować wykres (poniżej znajdziesz przykład wykorzystujący dane losowe)

//        XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        series.setName(selectedStation);

        // Dodaj przykładowe dane (możesz zastąpić tym logiką pobierania danych z bazy)
//        for (int i = 1; i <= 10; i++) {
//            series.getData().add(new XYChart.Data<>(i, Math.random() * 30));
//        }
        List<Measurement> allData = Api.getAllDataFromDatabase();
//        lineChart.getData().clear();

        for (Measurement measurement : allData) {
            if (Objects.equals(measurement.getStacja(), selectedStation)){

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(measurement.getData_pomiaru());

            String date = measurement.getData_pomiaru();
            float temperature = measurement.getTemperatura();

            series.getData().add(new XYChart.Data<>(date, temperature));

            lineChart.getData().add(series);
            }
//        lineChart.getData().add(series);
        }

    }

    public static void main(String[] args) {
        Api.main(args);

        launch();
    }

    private ObservableList<String> getStationNames(){
        List<Measurement> allData = Api.getAllDataFromDatabase();
//        allData.forEach(measurement ->  );
        List<String> stationStrings = allData.stream().map(m -> m.getStacja()).collect(Collectors.toList());
        for (Measurement m: allData
             ) {
            System.out.println(m.getStacja()+" "+m.getTemperatura()+" "+m.getData_pomiaru());
        }
        System.out.println(stationStrings);
        ObservableList<String> stationNames = FXCollections.observableArrayList(
//            "Station 1", "Station 2", "Station 3" // Dodaj nazwy stacji zgodnie z Twoimi danymi
                stationStrings
        );
        return stationNames;
//        return
////            getStationNames();
//                FXCollections.observableArrayList(
//                        "Station 1", "Station 2", "Station 3" // Dodaj nazwy stacji zgodnie z Twoimi danymi
//                );
    }
    private ObservableList<String> getTemperature(){
        List<Measurement> allData = Api.getAllDataFromDatabase();
        List<String> stationStrings = allData.stream().map(m -> m.getStacja()).collect(Collectors.toList());
        for (Measurement m: allData
        ) {
            System.out.println(m.getStacja()+" "+m.getTemperatura()+" "+m.getData_pomiaru());
        }
        System.out.println(stationStrings);
        ObservableList<String> stationNames = FXCollections.observableArrayList(
//            "Station 1", "Station 2", "Station 3" // Dodaj nazwy stacji zgodnie z Twoimi danymi
                stationStrings
        );
        return stationNames;
//        return
////            getStationNames();
//                FXCollections.observableArrayList(
//                        "Station 1", "Station 2", "Station 3" // Dodaj nazwy stacji zgodnie z Twoimi danymi
//                );
    }
}