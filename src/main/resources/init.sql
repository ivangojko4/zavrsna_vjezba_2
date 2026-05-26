CREATE TABLE IF NOT EXISTS Polaznik (
    PolaznikID SERIAL PRIMARY KEY,
    Ime VARCHAR(100) NOT NULL,
    Prezime VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS ProgramObrazovanja (
    ProgramObrazovanjaID SERIAL PRIMARY KEY,
    Naziv VARCHAR(100) NOT NULL,
    CSVET INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Upis (
    UpisID SERIAL PRIMARY KEY,
    IDPolaznik INT NOT NULL,
    IDProgramObrazovanja INT NOT NULL,
    FOREIGN KEY (IDPolaznik) REFERENCES Polaznik(PolaznikID),
    FOREIGN KEY (IDProgramObrazovanja) REFERENCES ProgramObrazovanja(ProgramObrazovanjaID)
);

CREATE OR REPLACE FUNCTION sp_insert_polaznik(p_ime VARCHAR, p_prezime VARCHAR)
RETURNS INT AS $$
DECLARE
    novi_id INT;
BEGIN
    INSERT INTO Polaznik (Ime, Prezime)
    VALUES (p_ime, p_prezime)
    RETURNING PolaznikID INTO novi_id;
    RETURN novi_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_insert_program(p_naziv VARCHAR, p_csvet INT)
RETURNS INT AS $$
DECLARE
    novi_id INT;
BEGIN
    INSERT INTO ProgramObrazovanja (Naziv, CSVET)
    VALUES (p_naziv, p_csvet)
    RETURNING ProgramObrazovanjaID INTO novi_id;
    RETURN novi_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_insert_upis(p_id_polaznik INT, p_id_program INT)
RETURNS INT AS $$
DECLARE
    novi_id INT;
BEGIN
    INSERT INTO Upis (IDPolaznik, IDProgramObrazovanja)
    VALUES (p_id_polaznik, p_id_program)
    RETURNING UpisID INTO novi_id;
    RETURN novi_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE sp_prebaci_polaznika(p_upis_id INT, p_novi_program_id INT)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE Upis
    SET IDProgramObrazovanja = p_novi_program_id
    WHERE UpisID = p_upis_id;
END;
$$;

CREATE OR REPLACE FUNCTION sp_polaznici_po_programu(p_program_id INT)
RETURNS TABLE(ime VARCHAR, prezime VARCHAR, naziv_programa VARCHAR, csvet INT) AS $$
BEGIN
    RETURN QUERY
    SELECT p.Ime, p.Prezime, pr.Naziv, pr.CSVET
    FROM Upis u
    JOIN Polaznik p ON u.IDPolaznik = p.PolaznikID
    JOIN ProgramObrazovanja pr ON u.IDProgramObrazovanja = pr.ProgramObrazovanjaID
    WHERE pr.ProgramObrazovanjaID = p_program_id;
END;
$$ LANGUAGE plpgsql;