Create table ScambiGioco 
(
    IdScambioGioco INT PRIMARY KEY
);

Create table CarteRisorsa 
(
    IdCartaRisorsa INT PRIMARY KEY,
    Tipo VARCHAR(10) not null
);

Create table LanciDadi 
(
    IdLancioDadi INT PRIMARY KEY,
    DadoUno INT not null,
    DadoDue INT not null,
    idTurno int not null
);

Create table CittaColonie 
(
    IdCittaColonia INT PRIMARY KEY,
    idVertice INT not null,
    aggiornataDalTurno INT
);

Create table Brigante 
(
    IdBrigante INT PRIMARY KEY,
    IdEsagono INT not null,
    idTurno int not null
);

Create table EsagoniTerreno 
(
    IdEsagonoTerreno INT PRIMARY KEY,
    Tipo VARCHAR(10) not null,
    Numero INT
);

Create table EsagoniPorto 
(
    IdEsagonoPorto INT PRIMARY KEY,
    RapportoScambio INT not null,
    Specializzazione VARCHAR(30) not null
);

Create table EsagoniMareAperto 
(
    IdEsagonoMareAperto INT PRIMARY KEY
);

Create table EsagoniMare 
(
    IdEsagonoMare    INT   PRIMARY KEY,
    idEsagonoPorto   INT,
    idEsagonoMareAperto   INT,
    constraint fkEsagoniPortoInEsagoniMare foreign key(idEsagonoPorto) references EsagoniPorto(idEsagonoPorto) on delete cascade,
    constraint fkEsagoniMareApInEsagoniMare foreign key(idEsagonoMareAperto) references EsagoniMareAperto(idEsagonoMareAperto) on delete cascade
);

Create table Esagoni 
(
    IdEsagono INT PRIMARY KEY,
    Posizione INT not null,
    idEsagonoMare INT,
    idEsagonoTerreno INT,
    idPartita INT not null,
    constraint fkEsagoniMareInEsagoni foreign key (idEsagonoMare) references EsagoniMare(idEsagonoMare) on delete cascade,
    constraint fkEsagoniTerrenoInEsagoni foreign key(idEsagonoTerreno) references EsagoniTerreno(idEsagonoTerreno) on delete cascade
);

Create table ScambiPorto
(
    IdScambioPorto INT PRIMARY KEY,
    idEsagonoPorto INT not null,
    constraint fkEsagoniPortoInScambiPorto foreign key (idEsagonoPorto) references EsagoniPorto(idEsagonoPorto)
);

Create table CarteProgresso 
(
    IdCartaProgresso INT PRIMARY KEY
);

Create table CartePuntoVittoria 
(
    IdCartaPuntoVittoria INT PRIMARY KEY
);

Create table CarteCavaliere 
(
    IdCartaCavaliere INT PRIMARY KEY
);

Create table CarteSviluppo 
(
    IdCartaSviluppo INT PRIMARY KEY,
    idCartaProgresso INT,
    idCartaPuntoVittoria INT,
    idCartaCavaliere INT,
    constraint fkProgressoInCarteSviluppo foreign key(idCartaProgresso) references CarteProgresso(idCartaProgresso) on delete cascade,
    constraint fkPuntoVittoriaInCarteSviluppo foreign key (idCartaPuntoVittoria) references CartePuntoVittoria(idCartaPuntoVittoria) on delete cascade,
    constraint fkCavaliereInCarteSviluppo foreign key (idCartaCavaliere) references CarteCavaliere(idCartaCavaliere) on delete cascade
);

Create table Carte 
(
    IdCarta INT PRIMARY KEY,
    IdCartaRisorsa INT,
    IdCartaSviluppo INT,
    idGiocatore int not null,
    constraint fkCarteRisorsaInCarte foreign key (idCartaRisorsa) references CarteRisorsa(idCartaRisorsa) on delete cascade,
    constraint fkCarteCarteSviluppoInCarte foreign key (idCartaSviluppo) references CarteSviluppo(idCartaSviluppo) on delete cascade
);

Create table Strade 
(
    IdStrada INT PRIMARY KEY,
    idCittaColoniaPrec INT,
    idCittaColoniaSucc INT,
    idLato int not null,
    constraint fkCittaColoniePreInStrade foreign key(idCittaColoniaPrec) references CittaColonie(idCittaColonia) on delete cascade,
    constraint fkCittaColonieSuccInStrade foreign key(idCittaColoniaSucc) references CittaColonie(idCittaColonia) on delete cascade
);

Create table Partite
(
    IdPartita INT PRIMARY KEY,
    Data DATE not null, 
    Durata INT not null
);

