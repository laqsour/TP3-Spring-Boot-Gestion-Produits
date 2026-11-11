package ma.oussama.gestionproduits.repository;

import ma.oussama.gestionproduits.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired ProductRepository repository;

    @Test
    void saveAndFindProduct() {
        Product saved = repository.save(new Product(null, "Test", 99.0, 3, "Produit test"));
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }

    @Test
    void searchByName() {
        repository.save(new Product(null, "Clavier", 200.0, 4, "USB"));
        assertThat(repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase("clav", "clav"))
                .hasSize(1);
    }
}
