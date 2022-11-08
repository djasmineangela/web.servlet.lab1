package com.webshoppe.ecommerce.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webshoppe.ecommerce.entity.Book;
import com.webshoppe.ecommerce.entity.Flower;
import com.webshoppe.ecommerce.entity.Product;
import com.webshoppe.ecommerce.entity.Toy;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.jdbc.JdbcConnectionManager;

public class ProductRepository {
	private final static String TOY_FIND_ALL = "SELECT tid, tname, tdesc, tprice FROM ToysDetails";
	private final static String BOOK_FIND_ALL = "SELECT bid, title, bookdesc, aid, bookprice FROM BooksDetails";
	private final static String FLOWER_FIND_ALL = "SELECT fid, fname, fdesc, fprice FROM flowersDetails";
	private final static String TOY_FIND_BY_PRICE = TOY_FIND_ALL + " WHERE tprice BETWEEN ? AND ?";
	private final static String BOOK_FIND_BY_PRICE = BOOK_FIND_ALL + " WHERE bookprice BETWEEN ? AND ?";
	private final static String FLOWER_FIND_BY_PRICE = FLOWER_FIND_ALL + " WHERE fprice BETWEEN ? AND ?";

	private JdbcConnectionManager jdbcConnectionManager;

	public ProductRepository(JdbcConnectionManager jdbcConnectionManager) {
		this.jdbcConnectionManager = jdbcConnectionManager;
	}

	public List<Product> findAllProduct(String type) {
		try {
			final Connection connection = jdbcConnectionManager.getConnection();
			String statementType = null;
			if (type.equals("toys")) {
				statementType = TOY_FIND_ALL;
			} else if (type.equals("books")) {
				statementType = BOOK_FIND_ALL;
			} else {
				statementType = FLOWER_FIND_ALL;
			}
			final PreparedStatement findAllQuery = connection.prepareStatement(statementType);

			final ResultSet resultSet = findAllQuery.executeQuery();
			final List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				Product product = null;

				if (type.equals("toys")) {
					product = new Toy(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getBigDecimal(4));
					products.add(product);
				} else if (type.equals("books")) {
					product = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getBigDecimal(5));
					products.add(product);
				} else {
					product = new Flower(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getBigDecimal(4));
					products.add(product);
				}

			}

			return products;
		} catch (Exception e) {
			throw DataAccessException.instance("failed_to_retrieve_toys");
		}
	}

	public List<Product> findProductsByPrice(BigDecimal minimumPrice, BigDecimal maximumPrice, String type) {
		try {
			final Connection connection = jdbcConnectionManager.getConnection();
			String statementType = null;
			if (type.equals("toys")) {
				statementType = TOY_FIND_BY_PRICE;
			} else if (type.equals("books")) {
				statementType = BOOK_FIND_BY_PRICE;
			} else {
				statementType = FLOWER_FIND_BY_PRICE;
			}
			final PreparedStatement findAllQuery = connection.prepareStatement(statementType);
			findAllQuery.setBigDecimal(1, minimumPrice);
			findAllQuery.setBigDecimal(2, maximumPrice);

			final ResultSet resultSet = findAllQuery.executeQuery();
			final List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				Product product = null;
				if (type.equals("toys")) {
					product = new Toy(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getBigDecimal(4));
					products.add(product);
				} else if (type.equals("books")) {
					product = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getBigDecimal(5));
					products.add(product);
				} else {
					product = new Flower(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getBigDecimal(4));
					products.add(product);
				}
				products.add(product);
			}

			return products;
		} catch (Exception e) {
			throw DataAccessException.instance("failed_to_retrieve_toys_by_price");
		}
	}
}
