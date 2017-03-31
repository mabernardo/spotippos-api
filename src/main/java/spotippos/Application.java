package spotippos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Classe com configura&ccedil;&otilde;es e ponto de partida da aplica&ccedil;&atilde;o.
 *
 * @author Marcio Bernardo
 */
@SpringBootApplication
@PropertySource("classpath:config.properties")
public class Application {

    /**
     * Ponto de partida da aplica&ccedil;&atilde;o.
     *
     * @param args argumentos da linha de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
