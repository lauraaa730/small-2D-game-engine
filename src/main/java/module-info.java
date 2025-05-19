module cz.cvut.fel.pjv.dudkolau {
    requires javafx.controls;

    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.logging;

    opens cz.cvut.fel.pjv.dudkolau.Model to com.fasterxml.jackson.databind;

    exports cz.cvut.fel.pjv.dudkolau;
    exports cz.cvut.fel.pjv.dudkolau.Model;
}