package backend;

import api.DataService;
import api.NumberLanguage;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class DataServiceImpl implements DataService {
	
	private static final Map<String, NumberLanguage> languagesMap = Map.of(
		    "English", new EnglishNumbers(),
		    "Türkçe", new TurkishNumbers()
	);
	
	@Activate
    void start() {
        System.out.println("Backend service activated");
    }

	@Override
	public String getData() {
		return "Please enter the values";
	}
	
	@Override
	public int convertStringToInteger(String textNumber, String language) {
		
		if (textNumber == null) {
			return 0;
		}
		
		textNumber = textNumber.toLowerCase().trim();
		
		try {
			return Integer.parseInt(textNumber); // check if numeric
		} catch (NumberFormatException ignored) {}
		
		NumberLanguage numberLanguage = languagesMap.get(language);
		if (numberLanguage == null) {
		    throw new IllegalArgumentException("Unsupported language: " + language);
		}
				
		String[] words = textNumber.split("\\s+"); 
		
		int current = 0;
		int result = 0;
		
		for (String word : words) {
			Integer value = numberLanguage.getValue(word);
			
			if (value == null) {
				throw new IllegalArgumentException("Unknown word : " + word);
			}
			
			if (value == 100) {
				current = (current == 0 ? 1 : current) * 100;
			} else if (value >= 1000) {
				current = (current == 0 ? 1 : current) * value;
				result += current;
				current = 0;
			} else {
				current += value;
			}
		}
		
		return result + current;
		
	}

	@Override
	public String convertIntegerToString(int number, String language) {
		
		NumberLanguage lang;

	    if (language.equalsIgnoreCase("English")) {
	        return ((EnglishNumbers) new EnglishNumbers()).toString(number);
	    } else if (language.equalsIgnoreCase("Türkçe")) {
	        return ((TurkishNumbers) new TurkishNumbers()).toString(number);
	    } else {
	        throw new IllegalArgumentException("Unsupported language");
	    }
	    
	}

}
