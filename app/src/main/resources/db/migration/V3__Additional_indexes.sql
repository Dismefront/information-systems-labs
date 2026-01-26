
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON IS1_USER_ROLES(user_id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role ON IS1_USER_ROLES(role);

CREATE INDEX IF NOT EXISTS idx_location_coords ON IS1_LOCATION(x, y, z);
CREATE INDEX IF NOT EXISTS idx_coordinates_coords ON IS1_COORDINATES(x, y);

CREATE INDEX IF NOT EXISTS idx_person_location_height ON IS1_PERSON(location_id, height);
CREATE INDEX IF NOT EXISTS idx_organization_addresses ON IS1_ORGANIZATION(officialaddress_id, postaladdress_id);
CREATE INDEX IF NOT EXISTS idx_product_coordinates_unit ON IS1_PRODUCT(coordinates_id, unitofmeasure);
CREATE INDEX IF NOT EXISTS idx_event_timestamp_actor ON IS1_EVENT(timestamp, actor);

CREATE INDEX IF NOT EXISTS idx_person_eyecolor ON IS1_PERSON(eyecolor) WHERE eyecolor IS NOT NULL;
CREATE INDEX IF NOT EXISTS idx_person_haircolor ON IS1_PERSON(haircolor) WHERE haircolor IS NOT NULL;
CREATE INDEX IF NOT EXISTS idx_organization_full_name ON IS1_ORGANIZATION(fullname) WHERE fullname IS NOT NULL;
CREATE INDEX IF NOT EXISTS idx_product_part_number ON IS1_PRODUCT(partnumber) WHERE partnumber IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_person_name_asc ON IS1_PERSON(name ASC);
CREATE INDEX IF NOT EXISTS idx_organization_name_asc ON IS1_ORGANIZATION(name ASC);
CREATE INDEX IF NOT EXISTS idx_product_name_asc ON IS1_PRODUCT(name ASC);
CREATE INDEX IF NOT EXISTS idx_product_price_asc ON IS1_PRODUCT(price ASC);

CREATE UNIQUE INDEX IF NOT EXISTS uk_user_username ON IS1_USER(username);
CREATE UNIQUE INDEX IF NOT EXISTS uk_organization_full_name ON IS1_ORGANIZATION(fullname) WHERE fullname IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_address_town_id ON IS1_ADDRESS(town_id);
CREATE INDEX IF NOT EXISTS idx_person_location_id ON IS1_PERSON(location_id);
CREATE INDEX IF NOT EXISTS idx_product_coordinates_id ON IS1_PRODUCT(coordinates_id);
CREATE INDEX IF NOT EXISTS idx_product_manufacturer_id ON IS1_PRODUCT(manufacturer_id);
CREATE INDEX IF NOT EXISTS idx_product_owner_id ON IS1_PRODUCT(owner_id);