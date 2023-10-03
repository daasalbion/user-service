create table USERS
(
    ID         BIGINT auto_increment primary key,
    PUBLIC_UID UUID      default RANDOM_UUID()  not null,
    NAME       CHARACTER VARYING                not null,
    EMAIL      CHARACTER VARYING                not null,
    PASSWORD   CHARACTER VARYING                not null,
    CREATED_AT TIMESTAMP default LOCALTIMESTAMP not null,
    UPDATED_AT TIMESTAMP on update LOCALTIMESTAMP,
    LAST_LOGIN TIMESTAMP default LOCALTIMESTAMP,
    IS_ACTIVE  BOOLEAN   default TRUE           not null,
    TOKEN      CHARACTER VARYING
);


create table USER_PHONE_NUMBERS
(
    ID           BIGINT auto_increment PRIMARY KEY,
    PHONE_NUMBER CHARACTER VARYING not null,
    CITY_CODE    BIGINT,
    COUNTRY_CODE BIGINT,
    USER_ID      INTEGER,
    constraint "user_phone_numbers_USERS_ID_fk"
        foreign key (USER_ID) references USERS
            on update set null on delete set null
);