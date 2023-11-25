package pl.edu.pwr.tkubik.jp.lab04.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.edu.pwr.tkubik.jp.lab04.client.Api;
import pl.edu.pwr.tkubik.jp.lab04.client.Client;
import pl.edu.pwr.tkubik.jp.lab04.client.model.Measurement;

import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Client.name="nazwa nazwa";
        Client client = new Client();
//        client.
//        Client.

        List<Measurement> allData = Api.getAllDataFromDatabase();
//        System.out.println(allData.size());
//        System.out.println(allData.get(0).getStacja());
        String name = allData.get(0).getStacja();
        welcomeText.setText(name);

    }
}