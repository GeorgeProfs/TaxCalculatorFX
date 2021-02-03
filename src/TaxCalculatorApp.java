import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaxCalculatorApp extends Application {
  private CalculationGrid calculationGrid = new CalculationGrid();

  public static void main(String[] args) {
    Application.launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Tax Calculator");
    primaryStage.setWidth(500);
    primaryStage.setHeight(400);

    Scene primaryScene = new Scene(calculationGrid.constructCalculationGrid());
    primaryStage.setScene(primaryScene);
    primaryStage.show();
  }
}
