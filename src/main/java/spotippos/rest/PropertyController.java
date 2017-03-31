package spotippos.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spotippos.model.Property;
import spotippos.rest.exception.PropertyNotFoundException;
import spotippos.service.PropertyService;

import javax.validation.Valid;
import java.net.URI;

/**
 * Controller das opera&ccedil;&otilde;es REST das propriedades.
 *
 * @author Marcio Bernardo.
 */
@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
    public Property findById(@PathVariable Integer id) {
        Property p = propertyService.findById(id);
        if (p == null) {
            throw new PropertyNotFoundException(id);
        }
        return p;
    }

    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public RestListResponse findByArea(@RequestParam Integer ax, @RequestParam Integer ay,
                                       @RequestParam Integer bx, @RequestParam Integer by) {

        return new RestListResponse<>(propertyService.findByArea(ax, ay, bx, by));
    }

    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public ResponseEntity<Property> addProperty(@RequestBody @Valid Property property) {
        Property result = propertyService.add(property);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

}
