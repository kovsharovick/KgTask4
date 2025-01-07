module ru.vsu.cs.yesikov {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;


    opens ru.vsu.cs.yesikov to javafx.fxml;
    exports ru.vsu.cs.yesikov;
    exports ru.vsu.cs.yesikov.render_engine;
    opens ru.vsu.cs.yesikov.render_engine to javafx.fxml;
}