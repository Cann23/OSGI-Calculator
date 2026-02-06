package api;

import java.util.Map;
import java.util.NavigableMap;

public interface NumberLanguage {

	Integer getValue(String textNumber);
	
	Map<Integer, String> getNumberToWordMap();
    NavigableMap<Integer, String> getScaleMap();
	// grammer rule
	boolean omitOneBeforeScale(int scale);
	
	String getNegativeWord();
}
