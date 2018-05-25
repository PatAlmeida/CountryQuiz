import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HistoryWindow {
	
	private StackPane backPane;
	private StackPane innerPane;
	private VBox info;
	
	private Text text;
	private ResultText[][] results; 
	
	private HBox holdsCols;
	private VBox[] columns;
		
	private Scene scene;
	private Stage stage;
	
	public final static int numCols = 6;
	public final static int numInEachCol = 30;
	
	public HistoryWindow(ResultText[][] fullResults) {
		
		backPane = new StackPane();
		backPane.getStyleClass().add("darker");
		innerPane = new StackPane();
		innerPane.getStyleClass().add("lighter");
		info = new VBox();
		info.getStyleClass().add("lighter");
		info.setAlignment(Pos.CENTER);
		
		makeResults(fullResults);
		
		int amountOfResults = 0;
		int amountCorrect = 0;
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numInEachCol; j++) {
				if (results[i][j].getText().getText() != "") {
					amountOfResults++;
					if (results[i][j].isItRight()) {
						amountCorrect++;
					}
				}
			}
		}
		text = new Text("History: " + amountCorrect + "/" + amountOfResults);
		text.setFont(Font.font(null, FontWeight.BOLD, null, 18));
		info.getChildren().add(text);
		
		info.getChildren().add(holdsCols);
		
		innerPane.getChildren().add(info);
		backPane.getChildren().add(innerPane);
		
		// Displaying
		scene = new Scene(backPane, 1300, 850);
		scene.getStylesheets().add("style.css");
		stage = new Stage();
		stage.setTitle("Country Quiz - History");
		stage.setScene(scene);
		stage.show();
		
	}

	private void makeResults(ResultText[][] fullResults) {

		results = fullResults;
		
		holdsCols = new HBox();
		holdsCols.setAlignment(Pos.CENTER);
		holdsCols.getStyleClass().add("lighter");
		
		columns = new VBox[8];
		for (int i = 0; i < numCols; i++) {
			columns[i] = new VBox();
			columns[i].setAlignment(Pos.CENTER);
			columns[i].getStyleClass().add("lighter");
			columns[i].getChildren().add(new Text("           		         	                   "));
			holdsCols.getChildren().add(columns[i]);
		}
		
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numInEachCol; j++) {
				
				if (!checkEmptyCol(i)) 
					columns[i].getChildren().add(results[i][j].getText());
				
			}
		}
		
	}

	private boolean checkEmptyCol(int i) {

		boolean allEmpty = true;
		
		for (ResultText r : results[i]) {
			if (!r.getText().getText().equals(""))
				allEmpty = false;
		}
		
		return allEmpty;
		
	}

}
