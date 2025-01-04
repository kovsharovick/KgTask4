module ru.vsu.cs.yesikov {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;


    opens ru.vsu.cs.yesikov to javafx.fxml;
    exports ru.vsu.cs.yesikov;
}