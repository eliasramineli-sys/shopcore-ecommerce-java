CREATE TABLE products (
 id BIGSERIAL PRIMARY KEY,
 sku VARCHAR(60) NOT NULL UNIQUE,
 name VARCHAR(150) NOT NULL,
 price NUMERIC(12,2) NOT NULL CHECK (price >= 0),
 stock INTEGER NOT NULL CHECK (stock >= 0),
 version BIGINT NOT NULL DEFAULT 0
);
CREATE TABLE carts (
 id BIGSERIAL PRIMARY KEY,
 customer_email VARCHAR(254) NOT NULL,
 status VARCHAR(20) NOT NULL
);
CREATE TABLE cart_items (
 id BIGSERIAL PRIMARY KEY,
 cart_id BIGINT NOT NULL REFERENCES carts(id),
 product_id BIGINT NOT NULL REFERENCES products(id),
 quantity INTEGER NOT NULL CHECK (quantity > 0),
 CONSTRAINT uq_cart_product UNIQUE(cart_id,product_id)
);
CREATE TABLE orders (
 id BIGSERIAL PRIMARY KEY,
 customer_email VARCHAR(254) NOT NULL,
 total NUMERIC(14,2) NOT NULL,
 status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE TABLE order_items (
 id BIGSERIAL PRIMARY KEY,
 order_id BIGINT NOT NULL REFERENCES orders(id),
 product_id BIGINT NOT NULL,
 product_name VARCHAR(150) NOT NULL,
 unit_price NUMERIC(12,2) NOT NULL,
 quantity INTEGER NOT NULL CHECK (quantity > 0)
);
