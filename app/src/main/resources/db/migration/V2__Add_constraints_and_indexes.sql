
ALTER TABLE IS1_ORGANIZATION 
ADD CONSTRAINT chk_annual_turnover_positive CHECK (annualturnover > 0),
ADD CONSTRAINT chk_employees_count_positive CHECK (employeescount > 0);

ALTER TABLE IS1_PERSON 
ADD CONSTRAINT chk_height_positive CHECK (height > 0);

ALTER TABLE IS1_COORDINATES 
ADD CONSTRAINT chk_x_coord_max CHECK (x <= 988);

ALTER TABLE IS1_PRODUCT 
ADD CONSTRAINT chk_price_positive CHECK (price > 0),
ADD CONSTRAINT chk_rating_positive CHECK (rating > 0);

CREATE INDEX IF NOT EXISTS idx_product_manufacturer ON IS1_PRODUCT(manufacturer_id);
CREATE INDEX IF NOT EXISTS idx_product_owner ON IS1_PRODUCT(owner_id);
CREATE INDEX IF NOT EXISTS idx_address_zip_code ON IS1_ADDRESS(zipcode);
CREATE INDEX IF NOT EXISTS idx_import_history_user ON IS2_IMPORT_HISTORY(user_id);
CREATE INDEX IF NOT EXISTS idx_admin_request_user ON IS1_ADMIN_REQUEST(userid);

COMMENT ON TABLE IS1_USER IS 'User accounts with authentication credentials';
COMMENT ON TABLE IS1_USER_ROLES IS 'User role assignments';
COMMENT ON TABLE IS1_LOCATION IS '3D spatial coordinates';
COMMENT ON TABLE IS1_COORDINATES IS '2D geographic coordinates';
COMMENT ON TABLE IS1_PERSON IS 'Person records with biometric data';
COMMENT ON TABLE IS1_ADDRESS IS 'Postal addresses';
COMMENT ON TABLE IS1_ORGANIZATION IS 'Organization information';
COMMENT ON TABLE IS1_PRODUCT IS 'Product catalog';
COMMENT ON TABLE IS1_EVENT IS 'Audit trail for data modifications';
COMMENT ON TABLE IS2_IMPORT_HISTORY IS 'Data import tracking';
COMMENT ON TABLE IS1_ADMIN_REQUEST IS 'Administrator access requests';