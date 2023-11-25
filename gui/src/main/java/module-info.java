module gui {
    requires client;

    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.tkubik.jp.lab04.gui to javafx.fxml; // klasy mogą być prześwietlana przez fajafv
    exports pl.edu.pwr.tkubik.jp.lab04.gui;
    exports pl.edu.pwr.tkubik.jp.lab04.gui.controllers;
    opens pl.edu.pwr.tkubik.jp.lab04.gui.controllers to javafx.fxml; // jak to koniec to nei trzeba
}