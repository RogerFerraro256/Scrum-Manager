package widgets.designComponents.friendshipContents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import db.pojos.USER_PROFILE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBFriendRequests extends HBox {
	private Label lblName;
	private VBox vbAlignItemsLeft, vbAlignItemsMiddle, vbAlignItemsRight;

	private ImageView image;
	private File path;
	private FileInputStream fis;

	private HBox layout;
	private Button btnAccept, btnRefuse;

	public HBFriendRequests(USER_PROFILE p) throws FileNotFoundException {
		this.image = new ImageView();

		this.lblName = new Label(p.getName());

		this.btnAccept = new Button("Aceitar");
		this.btnRefuse = new Button("Recusar");

		this.vbAlignItemsLeft = new VBox();
		this.vbAlignItemsMiddle = new VBox();
		this.vbAlignItemsRight = new VBox();

		this.image.setFitHeight(100);
		this.image.setFitWidth(100);

		
		this.path = new File("resources/images/icons/scrum_icon.png");
		this.fis = new FileInputStream(path);

		this.image.setImage(new Image(fis));

		this.vbAlignItemsLeft.getChildren().add(image);

		this.vbAlignItemsMiddle.getChildren().addAll(lblName);
		this.vbAlignItemsMiddle.setSpacing(10);
		this.vbAlignItemsMiddle.setAlignment(Pos.CENTER);

		this.vbAlignItemsRight.getChildren().addAll(btnAccept, btnRefuse);
		this.vbAlignItemsRight.setSpacing(10);

		this.layout = new HBox();

		this.layout.getChildren().addAll(vbAlignItemsLeft, vbAlignItemsMiddle);
		this.layout.getChildren().addAll(btnAccept, btnRefuse);

		this.setSpacing(10);
		this.getChildren().add(layout);
	}

	public void setEventAccept(EventHandler<ActionEvent> e) {
		this.btnAccept.setOnAction(e);
	};

	public void setEventRefuse(EventHandler<ActionEvent> e) {
		this.btnRefuse.setOnAction(e);
	};
}
