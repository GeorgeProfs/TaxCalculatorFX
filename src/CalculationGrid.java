import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

class CalculationGrid {
  private GridPane calculationGrid;

  private final ArrayList<Node> gridElements = new ArrayList(0);
  private final ArrayList<TextField> gridOldRefElements = new ArrayList(0);
  private final ArrayList<TextField> gridNewRefElements = new ArrayList(0);
  private final Label label = new Label();
  Perfomance oldPerfomance;
  Perfomance newPerfomance;

  CalculationGrid() {
    System.out.println("CalculationGrid constructed");
  }

  public GridPane constructCalculationGrid(){
    calculationGrid = new GridPane();
    calculationGrid.setPadding(new Insets(10, 10, 10, 10));
    calculationGrid.setVgap(5);
    calculationGrid.setHgap(5);

    addWaterFields();
    addElectricityFields();
    addSubmitButtons();
    addCalcButton();
    addReferenceIntoGrid();
    return calculationGrid;
  }

  private void addWaterFields() {
    addHotWaterFields();
    addColdWaterFields();
  }

  private void addHotWaterFields() {
    TextField oldHotWater = TextFieldFactory.constructTextField("Old hot water");
    calculationGrid.setConstraints(oldHotWater, 0, 0);
    gridElements.add(oldHotWater);
    gridOldRefElements.add(oldHotWater);

    TextField newHotWater = TextFieldFactory.constructTextField("New hot water");
    calculationGrid.setConstraints(newHotWater, 10, 0);
    gridElements.add(newHotWater);
    gridNewRefElements.add(oldHotWater);
  }

  private void addColdWaterFields() {
    TextField oldColdWater = TextFieldFactory.constructTextField("Old cold water");
    calculationGrid.setConstraints(oldColdWater, 0, 2);
    gridElements.add(oldColdWater);
    gridOldRefElements.add(oldColdWater);

    TextField newColdWater = TextFieldFactory.constructTextField("New cold water");
    calculationGrid.setConstraints(newColdWater, 10, 2);
    gridElements.add(newColdWater);
    gridNewRefElements.add(newColdWater);
  }

  private void addElectricityFields() {
    addT1();
    addT2();
    addT3();
  }

  private void addT1() {
    TextField oldT1 = TextFieldFactory.constructTextField("Old T1");
    calculationGrid.setConstraints(oldT1, 0, 4);
    gridElements.add(oldT1);
    gridOldRefElements.add(oldT1);

    TextField newT1 = TextFieldFactory.constructTextField("New T1");
    calculationGrid.setConstraints(newT1, 10, 4);
    gridElements.add(newT1);
    gridNewRefElements.add(newT1);
  }

  private void addT2() {
    TextField oldT2 = TextFieldFactory.constructTextField("Old T2");
    calculationGrid.setConstraints(oldT2, 0, 6);
    gridElements.add(oldT2);
    gridOldRefElements.add(oldT2);

    TextField newT2 = TextFieldFactory.constructTextField("New T2");
    calculationGrid.setConstraints(newT2, 10, 6);
    gridElements.add(newT2);
    gridNewRefElements.add(newT2);
  }

  private void addT3() {
    TextField oldT3 = TextFieldFactory.constructTextField("Old T3");
    calculationGrid.setConstraints(oldT3, 0, 8);
    gridElements.add(oldT3);
    gridOldRefElements.add(oldT3);

    TextField newT3 = TextFieldFactory.constructTextField("New T3");
    calculationGrid.setConstraints(newT3, 10, 8);
    gridElements.add(newT3);
    gridNewRefElements.add(newT3);
  }

  private void addSubmitButtons() {
    addNewLabel();

    Button submitOld = new Button("Submit");
    calculationGrid.setConstraints(submitOld, 0, 10);
    gridElements.add(submitOld);
    submitOld.setOnAction(e -> validateOldFields());

    Button submitNew = new Button("Submit");
    calculationGrid.setConstraints(submitNew, 10, 10);
    gridElements.add(submitNew);
    submitNew.setOnAction(e -> validateNewFields());
  }

  private void validateOldFields() {
    oldPerfomance = validateFields(gridOldRefElements);
  }

  private void validateNewFields() {
    newPerfomance = validateFields(gridNewRefElements);
  }

  private void addNewLabel() {
    calculationGrid.setConstraints(label, 0, 12);
    calculationGrid.setColumnSpan(label, 1);
    calculationGrid.getChildren().add(label);
  }

  private Perfomance validateFields(ArrayList<TextField> refElements) {
    boolean correct = true;
    ArrayList<Integer> listOfValues = new ArrayList(0);

    for (TextField element : refElements) {
      if ((element.getText() == null || element.getText().isEmpty())) {
        label.setText("You have not left a comment for " + element.getPromptText());
        label.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        correct = false;
        break;
      }
    }

    if (correct) {
      label.setText("Everything is correct. Result saved");
      label.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
      for (TextField element : refElements) {
        listOfValues.add(Integer.parseInt(element.getText().trim()));
      }
      return new Perfomance(listOfValues.get(0), listOfValues.get(1), listOfValues.get(2), listOfValues.get(3), listOfValues.get(4));
    }

    return null;
  }

  private void addCalcButton() {
    Button calcButton = new Button("Calc");
    calculationGrid.setConstraints(calcButton, 0, 15);
    gridElements.add(calcButton);
    CalculationLogic calculationLogic = new CalculationLogic();
    calcButton.setOnAction(e -> System.out.println(calculationLogic.calcResultOfMonth(oldPerfomance, newPerfomance, 2)));
  }

  private void addReferenceIntoGrid() {
    for (Node element : gridElements){
      calculationGrid.getChildren().add(element);
    }
  }

}
