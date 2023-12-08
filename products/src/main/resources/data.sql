INSERT INTO company (id, created_at, updated_at, name, discount, margin) VALUES
    (1, current_timestamp, current_timestamp, 'Company A', 0.1, 0.2),
    (2, current_timestamp, current_timestamp, 'Company B', 0.05, 0.15),
    (3, current_timestamp, current_timestamp, 'Company C', 0.2, 0.1);

INSERT INTO product_group (id, created_at, updated_at, name, company_id) VALUES
     (1, current_timestamp, current_timestamp, 'Electronics', 1),
     (2, current_timestamp, current_timestamp, 'Clothing', 2),
     (3, current_timestamp, current_timestamp, 'Home and Kitchen', 3);

INSERT INTO product (id, created_at, updated_at, name, number, description, list_price, discount, margin, company_id, group_id) VALUES
    (1, current_timestamp, current_timestamp, 'Laptop', 'P123456', 'High-performance laptop', 1200, 0.1, 0.2, 1, 1),
    (2, current_timestamp, current_timestamp, 'T-shirt', 'C789012', 'Comfortable cotton T-shirt', 25, 0.05, 0.15, 2, 2),
    (3, current_timestamp, current_timestamp, 'Coffee Maker', 'H456789', 'Automatic coffee maker', 80, 0.2, 0.1, 3, 3);

INSERT INTO tag (id, created_at, updated_at, name, color) VALUES
    (1, current_timestamp, current_timestamp, 'Electronics Tag', '#3498db'),
    (2, current_timestamp, current_timestamp, 'Clothing Tag', '#e74c3c'),
    (3, current_timestamp, current_timestamp, 'Kitchen Tag', '#2ecc71');

INSERT INTO product_tag (product_id, tag_id) VALUES
    (1, 1), -- Laptop is associated with Electronics Tag
    (2, 2), -- T-shirt is associated with Clothing Tag
    (3, 3); -- Coffee Maker is associated with Kitchen Tag