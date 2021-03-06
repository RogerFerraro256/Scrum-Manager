package view.popoups;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.criterion.ProjectionList;

import db.pojos.PROJECT;
import db.pojos.PROJECT_SPRINT;
import db.pojos.PROJECT_TASK;
import db.pojos.USER_PROFILE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.PROJECT_SESSION;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.ENUMS.SPRINT_PROJECT;
import view.scenes.ProjectScene;
import statics.SESSION;
import widgets.designComponents.projectContents.ScrumFrame;

public class DefinedSprintPOPOUP extends Stage{

	private TextField txtTittle;
	private TextArea txtTask;
	private ToggleButton t1, t3, t5, t8;
	private ToggleGroup group;
	private HBox hToggle;
	private HBox hButtons;
	private Button btnCancel, btnConfirmation;
	private Label lblPontuation;

	private int pontuation = 0;
	
	private VBox content;
	

	private ScrumFrame sprintColumns;

	public DefinedSprintPOPOUP(USER_PROFILE p, PROJECT pj) {
		proj=pj;
		init();
	}

	public DefinedSprintPOPOUP(PROJECT_SPRINT task, ScrumFrame f, PROJECT pj) {
		this.proj=pj;
		init();
		
		sprintColumns = f;
		this.txtTittle.setText(task.getSprintTitle());
		this.txtTask.setText(task.getSprint());
		switch (task.getSprintPontuation()) {
		case 1:
			t1.setSelected(true);
			break;
		case 3:
			t3.setSelected(true);
			break;
		case 5:
			t5.setSelected(true);
			break;
		case 7:
			t8.setSelected(true);
			break;
		default:
			break;
		}
		blockContents();
	}

	PROJECT proj;
	
	public DefinedSprintPOPOUP(PROJECT_TASK task, USER_PROFILE p, PROJECT pj) {
		proj=pj;
		init();
		this.txtTittle.setText(task.getTaskTitle());
		this.txtTask.setText(task.getTask());
		switch (task.getTaskPontuation()) {
		case 1:
			t1.setSelected(true);
			break;
		case 3:
			t3.setSelected(true);
			break;
		case 5:
			t5.setSelected(true);
			break;
		case 8:
			t8.setSelected(true);
			break;
		default:
			break;
		}
		blockContents();
	}

	public DefinedSprintPOPOUP(PROJECT_TASK task) {
		init();
	}

	private void blockContents() {
		txtTittle.setEditable(true);
		txtTask.setEditable(true);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				newValue = newValue == null ? oldValue : newValue;
				newValue.setSelected(true);
			}
		});
		;
	}

	private void init() {

		this.initStyle(StageStyle.UNDECORATED);
		this.setWidth(400);

		this.txtTittle = new TextField();
		txtTittle.setAlignment(Pos.CENTER);
		txtTittle.setPromptText("Titulo do Sprint");

		this.txtTask = new TextArea();
		txtTask.setWrapText(true);
		txtTask.setPromptText("Digite o que você deseja fazer nesse sprint");

		t1 = new ToggleButton("1");
		t3 = new ToggleButton("3");
		t5 = new ToggleButton("5");
		t8 = new ToggleButton("8");

		group = new ToggleGroup();

		t1.setToggleGroup(group);
		t3.setToggleGroup(group);
		t5.setToggleGroup(group);
		t8.setToggleGroup(group);

		t1.setSelected(true);

		this.lblPontuation = new Label("Defina a pontuação: ");

		hToggle = new HBox();
		hToggle.getChildren().addAll(lblPontuation, t1, t3, t5, t8);
		hToggle.setAlignment(Pos.CENTER);

		content = new VBox();

		this.txtTittle.setOnMouseClicked(e -> {

		});

		content.getChildren().addAll(txtTittle, txtTask, hToggle);

		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setId("back");
		this.btnCancel.setOnAction(e -> {
			this.close();
		});
		this.btnConfirmation = new Button("Fazer Sprint");
		this.btnConfirmation.setOnAction(e -> {
			
			
			if(t1.isSelected()==true) {
				pontuation=1;
			}else if(t3.isSelected()==true) {
				pontuation=3;
			}else if(t5.isSelected()==true) {
				pontuation=5;
			}else if(t8.isSelected()==true) {
				pontuation=8;
			}
			

						
			
			
			PROJECT_SPRINT sprint = new PROJECT_SPRINT();
			sprint.setSprint(txtTask.getText());
			sprint.setProjectSprintCod(proj.getProjectCod());
			sprint.setSprintTitle(txtTittle.getText());
			sprint.setSprintPontuation(pontuation);
			sprint.setSprintStatus(ENUMS.SPRINT_PROJECT.DOING);
						
			DB_OPERATION.PERSIST(sprint);
			this.close();
			
			
		});
		this.hButtons = new HBox();

		hButtons.getChildren().addAll(btnCancel, btnConfirmation);
		hButtons.setAlignment(Pos.CENTER);
		hButtons.setSpacing(20);

		content.getChildren().add(hButtons);
		content.setSpacing(10);

		Scene scene = new Scene(content);

		scene.getStylesheets().add(this.getClass().getResource("/css/CREATE_STORY_COMPONENT.css").toExternalForm());

		this.setScene(scene);
		this.show();
	}

	public void setCancelAction(EventHandler<ActionEvent> e) {
		this.btnCancel.setOnAction(e);
	}
}