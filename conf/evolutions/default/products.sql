CREATE TABLE products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  product_name VARCHAR(255),
  category VARCHAR(255),
  sub_category VARCHAR(255),
  brand VARCHAR(255),
  unit VARCHAR(50),
  sku VARCHAR(100),
  minimum_qty INT,
  quantity INT,
  description TEXT,
  tax DECIMAL(10,2),
  discount_type VARCHAR(50),
  price DECIMAL(10,2),
  status BOOLEAN,
  product_image VARCHAR(255)
);
