package pl.edu.pwr.tkubik.jp.lab04.client;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pl.edu.pwr.tkubik.jp.lab04.client.model.Measurement;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    public static void main(String[] args) {
        try {
            // Check if the database file exists
            File databaseFile = new File("client/src/main/resources/synop_data.db");

            if (!databaseFile.exists()) {
                // Create an empty database file
                boolean fileCreated = databaseFile.createNewFile();

                if (!fileCreated) {
                    System.err.println("Error creating the database file.");
                    System.exit(1);
                }
            }

            // Pobierz dane JSON z API
            String apiURL = "https://danepubliczne.imgw.pl/api/data/synop";
            String json = fetchJson(apiURL);
            List<Measurement> measurementList = convertJsonToMeasurmentList(json);

            // Zapisz dane w bazie danych SQLite
            if (!isDataInDatabase(measurementList))
                saveDataToDatabase(measurementList);
            List<Measurement> allData = getAllDataFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String fetchJson(String apiUrl) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static void saveDataToDatabase(List<Measurement> measurementList) throws SQLException {
        // Łącz z bazą danych SQLite
        String jdbcUrl = "jdbc:sqlite:client/src/main/resources/synop_data.db";
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Utwórz tabelę, jeśli nie istnieje

            String createTableSql = "CREATE TABLE IF NOT EXISTS synop ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "id_stacji INTEGER,"
                    + "stacja TEXT,"
                    + "data_pomiaru TEXT,"
                    + "godzina_pomiaru TEXT,"
                    + "temperatura REAL,"
                    + "predkosc_wiatru INTEGER,"
                    + "kierunek_wiatru INTEGER,"
                    + "wilgotnosc_wzgledna REAL,"
                    + "suma_opadu REAL,"
                    + "cisnienie REAL)";
            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSql)) {
                createTableStatement.executeUpdate();
            }

            for (Measurement measurement : measurementList) {
                String sql = "INSERT INTO synop (id_stacji, stacja, data_pomiaru, godzina_pomiaru, temperatura, " +
                        "predkosc_wiatru, kierunek_wiatru, wilgotnosc_wzgledna, suma_opadu, cisnienie) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, measurement.getStationId());
                    preparedStatement.setString(2, measurement.getStacja());
                    preparedStatement.setString(3, measurement.getData_pomiaru());
                    preparedStatement.setString(4, measurement.getGodzina_pomiaru());
                    preparedStatement.setFloat(5, measurement.getTemperatura());
                    preparedStatement.setFloat(6, measurement.getWindSpeed());
                    preparedStatement.setFloat(7, measurement.getWindDirection());
                    preparedStatement.setFloat(8, measurement.getRelativeHumidity());
                    preparedStatement.setFloat(9, measurement.getPrecipitation());
                    preparedStatement.setFloat(10, measurement.getCisnienie());

                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Data saved to the database successfully!");

            System.out.println("Dane zostały pobrane i zapisane w bazie danych.");
        }
    }

    public static List<Measurement> getAllDataFromDatabase() {
        List<Measurement> measurementList = new ArrayList<>();

        // Łącz z bazą danych SQLite
        String jdbcUrl = "jdbc:sqlite:client/src/main/resources/synop_data.db";
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Wykonaj zapytanie SQL, aby pobrać wszystkie dane z tabeli
            String sql = "SELECT * FROM synop";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                // Iteruj przez wyniki zapytania i dodawaj do listy
                while (resultSet.next()) {
                    Measurement measurement = new Measurement();
                    measurement.setStationId(resultSet.getString("id_stacji"));
                    measurement.setStacja(resultSet.getString("stacja"));
                    measurement.setData_pomiaru(resultSet.getString("data_pomiaru"));
                    measurement.setGodzina_pomiaru(resultSet.getString("godzina_pomiaru"));
                    measurement.setTemperatura(resultSet.getFloat("temperatura"));
                    measurement.setWindSpeed(resultSet.getFloat("predkosc_wiatru"));
                    measurement.setWindDirection(resultSet.getFloat("kierunek_wiatru"));
                    measurement.setRelativeHumidity(resultSet.getFloat("wilgotnosc_wzgledna"));
                    measurement.setPrecipitation(resultSet.getFloat("suma_opadu"));
                    measurement.setCisnienie(resultSet.getFloat("cisnienie"));

                    measurementList.add(measurement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving data from the database.", e);
        }

        return measurementList;
    }

    private static List<Measurement> convertJsonToMeasurmentList(String json){
        Type measurementsListType = new TypeToken<List<Measurement>>() {}.getType();

        // Parse JSON to List<Measurements>
        return new Gson().fromJson(json, measurementsListType);
    }

    private static boolean isDataInDatabase(List<Measurement> measurementList) {
        List<String> dates = new ArrayList<>();

        // Łącz z bazą danych SQLite
        String jdbcUrl = "jdbc:sqlite:client/src/main/resources/synop_data.db";
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Wykonaj zapytanie SQL, aby pobrać wszystkie dane z tabeli
            String sql = "SELECT data_pomiaru FROM synop GROUP BY data_pomiaru";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                // Iteruj przez wyniki zapytania i dodawaj do listy
                while (resultSet.next()) {
                    dates.add(resultSet.getString("data_pomiaru"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving data from the database.", e);
        }
        return dates.contains(measurementList.get(0).getData_pomiaru());
    }
}
