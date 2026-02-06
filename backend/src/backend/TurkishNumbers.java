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

    @Override
    public Map<Integer, String> getNumberToWordMap() {
        return numberToWord;
    }

    @Override
    public NavigableMap<Integer, String> getScaleMap() {
        return SCALE_MAP;
    }
    
    @Override
    public boolean omitOneBeforeScale(int scale) {
        return scale == 100 || scale == 1000;
    }

	@Override
	public Integer getValue(String textNumber) {
		return turkishWordToNumberMap.get(textNumber);
	}
	
	@Override
	public String getNegativeWord() {
	    return "eksi";
	}


}
