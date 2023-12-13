ALTER TABLE rental_history
    ADD COLUMN account_version BIGINT;
UPDATE rental_history
SET account_version = id;
ALTER TABLE rental_history
    ALTER COLUMN account_version SET NOT NULL;
ALTER TABLE rental_history
    ADD CONSTRAINT unique_acc_ver UNIQUE (account_id, account_version);

