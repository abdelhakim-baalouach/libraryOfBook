CREATE TABLE IF NOT EXISTS  authors (
    id                          SERIAL      PRIMARY KEY,
    date_of_death               timestamp,
    date_ofbirth                timestamp,
    first_name                  varchar(100),
    last_name                   varchar(100),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS languages (
    id                          SERIAL      PRIMARY KEY,
    name                        varchar(30),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS genres (
    id                          SERIAL      PRIMARY KEY,
    name                        varchar(30),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS books (
    id                          SERIAL      PRIMARY KEY,
    title                       varchar(255),
    isbn                        varchar(20),
    summary                     TEXT,
    imprint                     TEXT,
    language_id                 INTEGER REFERENCES languages (id),
    ---- commun infos ----
    state_enum                  varchar(10) NOT NULL DEFAULT 'ACTIVE',
    created_date                timestamp  with time zone NOT NULL DEFAULT NOW(),
    last_modified_date          timestamp  with time zone NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS books_authors (
    authors_id                   INTEGER NOT NULL REFERENCES authors (id),
    books_id                    INTEGER NOT NULL REFERENCES books (id),
    constraint authors_books_pkey primary key (authors_id, books_id)
);

CREATE TABLE IF NOT EXISTS books_genres (
    genres_id                   INTEGER NOT NULL REFERENCES genres (id),
    books_id                   INTEGER NOT NULL REFERENCES books (id),
    constraint books_genres_pkey  primary key (genres_id, books_id)
);
