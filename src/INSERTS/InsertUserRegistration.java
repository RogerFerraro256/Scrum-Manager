
package INSERTS;

import java.util.ArrayList;import org.hibernate.sql.Insert;

import DB.Factory;
import hibernatebook.annotations.Profile;
import hibernatebook.annotations.UserRegistration;
import main.Window;

public class InsertUserRegistration {
	
		private static  UserRegistration registration;
		private static Profile userProfile;
		
		public static void registration (ArrayList<String> data) {
			if(registration==null) registration = new UserRegistration();
			if(userProfile==null)userProfile=new Profile();
			
			userProfile.setName(data.get(0));
			registration.setEmail(data.get(1));
			registration.setUserName(data.get(2));
			registration.setPassword(data.get(3));
			registration.setProfile(userProfile);
			
			if(Factory.insert(registration)) System.out.println("foi");;
			
		}
	
}

