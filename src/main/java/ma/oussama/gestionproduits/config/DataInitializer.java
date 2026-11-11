package ma.oussama.gestionproduits.config;

import ma.oussama.gestionproduits.entity.Product;
import ma.oussama.gestionproduits.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner loadSampleProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Product(null, "Ordinateur portable", 7500.0, 8, "PC pour développement"));
                repository.save(new Product(null, "Clavier mécanique", 650.0, 15, "Clavier USB AZERTY"));
                repository.save(new Product(null, "Écran 24 pouces", 1800.0, 5, "Écran Full HD"));
            }
        };
    }
}
