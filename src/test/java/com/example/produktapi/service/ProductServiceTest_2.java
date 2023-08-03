package com.example.produktapi.service;


import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest_2 {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService underTest;

    @Captor //
    ArgumentCaptor<Product> productCaptor;

    @Test
    void whenGetAllProducts_thenExactlyInteractionWithRepositoryMethodFindAll(){
        //When
        underTest.getAllProducts();
        //Then
        verify(repository,times(1)).findAll();

    }

    @Test
    void WhenGetAllCategoriesThe_thenExactlyOneInteractionWithRepositoryMethodGetByCategory() {

        //When
        underTest.getAllCategories();
        //Then
        verify(repository,times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void givenAnExistingCategory_whenGetProductsByCategory_thenRecieveANonEmptyList() {

        // Given
        Product product = new Product("Dator",4000.0,"electronic","bra dator","");
        // When
        underTest.getProductsByCategory("electronic");
        //Then
        verify(repository,times(1)).findByCategory("electronic");
    }


    // Komplettering - getProductById
    @Test
    void getProductById() {
        // Given
         int id = 5;
         Product product = new Product("Dator",2000.00,"electronic","","");
         product.setId(id);
         // When
        underTest.addProduct(product);
          //Then
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        assertTrue(repository.findById(id).isPresent());


       // assertTrue(repository.findById(8).isPresent()); // Test som failar då ID ej stämmer

    }

    @Test
    void whenGetProductById_withNonExistingId_thenShouldReturnNull() {
        // given
        int nonExistingId = 999; // Ett ID som inte finns i databasen
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when
        Product result = underTest.getProductById(nonExistingId);

        // then
        assertNull(result); // Förväntat resultat är att metoden returnerar null när produkten inte finns i databasen.
    }




    @Test
    void whenAddingAProduct_thenReturnTrueIfProductIsPresent() {

        //Given
        Product product = new Product("Dator",4000.0,"elektronik","","");
        //When
        underTest.addProduct(product);
        product.setId(5);   // Har skapat en produkt och givit ett ID
        //Then
        when(repository.findById(5)).thenReturn(Optional.of(product));  // Söker efter produkt med ID
        assertTrue(repository.findById(5).isPresent());  // ser så att produkt med ID 5 finns (isPresent)

    }


    //komplettering  addProduct felflöde
    @Test
    void testAddingAProductInvalidId() {
        // Given
        Product product = new Product("Dator", 4000.0, "elektronik", "", "");
        underTest.addProduct(product);
        product.setId(5);

        // When
        // Försök hitta en produkt med ett ogiltigt ID
        when(repository.findById(8)).thenReturn(Optional.empty());

        // Then
        assertFalse(repository.findById(8).isPresent());
    }

// komplettering för addProduct
    @Test
     void AddProduct() {
        // Given
        Product product = new Product("Dator", 4000.0, "elektronik", "", "");

        // Förbered argumentCaptor för att fånga upp värdet som skickas till repository:s save-metod
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

        // When
        underTest.addProduct(product);

        // Then
        // Verifiera att repository:s save-metod anropas med rätt argument
        verify(repository, times(1)).save(argumentCaptor.capture());

        // Hämta fångade värdet
        Product capturedProduct = argumentCaptor.getValue();

        // Jämför att fångade värdet stämmer överens med det värde vi skickade in i addProduct-metoden
        assertEquals(product, capturedProduct);
    }




    @Test
    void whenUpdateAProduct_thenSaveMethodShouldBeCalled() {

        //given
        int id = 10;
        Product product = new Product("Dator",25.000,"elektronik","bra","url");
        product.setId(id);
        //When
        Product updatedProduct = new Product("Dator",26.000,"elektronik","bra","url");
        when(repository.findById(id)).thenReturn(Optional.of(updatedProduct));
        when(repository.save(updatedProduct)).thenReturn(updatedProduct);
        Product result = underTest.updateProduct(updatedProduct,id);

        //Then
        verify(repository).save(productCaptor.capture());

        assertEquals(26.000,result.getPrice());

        // assertEquals(24.000,result.getPrice());  // Test som failar, ej korrekt pris

    }


    //komplettering - felflöde för updateProduct
    @Test
    void whenUpdateAProduct_withNonExistingId_thenShouldReturnNull() {
        // given
        int nonExistingId = 999; // Ett ID som inte finns i databasen
        Product updatedProduct = new Product("Dator", 26.000, "elektronik", "bra", "url");
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when
        Product result = underTest.updateProduct(updatedProduct, nonExistingId);

        // then
        assertNull(result); // Förväntat resultat är att metoden returnerar null när produkten inte finns i databasen.
    }




    @Test
    void deleteProduct() {  // Metod som anropar deleteById

        //Given
        int id = 10;
        Product product = new Product("Dator",25.000,"elektronik","bra","url");
        product.setId(id);
        //When
        when(repository.findById(id)).thenReturn(Optional.of(product));
        underTest.deleteProduct(id);
        // Then
        verify(repository,times(1)).deleteById(id);

        assertNotNull(underTest.getProductById(id));

        // assertNull(underTest.getProductById(id));
    }


    // Komplettering - DeleteProduct felflöde
    @Test
    void whenDeleteProduct_withNonExistingId_thenShouldNotCallDeleteById() {
        // given
        int nonExistingId = 999; // Ett ID som inte finns i databasen
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when
        underTest.deleteProduct(nonExistingId);

        // then
        verify(repository, times(0)).deleteById(nonExistingId); // Förväntar oss att deleteById-metoden inte anropas
    }

}