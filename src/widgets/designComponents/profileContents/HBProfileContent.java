package widgets.designComponents.profileContents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
import friendship.FriendshipActions;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import statics.PROFILE_IMG;

public class HBProfileContent extends HBox {
	protected VBox vbUsrIMG, vbUsrLABEL, vbUsrBUTTON;
	protected ImageView image;
	protected Label lblName;
	protected Button btnAdd;
	protected FriendshipActions fRequest;
	protected USER_PROFILE profile;
	
	
	public HBProfileContent(USER_PROFILE p) throws IOException {
		this.lblName = new Label(p.getName());
		this.profile = p;
		init(p);
	}
	public HBProfileContent(USER_REGISTRATION u) throws IOException {
		this.lblName = new Label(u.getProfile().getName());
		init(u.getProfile());
	}
	
	private void init(USER_PROFILE p) throws IOException {
		this.vbUsrIMG = new VBox();
		this.vbUsrLABEL = new VBox();
		this.vbUsrBUTTON = new VBox();
		this.image = new ImageView();
		this.btnAdd = new Button();
		this.fRequest = new FriendshipActions(p);
		
		this.getStylesheets().add(this.getClass().getResource("/css/PROFILE_CONTENT.css").toExternalForm());
		this.btnAdd.setOnAction(e -> {
			fRequest.sendFriendshipRequest();
		});
		if (p.getPhoto() == null || p.getPhoto().length == 0) {
			this.image.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		} else {
			this.image.setImage(PROFILE_IMG.getImage(p));
		}
		this.image.setFitWidth(70);
		this.image.setFitHeight(100);
		
		this.vbUsrIMG.getChildren().addAll(this.image);
		this.vbUsrIMG.setAlignment(Pos.CENTER);
		
		this.vbUsrBUTTON.getChildren().add(btnAdd);
		this.vbUsrBUTTON.setAlignment(Pos.CENTER);
		
		this.lblName.setPrefWidth(this.vbUsrLABEL.getMaxWidth());
		this.vbUsrLABEL.getChildren().addAll(this.lblName);
		this.vbUsrLABEL.setAlignment(Pos.CENTER);
		
		
		try {
			ImageView icon_add = new ImageView();
			icon_add.setImage(new Image(new FileInputStream(new File("resources/images/icons/add.png"))));
			icon_add.setFitHeight(30);
			icon_add.setFitWidth(30);
			this.btnAdd.setGraphic(icon_add);
			
	
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		btnAdd.setMaxWidth(40);
		HBox.setHgrow(vbUsrLABEL, Priority.ALWAYS);
		
		this.getChildren().addAll(vbUsrIMG, vbUsrLABEL, vbUsrBUTTON);
	}
	
	public Button getBtnAdd() {
		return btnAdd;
	}
	
	public void setBtnAdd(Button btnAdd) {
		this.btnAdd = btnAdd;
	}
	
	public ImageView getImage() {
		return image;
	}
	
	public Label getLblName() {
		return lblName;
	}
	
	
	public USER_PROFILE getProfile() {
		return profile;
	}
}