
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

-- ==============================================
-- USERS
-- ==============================================

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================
-- CATEGORIES
-- ==============================================

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
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


-- Relación muchos a muchos productos-categorías


CREATE TABLE product_categories (
                                    product_id UUID REFERENCES products_catalog(id) ON DELETE CASCADE,
                                    category_id BIGSERIAL REFERENCES categories(id) ON DELETE CASCADE,
                                    PRIMARY KEY(product_id, category_id)
);

-- ==============================================
-- ORDERS
-- ==============================================

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT REFERENCES users(id),
                        status order_status NOT NULL DEFAULT 'CREATED',
                        shipping_method shipping_method DEFAULT 'STANDARD',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP
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
-- BILLS
-- ==============================================

CREATE TABLE bills (
                       id UUID PRIMARY KEY,
                       order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
                       client_rfc VARCHAR(50),
                       total_amount NUMERIC(10,2) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================
-- PAYMENTS
-- ==============================================

CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY,
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

