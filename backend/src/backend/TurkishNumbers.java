package backend;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import api.NumberLanguage;

public class TurkishNumbers implements NumberLanguage {
	
	private static final Map<String, Integer> turkishWordToNumberMap = Map.ofEntries(
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
    
    // int -> word
    private static final Map<Integer, String> numberToWord =
		turkishWordToNumberMap.entrySet()
              .stream()
              .collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));
    
    private static final NavigableMap<Integer, String> SCALE_MAP =
            new TreeMap<>(Map.of(
                    1_000_000, "milyon",
                    1_000, "bin",
                    100, "yüz"
            )).descendingMap();

    public String toWords(int number) {
        if (number == 0) return "sıfır";
        if (number < 0) return "eksi " + toWords(-number);

        StringBuilder sb = new StringBuilder();

        // scale values (SCALE_MAP)
        for (var entry : SCALE_MAP.entrySet()) {
            int value = entry.getKey();
            String scaleWord = entry.getValue();

            if (number >= value) {
                int quotient = number / value;

                // not to say bir yüz, bir bin
                if (!(quotient == 1 && value != 1_000_000)) {
                    sb.append(toWords(quotient)).append(" ");
                }

                sb.append(scaleWord);
                
                number %= value;
                if (number > 0) sb.append(" ");
            }
        }

        // direct lookup (0-19, 20, 30, 40, ..., 90)
        if (number > 0) {
            if (numberToWord.containsKey(number)) {
                sb.append(numberToWord.get(number));
            } else {
                int tens = (number / 10) * 10;
                int units = number % 10;

                sb.append(numberToWord.get(tens));
                if (units > 0) {
                    sb.append(" ").append(numberToWord.get(units));
                }
            }
        }

        return sb.toString().trim();
    }

	@Override
	public Integer getValue(String textNumber) {
		return turkishWordToNumberMap.get(textNumber);
	}

}
