package com.webshoppe.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import com.webshoppe.ecommerce.entity.Book;
import com.webshoppe.ecommerce.entity.Flower;
import com.webshoppe.ecommerce.entity.Product;
import com.webshoppe.ecommerce.entity.Toy;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.exception.ServiceException;
import com.webshoppe.ecommerce.repository.ProductRepository;

public class ProductCatalogService {
    private ProductRepository toyRepository;

    public ProductCatalogService(ProductRepository toyRepository) {
        this.toyRepository = toyRepository;
    }
    
    public List<Product> getProductCatalog(String type) {
        try {
            return toyRepository.findAllProduct(type);
        } catch (DataAccessException e) {
            throw ServiceException.instance(e.getMessage());
        }

    }
    
    public List<Product> getProductCatalog(BigDecimal minimumPrice, BigDecimal maximumPrice, String type) {
        try {
            return toyRepository.findProductsByPrice(minimumPrice, maximumPrice, type);
        } catch (DataAccessException e) {
            throw ServiceException.instance(e.getMessage());
        }

    }

}
