package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired  // Liten konstruktor av vår Product
    private ProductRepository underTest;

    @Test
    void testingOurRepository() {
        List<Product> product = underTest.findAll();

        assertFalse(product.isEmpty());

       // assertTrue(product.isEmpty());

    }

    @Test
    void findByCategory() {
        // Given
        Product product = new Product("",25.00,"Woman","","");
        underTest.save(product);
        //When
        List<Product> productList = underTest.findByCategory("Woman");
        //Then
        assertFalse(productList.isEmpty());
        assertEquals("Woman",productList.get(0).getCategory());

          // assertEquals("woman",productList.get(0).getCategory());  // Test som failar. Case sensitive

    }

    @Test
    void findByNonExistingCategory() {

        //Given
        underTest.deleteAll();
        //When
        List<Product> productList = underTest.findByCategory("Woman");
        //Then
        assertTrue(productList.isEmpty());

       // assertFalse(productList.isEmpty());  // Test som failar. Förväntar sig att isEmpty inte stämmer

    }

    @Test
    void findByTitle() {

        //Given
        Product product = new Product("Dator",2000.0,"Elektronik",
                 "Används för Java","URLsträng");
         underTest.save(product);

         //When
        Optional<Product> result = underTest.findByTitle(product.getTitle());

        //Then
        Assertions.assertAll(
                ()-> assertTrue(result.isPresent()),
                () -> assertEquals(result.get().getTitle(),"Dator")
        );

    }

    @Test
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional(){
        //Given
        String title = "En titel som absolut inte finns";

        //When
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        //Then
        Assertions.assertAll(
                ()-> assertFalse(optionalProduct.isPresent()),
                ()-> assertTrue(optionalProduct.isEmpty())
        );
    }

    @Test
    void findAllCategories () {
        // When
        List<String> productList = underTest.findAllCategories();

        // Then
        assertFalse(productList.isEmpty());
        assertEquals(productList.size(),4);

        //  assertEquals(productList.size(),5); // Test som failar. Fel antal kategorier(finns 4).
    }

    @Test
    void whenFindAllCategories_givenListOfValidCategories() {

        //Given
        List<String> validCategories = new ArrayList<>(Arrays.asList("electronics","jewelery","men's clothing","women's clothing"));
        validCategories.stream().distinct().collect(Collectors.toList());
        //When
        List<String> productList = underTest.findAllCategories();
        //Then
        assertFalse(productList.size()>4);
        assertEquals(validCategories,productList);

    }

}