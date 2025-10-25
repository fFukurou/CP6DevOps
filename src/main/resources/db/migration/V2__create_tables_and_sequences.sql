-- V2__create_tables_and_sequences.sql

-- Create DEITY_TYPE table
CREATE TABLE deity_type (
    id_type       NUMBER(10) PRIMARY KEY,
    type_name     VARCHAR2(100) NOT NULL UNIQUE,
    origin        VARCHAR2(250),
    description   VARCHAR2(500),
    lifespan      VARCHAR2(100),
    power_source  VARCHAR2(150)
);

-- Create DEITY table
CREATE TABLE deity (
    id_deity    NUMBER(10) PRIMARY KEY,
    name        VARCHAR2(100) NOT NULL UNIQUE,
    domain      VARCHAR2(150),
    roman_name  VARCHAR2(100),
    description VARCHAR2(500),
    id_type     NUMBER(10) NOT NULL,
    CONSTRAINT fk_deity_type FOREIGN KEY (id_type)
        REFERENCES deity_type(id_type)
);

-- Create sequences and set default values for IDs
DECLARE
    v_count NUMBER;
BEGIN
    -- Sequence for DEITY_TYPE
    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'DEITY_TYPE_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE DEITY_TYPE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE';
    END IF;

    EXECUTE IMMEDIATE 'ALTER TABLE deity_type MODIFY (id_type DEFAULT DEITY_TYPE_SEQ.NEXTVAL)';

    -- Sequence for DEITY
    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'DEITY_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE DEITY_SEQ START WITH 1 INCREMENT BY 1 NOCACHE';
    END IF;

    EXECUTE IMMEDIATE 'ALTER TABLE deity MODIFY (id_deity DEFAULT DEITY_SEQ.NEXTVAL)';

EXCEPTION
    WHEN OTHERS THEN

        IF SQLCODE NOT IN (-1442, -2260, -2261, -2264, -2275, -2443) THEN
            RAISE;
        END IF;
END;
/

COMMIT;