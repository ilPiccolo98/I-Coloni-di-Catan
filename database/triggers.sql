-- FUNCTIONS
-- ottiene l'idEsagono di una partita in base alla posizione
create or replace function GetIdEsagono(idPartita Partite.idPartita%type, posizione integer) return Esagoni.idEsagono%type
    is
        idEsagono Esagoni.idEsagono%type;
    begin
        for i in (select *
                  from Esagoni E)
        loop
            if i.idPartita = idPartita and i.posizione = posizione then
                idEsagono := i.idEsagono;
            end if;
        end loop;
        return idEsagono;
    end;
    /

-- restituisce il turno di una partita di un giocatore di quel giro
create or replace function getIdTurno(idGiocatore Giocatori.idGiocatore%type, idPartita Partite.idPartita%type, giro integer) return Turni.idTurno%type
    is
        idTurno Turni.idTurno%type := 0;
    begin
        for i in (select T.idTurno, T.giro, T.idGiocatore, T.idPartita
                  from Turni T)
        loop
            if i.idGiocatore = idGiocatore and i.idPartita = idPartita and i.giro = giro then
                idTurno := i.idTurno;
            end if;
        end loop;
        return idTurno;
    end;
    /

-- ottiene l'idLato di una partita in base alla posizione
create or replace function GetIdLato(idPartita Partite.idPartita%type, posizione integer) return Lati.idLato%type
    is
        idLato Lati.idLato%type;
    begin
        for i in (select E.idPartita, L.posizione, L.idLato
                  from Esagoni E, LatiEsagoni LE, Lati L
                  where E.idEsagono = LE.idEsagono and
                        LE.idLato = L.idLato)
            loop
                if i.idPartita = idPartita and i.posizione = posizione then
                    idLato := i.idLato;
                end if;
            end loop;
        return idLato;
    end;
    /

-- ottiene l'idLato di una partita in base alla posizione
create or replace function GetIdVertice(idPartita Partite.idPartita%type, posizione integer) return Vertici.idVertice%type
    is
        idVertice Vertici.idVertice%type;
    begin
        for i in (select E.idPartita, V.posizione, V.idVertice
                  from Esagoni E, VerticiEsagoni VE, Vertici V
                  where E.idEsagono = VE.idEsagono and
                        VE.idVertice = V.idVertice)
        loop
            if i.idPartita = idPartita and i.posizione = posizione then
                idVertice := i.idVertice;
            end if;
        end loop;
        return idVertice;
    end;
    /

-- restituisce 1 se è una citta
create or replace function isCitta(idCittaColonia CittaColonie.idCittaColonia%type, idTurno Turni.idTurno%type) return integer
    is
        counter integer := 0;
    begin
        for i in (select CC.idCittaColonia, CC.aggiornataDalTurno
                  from CittaColonie CC)
        loop
            if i.idCittaColonia = idCittaColonia and i.aggiornataDalTurno is not null and i.aggiornataDalTurno <= idTurno then
                counter := counter + 1;
            end if;
        end loop;
        return counter;
    end;
    /

-- restituisce 1 se è una colonia
create or replace function isColonia(idCittaColonia CittaColonie.idCittaColonia%type, idTurno Turni.idTurno%type) return integer
    is
    begin
        if isCitta(idCittaColonia, idTurno) = 0 then
            return 1;
        end if;
        return 0;
    end;
    /

-- restituisce la partita di un turno
create or replace function getIdPartita(idTurno in Turni.idTurno%type) return integer
    is
        idPartita Partite.idPartita%type;
    begin
        for i in (select T.idPartita, T.idTurno
                  from Turni T)
        loop
            if i.idTurno = idTurno then
                idPartita := i.idPartita;
            end if;
        end loop;
        return idPartita;
    end;
    /

-- restituisce l'ultimo turno con idTurnoEscluso escluso
create or replace function getPenultimoTurno(idTurnoEscluso Turni.idTurno%type, idPartita Partite.idPartita%type) return Turni.idTurno%type
    is
        idTurno Turni.idTurno%type := 0;
    begin
        for i in (select T.idTurno, T.idPartita
                  from Turni T)
        loop
            if i.idPartita = idPartita and idTurno < i.idTurno and i.idTurno <> idTurnoEscluso then
                idTurno := i.idTurno;
            end if;
        end loop;
        return idTurno;
    end;
    /

-- restituisce l'ultimo turno di un giocatore in una partita
create or replace function getMaxTurno(idGiocatore Giocatori.idGiocatore%type, idPartita Partite.idPartita%type) return Turni.idTurno%type
    is
        idTurno Turni.idTurno%type := 0;
    begin 
        for i in (select T.idTurno, T.idPartita, T.idGiocatore
                  from Turni T)
        loop
            if i.idPartita = idPartita and i.idGiocatore = idGiocatore and i.idTurno > idTurno then
                idTurno := i.idTurno;
            end if;
        end loop;
        return idTurno;
    end;
    /

-- ottiene l'idCarta di una CartaRisorsa
create or replace function getIdCartaDiCartaRisorsa(idCartaRisorsa CarteRisorsa.idCartaRisorsa%type) return integer
    is
        idCarta Carte.idCarta%type := 0;
    begin
        for i in (select C.idCarta, CR.idCartaRisorsa
                  from Carte C, CarteRisorsa CR
                  where C.idCartaRisorsa = CR.idCartaRisorsa)
        loop
            if i.idCartaRisorsa = idCartaRisorsa then
                idCarta := i.idCarta;
            end if;
        end loop;
        return idCarta;
    end;
    /

-- permette di aggiungere una carta risorsa
create or replace function AggiungiCartaRisorsa(idGiocatore Giocatori.idGiocatore%type, idTurno Turni.idTurno%type, tipo varchar) return integer
    is
        idCartaRisorsa CarteRisorsa.idCartaRisorsa%type := incrementoIdCartaRisorsa.nextVal;
        idCarta Carte.idCarta%type := incrementoIdCarta.nextVal;
        idCartaTurno CarteTurni.idCartaTurno%type := incrementoIdCartaTurno.nextVal;
    begin
        insert into CarteRisorsa values(idCartaRisorsa, tipo);
        insert into Carte values(idCarta, idCartaRisorsa, null, idGiocatore);
        insert into CarteTurni values(idCartaTurno, idCarta, idTurno); 
        return idCartaRisorsa;
    end;
    /

-- aggiunge uno scambio con il gioco
create or replace function AggiungiScambioGioco(idTurno Turni.idTurno%type) return integer
    is 
        idScambioGioco ScambiGioco.idScambioGioco%type := incrementoIdScambioGioco.nextVal;
        idScambio Scambi.idScambio%type := incrementoIdScambio.nextVal;
    begin
        insert into ScambiGioco values(idScambioGioco);
        insert into Scambi values(idScambio, null, idScambioGioco, null, idTurno);
        return idScambio;
    end;
    /

-- aggiunge uno scambio con un giocatore
create or replace function AggiungiScambioGiocatore(idTurno Turni.idTurno%type, idGiocatore Giocatori.idGiocatore%type) return integer
    is 
        idScambioGiocatore ScambiGiocatore.idScambioGiocatore%type := incrementoIdScambioGiocatore.nextVal;
        idScambio Scambi.idScambio%type := incrementoIdScambio.nextVal;
    begin
        insert into ScambiGiocatore values(idScambioGiocatore, idGiocatore);
        insert into Scambi values(idScambio, null, null, idScambioGiocatore, idTurno);
        return idScambio;
    end;
    /

