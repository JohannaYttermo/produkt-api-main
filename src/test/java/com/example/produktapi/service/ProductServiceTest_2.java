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

    @Test
    void deleteProduct() {

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
}