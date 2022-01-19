CREATE TABLE IF NOT EXISTS book_instances (
    uid                         varchar(255) NOT NULL PRIMARY KEY,
    book_id                     INTEGER NOT NULL REFERENCES books (id),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS subscribers (
    id                          SERIAL      PRIMARY KEY,
    date_ofbirth                timestamp,
    email                       varchar(255),
    first_name                  varchar(100),
    last_name                   varchar(100),
    phone                       varchar(20),
    id_card                     varchar(50),
    UNIQUE(id_card, email),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS occupations (
    id                          SERIAL      PRIMARY KEY,
    due_back                    timestamp,
    status                      varchar(20),
    active                      boolean NOT NULL DEFAULT TRUE,
    book_instance_id            varchar(255) NOT NULL REFERENCES book_instances (uid),
    subscriber_id               INTEGER REFERENCES subscribers (id),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);
