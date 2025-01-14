module io.github.palexdev {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive java.sql;

    requires javafx.base;  // Add this if not already there

    opens io.github.palexdev.model to javafx.base;  // This opens the model package to javafx.base

    opens io.github.palexdev to javafx.fxml;
    opens io.github.palexdev.controller to javafx.fxml;
    opens io.github.palexdev.view to javafx.fxml;

    exports io.github.palexdev;
    exports io.github.palexdev.controller;
}
