
-- ==============================================
-- SEQUENCES
-- ==============================================
CREATE SEQUENCE roles_seq START 1;
CREATE SEQUENCE user_address_seq START 1;
CREATE SEQUENCE users_seq START 1;
CREATE SEQUENCE categories_seq START 1;
CREATE SEQUENCE orders_seq START 1;
CREATE SEQUENCE payments_seq START 1;
CREATE SEQUENCE product_seq START 1;

-- ==============================================
-- ROLES
-- ==============================================
CREATE TABLE roles (
                       id BIGINT PRIMARY KEY DEFAULT nextval('roles_seq'),
                       role VARCHAR(30) NOT NULL DEFAULT 'USER'

);
-- ==============================================
-- USERS
-- ==============================================
CREATE TABLE users (
                       id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at DATE ,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
                       status_description VARCHAR(255)
);

-- ==============================================
-- ADDRESS
-- ==============================================
CREATE TABLE user_address (
                              id BIGINT PRIMARY KEY DEFAULT nextval('user_address_seq'),

                              user_id BIGINT NOT NULL,

                              street VARCHAR(150) NOT NULL,
                              city VARCHAR(80) NOT NULL,
                              state VARCHAR(80),
                              postal_code VARCHAR(20) NOT NULL,
                              country VARCHAR(50) NOT NULL,

                              address_type VARCHAR(20) DEFAULT 'HOME', -- HOME, WORK, BILLING, SHIPPING

                              is_default BOOLEAN DEFAULT FALSE,

                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_user_address_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES users(id)
                                      ON DELETE CASCADE
);


-- ==============================================
-- USER-ROLES (Many-to-Many)
-- ==============================================
CREATE TABLE user_roles (
                            user_id BIGINT REFERENCES users(id),
                            role_id BIGINT REFERENCES roles(id),
                            PRIMARY KEY (user_id, role_id)
);

-- ==============================================
-- CATEGORIES
-- ==============================================
CREATE TABLE categories (
                            id BIGINT PRIMARY KEY DEFAULT nextval('categories_seq'),
                            category VARCHAR(30) NOT NULL,
                            description TEXT
);

-- ==============================================
-- PRODUCTS CATALOG
-- ==============================================
CREATE TABLE products_catalog (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  product_name VARCHAR(200) NOT NULL,
                                  description TEXT,
                                  price NUMERIC(10,2) NOT NULL,
                                  stock_quantity BIGINT DEFAULT 0,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================
-- PRODUCT-CATEGORIES (Many-to-Many)
-- ==============================================
CREATE TABLE product_categories (
                                    product_id UUID REFERENCES products_catalog(id) ON DELETE CASCADE,
                                    category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE,
                                    PRIMARY KEY(product_id, category_id)
);
-- ==============================================
-- BILLS
-- ==============================================
CREATE TABLE bills (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       tax_id VARCHAR(50),
                       total_amount NUMERIC(10,2) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================
-- ORDERS
-- ==============================================
CREATE TABLE orders (
                        id BIGINT PRIMARY KEY DEFAULT nextval('orders_seq'),
                        user_id BIGINT REFERENCES users(id),
                        id_bill UUID UNIQUE NOT NULL,
                        status VARCHAR(30) NOT NULL DEFAULT 'CREATED',
                        shipping_method VARCHAR(30) DEFAULT 'STANDARD',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP,
                        FOREIGN KEY (id_bill) REFERENCES bills(id) ON DELETE CASCADE
);

-- ==============================================
-- PRODUCTS
-- ==============================================

CREATE TABLE products (
                          id BIGINT PRIMARY KEY DEFAULT nextval('product_seq'),
                          quantity INT DEFAULT 1,
                          id_product_catalog UUID,
                          id_order BIGINT,
                          FOREIGN KEY (id_order) REFERENCES orders(id) ON DELETE CASCADE,
                          FOREIGN KEY (id_product_catalog) REFERENCES products_catalog(id) ON DELETE CASCADE
);

-- ==============================================
-- PAYMENTS
-- ==============================================
CREATE TABLE payments (
                          id BIGINT PRIMARY KEY DEFAULT nextval('payments_seq'),
                          order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
                          amount NUMERIC(10,2) NOT NULL,
                          method VARCHAR(30) NOT NULL,
                          status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================
-- INDICES
-- ==============================================
CREATE INDEX idx_product_name ON products_catalog(product_name);
CREATE INDEX idx_order_status ON orders(status);
CREATE INDEX idx_payment_status ON payments(status);
