module client {
    exports pl.edu.pwr.tkubik.jp.lab04.client;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;
    // other module configurations
    opens pl.edu.pwr.tkubik.jp.lab04.client.model to com.google.gson;
    exports pl.edu.pwr.tkubik.jp.lab04.client.model;


}