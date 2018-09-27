package view.popoups;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import validation.CheckEmptyFields;
import widgets.designComponents.HBProfileContentForgotPassword;

public class ForgotPasswordPOPOUP extends StandartLayoutPOPOUP {

	private Label lblQuestion, lblEmailOrUsername, lblNewPasswrd, lblConfirmPassword;
	private TextField txtEmailUserName, txtAnswer;
	private PasswordField newPassword, passwordConfirmation;
	private EntityManager em;
	private Query q;
	private Button btnCancel, btnEmail, btnAnswer, btnPasswords;
	private HBox hbButtons;
	private ToggleButton tbYes, tbNot;
	private HBProfileContentForgotPassword hbp;
	private ToggleGroup tbGroup;
	private CheckEmptyFields checkFields;
	private USER_PROFILE p;
	private USER_REGISTRATION u;

	public ForgotPasswordPOPOUP(Window owner) {
		super(owner);
		init(owner);
	}

	public void init(Window owner) {

		this.checkFields = new CheckEmptyFields();

		this.lblQuestion = new Label("Este(ª) é voce?");

		this.lblEmailOrUsername = new Label("Digite seu email ou nome de usuario ");
		this.txtEmailUserName = new TextField();
		this.txtAnswer = new TextField();

		this.newPassword = new PasswordField();
		this.lblNewPasswrd = new Label("Digite uma nova senha: ");

		this.lblConfirmPassword = new Label("Confirmação de senha: ");
		this.passwordConfirmation = new PasswordField();

		this.hbButtons = new HBox();

		this.tbNot = new ToggleButton("Não");
		this.tbYes = new ToggleButton("Sim");

		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setOnAction(e -> {
			this.close();
		});

		this.tbGroup = new ToggleGroup();
		this.tbNot.setToggleGroup(tbGroup);
		this.tbYes.setToggleGroup(tbGroup);

		this.btnPasswords = new Button("Enviar");
		this.btnAnswer = new Button("Enviar");
		this.btnEmail = new Button("Enviar");

		btnEmail.setOnAction(e -> {
			findUser();
		});

		this.hbButtons.setAlignment(Pos.CENTER);
		this.hbButtons.getChildren().clear();
		this.hbButtons.getChildren().addAll(this.btnCancel, this.btnEmail);

		this.hbButtons.setSpacing(10);
		valideEmail();

		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);

	}

	public void valideEmail() {
		this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, hbButtons);

		this.btnEmail.setOnMouseClicked(e -> {
			if (checkFields.isTextFieldEmpty(txtEmailUserName)) {
				this.layout.getChildren().clear();
				this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.hbButtons);
				this.layout.getChildren().add(new Label("Informe o seu email"));
				return;
			}
		});
		this.txtEmailUserName.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (checkFields.isTextFieldEmpty(txtEmailUserName)) {
					this.layout.getChildren().clear();
					this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.hbButtons);
					this.layout.getChildren().add(new Label("Informe o seu email"));
					return;
				}
				findUser();
			}
		});
		this.txtEmailUserName.setOnMouseClicked(e -> {
			this.layout.getChildren().clear();
			this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.hbButtons);
		});
	}

	private void findUser() {
		if (this.em == null)
			this.em = Database.createEntityManager();

		this.q = em.createQuery("from UserRegistration where email =:email or userName =:username");
		this.q.setParameter("email", txtEmailUserName.getText());
		this.q.setParameter("username", txtEmailUserName.getText());

		if (this.q.getResultList().isEmpty()) {
			this.layout.getChildren().add(new Label("Nenhum usuario encontrado"));
			return;
		}
		if (!this.q.getResultList().isEmpty()) {
			this.layout.getChildren().clear();

			this.u = (USER_REGISTRATION) this.q.getResultList().get(0);
			this.p = u.getProfile();
			try {
				this.hbp = new HBProfileContentForgotPassword(p);

				this.hbButtons.getChildren().clear();
				this.hbButtons.getChildren().addAll(tbNot, tbYes);
				this.layout.getChildren().addAll(hbp, hbButtons);
				this.tbGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> old_value, Toggle toggle, Toggle new_toggle) {
						if (tbGroup.getSelectedToggle() == tbNot) {
						}
						if (tbGroup.getSelectedToggle() == tbYes) {
							securityQuestion();
						}
					}
				});
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	private void securityQuestion() {
		this.layout.getChildren().clear();
		this.lblQuestion.setText(this.u.getSecurityQuestion());

		this.hbButtons.getChildren().clear();
		this.hbButtons.getChildren().addAll(this.btnCancel, this.btnAnswer);

		this.layout.getChildren().addAll(this.hbp, this.lblQuestion, this.txtAnswer, this.hbButtons);
		this.txtAnswer.setOnMouseClicked(e -> {
			this.layout.getChildren().clear();
			this.hbButtons.getChildren().clear();
			this.hbButtons.getChildren().addAll(this.btnCancel, this.btnAnswer);
			this.lblQuestion.setText(this.u.getSecurityQuestion());
			this.layout.getChildren().addAll(this.hbp, this.lblQuestion, this.txtAnswer, this.btnCancel, this.btnAnswer);

		});

		this.txtAnswer.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (checkFields.isTextFieldEmpty(this.txtAnswer)) {
					this.layout.getChildren().clear();
					this.lblQuestion.setText(this.u.getSecurityQuestion());
					this.layout.getChildren().addAll(this.hbp, this.lblQuestion, this.txtAnswer, this.btnCancel, this.btnAnswer);
					this.layout.getChildren().add(new Label("Digite a resposta"));
					return;
				}
				if (valideAnswer()) {
					setNewPassword();
					return;
				}
			}

		});
		this.btnAnswer.setOnAction(e -> {
			if (checkFields.isTextFieldEmpty(this.txtAnswer)) {
				this.layout.getChildren().clear();
				this.lblQuestion.setText(this.u.getSecurityQuestion());
				this.layout.getChildren().addAll(this.hbp, this.lblQuestion, this.txtAnswer, this.btnAnswer);
				this.layout.getChildren().add(new Label("Digite a resposta"));
				return;
			}

			if (valideAnswer()) {
				setNewPassword();
				return;
			}

		});

	}

	private boolean valideAnswer() {
		if (this.txtAnswer.getText().equals(this.u.getSecurityAnswer().toString()))
			return true;
		return false;

	}

	private void setNewPassword() {
		this.layout.getChildren().clear();
		this.layout.getChildren().addAll(this.hbp, this.lblNewPasswrd, this.newPassword, this.lblConfirmPassword, this.passwordConfirmation, this.btnPasswords);

		this.btnPasswords.setOnAction(e -> {
			if (checkFields.isPasswordFieldEmpty(newPassword) || checkFields.isPasswordFieldEmpty(passwordConfirmation)) {
				this.layout.getChildren().add(new Label("Informe uma nova senha"));
				return;
			}
			if (!newPassword.getText().equals(passwordConfirmation.getText())) {
				this.layout.getChildren().add(new Label("Senhas não correspondem"));
				return;
			}
			if (newPassword.getText().length() < 8 || passwordConfirmation.getText().length() < 8) {
				this.layout.getChildren().add(new Label("A senha deve ter no minimo 8 caracteres"));
				return;
			}
			USER_REGISTRATION update = this.u;

			update.setPassword(newPassword.getText());

			this.em.getTransaction().begin();
			this.em.merge(update);
			this.em.getTransaction().commit();
			this.em.clear();

			this.layout.getChildren().add(new Label("Senha alterada"));

		});
		this.passwordConfirmation.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (checkFields.isPasswordFieldEmpty(newPassword) || checkFields.isPasswordFieldEmpty(passwordConfirmation)) {
					this.layout.getChildren().add(new Label("Informe uma nova senha"));
					return;
				}
			}
		});

		this.passwordConfirmation.setOnMouseClicked(e -> {
			drawComponentsNewPassword();
		});
		this.newPassword.setOnMouseClicked(e -> {
			drawComponentsNewPassword();
		});

		this.layout.setOnMouseClicked(e -> {
			drawComponentsNewPassword();
		});

	}

	private void drawComponentsNewPassword() {
		this.layout.getChildren().clear();
		this.layout.getChildren().addAll(this.hbp, this.lblNewPasswrd, this.newPassword, this.lblConfirmPassword, this.passwordConfirmation, this.btnPasswords);
	}

}