Create table Giocatori
(
    IdGiocatore INT PRIMARY KEY,
    Eta INT not null
);

Create table GiocatoriPartite
(
    IdGiocatorePartita INT PRIMARY KEY,
    idGiocatore INT,
    idPartita INT,
    Nickname VARCHAR(30) not null,
    colore varchar(20) not null,
    constraint fkGiocatoriInGiocatoriPartite foreign key (idGiocatore) references Giocatori(idGiocatore),
    constraint fkPartiteInGiocatoriPartite foreign key (idPartita) references Partite(idPartita) on delete cascade
);

Create table ScambiGiocatore 
(
    IdScambioGiocatore INT PRIMARY KEY,
    idGiocatore INT not null,
    constraint fkGiocatoriInScambiGiocatore foreign key(idGiocatore) references Giocatori(idGiocatore)
);

Create table Scambi 
(
    IdScambio INT PRIMARY KEY,
    idScambioPorto INT,
    idScambioGioco INT,
    idScambioGiocatore INT,
    idTurno INT not null,
    constraint fkScambiPortoInScambi foreign key(idScambioPorto) references ScambiPorto(idScambioPorto) on delete cascade,
    constraint fkScambiGiocoInScambi foreign key (idScambioGioco) references ScambiGioco(idScambioGioco) on delete cascade,
    constraint fkScambiGiocatoreInScambi foreign key (idScambioGiocatore) references ScambiGiocatore(idScambioGiocatore) on delete cascade
);

Create table Turni 
(
    IdTurno   INT   PRIMARY KEY, 
    Giro     INT not null,
    idGiocatore    INT not null,
    idPartita       INT not null,
    constraint fkGiocatoriInTurni foreign key (idGiocatore) references Giocatori(idGiocatore),
    constraint fkPartiteInTurni foreign key (idPartita) references Partite(idPartita)
);

Create table TurniCittaColonie 
(
    IdTurnoCittaColonia INT PRIMARY KEY,
    idCittaColonia INT not null,
    idTurno INT not null,
    constraint fkCittaColoInTurniCittaColonie foreign key (idCittaColonia) references CittaColonie(idCittaColonia) on delete cascade,
    constraint fkTurniInTurniCittaColonie foreign key (idTurno) references Turni(idTurno)
);

Create table StradeTurni 
(
    IdStradaTurno INT PRIMARY KEY,
    idStrada INT not null,
    idTurno INT not null,
    constraint fkStradeInStradeTurni foreign key(idStrada) references Strade(idStrada) on delete cascade,
    constraint fkTurniInStradeTurni foreign key(idTurno) references Turni(idTurno)
);

Create table CarteTurni
(
	idCartaTurno INT PRIMARY KEY,
	idCarta INT not null,
	idTurno INT not null,
	constraint fkCarteInCarteTurni foreign key(idCarta) references Carte(idCarta) on delete cascade,
	constraint fkTurniInCarteTurni foreign key(idTurno) references Turni(idTurno)
);

create table ScambiCarteRisorsaDate
(
	idScambioCartaRisorsaData int primary key,
	idCartaRisorsa int not null,
	idScambio int not null,
	constraint fkCarteInScambiCarteRisorseD foreign key(idCartaRisorsa) references CarteRisorsa(idCartaRisorsa) on delete cascade,
	constraint fkScambiInScambiCarteRisorseD foreign key(idScambio) references Scambi(idScambio) on delete cascade
);

create table ScambiCarteRisorsaRicevute
(
	idScambioCartaRisorsaRicevuta int primary key,
	idCartaRisorsa int not null,
	idScambio int not null,
	constraint fkCartaInScambiCarteRisorsaR foreign key(idCartaRisorsa) references CarteRisorsa(idCartaRisorsa) on delete cascade,
	constraint fkScambiInScambiCarteRisorsaR foreign key(idScambio) references Scambi(idScambio) on delete cascade
);

create table Vertici
(
	idVertice int primary key,
	posizione int not null
);

create table Lati
(
	idLato int primary key,
	posizione int not null
);

create table VerticiEsagoni
(
	idVerticeEsagono int primary key,
	idVertice int not null,
	idEsagono int not null,
	constraint fkVerticiInVerticiEsag foreign key(idVertice) references Vertici(idVertice) on delete cascade,
	constraint fkEsagoniInVerticiEsag foreign key(idEsagono) references Esagoni(idEsagono)
);

create table LatiEsagoni
(
	idLatoEsagono int primary key,
	idLato int not null,
	idEsagono int not null,
	constraint fkLatiInLatiEsagoni foreign key(idLato) references Lati(idLato) on delete cascade,
	constraint fkEsagoniInLatiEsagoni foreign key(idEsagono) references Esagoni(idEsagono)
);

