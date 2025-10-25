-- V1__drop_tables_and_sequences.sql

-- Drop DEITY table (must be dropped before DEITY_TYPE due to foreign key)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE deity CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

-- Drop DEITY_TYPE table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE deity_type CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

-- Drop sequences
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE deity_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE deity_type_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

COMMIT;