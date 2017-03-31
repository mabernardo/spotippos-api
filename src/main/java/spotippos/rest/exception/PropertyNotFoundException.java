package spotippos.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception disparada quando uma propriedade n&atilde;o &eacute; localizada.
 *
 * @author Marcio Bernardo.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(Integer id) {
        super(String.format("Propriedade n\u00e3o encontrada '%d'.", id));
    }
}
