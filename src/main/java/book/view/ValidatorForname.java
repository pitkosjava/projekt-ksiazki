/**
 * 
 */
package book.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("book.view.ValidatorForname")
public class ValidatorForname implements Validator{
	
	private static final  String FORNAME_PATTERN="\\p{IsLatin}{3,20}";
	private Pattern pattern;
	private Matcher matcher;
	
	public ValidatorForname(){
		pattern=Pattern.compile(FORNAME_PATTERN);
	}

	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("validate error.'", "Błedne dane ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
	}
}
