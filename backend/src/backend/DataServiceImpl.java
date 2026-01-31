package backend;

import api.DataService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class DataServiceImpl implements DataService {
	
	@Activate
    void start() {
        System.out.println("Backend service activated");
    }

	@Override
	public String getData() {
		return "Please enter the values";
	}
	
	@Override
	public int convertStringToInteger(String textNumber) {
		return 5;
	}

}
