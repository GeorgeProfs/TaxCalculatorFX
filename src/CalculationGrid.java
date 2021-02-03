import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;

class CalculationGrid {
  private final GridPane calculationGrid = new GridPane();
  private final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
  private final Label label = new Label();
  private final CalculationLogic calculationLogic = new CalculationLogicProcess();
  private final MongoApp mongoApp = new MongoApp();

  private Perfomance oldPerfomance;
  private Perfomance newPerfomance;

  private final ArrayList<Node> gridElements = new ArrayList(0);
  private final ArrayList<TextField> gridOldRefElements = new ArrayList(0);
  private final ArrayList<TextField> gridNewRefElements = new ArrayList(0);

  CalculationGrid() {
    System.out.println("CalculationGrid constructed");
  }

  public GridPane constructCalculationGrid(){
    calculationGrid.setPadding(new Insets(10, 10, 10, 10));
    calculationGrid.setVgap(5);
    calculationGrid.setHgap(5);

    addWaterFields();
    addElectricityFields();
    addNewLabel();
    addSubmitButtons();
    addCalcButton();
    addReferenceIntoGrid();
    return calculationGrid;
  }

  private void setupGrid(Node node, int columnIndex, int rowIndex, boolean needToAddInRef, RefElements refElements) {
    calculationGrid.setConstraints(node, columnIndex, rowIndex);
    gridElements.add(node);
    if (needToAddInRef) {
      switch (refElements){
        case OLD: {
          gridOldRefElements.add((TextField) node);
          break;
        }
        case NEW: {
          gridNewRefElements.add((TextField) node);
          break;
        }
      }
    }
  }

  private void addWaterFields() {
    addHotWaterFields();
    addColdWaterFields();
  }

  private void addHotWaterFields() {
    TextField oldHotWater = TextFieldFactory.constructTextField("Old hot water");
    setupGrid(oldHotWater, 0, 0, true, RefElements.OLD);

    TextField newHotWater = TextFieldFactory.constructTextField("New hot water");
    setupGrid(newHotWater, 10, 0, true, RefElements.NEW);
  }

  private void addColdWaterFields() {
    TextField oldColdWater = TextFieldFactory.constructTextField("Old cold water");
    setupGrid(oldColdWater, 0, 2, true, RefElements.OLD);

    TextField newColdWater = TextFieldFactory.constructTextField("New cold water");
    setupGrid(newColdWater, 10, 2, true, RefElements.NEW);
  }

  private void addElectricityFields() {
    addT1();
    addT2();
    addT3();
  }

  private void addT1() {
    TextField oldT1 = TextFieldFactory.constructTextField("Old T1");
    setupGrid(oldT1, 0, 4, true, RefElements.OLD);

    TextField newT1 = TextFieldFactory.constructTextField("New T1");
    setupGrid(newT1, 10, 4, true, RefElements.NEW);
  }

  private void addT2() {
    TextField oldT2 = TextFieldFactory.constructTextField("Old T2");
    setupGrid(oldT2, 0, 6, true, RefElements.OLD);

    TextField newT2 = TextFieldFactory.constructTextField("New T2");
    setupGrid(newT2, 10, 6, true, RefElements.NEW);
  }

  private void addT3() {
    TextField oldT3 = TextFieldFactory.constructTextField("Old T3");
    setupGrid(oldT3, 0, 8, true, RefElements.OLD);

    TextField newT3 = TextFieldFactory.constructTextField("New T3");
    setupGrid(newT3, 10, 8, true, RefElements.NEW);
  }

  private void addSubmitButtons() {
    Button submitOld = new Button("Submit");
    setupGrid(submitOld, 0, 10, false, null);
    submitOld.setOnAction(e -> validateFields(gridOldRefElements, RefElements.OLD));

    Button submitNew = new Button("Submit");
    setupGrid(submitNew, 10, 10, false, null);
    submitNew.setOnAction(e -> validateFields(gridNewRefElements, RefElements.NEW));
  }

  private void addNewLabel() {
    calculationGrid.setConstraints(label, 0, 12);
    calculationGrid.setColumnSpan(label, 1);
    calculationGrid.getChildren().add(label);
  }

  public void setUpLabel(Boolean correct, String fieldMiss) {
    if (!correct) {
      label.setText("You have not left a comment for " + fieldMiss);
      label.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
    } else {
      label.setText("Everything is correct. Result saved");
      label.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
    }
  }

  private void perfomanceMaker(RefElements refElements, ArrayList<Integer> listOfValues) {
    switch (refElements) {
      case OLD: {
        oldPerfomance = new Perfomance(listOfValues.get(0), listOfValues.get(1), listOfValues.get(2), listOfValues.get(3), listOfValues.get(4));
        break;
      }
      case NEW: {
        newPerfomance = new Perfomance(listOfValues.get(0), listOfValues.get(1), listOfValues.get(2), listOfValues.get(3), listOfValues.get(4));
        break;
      }
    }
  }

  private void validateFields(ArrayList<TextField> arrayRefElements, RefElements refElements) {
    boolean correct = true;

    for (TextField element : arrayRefElements) {
      if ((element.getText() == null || element.getText().isEmpty())) {
        correct = false;
        setUpLabel(false, element.getPromptText());
        break;
      }
    }

    if (correct) {
      setUpLabel(true, null);
      ArrayList<Integer> listOfValues = new ArrayList(0);
      for (TextField element : arrayRefElements) {
        listOfValues.add(Integer.parseInt(element.getText().trim()));
      }
      perfomanceMaker(refElements, listOfValues);
    }
  }

  private void addCalcButton() {
    Button calcButton = new Button("Calc");
    setupGrid(calcButton, 0, 15, false, null);

    calcButton.setOnAction(e -> {
      if (oldPerfomance != null && newPerfomance != null) {
        int taxResult = calculationLogic.calcResultOfMonth(oldPerfomance, newPerfomance, 2);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, your result is " + taxResult);
        alert.setContentText("You should pay it");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
          System.out.println(mongoApp.addPayResultToMongo(taxResult));
        } else {
          // ... user chose CANCEL or closed the dialog
        }
      }
    });
  }

  private void addReferenceIntoGrid() {
    for (Node element : gridElements){
      calculationGrid.getChildren().add(element);
    }
  }

}
