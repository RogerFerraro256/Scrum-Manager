package friendship;

import java.io.IOException;
import java.util.ArrayList;

import statics.GENERAL_STORE;
import widgets.designComponents.profileContents.HBProfileContent;

public class SearchFriend {
	private ArrayList<HBProfileContent> returnFromSearch;

	public SearchFriend() {
		this.returnFromSearch = new ArrayList<HBProfileContent>();
	}

	public void loadOptions(String str) throws IOException {
		
		this.returnFromSearch.clear();
		for ( int i = 0  ; i < GENERAL_STORE.listUsers().size() ; i ++)  {
			
			boolean x = GENERAL_STORE.listUsers().get(i).getName().endsWith(str);
			boolean y = GENERAL_STORE.listUsers().get(i).getName().startsWith(str);
			
			if ( x || y ) { 
				this.returnFromSearch.add(new HBProfileContent(GENERAL_STORE.listUsers().get(i)));
			}
		}
	}
	public ArrayList<HBProfileContent> getResults() {
		return this.returnFromSearch;
	}
}














