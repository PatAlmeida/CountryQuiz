import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Region {
	
	private String regionName;
	private ArrayList<String> countries;
	
	public Region(String regionName) {
		
		this.regionName = regionName;
		
		try {
			
			Scanner scanner = new Scanner(new File("src/countryLists/" + regionName + ".txt"));
			
			countries = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				Scanner lineScan = new Scanner(scanner.nextLine());
				countries.add(lineScan.nextLine());
				lineScan.close();
			}
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return regionName;
	}
	
	public String getCountry(int i) {
		return countries.get(i);
	}
	
	public int getAmountOfCountries() {
		return countries.size();
	}

}
