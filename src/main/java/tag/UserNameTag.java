package tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class creates a jsp tag that displays the user's first and last name.
 *
 * @author Andrey Akulich
 * @see TagSupport
 */
public class UserNameTag extends TagSupport {

    /**
     * A constant has an instance of {@link Logger} to logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(UserNameTag.class.getSimpleName());

    /**
     * SerialVersionUID is used for interoperability.
     */
    private static final long serialVersionUID = -7234209293530570441L;

    /**
     * A user's first name
     */
    private String name;

    /**
     * A user's last name
     */
    private String surname;

    /**
     * Sets a user's first name.
     * @param name the user's first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a user's last name.
     * @param surname the user's last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Writes the user's first and last name to jsp.
     * @return a constant that says what to do next
     * @throws JspException if method has input or output error on jsp
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.print(name + " " + surname);
        } catch (IOException e) {
            LOGGER.error("Can't write data in jsp");
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
