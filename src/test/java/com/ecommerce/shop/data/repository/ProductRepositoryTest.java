package com.ecommerce.shop.data.repository;

import com.ecommerce.shop.data.model.Currency;
import com.ecommerce.shop.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepositoryImpl;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void createProductTest() {
        Product product = new Product();
        product.setName("Luxury sofa");
        product.setPrice(4000.0);
        product.setCurrency(Currency.NGN);
        product.setDetails("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        assertThat(product).isNotNull();
        assertThat(product.getId()).isNull();
        log.info("Product before saving -> {}", product);
        productRepositoryImpl.save(product);
        assertThat(product.getId()).isNotNull();
        log.info("Product after saving -> {}", product);
    }

    @Test
    @Transactional
    public void whenFindAllProductIsCalledThenProductListIsReturnedTest() {
        List<Product> products = productRepositoryImpl.findAll();
        assertThat(products).hasSize(4);
        log.info("Product returned from database ->{}", products);
    }

    @Test
    public void findExistingProductById() {
         Product existingProduct = productRepositoryImpl.findById(110L).orElse(null);
         assertThat(existingProduct).isNotNull();
         log.info("Product -> {}", existingProduct);
    }

    @Test
    public void deleteExistingProductById() {
        assertThat(productRepositoryImpl.findById(110L).orElse(null)).isNotNull();
        productRepositoryImpl.deleteById(110L);
        assertThat(productRepositoryImpl.findById(110L).orElse(null)).isNull();
    }
}