package api;

public interface DataService {

	int convertStringToInteger(String textNumber, String language);
	
	String convertIntegerToString(int number, String language);
}
