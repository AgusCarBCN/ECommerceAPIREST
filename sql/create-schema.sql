-- ==============================================
-- ENUMS
-- ==============================================
CREATE TYPE category AS ENUM (
    'MONITORS',
    'HOME_COMPUTERS',
    'GAMING_COMPUTERS',
    'PROFESSIONAL_WORKSTATIONS',
    'MICE',
    'KEYBOARDS',
    'HEADSETS',
    'PC_COMPONENTS',
    'STORAGE_DEVICES',
    'NETWORKING_EQUIPMENT'
);

CREATE TYPE order_status AS ENUM (
    'CREATED',
    'PAID',
    'CANCELLED',
    'PROCESSING',
    'SHIPPED',
    'DELIVERED',
    'PAYMENT_PENDING',
    'PAYMENT_FAILED',
    'REFUNDED',
    'ON_HOLD',
    'RETURNED',
    'FAILED'
);

CREATE TYPE payment_status AS ENUM (
    'PENDING',
    'COMPLETED',
    'FAILED',
    'REFUNDED',
    'CANCELLED'
);

CREATE TYPE payment_method AS ENUM (
    'CREDIT_CARD',
    'DEBIT_CARD',
    'PAYPAL',
    'BANK_TRANSFER',
    'CASH_ON_DELIVERY',
    'CRYPTOCURRENCY'
);

CREATE TYPE shipping_method AS ENUM (
    'STANDARD',
    'EXPRESS',
    'SAME_DAY',
    'PICKUP',
    'DRONE'
);
CREATE TYPE user_role AS ENUM(
    'ADMIN',
    'USER'
);
-- ==============================================
-- SEQUENCES
-- ==============================================
CREATE SEQUENCE roles_seq START 1;
CREATE SEQUENCE users_seq START 1;
CREATE SEQUENCE categories_seq START 1;
CREATE SEQUENCE orders_seq START 1;
CREATE SEQUENCE payments_seq START 1;


-- ==============================================
-- ROLES
-- ==============================================
CREATE TABLE roles (
                       id BIGINT PRIMARY KEY DEFAULT nextval('roles_seq'),
                       role user_role NOT NULL DEFAULT 'USER'

);


-- ==============================================
-- USERS
-- ==============================================
CREATE TABLE users (
                       id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
                            category category NOT NULL,
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
                        status order_status NOT NULL DEFAULT 'CREATED',
                        shipping_method shipping_method DEFAULT 'STANDARD',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP,
                        FOREIGN KEY (id_bill) REFERENCES bills(id) ON DELETE CASCADE
);

-- ==============================================
-- ORDER PRODUCTS
-- ==============================================
CREATE TABLE order_products (
                                order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
                                product_catalog_id UUID REFERENCES products_catalog(id),
                                quantity BIGINT NOT NULL,
                                PRIMARY KEY(order_id, product_catalog_id)
);


-- ==============================================
-- PAYMENTS
-- ==============================================
CREATE TABLE payments (
                          id BIGINT PRIMARY KEY DEFAULT nextval('payments_seq'),
                          order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
                          amount NUMERIC(10,2) NOT NULL,
                          method payment_method NOT NULL,
                          status payment_status NOT NULL DEFAULT 'PENDING',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================
-- INDICES
-- ==============================================
CREATE INDEX idx_product_name ON products_catalog(product_name);
CREATE INDEX idx_order_status ON orders(status);
CREATE INDEX idx_payment_status ON payments(status);