-- aggiunge uno scambio con un porto
create or replace function AggiungiScambioPorto(idTurno Turni.idTurno%type, idEsagonoPorto EsagoniPorto.idEsagonoPorto%type) return integer
    is 
        idScambioPorto ScambiPorto.idScambioPorto%type := incrementoIdScambioPorto.nextVal;
        idScambio Scambi.idScambio%type := incrementoIdScambio.nextVal;
    begin
        insert into ScambiPorto values(idScambioPorto, idEsagonoPorto);
        insert into Scambi values(idScambio, idScambioPorto, null, null, idTurno);
        return idScambio;
    end;
    /

-- restituisce l'idEsagono su cui è piazzato il brigante
create or replace function getIdEsagonoBrigante(idTurno Turni.idTurno%type) return Esagoni.idEsagono%type
    is
        idEsagono Esagoni.idEsagono%type;
    begin
        for i in (select B.idEsagono, B.idTurno
                  from Brigante B)
        loop
            if i.idTurno = idTurno then
                idEsagono := i.idEsagono;
            end if;
        end loop;
        return idEsagono;
    end;
    /

-- PROCEDURES
-- crea un esagono terreno per una partita
create or replace procedure CreaEsagonoTerreno(idPartita Partite.idPartita%type, posizione integer, tipo varchar , numero integer)
    is
        idEsagono Esagoni.idEsagono%type := incrementoIdEsagono.nextVal;
        idEsagonoTerreno EsagoniTerreno.idEsagonoTerreno%type := incrementoIdEsagonoTerreno.nextVal;
    begin
        insert into EsagoniTerreno values(idEsagonoTerreno, tipo, numero);
        insert into Esagoni values(idEsagono, posizione, null, idEsagonoTerreno, idPartita);
    end;
    /

-- crea un esagono porto per una partita
create or replace procedure CreaEsagonoPorto(idPartita Partite.idPartita%type, posizione integer, rapportoScambio integer, specializzazione varchar)
    is
        idEsagonoPorto EsagoniPorto.idEsagonoPorto%type := incrementoIdEsagonoPorto.nextVal;
        idEsagonoMare EsagoniMare.idEsagonoMare%type := incrementoIdEsagonoMare.nextVal;
        idEsagono Esagoni.idEsagono%type := incrementoIdEsagono.nextVal;
    begin
        insert into EsagoniPorto values(idEsagonoPorto, rapportoScambio, specializzazione);
        insert into EsagoniMare values(idEsagonoMare, idEsagonoPorto, null);
        insert into Esagoni values(idEsagono, posizione, idEsagonoMare, null, idPartita);
    end;
    /

-- crea un esagono mare aperto per una partita
create or replace procedure CreaEsagonoMareAperto(idPartita Partite.idPartita%type, posizione integer)
    is
        idEsagonoMareAperto EsagoniMareAperto.idEsagonoMareAperto%type := incrementoIdEsagonoMareAperto.nextVal;
        idEsagonoMare EsagoniMare.idEsagonoMare%type := incrementoIdEsagonoMare.nextVal;
        idEsagono Esagoni.idEsagono%type := incrementoIdEsagono.nextVal;
    begin
        insert into EsagoniMareAperto values(idEsagonoMareAperto);
        insert into EsagoniMare values(idEsagonoMare, null, idEsagonoMareAperto);
        insert into Esagoni values(idEsagono, posizione, idEsagonoMare, null, idPartita);
    end;
    /

-- si occupa dell'inserimento dei valori in VerticiEsagoni
create or replace procedure InserisciVerticiEsagoni(idEsagonoUno Esagoni.idEsagono%type, idEsagonoDue Esagoni.idEsagono%type, idEsagonoTre Esagoni.idEsagono%type, idVertice Vertici.idVertice%type)
    is
        idVerticeEsagonoUno VerticiEsagoni.idVerticeEsagono%type := incrementoIdVerticeEsagono.nextVal;
        idVerticeEsagonoDue VerticiEsagoni.idVerticeEsagono%type := incrementoIdVerticeEsagono.nextVal;
        idVerticeEsagonoTre VerticiEsagoni.idVerticeEsagono%type := incrementoIdVerticeEsagono.nextVal;
    begin
        insert all
            into VerticiEsagoni values(idVerticeEsagonoUno, idVertice, idEsagonoUno)
            into VerticiEsagoni values(idVerticeEsagonoDue, idVertice, idEsagonoDue)
            into VerticiEsagoni values(idVerticeEsagonoTre, idVertice, idEsagonoTre)
        select * from dual; 
    end;
    /

-- crea una mappa da gioco per una partita inserita
create or replace procedure CreaMappaDefault(idPartita Partite.idPartita%type)
    is
    begin
        CreaEsagonoTerreno(idPartita, 0, 'Deserto', null);
        CreaEsagonoTerreno(idPartita, 1, 'Foresta', 5);
        CreaEsagonoTerreno(idPartita, 8, 'Foresta', 6);
        CreaEsagonoTerreno(idPartita, 12, 'Foresta', 11);
        CreaEsagonoTerreno(idPartita, 16, 'Foresta', 3);
        CreaEsagonoTerreno(idPartita, 2, 'Pascolo', 12);
        CreaEsagonoTerreno(idPartita, 10, 'Pascolo', 4);
        CreaEsagonoTerreno(idPartita, 11, 'Pascolo', 10);
        CreaEsagonoTerreno(idPartita, 17, 'Pascolo', 9);
        CreaEsagonoTerreno(idPartita, 3, 'Montagna', 8);
        CreaEsagonoTerreno(idPartita, 5, 'Montagna', 5);
        CreaEsagonoTerreno(idPartita, 13, 'Montagna', 11);
        CreaEsagonoTerreno(idPartita, 4, 'Collina', 10);
        CreaEsagonoTerreno(idPartita, 6, 'Collina', 8);
        CreaEsagonoTerreno(idPartita, 18, 'Collina', 2);
        CreaEsagonoTerreno(idPartita, 4, 'Collina', 10);
        CreaEsagonoTerreno(idPartita, 14, 'Campo', 6);
        CreaEsagonoTerreno(idPartita, 15, 'Campo', 3);
        CreaEsagonoTerreno(idPartita, 7, 'Campo', 4);
        CreaEsagonoTerreno(idPartita, 9, 'Campo', 9);
        CreaEsagonoMareAperto(idPartita, 20);
        CreaEsagonoMareAperto(idPartita, 22);
        CreaEsagonoMareAperto(idPartita, 24);
        CreaEsagonoMareAperto(idPartita, 26);
        CreaEsagonoMareAperto(idPartita, 28);
        CreaEsagonoMareAperto(idPartita, 30);
        CreaEsagonoMareAperto(idPartita, 32);
        CreaEsagonoMareAperto(idPartita, 34);
        CreaEsagonoMareAperto(idPartita, 36);
        CreaEsagonoPorto(idPartita, 19, 2, 'Minerale');
        CreaEsagonoPorto(idPartita, 21, 2, 'Lana');
        CreaEsagonoPorto(idPartita, 23, 3, 'InputUtente');
        CreaEsagonoPorto(idPartita, 25, 3, 'InputUtente');
        CreaEsagonoPorto(idPartita, 27, 2, 'Grano');
        CreaEsagonoPorto(idPartita, 29, 3, 'InputUtente');
        CreaEsagonoPorto(idPartita, 31, 2, 'Argilla');
        CreaEsagonoPorto(idPartita, 33, 3, 'InputUtente');
        CreaEsagonoPorto(idPartita, 35, 2, 'Legno');
    end;
    /        

