module io.github.palexdev {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive java.sql;

    opens io.github.palexdev to javafx.fxml;
    exports io.github.palexdev;
}
