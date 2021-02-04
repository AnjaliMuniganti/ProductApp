package com.ytp.spring.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductTests {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	@Rollback(false)
	//@Order(1)
	public void testCreateProduct() {
		Product product=new Product("motog",5000);
		Product savedProduct=productRepository.save(product);
	    assertNotNull(savedProduct);
	}
	@Test
	//@Order(2)
	public void testFindProductByName() {
		String name="Motog";
		Product product=productRepository.findByName(name);
		assertThat(product.getName()).isEqualTo(name);
	}
	@Test
	//@Order(3)
	public void testFindProductByNameNotExist() {
		String name="iphone 11";
		Product product=productRepository.findByName(name);
		//assertThat(product.getName()).isEqualTo(name);
		assertNull(product);
	}
	@Test
	@Rollback(false)
	//@Order(4)
	public void testUpdateProduct() {
		String productName="Airtel";
		Product product=new Product(productName,199);
		product.setId(8);
		
		productRepository.save(product);
		
		Product updateProduct=productRepository.findByName(productName);
		assertThat(updateProduct.getName()).isEqualTo(productName);
	}
	@Test
	//@Order(5)
	public void testListProducts() {
		List<Product> products=(List<Product>) productRepository.findAll();
		for(Product product:products) {
			System.out.println(product);
		}
		assertThat(products).size().isGreaterThan(0);
	}
	@Test
	@Rollback(false)
	//@Order(6)
	public void testDeleteProduct()
	{
		Integer id=9;
		boolean isExistBeforeDelete=productRepository.findById(id).isPresent();
		productRepository.deleteById(id);
		boolean notExistAfterDelete=productRepository.findById(id).isPresent();
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistAfterDelete);
	}
}

