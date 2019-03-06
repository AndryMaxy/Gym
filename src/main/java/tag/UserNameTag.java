package tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserNameTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(UserNameTag.class.getSimpleName());
    private static final long serialVersionUID = -7234209293530570441L;

    private String name;
    private String surname;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.print(name + " " + surname);
        } catch (IOException e) {
            LOGGER.error("Can't write data in jsp");
        }
        return SKIP_BODY;
    }
}