-- associa un vertice con gli esagoni confinanti
create or replace procedure AssociaVerticiEsagoni(idPartita Partite.idPartita%type, idVertice Vertici.idVertice%type, posizioneVertice integer)
    is
    begin
        if posizioneVertice = 0 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 1), GetIdEsagono(idPartita, 2), idVertice);
        elsif posizioneVertice = 1 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 2), GetIdEsagono(idPartita, 3), idVertice);
        elsif posizioneVertice = 2 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 4), idVertice);
        elsif posizioneVertice = 3 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 5), idVertice);
        elsif posizioneVertice = 4 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 6), idVertice);
        elsif posizioneVertice = 5 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 1), GetIdEsagono(idPartita, 6), idVertice);
        elsif posizioneVertice = 6 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 2), GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 1), idVertice);
        elsif posizioneVertice = 7 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 2), idVertice);
        elsif posizioneVertice = 8 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 2), GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 10), idVertice);
        elsif posizioneVertice = 9 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 2), GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 10), idVertice);
        elsif posizioneVertice = 10 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 10), GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 3), idVertice);
        elsif posizioneVertice = 11 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 12), idVertice);
        elsif posizioneVertice = 12 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 4), idVertice);
        elsif posizioneVertice = 13 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 13), idVertice);
        elsif posizioneVertice = 14 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 14), idVertice);
        elsif posizioneVertice = 15 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 14), idVertice);
        elsif posizioneVertice = 16 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 14), idVertice);
        elsif posizioneVertice = 17 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 16), GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 15), idVertice);
        elsif posizioneVertice = 18 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 6), GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 16), idVertice);
        elsif posizioneVertice = 19 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 6), GetIdEsagono(idPartita, 16), idVertice);
        elsif posizioneVertice = 20 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 18), GetIdEsagono(idPartita, 6), GetIdEsagono(idPartita, 17), idVertice);
        elsif posizioneVertice = 21 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 1), GetIdEsagono(idPartita, 6), GetIdEsagono(idPartita, 18), idVertice);
        elsif posizioneVertice = 22 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 1), GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 18), idVertice);
        elsif posizioneVertice = 23 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 1), idVertice);
        elsif posizioneVertice = 24 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 20), GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 7), idVertice);
        elsif posizioneVertice = 25 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 20), GetIdEsagono(idPartita, 21), GetIdEsagono(idPartita, 8), idVertice);
        elsif posizioneVertice = 26 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 21), GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 8), idVertice);
        elsif posizioneVertice = 27 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 21), GetIdEsagono(idPartita, 22), GetIdEsagono(idPartita, 9), idVertice);
        elsif posizioneVertice = 28 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 22), GetIdEsagono(idPartita, 23), GetIdEsagono(idPartita, 9), idVertice);
        elsif posizioneVertice = 29 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 23), GetIdEsagono(idPartita, 10), idVertice);
        elsif posizioneVertice = 30 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 23), GetIdEsagono(idPartita, 24), GetIdEsagono(idPartita, 10), idVertice);
        elsif posizioneVertice = 31 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 24), GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 10), idVertice);
        elsif posizioneVertice = 32 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 24), GetIdEsagono(idPartita, 25), GetIdEsagono(idPartita, 11), idVertice);
        elsif posizioneVertice = 33 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 25), GetIdEsagono(idPartita, 26), idVertice);
        elsif posizioneVertice = 34 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 26), GetIdEsagono(idPartita, 12), idVertice);
        elsif posizioneVertice = 35 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 26), GetIdEsagono(idPartita, 27), idVertice);
        elsif posizioneVertice = 36 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 27), GetIdEsagono(idPartita, 13), idVertice);
        elsif posizioneVertice = 37 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 27), GetIdEsagono(idPartita, 28), idVertice);
        elsif posizioneVertice = 38 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 28), GetIdEsagono(idPartita, 29), idVertice);
        elsif posizioneVertice = 39 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 14), GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 29), idVertice);
        elsif posizioneVertice = 40 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 14), GetIdEsagono(idPartita, 29), GetIdEsagono(idPartita, 30), idVertice);
        elsif posizioneVertice = 41 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 14), GetIdEsagono(idPartita, 30), idVertice);
        elsif posizioneVertice = 42 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 30), GetIdEsagono(idPartita, 31), idVertice);
        elsif posizioneVertice = 43 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 32), GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 31), idVertice);
        elsif posizioneVertice = 44 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 16), GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 32), idVertice);
        elsif posizioneVertice = 45 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 33), GetIdEsagono(idPartita, 16), GetIdEsagono(idPartita, 32), idVertice);
        elsif posizioneVertice = 46 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 16), GetIdEsagono(idPartita, 33), idVertice);
        elsif posizioneVertice = 47 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 34), GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 33), idVertice);
        elsif posizioneVertice = 48 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 35), GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 34), idVertice);
        elsif posizioneVertice = 49 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 35), GetIdEsagono(idPartita, 18), GetIdEsagono(idPartita, 17), idVertice);
        elsif posizioneVertice = 50 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 36), GetIdEsagono(idPartita, 18), GetIdEsagono(idPartita, 35), idVertice);
        elsif posizioneVertice = 51 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 36), GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 18), idVertice);
        elsif posizioneVertice = 52 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 19), GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 36), idVertice);
        elsif posizioneVertice = 53 then
            InserisciVerticiEsagoni(GetIdEsagono(idPartita, 19), GetIdEsagono(idPartita, 20), GetIdEsagono(idPartita, 7), idVertice);
        end if;
    end;
    /

-- crea i vertici della mappa della partita creata
create or replace procedure CreaVertici(idPartita Partite.idPartita%type)
    is
        numeroVertici constant integer := 53;
        idVertice Vertici.idVertice%type;
    begin
        for i in 0..53 loop
            idVertice := incrementoIdVertice.nextVal;
            insert into Vertici values(idVertice, i);
            AssociaVerticiEsagoni(idPartita, idVertice, i);
        end loop;
    end;
    /

-- si occupa dell'inserimento dei lati in LatiEsagoni
create or replace procedure InserisciLatiEsagoni(idEsagonoUno Esagoni.idEsagono%type, idEsagonoDue Esagoni.idEsagono%type, idLato Lati.idLato%type)
    is
        idLatoEsagonoUno LatiEsagoni.idLatoEsagono%type := incrementoIdLatoEsagono.nextVal;
        idLatoEsagonoDue LatiEsagoni.idLatoEsagono%type := incrementoIdLatoEsagono.nextVal;
    begin
        insert all
            into LatiEsagoni values(idLatoEsagonoUno, idLato, idEsagonoUno)
            into LatiEsagoni values(idLatoEsagonoDue, idLato, idEsagonoDue)
        select * from dual; 
    end;
    /