create table VerticiLati
(
	idVerticeLato int primary key,
	idVertice int not null,
	idLato int not null,
	constraint fkVerticiInVerticiLati foreign key(idVertice) references Vertici(idVertice) on delete cascade,
	constraint fkLatiInVerticiLati foreign key(idLato) references Lati(idLato) on delete cascade
);

create table StradeSuccessive
(
	idStradaSucc int primary key,
	idStradaCorrente int not null,
	idStradaSuccessiva int not null,
	constraint fkStradaCorInStradeSucc foreign key(idStradaCorrente) references Strade(idStrada) on delete cascade,
	constraint fkStradaSucInStradeSucc foreign key(idStradaSuccessiva) references Strade(idStrada) on delete cascade
);

create table StradePrecedenti
(
	idStradaPrec int primary key,
	idStradaCorrente int not null,
	idStradaPrecedente int not null,
	constraint fkStradaCorInStradePrec foreign key(idStradaCorrente) references Strade(idStrada) on delete cascade,
	constraint fkStradaSucInStradePrec foreign key(idStradaPrecedente) references Strade(idStrada) on delete cascade
);


alter table CittaColonie add constraint fkTurnoInCittaColonie foreign key(aggiornataDalTurno) references Turni(idTurno);

alter table Carte add constraint fkGiocatoriInCarte foreign key(idGiocatore) references Giocatori(idGiocatore);

alter table Brigante add constraint fkTurniInBrigante foreign key(idTurno) references Turni(idTurno) on delete cascade;

alter table LanciDadi add constraint fkTurniInLanciDadi foreign key(idTurno) references Turni(idTurno) on delete cascade;

ALTER table Scambi ADD constraint fkTurniInScambi foreign key (idTurno) references Turni(idTurno) on delete cascade;

ALTER table Brigante ADD constraint fkEsagoniInBrigante foreign key (idEsagono) references Esagoni(idEsagono) on delete cascade;

ALTER table Esagoni ADD constraint fkPartiteInEsagoni foreign key(idPartita) references Partite(idPartita);

alter table CittaColonie add constraint fkVerticiInCittaColonie foreign key(idVertice) references Vertici(idVertice);

alter table Strade add constraint fkLatiInStrade foreign key(idLato) references Lati(idLato) on delete cascade;

ALTER table Carte ADD CONSTRAINT VincoloCarte CHECK 
(
    (IdCartaSviluppo IS NOT NULL AND IdCartaRisorsa IS NULL) OR
    (IdCartaRisorsa IS NOT NULL AND IdCartaSviluppo IS NULL)
); 

ALTER table CarteSviluppo ADD CONSTRAINT VincoloCarteSviluppo CHECK 
(
    (IdCartaProgresso IS NOT NULL AND IdCartaPuntoVittoria IS NULL AND idCartaCavaliere IS NULL) OR 
    (IdCartaPuntoVittoria IS NOT NULL AND IdCartaProgresso IS NULL  AND idCartaCavaliere IS NULL) OR
    (IdCartaCavaliere IS NOT NULL AND IdCartaProgresso IS NULL AND IdCartaPuntoVittoria IS NULL)
);

ALTER table Scambi ADD CONSTRAINT VincoloScambi CHECK
(
    (IdScambioPorto IS NOT NULL AND idScambioGioco IS NULL AND IdScambioGiocatore IS NULL) OR
    (IdScambioGioco IS NOT NULL AND IdScambioPorto IS NULL AND IdScambioGiocatore IS NULL) OR
    (IdScambioGiocatore IS NOT NULL AND IdScambioGioco IS NULL AND IdScambioPorto IS NULL)
);

ALTER table Esagoni ADD CONSTRAINT VincoloEsagoni CHECK
(
    (IdEsagonoTerreno IS NOT NULL AND IdEsagonoMare IS NULL) OR
    (IdEsagonoMare IS NOT NULL AND IdEsagonoTerreno IS NULL)
);

ALTER table EsagoniMare ADD CONSTRAINT VincoloEsagoniMare CHECK 
(
    (IdEsagonoPorto IS NOT NULL AND IdEsagonoMareAperto IS NULL) OR
    (IdEsagonoMareAperto IS NOT NULL AND IdEsagonoPorto IS NULL)
);

ALTER table CarteRisorsa ADD CONSTRAINT VincoloTipoCarteRisorsa CHECK
(
    Tipo = 'Grano' OR Tipo = 'Argilla' OR Tipo = 'Minerale' OR Tipo = 'Legno' OR Tipo = 'Lana'
);

ALTER table LanciDadi ADD CONSTRAINT VincoloLanciDadi CHECK 
(
    DadoUno BETWEEN 1 AND 6 AND DadoDue BETWEEN 1 AND 6
);

