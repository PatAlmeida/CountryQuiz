import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResultText {
	
	private Text text;
	private boolean gotItRight;
	
	public ResultText() {
		
		text = new Text("");
		text.setFont(Font.font(18));
		
		gotItRight = false;
		
	}
	
	public Text getText() {
		return text;
	}
	
	public void update(boolean correct, String country) {
		
		text.setText(country);
		if (correct) {
			text.setFill(Color.GREEN);
		} else text.setFill(Color.RED);
		gotItRight = correct;
		
	}
	
	public void change() {
		
		gotItRight = !gotItRight;
		
		if (gotItRight) {
			text.setFill(Color.GREEN);
		} else text.setFill(Color.RED);
		
	}
	
	public void reset() { text.setText(""); }
	
	public boolean isItRight() { return gotItRight; }

}