-- associa un lato con gli esagoni confinanti
create or replace procedure AssociaLatiEsagoni(idPartita Partite.idPartita%type, idLato Lati.idLato%type, posizioneLato integer)
    is
    begin
        if posizioneLato = 0 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 2), idLato);
        elsif posizioneLato = 1 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 3), idLato);    
        elsif posizioneLato = 2 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 4), idLato);
        elsif posizioneLato = 3 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 5), idLato);
        elsif posizioneLato = 4 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 6), idLato);
        elsif posizioneLato = 5 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 0), GetIdEsagono(idPartita, 1), idLato);
        elsif posizioneLato = 6 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 1), GetIdEsagono(idPartita, 2), idLato);
        elsif posizioneLato = 7 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 2), idLato);
        elsif posizioneLato = 8 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 2), idLato);
        elsif posizioneLato = 9 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 2), GetIdEsagono(idPartita, 10), idLato);
        elsif posizioneLato = 10 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 2), GetIdEsagono(idPartita, 3), idLato);
        elsif posizioneLato = 11 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 10), GetIdEsagono(idPartita, 3), idLato);
        elsif posizioneLato = 12 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 11), idLato);
        elsif posizioneLato = 13 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 12), idLato);
        elsif posizioneLato = 14 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 3), GetIdEsagono(idPartita, 4), idLato);
        elsif posizioneLato = 15 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 12), idLato);
        elsif posizioneLato = 16 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 13), idLato);
        elsif posizioneLato = 17 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 4), GetIdEsagono(idPartita, 14), idLato);
        elsif posizioneLato = 18 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 4), idLato);
        elsif posizioneLato = 19 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 14), idLato);
        elsif posizioneLato = 20 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 15), idLato);
        elsif posizioneLato = 21 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 5), GetIdEsagono(idPartita, 16), idLato);
        elsif posizioneLato = 22 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 6), GetIdEsagono(idPartita, 5), idLato);
        elsif posizioneLato = 23 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 6), GetIdEsagono(idPartita, 16), idLato);
        elsif posizioneLato = 24 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 6), idLato);
        elsif posizioneLato = 25 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 18), GetIdEsagono(idPartita, 6), idLato);
        elsif posizioneLato = 26 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 1), GetIdEsagono(idPartita, 6), idLato);
        elsif posizioneLato = 27 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 18), GetIdEsagono(idPartita, 1), idLato);
        elsif posizioneLato = 28 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 1), idLato);
        elsif posizioneLato = 29 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 1), idLato);
        elsif posizioneLato = 30 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 8), idLato);
        elsif posizioneLato = 31 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 20), GetIdEsagono(idPartita, 8), idLato);
        elsif posizioneLato = 32 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 21), GetIdEsagono(idPartita, 8), idLato);
        elsif posizioneLato = 33 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 8), GetIdEsagono(idPartita, 9), idLato);
        elsif posizioneLato = 34 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 21), GetIdEsagono(idPartita, 9), idLato);
        elsif posizioneLato = 35 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 22), GetIdEsagono(idPartita, 9), idLato);
        elsif posizioneLato = 36 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 23), idLato);
        elsif posizioneLato = 37 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 9), GetIdEsagono(idPartita, 10), idLato);
        elsif posizioneLato = 38 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 23), GetIdEsagono(idPartita, 10), idLato);
        elsif posizioneLato = 39 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 10), GetIdEsagono(idPartita, 24), idLato);
        elsif posizioneLato = 40 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 10), GetIdEsagono(idPartita, 11), idLato);
        elsif posizioneLato = 41 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 24), GetIdEsagono(idPartita, 11), idLato);
        elsif posizioneLato = 42 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 25), idLato);
        elsif posizioneLato = 43 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 11), GetIdEsagono(idPartita, 26), idLato);
        elsif posizioneLato = 44 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 11), idLato);
        elsif posizioneLato = 45 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 26), idLato);
        elsif posizioneLato = 46 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 27), idLato);
        elsif posizioneLato = 47 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 12), GetIdEsagono(idPartita, 13), idLato);
        elsif posizioneLato = 48 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 27), idLato);
        elsif posizioneLato = 49 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 28), idLato);
        elsif posizioneLato = 50 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 13), GetIdEsagono(idPartita, 29), idLato);
        elsif posizioneLato = 51 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 14), GetIdEsagono(idPartita, 13), idLato);
        elsif posizioneLato = 52 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 14), GetIdEsagono(idPartita, 29), idLato);
        elsif posizioneLato = 53 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 14), GetIdEsagono(idPartita, 30), idLato);
        elsif posizioneLato = 54 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 14), idLato);
        elsif posizioneLato = 55 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 30), idLato);
        elsif posizioneLato = 56 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 15), GetIdEsagono(idPartita, 31), idLato);
        elsif posizioneLato = 57 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 32), GetIdEsagono(idPartita, 15), idLato);
        elsif posizioneLato = 58 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 16), GetIdEsagono(idPartita, 15), idLato);
        elsif posizioneLato = 59 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 16), GetIdEsagono(idPartita, 32), idLato);
        elsif posizioneLato = 60 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 33), GetIdEsagono(idPartita, 16), idLato);
        elsif posizioneLato = 61 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 16), idLato);
        elsif posizioneLato = 62 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 17), GetIdEsagono(idPartita, 33), idLato);
        elsif posizioneLato = 63 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 34), GetIdEsagono(idPartita, 17), idLato);
        elsif posizioneLato = 64 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 35), GetIdEsagono(idPartita, 17), idLato);
        elsif posizioneLato = 65 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 18), GetIdEsagono(idPartita, 17), idLato);
        elsif posizioneLato = 66 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 35), GetIdEsagono(idPartita, 18), idLato);
        elsif posizioneLato = 67 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 36), GetIdEsagono(idPartita, 18), idLato);
        elsif posizioneLato = 68 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 7), GetIdEsagono(idPartita, 18), idLato);
        elsif posizioneLato = 69 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 36), GetIdEsagono(idPartita, 7), idLato);
        elsif posizioneLato = 70 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 19), GetIdEsagono(idPartita, 7), idLato);
        elsif posizioneLato = 71 then
            InserisciLatiEsagoni(GetIdEsagono(idPartita, 20), GetIdEsagono(idPartita, 7), idLato);
        end if;
    end;
    /

-- crea i lati della mappa della partita creata
create or replace procedure CreaLati(idPartita Partite.idPartita%type)
    is
        idLato Lati.idLato%type;
    begin
        for i in 0..71 loop
            idLato := incrementoIdLato.nextVal;
            insert into Lati values(idLato, i);
            AssociaLatiEsagoni(idPartita, idLato, i);
        end loop;
    end;
    /

-- inserisce in VerticiLati 3 lati associati a un vertice
create or replace procedure InserisciVerticiLatiTre(idVertice Vertici.idVertice%type, idLatoUno Lati.idLato%type, idLatoDue Lati.idLato%type, idLatoTre Lati.idLato%type)
    is
        idVerticeLatoUno VerticiLati.idVerticeLato%type := incrementoIdVerticeLato.nextVal;
        idVerticeLatoDue VerticiLati.idVerticeLato%type := incrementoIdVerticeLato.nextVal;
        idVerticeLatoTre VerticiLati.idVerticeLato%type := incrementoIdVerticeLato.nextVal;
    begin
        insert all
            into VerticiLati values(idVerticeLatoUno, idVertice, idLatoUno) 
            into VerticiLati values(idVerticeLatoDue, idVertice, idLatoDue)
            into VerticiLati values(idVerticeLatoTre, idVertice, idLatoTre)
        select * from dual;
    end;
    /

