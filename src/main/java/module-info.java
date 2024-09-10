module org.aldanari.asciiinc {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;


	opens org.aldanari.asciiinc to javafx.fxml;
    exports org.aldanari.asciiinc;
	exports org.aldanari.asciiinc.cells;
	opens org.aldanari.asciiinc.cells to javafx.fxml;
}