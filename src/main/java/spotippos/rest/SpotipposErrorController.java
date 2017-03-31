package spotippos.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller de erros customizada.
 * Ideia original de acohen.
 *
 * @author Marcio Bernardo
 */
@RestController
@RequestMapping("/error")
public class SpotipposErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public SpotipposErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public Map<String, Object> error(HttpServletRequest aRequest){
        Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
        String trace = (String) body.get("trace");
        if (trace != null) {
            String[] lines = trace.split("\n\t");
            body.put("trace", lines);
        }
        return body;
    }

    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        return parameter != null && !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}