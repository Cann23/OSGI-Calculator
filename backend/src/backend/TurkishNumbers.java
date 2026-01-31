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

    public String toWords(int number) {
        if (number == 0) return "sıfır";
        if (number < 0) return "eksi " + toWords(-number);

        StringBuilder sb = new StringBuilder();

        if (number >= 1_000_000) {
            sb.append(toWords(number / 1_000_000)).append(" milyon");
            number %= 1_000_000;
            if (number > 0) sb.append(" ");
        }
        if (number >= 1000) {
            sb.append(toWords(number / 1000)).append(" bin");
            number %= 1000;
            if (number > 0) sb.append(" ");
        }
        if (number >= 100) {
            sb.append(toWords(number / 100)).append(" yüz");
            number %= 100;
            if (number > 0) sb.append(" ");
        }
        if (number >= 10) {
            sb.append(tens[number / 10]);
            number %= 10;
            if (number > 0) sb.append(" ");
        }
        if (number > 0) {
            sb.append(units[number]);
        }

        return sb.toString().trim();
    }

	@Override
	public Integer getValue(String textNumber) {
		return turkishMap.get(textNumber);
	}

}
