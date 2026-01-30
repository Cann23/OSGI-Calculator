package frontend;

import api.DataService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.swing.SwingUtilities;

@Component(immediate = true)
public class UI {

	@Reference
    DataService dataService;
	
	@Activate
	void start() {
		System.out.println("Frontend activated");
		
		SwingUtilities.invokeLater(() -> {
			new MainWindow(dataService.getData()).setVisible(true);
		});
	}
	
}
