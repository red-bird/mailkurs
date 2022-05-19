CREATE TABLE files
(
    path    UUID NOT NULL,
    name    VARCHAR(255),
    mail_id BIGINT,
    CONSTRAINT pk_files PRIMARY KEY (path)
);

ALTER TABLE files
    ADD CONSTRAINT FK_FILES_ON_MAIL FOREIGN KEY (mail_id) REFERENCES mails (id);