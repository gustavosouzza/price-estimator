SmartPricing - Price Calculator
Overview

SmartPricing is a web-based price calculator designed to help businesses determine optimal selling prices based on purchase cost, desired profit margin, and quantity. It allows users to manage products, adjust profit margins, and clear the product list.

Features

Add new products with name, purchase price, and quantity.

Calculate selling price based on a default or custom profit margin.

Update profit margin for individual products.

Remove products from the list.

Clear all products from the list.

Technologies Used

Frontend: HTML, CSS (Bootstrap 5), Thymeleaf

Backend: Java (Spring Boot)

Database: MySQL

Getting Started
Prerequisites

Java 17 or higher

Maven 3.8.1 or higher

Installation
```bash
Clone the repository:

git clone https://github.com/gustavosouzza/price-estimator.git
cd smartpricing
```

Build the project:
```bash
mvn clean install
```

Run the application:
```bash
mvn spring-boot:run
```

Access the application at http://localhost:8080.

Usage

Add Product: Enter product name, purchase price, and quantity, then click "Add".

Update Margin: Enter a new margin percentage and click "Update".

Remove Product: Click "Remove" next to the product.

Clear List: Click "Clear List" to remove all products.