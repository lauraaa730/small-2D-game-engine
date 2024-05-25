module cz.cvut.fel.pjv.dudkolau {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;

    opens cz.cvut.fel.pjv.dudkolau to javafx.fxml;
    exports cz.cvut.fel.pjv.dudkolau;
    exports cz.cvut.fel.pjv.dudkolau.Model;
}