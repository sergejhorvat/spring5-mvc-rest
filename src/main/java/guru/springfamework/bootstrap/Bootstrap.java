package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
// Spring boot events - CommandLineRunner
public class Bootstrap implements CommandLineRunner {

    // Inject repository
    public CategoryRepository categoryRepository;

    // Wire-up trough constructor
    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Implement CommandLineRunner
    @Override
    public void run(String... args) throws Exception {

        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        dried.setName("Fresh");

        Category exotic = new Category();
        dried.setName("Exotic");

        Category nuts = new Category();
        dried.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data loaded = " + categoryRepository.count());
    }




}
