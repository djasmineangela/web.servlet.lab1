package com.webshoppe.ecommerce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webshoppe.ecommerce.entity.Book;
import com.webshoppe.ecommerce.entity.Flower;
import com.webshoppe.ecommerce.entity.Product;
import com.webshoppe.ecommerce.entity.Toy;
import com.webshoppe.ecommerce.jdbc.JdbcConnectionManager;
import com.webshoppe.ecommerce.repository.ProductRepository;
import com.webshoppe.ecommerce.service.ProductCatalogService;

public class ProductCatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductCatalogService productCatalogService;

	@Override
	public void init() throws ServletException {
		final JdbcConnectionManager jdbcConnectionManager = new JdbcConnectionManager();
		final ProductRepository productRepository = new ProductRepository(jdbcConnectionManager);
		productCatalogService = new ProductCatalogService(productRepository);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String type = request.getParameter("type");
		final StringBuilder stringBuilder = new StringBuilder();

		List<Product> products = null;
		products = productCatalogService.getProductCatalog(type);
		productStringBuilderAppend(stringBuilder, type, products);

		PrintWriter out = response.getWriter();
		out.println(stringBuilder.toString());
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		final String minimumPriceParam = request.getParameter("minimum-price");
		final BigDecimal minimumPrice = new BigDecimal(minimumPriceParam);

		final String maximumPriceParam = request.getParameter("maximum-price");
		final BigDecimal maximumPrice = new BigDecimal(maximumPriceParam);

		List<Product> products = null;

		final StringBuilder stringBuilder = new StringBuilder();
		final String type = request.getParameter("category");
		products = productCatalogService.getProductCatalog(minimumPrice, maximumPrice, type);
		productStringBuilderAppend(stringBuilder, type, products);

		PrintWriter out = response.getWriter();
		out.println(stringBuilder.toString());
		out.flush();
		out.close();
	}

	private void productStringBuilderAppend(StringBuilder stringBuilder, String type, List<Product> products) {
		if (products.isEmpty()) {
			stringBuilder.append("<b>Toy Catalog Empty</b>");
		} else {
			stringBuilder.append("<table class='table'>");
			stringBuilder.append("<thead>");
			stringBuilder.append("<th scope='col'>ID</th>");
			stringBuilder.append("<th scope='col'>Name</th>");
			stringBuilder.append("<th scope='col'>Description</th>");
			if (type.equals("books")) {
				stringBuilder.append("<th scope='col'>AID</th>");
			}
			stringBuilder.append("<th scope='col'>Price</th>");
			stringBuilder.append("</thead>");

			products.forEach(e -> {
				Product product;
				if (type.equals("toys")) {
					product = (Toy) e;
				} else if (type.equals("books")) {
					product = (Book) e;
				} else {
					product = (Flower) e;
				}
				stringBuilder.append("<tr scope='row'>");
				stringBuilder.append("<td>").append(product.getId()).append("</td>");
				stringBuilder.append("<td>").append(product.getName()).append("</td>");
				stringBuilder.append("<td>").append(product.getDescription()).append("</td>");
				if (type.equals("books")) {
					stringBuilder.append("<td>").append(((Book) product).getAid()).append("</td>");
				}
				stringBuilder.append("<td>").append(product.getPrice()).append("</td>");
				stringBuilder.append("</tr>");
			});
			stringBuilder.append("</table>");
		}
	}
}
