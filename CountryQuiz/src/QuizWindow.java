import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuizWindow {

	private Stage stage;
	private Scene scene;

	private StackPane backPane;
	private StackPane innerPane;
	private HBox info;
	private VBox question;

	private Image map;
	private ImageView mapView;
	private Region region;
	private String country;
	private Text questionText;
	private TextField questionAnswer;
	private Button submitButton;
	private Button logButton;
	private Button spellingButton;
	
	private HBox input;

	private ArrayList<Region> regions;

	private int countryCount;
	
	private ArrayList<ResultText> results;
	private int currentResult;
	
	private ResultText[][] fullResults;
	private int currentFullCol;
	private int currentFullCount;
	
	public QuizWindow() {

		// Initializing the regions
		regions = new ArrayList<Region>();
		regions.add(new Region("Africa"));
		regions.add(new Region("Asia"));
		regions.add(new Region("Aus"));
		regions.add(new Region("EU"));
		regions.add(new Region("NA"));
		regions.add(new Region("SA"));

		// Setting up the countryCount
		countryCount = 0;
		for (Region r : regions) {
			countryCount += r.getAmountOfCountries(); 
			//System.out.println("total countries: " + countryCount);
		}

		// Setting up the ImageView
		mapView = new ImageView();
		mapView.setPreserveRatio(true);
		mapView.setFitHeight(500);

		// Picking the first country
		randomlyPickACountry("FromAll");

		// Initializing the window
		stage = new Stage();
		stage.setTitle("Country Quiz");

		// Setting the panes
		backPane = new StackPane();
		backPane.getStyleClass().add("darker");
		innerPane = new StackPane();
		innerPane.getStyleClass().add("lighter");
		info = new HBox();
		info.getStyleClass().add("lighter");
		info.setAlignment(Pos.CENTER);
		question = new VBox();
		question.getStyleClass().add("lighter");
		question.setAlignment(Pos.CENTER);
		
		// Setting up full Results
		fullResults = new ResultText[HistoryWindow.numCols][HistoryWindow.numInEachCol];
		currentFullCol = 0;
		currentFullCount = 0;
		for (int i = 0; i < HistoryWindow.numCols; i++) {
			for (int j = 0; j < HistoryWindow.numInEachCol; j++) {
				fullResults[i][j] = new ResultText();
			}
		}

		// Setting up the question VBox
		questionText = new Text("What is this country?");
		questionText.setFont(Font.font(null, FontWeight.BOLD, null, 18));
		question.getChildren().add(questionText);
		Text blank = new Text("");
		blank.setFont(Font.font(5));
		question.getChildren().add(blank);
		input = new HBox();
		input.getStyleClass().add("lighter");
		input.setAlignment(Pos.CENTER);
		questionAnswer = new TextField();
		questionAnswer.setFont(Font.font(18));
		questionAnswer.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ENTER:
				reset();
				break;
			default:
				break;
			}
		});
		input.getChildren().add(questionAnswer);
		Text hBlank = new Text("    ");
		input.getChildren().add(hBlank);
		spellingButton = new Button("SP");
		spellingButton.setFont(Font.font(15));
		spellingButton.setOnAction(event -> {
			if (currentResult > 0) {
				results.get(currentResult - 1).change();
				fullResults[currentFullCol][currentFullCount - 1].change();
			}
		});
		input.getChildren().add(spellingButton);
		question.getChildren().add(input);
		Text blank2 = new Text("");
		blank2.setFont(Font.font(8));
		question.getChildren().add(blank2);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font(15));
		submitButton.setOnAction(event -> {
			reset();
		});
		question.getChildren().add(submitButton);
		Text blank3 = new Text("");
		blank3.setFont(Font.font(10));
		question.getChildren().add(blank3);
		makeResultList();
		Text blank4 = new Text("");
		blank4.setFont(Font.font(10));
		question.getChildren().add(blank4);
		logButton = new Button("View History");
		logButton.setFont(Font.font(15));
		logButton.setOnAction(event -> {
			HistoryWindow hw = new HistoryWindow(fullResults);
		});
		question.getChildren().add(logButton);

		// Adding everything to the panes
		info.getChildren().add(question);
		info.getChildren().add(new Text("    	     "));
		info.getChildren().add(mapView);
		innerPane.getChildren().add(info);
		backPane.getChildren().add(innerPane);

		// Setting the size of the frame based on the region
		setFrameSize();

		// Displaying
		scene = new Scene(backPane, 900, 600);
		scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		stage.show();

	}
	
	public void makeResultList() {
		
		results = new ArrayList<ResultText>();
		currentResult = 0;
		
		for (int i = 0; i < 10; i++) {
			results.add(new ResultText());
			question.getChildren().add(results.get(i).getText());
		}
		
	}

	public void reset() {
		updateResults();
		randomlyPickACountry("FromAll");
		setFrameSize();
		questionAnswer.setText("");
	}
	
	public void updateResults() {
		
		updateFullResults();
		
		if (currentResult == 10) {
			resetResults();
			currentResult = 0;
		}
		
		if (questionAnswer.getText().equals(country)) {
			results.get(currentResult).update(true, country);
		} else results.get(currentResult).update(false, country);
		
		currentResult++;
		
	}
	
	public void updateFullResults() {

		if (!fullResults[currentFullCol][HistoryWindow.numInEachCol - 1].getText().getText().equals("")) {
			if (currentFullCol < HistoryWindow.numCols - 1) 
				currentFullCol++;
			currentFullCount = 0;
		}
		
		if (questionAnswer.getText().equals(country)) {
			fullResults[currentFullCol][currentFullCount].update(true, country);
		} else fullResults[currentFullCol][currentFullCount].update(false, country);
		
		currentFullCount++;
		
	}

	public void resetResults() {
		
		for (ResultText r : results) {
			r.reset();
		}
		
	}

	public void setFrameSize() {

		switch(region.getName()) {

		case "Africa":
			stage.setWidth(886);
			break;
		case "Asia":
			stage.setWidth(1240);
			break;
		case "Aus":
			stage.setWidth(1099);
			break;
		case "EU":
			stage.setWidth(1138);
			break;
		case "NA":
			stage.setWidth(1046);
			break;
		case "SA":
			stage.setWidth(775);
			break;

		}

	}

	public void randomlyPickACountry(String type) {

		if (type.equals("")) {

			int regionNum = (int) (6 * Math.random());
			region = regions.get(regionNum);

			int countryNum = (int) (region.getAmountOfCountries() * Math.random());
			country = region.getCountry(countryNum);

			map = new Image("countries/" + region.getName() + "/" + country + ".png");
			mapView.setImage(map);

		} else if (type.equals("FromAll")) {

			int countryNum = (int) (countryCount * Math.random());
			
			int regionNum = getRegionFromCountryNum(countryNum);
			
			region = regions.get(regionNum);
			country = region.getCountry(getNewCountryNum(countryNum));
			
			map = new Image("countries/" + region.getName() + "/" + country + ".png");
			mapView.setImage(map);

		}

	}
	
	public int getNewCountryNum(int n) {
		
		if (n < 50) {
			return n;
		} else if (n < 94) {
			return n - 50;
		} else if (n < 105) {
			return n - 94;
		} else if (n < 149) {
			return n - 105;
		} else if (n < 164) {
			return n - 149;
		} else return n - 164;
		
	}
	
	public int getRegionFromCountryNum(int n) {
		
		if (n < 50) {
			return 0;
		} else if (n < 94) {
			return 1;
		} else if (n < 105) {
			return 2;
		} else if (n < 149) {
			return 3;
		} else if (n < 164) {
			return 4;
		} else return 5;
		
	}

	public void printCurrentCountryInfo() {

		System.out.println("Region: " + region.getName());
		System.out.println("Country: " + country);

	}

}
