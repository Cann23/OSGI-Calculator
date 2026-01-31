package backend;

import java.util.Map;

import api.NumberLanguage;

public class TurkishNumbers implements NumberLanguage {
	
	private static final Map<String, Integer> turkishMap = Map.ofEntries(
	        Map.entry("sıfır", 0),
	        Map.entry("bir", 1),
	        Map.entry("iki", 2),
	        Map.entry("üç", 3),
	        Map.entry("dört", 4),
	        Map.entry("beş", 5),
	        Map.entry("altı", 6),
	        Map.entry("yedi", 7),
	        Map.entry("sekiz", 8),
	        Map.entry("dokuz", 9),
	        Map.entry("on", 10),
	        Map.entry("yirmi", 20),
	        Map.entry("otuz", 30),
	        Map.entry("kırk", 40),
	        Map.entry("elli", 50),
	        Map.entry("altmış", 60),
	        Map.entry("yetmiş", 70),
	        Map.entry("seksen", 80),
	        Map.entry("doksan", 90),
	        Map.entry("yüz", 100),
	        Map.entry("bin", 1000),
	        Map.entry("milyon", 1_000_000)
	    );
	
	private static final String[] units = {
        "sıfır","bir","iki","üç","dört","beş","altı","yedi","sekiz","dokuz"
    };

    private static final String[] tens = {
        "", "on","yirmi","otuz","kırk","elli",
        "altmış","yetmiş","seksen","doksan"
    };

    public String toString(int number) {
    	boolean negative = false;
    	String result;
    	
    	if (number == 0) {
    		return String.valueOf(number);
    	}
    	
    	if (number < 0) {
    		negative = true;
    		number *= -1;
    	}
    	
        if (number < 10) {
        	result =  units[number];
        }
        if (number < 100) {
        	result =  tens[number / 10] +
                   (number % 10 != 0 ? " " + units[number % 10] : "");
        } else {
        	result = String.valueOf(number);
        }
        
        return negative ? "eksi " + result : result;
    }

	@Override
	public Integer getValue(String textNumber) {
		return turkishMap.get(textNumber);
	}

}