ALTER table Giocatori ADD CONSTRAINT VincoloGiocatori CHECK 
(
    Eta >= 10
);

ALTER table EsagoniTerreno ADD CONSTRAINT VincoloTipoEsagoniTerreno CHECK 
(
    Tipo = 'Campo' OR Tipo = 'Montagna' OR Tipo = 'Foresta' OR Tipo = 'Pascolo' OR Tipo = 'Deserto' OR Tipo = 'Collina'
);

ALTER table EsagoniTerreno ADD CONSTRAINT VincoloEsagoniNumero CHECK 
(
    (Numero BETWEEN 1 AND 12 AND Numero <> 7) OR (Tipo = 'Deserto' and Numero = null)
);

ALTER table EsagoniPorto ADD CONSTRAINT VincoloEsagoniPortoSpecializ CHECK 
(
    Specializzazione = 'Grano' OR Specializzazione = 'Argilla' OR Specializzazione = 'Minerale' OR Specializzazione = 'Legno' OR Specializzazione = 'Lana' OR Specializzazione = 'InputUtente'
);

ALTER table EsagoniPorto ADD CONSTRAINT VincoloEsagoniPortoRapportoSc CHECK
(
	RapportoScambio = 2 OR RapportoScambio = 3
);

ALTER table GiocatoriPartite ADD CONSTRAINT VincoloColoreGiocatore CHECK
(
    Colore = 'Bianco' or Colore = 'Blu' or Colore = 'Rosso' or Colore = 'Arancione'
);

ALTER table Esagoni ADD CONSTRAINT VincoloPosizione CHECK
(
    Posizione >= 0 and Posizione <= 36
);

Alter table EsagoniPorto add constraint VincoloRapportoScambio CHECK
(
    RapportoScambio = 2 OR RapportoScambio = 3
);

Alter table Turni ADD CONSTRAINT VincoloGiroUnicita UNIQUE(Giro, idGiocatore, IdPartita);

alter table GiocatoriPartite add constraint VincoloUnicitaColore unique(idPartita, Colore);

alter table GiocatoriPartite add constraint VincoloUnicitaNickname unique(idPartita, Nickname);

alter table GiocatoriPartite add constraint VincoloUnicitaGiocatore unique(idPartita, idGiocatore);

alter table CittaColonie add constraint VincoloUnicitaVerCittaCol unique(idVertice);

alter table Strade add constraint VincoloUnicitaLatoStrade unique(idLato);

alter table Brigante add constraint VincoloUnicitaBrigante unique(idTurno);

create sequence incrementoIdTurno start with 200;
create sequence incrementoIdScambio start with 200;
create sequence incrementoIdScambioPorto start with 200;
create sequence incrementoIdScambioGioco start with 200;
create sequence incrementoIdScambioGiocatore start with 200;
create sequence incrementoIdCartaRisorsa start with 200;
create sequence incrementoIdCarta start with 200;
create sequence incrementoIdCartaSviluppo start with 200;
create sequence incrementoIdCartaProgresso start with 200;
create sequence incrementoIdCartaPuntoVittoria start with 200;
create sequence incrementoIdCartaCavaliere start with 200;
create sequence incrementoIdCartaTurno start with 200;
create sequence incrementoIdLancioDadi start with 200;
create sequence incrementoIdCittaColonia start with 200;
create sequence incrementoIdCitta start with 200;
create sequence incrementoIdColonia start with 200;
create sequence incrementoIdStrada start with 200;
create sequence incrementoIdBrigante start with 200;
create sequence incrementoIdGiocatore start with 200;
create sequence incrementoIdPartita start with 200;
create sequence incrementoIdGiocatorePartita start with 200;
create sequence incrementoIdEsagono start with 200;
create sequence incrementoIdEsagonoTerreno start with 200;
create sequence incrementoIdEsagonoMare start with 200;
create sequence incrementoIdEsagonoPorto start with 200;
create sequence incrementoIdEsagonoMareAperto start with 200;
create sequence incrementoIdTurnoCittaColonia start with 200;
create sequence incrementoIdStradaTurno start with 200;
create sequence incrementoIdScamCarRisData start with 200;
create sequence incrementoIdScamCarRisRicevuta start with 200;
create sequence incrementoIdVertice start with 200;
create sequence incrementoIdLato start with 200;
create sequence incrementoIdVerticeEsagono start with 200;
create sequence incrementoIdLatoEsagono start with 200;
create sequence incrementoIdVerticeLato start with 200;
create sequence incrementoIdStradaSucc start with 200;
create sequence incrementoIdStradaPrec start with 200;