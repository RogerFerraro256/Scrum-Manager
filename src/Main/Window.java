package Main;
import java.sql.SQLException;

import Database.DbLoadProfileHome;
import Scenes.HomePageScene;
import Scenes.LoginScene;
import Scenes.newProjectScene;
import javafx.stage.Stage;

public class Window extends Stage {

	
	public static Stage mainStage;
	
 	public  Window() throws ClassNotFoundException, SQLException {
 		

 		
 		Window.mainStage = this;
// 		mainStage.setScene(new newProjectScene());
 		mainStage.setScene(new LoginScene());
// 		Window.mainStage.setScene(new HomePageScene(new LoadProfileHome("jefter66")));
 		this.show();
	}
}