-- inserisce in VerticiLati 2 lati associati a un vertice
create or replace procedure InserisciVerticiLatiDue(idVertice Vertici.idVertice%type, idLatoUno Lati.idLato%type, idLatoDue Lati.idLato%type)
    is
        idVerticeLatoUno VerticiLati.idVerticeLato%type := incrementoIdVerticeLato.nextVal;
        idVerticeLatoDue VerticiLati.idVerticeLato%type := incrementoIdVerticeLato.nextVal;
    begin
        insert all
            into VerticiLati values(idVerticeLatoUno, idVertice, idLatoUno) 
            into VerticiLati values(idVerticeLatoDue, idVertice, idLatoDue)
        select * from dual;
    end;
    /

-- associa i Vertici con i lati di una partita
create or replace procedure AssociaVerticiLati(idPartita Partite.idPartita%type)
    is
    begin
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 0), GetIdLato(idPartita, 5), GetIdLato(idPartita, 6), GetIdLato(idPartita, 0));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 1), GetIdLato(idPartita, 1), GetIdLato(idPartita, 0), GetIdLato(idPartita, 10));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 2), GetIdLato(idPartita, 1), GetIdLato(idPartita, 2), GetIdLato(idPartita, 14));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 3), GetIdLato(idPartita, 3), GetIdLato(idPartita, 2), GetIdLato(idPartita, 18));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 4), GetIdLato(idPartita, 4), GetIdLato(idPartita, 3), GetIdLato(idPartita, 22));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 5), GetIdLato(idPartita, 5), GetIdLato(idPartita, 4), GetIdLato(idPartita, 26));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 6), GetIdLato(idPartita, 29), GetIdLato(idPartita, 7), GetIdLato(idPartita, 6));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 7), GetIdLato(idPartita, 33), GetIdLato(idPartita, 7), GetIdLato(idPartita, 8));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 8), GetIdLato(idPartita, 8), GetIdLato(idPartita, 37), GetIdLato(idPartita, 9));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 9), GetIdLato(idPartita, 9), GetIdLato(idPartita, 10), GetIdLato(idPartita, 11));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 10), GetIdLato(idPartita, 11), GetIdLato(idPartita, 12), GetIdLato(idPartita, 40));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 11), GetIdLato(idPartita, 12), GetIdLato(idPartita, 13), GetIdLato(idPartita, 44));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 12), GetIdLato(idPartita, 14), GetIdLato(idPartita, 13), GetIdLato(idPartita, 15));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 13), GetIdLato(idPartita, 15), GetIdLato(idPartita, 16), GetIdLato(idPartita, 47));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 14), GetIdLato(idPartita, 17), GetIdLato(idPartita, 16), GetIdLato(idPartita, 51));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 15), GetIdLato(idPartita, 18), GetIdLato(idPartita, 19), GetIdLato(idPartita, 17));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 16), GetIdLato(idPartita, 20), GetIdLato(idPartita, 19), GetIdLato(idPartita, 54));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 17), GetIdLato(idPartita, 21), GetIdLato(idPartita, 20), GetIdLato(idPartita, 58));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 18), GetIdLato(idPartita, 21), GetIdLato(idPartita, 22), GetIdLato(idPartita, 23));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 19), GetIdLato(idPartita, 23), GetIdLato(idPartita, 24), GetIdLato(idPartita, 61));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 20), GetIdLato(idPartita, 24), GetIdLato(idPartita, 25), GetIdLato(idPartita, 65));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 21), GetIdLato(idPartita, 25), GetIdLato(idPartita, 26), GetIdLato(idPartita, 27));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 22), GetIdLato(idPartita, 27), GetIdLato(idPartita, 28), GetIdLato(idPartita, 68));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 23), GetIdLato(idPartita, 28), GetIdLato(idPartita, 29), GetIdLato(idPartita, 30));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 24), GetIdLato(idPartita, 30), GetIdLato(idPartita, 31), GetIdLato(idPartita, 71));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 25), GetIdLato(idPartita, 31), GetIdLato(idPartita, 32));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 26), GetIdLato(idPartita, 32), GetIdLato(idPartita, 33), GetIdLato(idPartita, 34));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 27), GetIdLato(idPartita, 34), GetIdLato(idPartita, 35));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 28), GetIdLato(idPartita, 35), GetIdLato(idPartita, 36));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 29), GetIdLato(idPartita, 36), GetIdLato(idPartita, 37), GetIdLato(idPartita, 38));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 30), GetIdLato(idPartita, 38), GetIdLato(idPartita, 39));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 31), GetIdLato(idPartita, 39), GetIdLato(idPartita, 40), GetIdLato(idPartita, 41));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 32), GetIdLato(idPartita, 41), GetIdLato(idPartita, 42));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 33), GetIdLato(idPartita, 42), GetIdLato(idPartita, 43));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 34), GetIdLato(idPartita, 43), GetIdLato(idPartita, 44), GetIdLato(idPartita, 45));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 35), GetIdLato(idPartita, 45), GetIdLato(idPartita, 46));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 36), GetIdLato(idPartita, 46), GetIdLato(idPartita, 47), GetIdLato(idPartita, 48));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 37), GetIdLato(idPartita, 48), GetIdLato(idPartita, 49));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 38), GetIdLato(idPartita, 49), GetIdLato(idPartita, 50));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 39), GetIdLato(idPartita, 50), GetIdLato(idPartita, 51), GetIdLato(idPartita, 52));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 40), GetIdLato(idPartita, 52), GetIdLato(idPartita, 53));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 41), GetIdLato(idPartita, 53), GetIdLato(idPartita, 54), GetIdLato(idPartita, 55));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 42), GetIdLato(idPartita, 55), GetIdLato(idPartita, 56));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 43), GetIdLato(idPartita, 56), GetIdLato(idPartita, 57));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 44), GetIdLato(idPartita, 57), GetIdLato(idPartita, 58), GetIdLato(idPartita, 59));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 45), GetIdLato(idPartita, 60), GetIdLato(idPartita, 59));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 46), GetIdLato(idPartita, 60), GetIdLato(idPartita, 61), GetIdLato(idPartita, 62));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 47), GetIdLato(idPartita, 62), GetIdLato(idPartita, 63));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 48), GetIdLato(idPartita, 63), GetIdLato(idPartita, 64));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 49), GetIdLato(idPartita, 64), GetIdLato(idPartita, 65), GetIdLato(idPartita, 66));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 50), GetIdLato(idPartita, 66), GetIdLato(idPartita, 67));
        InserisciVerticiLatiTre(GetIdVertice(idPartita, 51), GetIdLato(idPartita, 67), GetIdLato(idPartita, 68), GetIdLato(idPartita, 69));        
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 52), GetIdLato(idPartita, 69), GetIdLato(idPartita, 70));
        InserisciVerticiLatiDue(GetIdVertice(idPartita, 53), GetIdLato(idPartita, 70), GetIdLato(idPartita, 71));
    end;
    /

