package backend;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import api.NumberLanguage;

public class EnglishNumbers implements NumberLanguage {
	
	private static final Map<String, Integer> englishWordToNumberMap = Map.ofEntries(
        Map.entry("zero", 0),
        Map.entry("one", 1),
        Map.entry("two", 2),
        Map.entry("three", 3),
        Map.entry("four", 4),
        Map.entry("five", 5),
        Map.entry("six", 6),
        Map.entry("seven", 7),
        Map.entry("eight", 8),
        Map.entry("nine", 9),
        Map.entry("ten", 10),
        Map.entry("eleven", 11),
        Map.entry("twelve", 12),
        Map.entry("thirteen", 13),
        Map.entry("fourteen", 14),
        Map.entry("fifteen", 15),
        Map.entry("sixteen", 16),
        Map.entry("seventeen", 17),
        Map.entry("eighteen", 18),
        Map.entry("nineteen", 19),
        Map.entry("twenty", 20),
        Map.entry("thirty", 30),
        Map.entry("forty", 40),
        Map.entry("fifty", 50),
        Map.entry("sixty", 60),
        Map.entry("seventy", 70),
        Map.entry("eighty", 80),
        Map.entry("ninety", 90),
        Map.entry("hundred", 100),
        Map.entry("thousand", 1000),
        Map.entry("million", 1_000_000)
    );
	
	private static final String[] units = {
        "zero","one","two","three","four","five","six","seven","eight","nine"
    };

    private static final String[] teens = {
        "ten","eleven","twelve","thirteen","fourteen","fifteen",
        "sixteen","seventeen","eighteen","nineteen"
    };

    private static final String[] tens = {
        "", "", "twenty","thirty","forty","fifty",
        "sixty","seventy","eighty","ninety"
    };
    
    private static final NavigableMap<Integer, String> SCALE_MAP =
        new TreeMap<>(Map.of(
            1_000_000, "million",
            1_000, "thousand",
            100, "hundred"
        )).descendingMap();
    
    
    // int -> string
    private static final Map<Integer, String> numberToWordMap =
		englishWordToNumberMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));


    public String toWords(int number) {
    	
        if (number == 0) return "zero";
        if (number < 0) return "minus " + toWords(-number);

        StringBuilder sb = new StringBuilder();
        
        // scale values (SCALE_MAP)
        for (var entry : SCALE_MAP.entrySet()) {
            int value = entry.getKey();
            String word = entry.getValue();

            if (number >= value) {
                sb.append(toWords(number / value))
                  .append(" ")
                  .append(word);

                number %= value;
                if (number > 0) sb.append(" ");
            }
        }
        
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

	@Override
	public Integer getValue(String textNumber) {
		return englishWordToNumberMap.get(textNumber);
	}

}
