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

    /**
     * Busca uma Propriedade por seu Id.
     *
     * @param id id da propriedade.
     * @return dados da propriedade.
     * @throws 404 caso n&atilde;o exista propriedade com o id informado.
     */
    @RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
    public Property findById(@PathVariable Integer id) {
        Property p = propertyService.findById(id);
        if (p == null) {
            throw new PropertyNotFoundException(id);
        }
        return p;
    }

    /**
     * Busca por todas as propriedades contidas nas coordenadas informadas.
     *
     * @param ax coordenada ax
     * @param ay coordenada ay
     * @param bx coordenada bx
     * @param by coordenada by
     * @return Lista das propriedades encontradas.
     */
    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public RestListResponse findByArea(@RequestParam Integer ax, @RequestParam Integer ay,
                                       @RequestParam Integer bx, @RequestParam Integer by) {

        return new RestListResponse<>(propertyService.findByArea(ax, ay, bx, by));
    }

    /**
     * Adiciona uma propriedade.
     *
     * @param property dados da propriedade.
     * @return dados na propriedade inserida.
     */
    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public ResponseEntity<Property> addProperty(@RequestBody @Valid Property property) {
        Property result = propertyService.add(property);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

}