-- elimina gli esagoni terreno di una partita
create or replace procedure EliminaEsagoniTerreno(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select E.idEsagonoTerreno, E.idPartita
                  from Esagoni E, EsagoniTerreno ET
                  where E.idEsagonoTerreno = ET.idEsagonoTerreno)
        loop
            if i.idPartita = idPartita then
                delete from EsagoniTerreno ET where ET.idEsagonoTerreno = i.idEsagonoTerreno;
            end if;
        end loop; 
    end;
    /

-- elimina gli esagoni porti di una partita
create or replace procedure EliminaEsagoniPorto(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select EP.idEsagonoPorto, E.idPartita
                  from Esagoni E, EsagoniMare EM, EsagoniPorto EP
                  where E.idEsagonoMare = EM.idEsagonoMare and
                        EM.idEsagonoPorto = EP.idEsagonoPorto)
        loop
            if i.idPartita = idPartita then
                delete from EsagoniPorto EP where i.idEsagonoPorto = EP.idEsagonoPorto;
            end if;
        end loop;   
    end;
    /

-- elimina gli esagoni mare aperto di una partita
create or replace procedure EliminaEsagoniMareAperto(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select EMA.idEsagonoMareAperto, E.idPartita
                  from Esagoni E, EsagoniMare EM, EsagoniMareAperto EMA
                  where E.idEsagonoMare = EM.idEsagonoMare and
                        EM.idEsagonoMareAperto = EMA.idEsagonoMareAperto)
        loop
            if i.idPartita = idPartita then
                delete from EsagoniMareAperto EMA where EMA.idEsagonoMareAperto = i.idEsagonoMareAperto;
            end if;
        end loop;
    end;
    /

-- elimina gli esagoni mare di una partita
create or replace procedure EliminaEsagoniMare(idPartita Partite.idPartita%type)
    is
    begin
        eliminaEsagoniPorto(idPartita);
        eliminaEsagoniMareAperto(idPartita);
    end;
    /

-- elimina gli esagoni di una partita
create or replace procedure EliminaEsagoni(idPartita Partite.idPartita%type)
    is
    begin
        EliminaEsagoniTerreno(idPartita);
        EliminaEsagoniMare(idPartita);
    end;
    /


-- elimina i vertici di una partita
create or replace procedure EliminaVertici(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select VE.idVertice, E.idPartita
                  from Esagoni E, VerticiEsagoni VE
                  where E.idEsagono = VE.idEsagono)
        loop
            if i.idPartita = idPartita then
                delete from Vertici V where V.idVertice = i.idVertice;
            end if;
        end loop;
    end;
    /

-- elimina i lati di una partita
create or replace procedure EliminaLati(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select LE.idLato, E.idPartita
                  from Esagoni E, LatiEsagoni LE
                  where E.idEsagono = LE.idEsagono)
        loop
            if i.idPartita = idPartita then
                delete from Lati L where L.idLato = i.idLato;
            end if;
        end loop;
    end;
    /

-- elimina le carte progresso di una partita
create or replace procedure EliminaCarteProgresso(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select CP.idCartaProgresso, T.idPartita 
                  from Turni T, CarteTurni CT, Carte C, CarteSviluppo CS, CarteProgresso CP
                  where T.idTurno = CT.idTurno and
                        CT.idCarta = C.idCarta and
                        C.idCartaSviluppo = CS.idCartaSviluppo and
                        CS.idCartaProgresso = CP.idCartaProgresso)
        loop
            if i.idPartita = idPartita then
                delete from CarteProgresso CP where CP.idCartaProgresso = i.idCartaProgresso; 
            end if;
        end loop; 
    end;
    /

-- elimina le carte cavaliere di una partita
create or replace procedure EliminaCarteCavaliere(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select CC.idCartaCavaliere, T.idPartita 
                  from Turni T, CarteTurni CT, Carte C, CarteSviluppo CS, CarteCavaliere CC
                  where T.idTurno = CT.idTurno and
                        CT.idCarta = C.idCarta and
                        C.idCartaSviluppo = CS.idCartaSviluppo and
                        CS.idCartaCavaliere = CC.idCartaCavaliere)
        loop
            if i.idPartita = idPartita then
                delete from CarteCavaliere CC where CC.idCartaCavaliere = i.idCartaCavaliere; 
             end if;
        end loop; 
    end;
    /   

-- elimina le carte cavaliere di una partita
create or replace procedure EliminaCartePuntoVittoria(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select CPV.idCartaPuntoVittoria, T.idPartita 
                  from Turni T, CarteTurni CT, Carte C, CarteSviluppo CS, CartePuntoVittoria CPV
                  where T.idTurno = CT.idTurno and
                        CT.idCarta = C.idCarta and
                        C.idCartaSviluppo = CS.idCartaSviluppo and
                        CS.idCartaPuntoVittoria = CPV.idCartaPuntoVittoria)
        loop
            if i.idPartita = idPartita then
                delete from CartePuntoVittoria CPV where CPV.idCartaPuntoVittoria = i.idCartaPuntoVittoria; 
             end if;
        end loop; 
    end;
    / 

-- elimina le carte sviluppo di una partita
create or replace procedure EliminaCarteSviluppo(idPartita Partite.idPartita%type)
    is
    begin
        EliminaCartePuntoVittoria(idPartita);
        EliminaCarteCavaliere(idPartita);
        EliminaCarteProgresso(idPartita);
    end;
    /

-- elimina le carte risorsa di una partita
create or replace procedure EliminaCarteRisorsa(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select T.idPartita, CR.idCartaRisorsa
                  from Turni T, CarteTurni CT, Carte C, CarteRisorsa CR
                  where T.idTurno = CT.idTurno and
                        CT.idCarta = C.idCarta and
                        C.idCartaRisorsa = CR.idCartaRisorsa)
        loop
            if i.idPartita = idPartita then
                delete from CarteRisorsa CR where CR.idCartaRisorsa = i.idCartaRisorsa;
            end if;
        end loop; 
    end;
    /

-- elimina le carte di una partita
create or replace procedure EliminaCarte(idPartita Partite.idPartita%type)
    is
    begin
        EliminaCarteRisorsa(idPartita);
        EliminaCarteSviluppo(idPartita);
    end;
    /

-- elimina gli scambi porto di una partita
create or replace procedure EliminaScambiPorto(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select T.idPartita, SP.idScambioPorto
                  from Turni T, Scambi S, ScambiPorto SP
                  where T.idTurno = S.idTurno and
                        S.idScambioPorto = SP.idScambioPorto)
        loop
            if i.idPartita = idPartita then
                delete from ScambiPorto SP where SP.idScambioPorto = i.idScambioPorto;
            end if;
        end loop;  
    end;
    /

-- elimina gli scambi gioco di una partita
create or replace procedure EliminaScambiGioco(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select T.idPartita, SG.idScambioGioco
                  from Turni T, Scambi S, ScambiGioco SG
                  where T.idTurno = S.idTurno and
                        S.idScambioGioco = SG.idScambioGioco)
        loop
            if i.idPartita = idPartita then
                delete from ScambiGioco SG where SG.idScambioGioco = i.idScambioGioco;
            end if;
        end loop;  
    end;
    /

