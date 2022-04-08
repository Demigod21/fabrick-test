CREATE TABLE transaction (
    uuid varchar(255) PRIMARY KEY,
    amount int,
    currency varchar(255),
    creditor_name varchar(255),
    description varchar(255),
    result varchar(255),
    error_message varchar(255),
    transaction_date DATE
);