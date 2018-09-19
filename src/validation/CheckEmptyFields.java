package validation;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CheckEmptyFields {

	/**
	 * If the return are false, the field isn't empty
	 * 
	 * @param TextField tfield
	 * @return boolean
	 * @author jefter66
	 */
	public boolean isTextFieldEmpty(TextField f) {
		if (f.getText().trim().isEmpty())
			return true;
		return false;
	}

	/**
	 * If the return are false, the field isn't empty
	 * 
	 * @param PasswordField pfield
	 * @return boolean
	 * @author jefter66
	 */
	public boolean isPasswordFieldEmpty(PasswordField p) {
		if (p.getText().isEmpty())
			return true;
		return false;
	}

	public boolean isTextAreaEmpty(TextArea t) {
		if (t.getText().isEmpty())
			return true;
		return false;
	}

}