-- elimina gli scambi giocatore di una partita
create or replace procedure EliminaScambiGiocatore(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select T.idPartita, SG.idScambioGiocatore
                  from Turni T, Scambi S, ScambiGiocatore SG
                  where T.idTurno = S.idTurno and
                        S.idScambioGiocatore = SG.idScambioGiocatore)
        loop
            if i.idPartita = idPartita then
                delete from ScambiGiocatore SG where SG.idScambioGiocatore = i.idScambioGiocatore;
            end if;
        end loop;  
    end;
    /

-- elimina gli scambi di una partita
create or replace procedure EliminaScambi(idPartita Partite.idPartita%type)
    is
    begin
        EliminaScambiPorto(idPartita);
        EliminaScambiGioco(idPartita);
        EliminaScambiGiocatore(idPartita);
    end;
    /

-- elimina le citta colonie di una partita
create or replace procedure EliminaCittaColonie(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select T.idPartita, TCC.idCittaColonia
                  from Turni T, TurniCittaColonie TCC
                  where T.idTurno = TCC.idTurno)
        loop
            if i.idPartita = idPartita then
                delete from CittaColonie CC where CC.idCittaColonia = i.idCittaColonia;
            end if;
        end loop;
    end;
    /

-- elimina i turni di una partita
create or replace procedure EliminaTurni(idPartita Partite.idPartita%type)
    is
    begin
        for i in (select T.idTurno, T.idPartita
                  from Turni T)
        loop
            if i.idPartita = idPartita then
                delete from Turni T where T.idTurno = i.idTurno;
            end if;
        end loop;
    end;
    /

-- si occupa di aggiungere una carta risorsa
create or replace procedure aggiungiRisorsa(idTurno Turni.idTurno%type, idGiocatore Giocatori.idGiocatore%type, tipo varchar)
    is
        idCartaRisorsa CarteRisorsa.idCartaRisorsa%type := incrementoIdCartaRisorsa.nextVal;
        idCarta Carte.idCarta%type := incrementoIdCarta.nextVal;
        idCartaTurno CarteTurni.idCartaTurno%type := incrementoIdCartaTurno.nextVal;
        tipoCarta varchar(30);
    begin
        if tipo = 'Campo' then
            tipoCarta := 'Grano';
        elsif tipo = 'Montagna' then
            tipoCarta := 'Minerale';
        elsif tipo = 'Foresta' then
            tipoCarta := 'Legno';
        elsif tipo = 'Pascolo' then
            tipoCarta := 'Lana';
        elsif tipo = 'Collina' then
            tipoCarta := 'Argilla';
        end if;
        insert into CarteRisorsa values(idCartaRisorsa, tipoCarta);
        insert into Carte values(idCarta, idCartaRisorsa, null, idGiocatore);
        insert into CarteTurni values(idCartaTurno, idCarta, idTurno);
    end;
    /

-- copia le carte del penultimo turno nell'ultimo
create or replace procedure CopiaCarte(idTurnoDestinazione Turni.idTurno%type, idPartita Partite.idPartita%type)
    is
        idTurnoPenultimo Turni.idTurno%type;
        idCartaTurno CarteTurni.idCartaTurno%type;
    begin
        idTurnoPenultimo := getPenultimoTurno(idTurnoDestinazione, idPartita);
        for i in (select CT.idTurno, CT.idCarta
                  from CarteTurni CT)
        loop
            if i.idTurno = idTurnoPenultimo then
                idCartaTurno := incrementoIdCartaTurno.nextVal;
                insert into CarteTurni values(idCartaTurno, i.idCarta, idTurnoDestinazione);
            end if;
        end loop;
    end;
    /

-- copia le cittaColonie del penultimo turno di un giocatore nell'ultimo
create or replace procedure CopiaCittaColonie(idGiocatore Giocatori.idGiocatore%type, idPartita Partite.idPartita%type, giro integer, idTurnoDestinazione Turni.idTurno%type)
    is
        idTurnoPenultimo Turni.idTurno%type;
        idTurnoCittaColonia TurniCittaColonie.idTurnoCittaColonia%type;
    begin
        idTurnoPenultimo := getIdTurno(idGiocatore, idPartita, giro);
        for i in (select TCC.idTurno, TCC.idCittaColonia
                  from TurniCittaColonie TCC)
        loop
            if i.idTurno = idTurnoPenultimo then
                idTurnoCittaColonia := incrementoIdTurnoCittaColonia.nextVal;
                insert into TurniCittaColonie values (idTurnoCittaColonia, i.idCittaColonia, idTurnoDestinazione);
            end if;
        end loop;
    end;
    /

-- copia le strade del penultimo turno di un giocatore nell'ultimo
create or replace procedure CopiaStrade(idGiocatore Giocatori.idGiocatore%type, idPartita Partite.idPartita%type, giro integer, idTurnoDestinazione Turni.idTurno%type)
    is
        idTurnoPenultimo Turni.idTurno%type;
        idStradaTurno StradeTurni.idStradaTurno%type;
    begin
        idTurnoPenultimo := getIdTurno(idGiocatore, idPartita, giro);
        for i in (select ST.idTurno, ST.idStrada
                  from StradeTurni ST)
        loop
            if i.idTurno = idTurnoPenultimo then
                idStradaTurno := incrementoIdStradaTurno.nextVal;
                insert into StradeTurni values (idStradaTurno, i.idStrada, idTurnoDestinazione);
            end if;
        end loop;
    end;
    /

-- copia le risorse nell'ultimo turno
create or replace procedure CopiaRisorse(idGiocatore Giocatori.idGiocatore%type, idPartita Partite.idPartita%type, idTurno Turni.idTurno%type, giro integer)
    is
    begin
        CopiaCarte(idTurno, idPartita);
        CopiaCittaColonie(idGiocatore, idPartita, giro - 1, idTurno);
        CopiaStrade(idGiocatore, idPartita, giro - 1, idTurno); 
    end;
    /

-- aggiunge uno ScambioCartaRisorsaDate
create or replace procedure AggiungiScambiCarteRisorsaDate(idScambio Scambi.idScambio%type, idCartaRisorsa CarteRisorsa.idCartaRisorsa%type)
    is
        idScambioCartaRisorsaDate ScambiCarteRisorsaDate.idScambioCartaRisorsaData%type := incrementoIdScamCarRisData.nextVal;
    begin
        insert into ScambiCarteRisorsaDate values(idScambioCartaRisorsaDate, idCartaRisorsa, idScambio);
    end;
    /
    
-- aggiunge uno ScambioCartaRisorsaRicevute
create or replace procedure AggiungiScambiCarteRisorsaRic(idScambio Scambi.idScambio%type, idCartaRisorsa CarteRisorsa.idCartaRisorsa%type)
    is
        idScambioCartaRisorsaRicevuta ScambiCarteRisorsaRicevute.idScambioCartaRisorsaRicevuta%type := incrementoIdScamCarRisRicevuta.nextVal;
    begin
        insert into ScambiCarteRisorsaRicevute values(idScambioCartaRisorsaRicevuta, idCartaRisorsa, idScambio);
    end;
    /

