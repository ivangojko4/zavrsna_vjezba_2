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