import javafx.scene.control.TextField;

class TextFieldFactory {
  private String name;

  TextFieldFactory(){
    System.out.println("TextFieldFactory constructed");
  }

  public static TextField constructTextField(String name){
    TextField textField = new TextField();
    textField.setPromptText(name);
    return textField;
  }

}
