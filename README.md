# Project init

```
DB_DRIVER=
DB_URL=
DB_USER=
DB_PASSWORD=
ROOT_ADMIN_PASSWORD=
server.servlet.session.cookie.http-only=false
```

`ROOT_ADMIN_PASSWORD` инициализируется в начале работы приложения. 
Если до этого база уже была инициирована, то в этом поле нет смысла

```
CREATE OR REPLACE FUNCTION get_objects_count_by_manufacturer()
RETURNS TABLE(manufacturer_id BIGINT, object_count BIGINT) AS
$$
BEGIN
    RETURN QUERY
    SELECT p.manufacturer_id, COUNT(*) AS object_count
    FROM is1_product p
    GROUP BY p.manufacturer_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION count_objects_by_rating(p_rating INT)
RETURNS BIGINT AS
$$
BEGIN
    RETURN (SELECT COUNT(*)
            FROM is1_product
            WHERE rating = p_rating);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION count_objects_by_partnumber(p_partnumber VARCHAR)
RETURNS BIGINT AS
$$
BEGIN
    RETURN (SELECT COUNT(*)
            FROM is1_product
            WHERE partNumber < p_partnumber);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_products_by_manufacturer(p_manufacturer_id INT)
RETURNS TABLE (
    id INT,
    name VARCHAR,
    partNumber VARCHAR,
    price BIGINT,
    manufactureCost FLOAT,
    rating INT,
    unitOfMeasure VARCHAR,
    coordinates_id BIGINT,
    manufacturer_id BIGINT
) AS
$$
BEGIN
    RETURN QUERY
    SELECT 
        p.id,
        p.name,
        p.partNumber,
        p.price,
        p.manufactureCost,
        p.rating,
        p.unitOfMeasure,
        p.coordinates_id,
        p.manufacturer_id
    FROM is1_product p
    WHERE p.manufacturer_id = p_manufacturer_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION reduce_prices_by_percentage(p_percentage NUMERIC)
RETURNS VOID AS $$
BEGIN
    IF p_percentage <= 0 OR p_percentage > 100 THEN
        RAISE EXCEPTION 'Percentage must be between 0 and 100';
    END IF;

    UPDATE is1_product
    SET price = price - (price * (p_percentage / 100.0))
    WHERE price > 0;
END;
$$ LANGUAGE plpgsql;
```

### For labs - check branches