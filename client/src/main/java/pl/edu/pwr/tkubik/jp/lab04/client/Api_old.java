//package pl.edu.pwr.tkubik.jp.lab04.client;
//
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import pl.edu.pwr.tkubik.jp.lab04.client.model.Measurement;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//
//public class Api_old {
//
//    public static void main(String[] args) {
//        try {
//            // Check if the database file exists
//            File databaseFile = new File("synop_data.db");
//
//            System.out.println("wczytanie pliku");
//            if (!databaseFile.exists()) {
//                System.out.println("tworzenie pliku");
//                // Create an empty database file
//                boolean fileCreated = databaseFile.createNewFile();
//
//                if (!fileCreated) {
//                    System.err.println("Error creating the database file.");
//                    System.exit(1);
//                }
//            }else
//                System.out.println("istanieje xd");
//
//            // Pobierz dane JSON z API
//            String apiURL = "https://danepubliczne.imgw.pl/api/data/synop";
//            String json = fetchJson(apiURL);
//
//            // Zapisz dane w bazie danych SQLite
//            saveDataToDatabase(json);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static String fetchJson(String apiUrl) throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(apiUrl))
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
////        URL url = new URL(apiUrl);
////        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////        connection.setRequestMethod("GET");
////
////        // Odczytaj odpowiedź z API
////        try (InputStream inputStream = connection.getInputStream();
////             Scanner scanner = new Scanner(inputStream)) {
////            scanner.useDelimiter("\\A");
////            return scanner.hasNext() ? scanner.next() : "";
////        }
//    }
//
//    private static void saveDataToDatabase(String json) throws SQLException {
//        // Łącz z bazą danych SQLite
//        String jdbcUrl = "jdbc:sqlite:synop_data.db";
//        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
//            String delete="DROP TABLE IF EXISTS synop;";
//            connection.prepareStatement(delete).executeUpdate();
//            // Utwórz tabelę, jeśli nie istnieje
//
//            String createTableSql = "CREATE TABLE IF NOT EXISTS synop ("
//                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + "id_stacji INTEGER,"
//                    + "stacja TEXT,"
//                    + "data_pomiaru TEXT,"
//                    + "godzina_pomiaru TEXT,"
//                    + "temperatura REAL,"
//                    + "predkosc_wiatru INTEGER,"
//                    + "kierunek_wiatru INTEGER,"
//                    + "wilgotnosc_wzgledna REAL,"
//                    + "suma_opadu REAL,"
//                    + "cisnienie REAL)";
//            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSql)) {
//                createTableStatement.executeUpdate();
//            }
//
////            MyObject myObject = new MyObject("John Doe", 25);
////            Gson gson = new Gson();
////            String jsonRepresentation = gson.toJson(myObject);
////            System.out.println("JSON representation: " + jsonRepresentation);
////
////            // Deserialization: Convert JSON back to a Java object
////            MyObject deserializedObject = gson.fromJson(jsonRepresentation, MyObject.class);
////            System.out.println("Deserialized Java object: " + deserializedObject);Api
//
//            Gson gson = new GsonBuilder().create();
////            WeatherData weatherData = gson.fromJson(json, WeatherData.class);
//
//            // Define the type of the object you're expecting (List<Measurements>)
//            Type measurementsListType = new TypeToken<List<Measurement>>() {}.getType();
//
//            // Parse JSON to List<Measurements>
//            List<Measurement> measurementList = new Gson().fromJson(json, measurementsListType);
//
////            JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
//
//
////            saveDataToDatabase(weatherData.getMeasurements());
//
//            for (Measurement measurement : measurementList) {
//                String sql = "INSERT INTO synop (id_stacji, stacja, data_pomiaru, godzina_pomiaru, temperatura, " +
//                        "predkosc_wiatru, kierunek_wiatru, wilgotnosc_wzgledna, suma_opadu, cisnienie) " +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                    preparedStatement.setString(1, measurement.getStationId());
//                    preparedStatement.setString(2, measurement.getStacja());
//                    preparedStatement.setString(3, measurement.getData_pomiaru());
//                    preparedStatement.setString(4, measurement.getGodzina_pomiaru());
//                    preparedStatement.setString(5, measurement.getTemperatura());
//                    preparedStatement.setString(6, measurement.getWindSpeed());
//                    preparedStatement.setString(7, measurement.getWindDirection());
//                    preparedStatement.setString(8, measurement.getRelativeHumidity());
//                    preparedStatement.setString(9, measurement.getPrecipitation());
//                    preparedStatement.setString(10, measurement.getCisnienie());
//
//                    preparedStatement.executeUpdate();
//                }
//            }
//            System.out.println("Data saved to the database successfully!");
//
//            System.out.println("Dane zostały pobrane i zapisane w bazie danych.");
//        }
//    }
//}
