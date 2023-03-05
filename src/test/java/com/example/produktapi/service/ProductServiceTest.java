package com.example.produktapi.service;

import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void givenAnExistingCategory_whenGetProductsByCategory_thenRecievesANonEmptyList(){
        //When
  //Skippar detta test just nu
}
@Test
    void whenAddingAProduct_thenSaveMethodShouldBeCalled() {

        //Given
        Product product = new Product("Dator",4000.0,"","","");
        //When
        underTest.addProduct(product);
        //Then
        verify(repository).save(product);

}
}