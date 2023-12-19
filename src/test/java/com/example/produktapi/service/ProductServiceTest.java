package com.example.produktapi.service;


import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

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



    //  KOMPLETTERAD
    @Test
    void getProductById_givenExistingId_whenGetProductById_thenRecieveProduct() {
        // Given
        int id = 5;
        Product product = new Product("Dator", 2000.00, "electronic", "", "");
        product.setId(id);

        // Kolla att produkten finns i databasen när findById anropas

        given(repository.findById(product.getId())).willReturn(Optional.of(product));

        // When
        Product result = underTest.getProductById(id);

        // Then

        // Lägg till en assertion för att kontrollera att rätt produkt returneras
        assertEquals(product, result);
    }



    @Test  // KOMPLETTERAD
    void whenGetProductById_withNonExistingId_thenShouldThrowException() {
        // given
        int nonExistingId = 999; // Ett ID som inte finns i databasen
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when & then

        // se till att exception-meddelande skickas vid icke existerande Id
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> underTest.getProductById(nonExistingId));
        // rätt exception-meddelande ska skrivas ut
        assertEquals(String.format("Produkt med id %d hittades inte", nonExistingId), exception.getMessage());
    }


    // KOMPLETTERING
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


//KOMPLETTERING

    @Test
    void testAddingProductWithExistingTitle() {
        // Given
        Product product = new Product("Dator", 4000.0, "elektronik", "", "");

        // Mocka scenariot där findByTitle returnerar en befintlig produkt
        given(repository.findByTitle(product.getTitle())).willReturn(Optional.of(product));

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> underTest.addProduct(product));

        // Verifiera att save inte anropas när titeln redan finns
        verify(repository, never()).save(any(Product.class));
        // Se att rätt exception meddelande skickas
        assertEquals("En produkt med titeln: Dator finns redan", exception.getMessage());
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

    }


    //KOMPLETTERAD
    @Test
    void whenUpdateAProduct_withNonExistingId_thenShouldThrowException() {
        // given
        int nonExistingId = 999; // Ett ID som inte finns i databasen
        Product updatedProduct = new Product("Dator", 26.000, "elektronik", "bra", "url");
        given(repository.findById(nonExistingId)).willReturn(Optional.empty());

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> underTest.updateProduct(updatedProduct, nonExistingId));
        assertEquals(String.format("Produkt med id %d hittades inte",nonExistingId), exception.getMessage());
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

    }

    // KOMPLETTERING

    @Test
    void whenDeleteProduct_withNonExistingId_thenShouldThrowException() {
        // given
        int nonExistingId = 999; // Ett ID som inte finns i databasen
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> underTest.deleteProduct(nonExistingId));

        verify(repository, never()).deleteById(any());
        assertEquals(String.format("Produkt med id %d hittades inte",nonExistingId), exception.getMessage());
    }

}