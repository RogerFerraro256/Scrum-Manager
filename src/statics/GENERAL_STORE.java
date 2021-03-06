package statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.main.Window;
import db.pojos.PROJECT;
import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.PROJECT_SESSION;
import project.QUERY_PROJECT;
import view.scenes.ProjectScene;
import widgets.designComponents.projectContents.HBProjectComponent;

public class GENERAL_STORE {

	/*
	 * home page stuffs
	 */
	private static Label lblName, lblUserName;
	private static ImageView imgProfile;
	private static ArrayList<PROJECT> memberInProjects;
	private static ArrayList<PROJECT> myProjects;
	private static ArrayList<USER_PROFILE> usersList;
	private static VBox vbProjectComponent;

	public static void setComponentsHOME(Label lblName, Label lblUserName, ImageView imgProfile,
			Button btnFriendRequest, Button btnFriendsList, Button btnProjectInvitation, VBox vbProjectComponents) { // ,VBox
																														// vbCurrentProjects,VBox
																														// vbFinishedProjects)
																														// {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.imgProfile = imgProfile;
		GENERAL_STORE.vbProjectComponent = vbProjectComponents;
	}

	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		imgProfile.setImage(PROFILE_IMG.loadImage());
		updateListProjects();
	}

	private static void updateListProjects() {

		GENERAL_STORE.vbProjectComponent.getChildren().clear();
		for (HBProjectComponent component : projectComponent()) {
			GENERAL_STORE.vbProjectComponent.getChildren().add(component);
		}
	}

	public static ArrayList<HBProjectComponent> projectComponent() {
		loadProjects();
		ArrayList<HBProjectComponent> list = new ArrayList<HBProjectComponent>();
		for (PROJECT p : allProjects()) {
			list.add(new HBProjectComponent(p, Pos.CENTER_LEFT));
		}
		for (int i = 0; i < list.size(); i++) {
			PROJECT p = list.get(i).getProject();
			list.get(i).setOnClick(e -> {
				Window.mainStage.setScene(new ProjectScene(p));
			});
		}
		return list;
	}

	public static void loadProjects() {
		myProjects = QUERY_PROJECT.USER_PROJECTS();
		memberInProjects = QUERY_PROJECT.USER_PROJECTS_MEMBER();
	}

	private static ArrayList<PROJECT> allProjects() {

		ArrayList<PROJECT> p = new ArrayList<PROJECT>();

		for (PROJECT project : myProjects) {
			p.add(project);
		}
		for (PROJECT project : memberInProjects) {
			p.add(project);
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<USER_PROFILE> searchUsers() {

		GENERAL_STORE.usersList = new ArrayList<USER_PROFILE>();
		List<?> userlist = DB_OPERATION.QUERY("FROM USER_PROFILE WHERE PROF_COD <> :COD", "COD",
				SESSION.getProfileLogged().getCod());

		List<USER_PROFILE> friendslist = QUERYs_FRIENDSHIP.friendsList();

		if (!friendslist.isEmpty() && !userlist.isEmpty()) {
			for (int i = 0; i < friendslist.size(); i++) {
				USER_PROFILE p = (USER_PROFILE) userlist.get(i);
				if (p.getCod() == friendslist.get(i).getCod()) {
					userlist.remove(i);
				}
			}
		}
		if (!userlist.isEmpty())
			for (USER_PROFILE up : (ArrayList<USER_PROFILE>) userlist) {
				GENERAL_STORE.usersList.add(up);
			}
		return GENERAL_STORE.usersList;
	}

	public static ArrayList<USER_PROFILE> listUsers() {
		GENERAL_STORE.usersList = GENERAL_STORE.usersList == null ? searchUsers() : usersList;
		return GENERAL_STORE.usersList;
	}
}