-- aggiunge una carta cavaliere
create or replace procedure AggiungiCartaCavaliere(idTurno Turni.idTurno%type, idGiocatore Giocatori.idGiocatore%type)
    is
        idCarta Carte.idCarta%type := incrementoIdCarta.nextVal;
        idCartaSviluppo CarteSviluppo.idCartaSviluppo%type := incrementoIdCartaSviluppo.nextVal;
        idCartaCavaliere CarteCavaliere.idCartaCavaliere%type := incrementoIdCartaCavaliere.nextVal;
        idCartaTurno CarteTurni.idCartaTurno%type := incrementoIdCartaTurno.nextVal;
    begin
        insert into CarteCavaliere values(idCartaCavaliere);
        insert into CarteSviluppo values(idCartaSviluppo, null, null, idCartaCavaliere);
        insert into Carte values(idCarta, null, idCartaSviluppo, idGiocatore);
        insert into CarteTurni values(idCartaTurno, idCarta, idTurno);
    end;
    /

-- aggiunge una carta punto vittoria
create or replace procedure AggiungiCartaPuntoVittoria(idTurno Turni.idTurno%type, idGiocatore Giocatori.idGiocatore%type)
    is
        idCarta Carte.idCarta%type := incrementoIdCarta.nextVal;
        idCartaSviluppo CarteSviluppo.idCartaSviluppo%type := incrementoIdCartaSviluppo.nextVal;
        idCartaPuntoVittoria CartePuntoVittoria.idCartaPuntoVittoria%type := incrementoIdCartaPuntoVittoria.nextVal;
        idCartaTurno CarteTurni.idCartaTurno%type := incrementoIdCartaTurno.nextVal;
    begin
        insert into CartePuntoVittoria values(idCartaPuntoVittoria);
        insert into CarteSviluppo values(idCartaSviluppo, null, idCartaPuntoVittoria, null);
        insert into Carte values(idCarta, null, idCartaSviluppo, idGiocatore);
        insert into CarteTurni values(idCartaTurno, idCarta, idTurno);
    end;
    /

-- aggiunge una carta progresso
create or replace procedure AggiungiCartaProgresso(idTurno Turni.idTurno%type, idGiocatore Giocatori.idGiocatore%type)
    is
        idCarta Carte.idCarta%type := incrementoIdCarta.nextVal;
        idCartaSviluppo CarteSviluppo.idCartaSviluppo%type := incrementoIdCartaSviluppo.nextVal;
        idCartaProgresso CarteProgresso.idCartaProgresso%type := incrementoIdCartaProgresso.nextVal;
        idCartaTurno CarteTurni.idCartaTurno%type := incrementoIdCartaTurno.nextVal;
    begin
        insert into CarteProgresso values(idCartaProgresso);
        insert into CarteSviluppo values(idCartaSviluppo, idCartaProgresso, null, null);
        insert into Carte values(idCarta, null, idCartaSviluppo, idGiocatore);
        insert into CarteTurni values(idCartaTurno, idCarta, idTurno);
    end;
    /

-- aggiorna una colonia in città
create or replace procedure AggiornaACitta(idCittaColonia CittaColonie.idCittaColonia%type, idTurno Turni.idTurno%type)
    is
    begin
        for i in (select CC.idCittaColonia
                  from CittaColonie CC)
        loop
            if i.idCittaColonia = idCittaColonia then
                update CittaColonie CC set CC.aggiornataDalTurno = idTurno where CC.idCittaColonia = i.idCittaColonia;
            end if;
        end loop;
    end;
    /

-- aggiunge delle risorse in base alla posizione della cittaColonie
create or replace procedure AggiungiRisorseCittaColonie(idTurno Turni.idTurno%type, idVertice Vertici.idVertice%type, idGiocatore Giocatori.idGiocatore%type) 
    is
    begin
        for i in (select V.idVertice, ET.tipo
                  from Vertici V, VerticiEsagoni VE, Esagoni E, EsagoniTerreno ET
                  where V.idVertice = VE.idVertice and
                        VE.idEsagono = E.idEsagono and
                        E.idEsagonoTerreno = ET.idEsagonoTerreno)
        loop
            if i.idVertice = idVertice then
                aggiungiRisorsa(idTurno, idGiocatore, i.tipo);
            end if;
        end loop;
    end;
    /

-- TRIGGERS
-- crea una mappa da gioco per una partita inserita
create or replace trigger VincoloMappaDefault
    after insert on Partite
    for each row
    declare
    begin
        CreaMappaDefault(:new.idPartita);
        CreaVertici(:new.idPartita);
        CreaLati(:new.idPartita);
        AssociaVerticiLati(:new.idPartita);
    end;
    /

-- controlla che ci sia un vertice di spazio tra le cittaColonie
create or replace trigger VincoloPosizioneCittaColonia
    before insert on CittaColonie
    for each row
    declare
        counter integer := 0;
        posizioneNonValida exception;
    begin
        for i in (select VL.idLato
                  from VerticiLati VL
                  where VL.idVertice = :new.idVertice)
        loop
            for y in (select VL.idVertice
                      from VerticiLati VL
                      where VL.idLato = i.idLato and
                            VL.idVertice <> :new.idVertice)
                loop
                    select count(*) into counter
                    from CittaColonie CC
                    where CC.idVertice = y.idVertice;
                    if counter <> 0 then
                        raise posizioneNonValida;
                    end if;
                end loop;
        end loop;
    end;
    /

-- si occupa di eliminare gli elementi di una partita
create or replace trigger VincoloEliminaPartita
    before delete on Partite
    for each row
    declare
    begin
        EliminaCarte(:old.idPartita);
        EliminaScambi(:old.idPartita);
        EliminaCittaColonie(:old.idPartita);
        EliminaLati(:old.idPartita);
        EliminaVertici(:old.idPartita);
        EliminaEsagoni(:old.idPartita);
        EliminaTurni(:old.idPartita);
    end;
    /

-- aggiunge le risorse dopo un lancio di dadi
create or replace trigger VincoloCreaRisorse
    after insert on LanciDadi
    for each row
    declare
        idPartita Partite.idPartita%type;
        idTurno Turni.idTurno%type;
    begin
        idPartita := getIdPartita(:new.idTurno);
        for i in (select GP.idGiocatore, GP.idPartita
                  from GiocatoriPartite GP)
        loop
            if i.idPartita = idPartita then
                idTurno := getMaxTurno(i.idGiocatore, idPartita);
                for y in (select CC.idCittaColonia, ET.tipo, TCC.idTurno, ET.numero, E.idEsagono
                        from TurniCittaColonie TCC, CittaColonie CC, Vertici V, VerticiEsagoni VE, Esagoni E, EsagoniTerreno ET
                        where TCC.idCittaColonia = CC.idCittaColonia and
                                CC.idVertice = V.idVertice and
                                V.idVertice = VE.idVertice and
                                VE.idEsagono = E.idEsagono and
                                E.idEsagonoTerreno = ET.idEsagonoTerreno)
                loop
                    if idTurno is not null and y.idTurno = idTurno and (:new.dadoUno + :new.dadoDue) = y.numero and y.idEsagono <> getIdEsagonoBrigante(idTurno) then
                        if isCitta(y.idCittaColonia, y.idTurno) = 1 then
                            aggiungiRisorsa(:new.idTurno, i.idGiocatore, y.tipo);
                            aggiungiRisorsa(:new.idTurno, i.idGiocatore, y.tipo);
                        else
                            aggiungiRisorsa(:new.idTurno, i.idGiocatore, y.tipo);
                        end if;
                    end if;
                end loop;
            end if;
        end loop;
    end;
    /
