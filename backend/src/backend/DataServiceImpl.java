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
		
		NumberLanguage numberLanguage = languagesMap.get(language);
		
		if (numberLanguage == null) {
			throw new IllegalArgumentException("Unsupported language: " + language);
		}

		return convert(number, numberLanguage);
	    
	}
	
	private String convert(int number, NumberLanguage lang) {

	    if (number == 0) {
	        return lang.getNumberToWordMap().get(0);
	    }

	    if (number < 0) {
	        return lang.getNegativeWord() + " " + convert(-number, lang);
	    }

	    StringBuilder sb = new StringBuilder();
	    
	    // scale values (SCALE_MAP)
	    for (var entry : lang.getScaleMap().entrySet()) {
	        int value = entry.getKey();
	        String scaleWord = entry.getValue();

	        if (number >= value) {
	            int quotient = number / value;

	            if (!(quotient == 1 && lang.omitOneBeforeScale(value))) {
	                sb.append(convert(quotient, lang)).append(" ");
	            }

	            sb.append(scaleWord);

	            number %= value;
	            if (number > 0) sb.append(" ");
	        }
	    }

	    Map<Integer, String> numberToWordMap = lang.getNumberToWordMap();

	    // direct lookup (0-19, 20, 30, 40, ..., 90)
	    if (number > 0) {
	        if (numberToWordMap.containsKey(number)) {
	            sb.append(numberToWordMap.get(number));
	        } else {
	            int tens = (number / 10) * 10;
	            int units = number % 10;

	            sb.append(numberToWordMap.get(tens));
	            if (units > 0) {
	                sb.append(" ").append(numberToWordMap.get(units));
	            }
	        }
	    }

	    return sb.toString().trim();
	}

}
