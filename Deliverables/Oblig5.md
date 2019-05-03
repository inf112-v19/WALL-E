# Oblig 4

## Deloppgave 1
##Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? 
Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?
* Vi synes teamet har tatt gode valg, vi har truffet godt med fordeling av roller og oppgaver. Vi har forskjellig kompetanse, og har vært flinke med tanke på det til å ta i bruk par-programmering og inndeling av oppgaver. 
##Hvordan er gruppedynamikken?
* Gruppedynamikken er fortsatt like god som den har vært fra start! Alle viser engasjement og samarbeidet går godt. Alle er flinke til å bidra at ting blir gjort. 
## Hvordan fungerer kommunikasjonen for dere?
* Kommunikasjonen fungerer veldig bra. Vi vurderte tidligere i prosjektet å ta i bruk Slack, men vi føler vi har alt vi trenger på facebook så vi har bare fortsatt med å bruke det. Alle er flinke til å svare på messenger og flinke til å være tilgjengelig. Vi får gått igjennom mye både på mandagsmøtene og gruppetimene på onsdager. Vi er flinke til å spørre om hjelp dersom man står fast, og flinke til å hjelpe og samarbeide. 

###Utfør et retrospektiv før leveranse med fokus på hele prosjektet:
##Hva justerte dere underveis, og hvorfor? Ble det bedre?
* Underveis i prosjektet ble vi mye bedre på å lage og bruke brancher for å unngå problemer med masteren, det ble da lettere å jobbe individuelt med forskjellige oppgaver. 
* Vi har også i mye større grad tatt i bruk pull requests på github senere i prosjektet. Dette gjorde vi også for å unngå problemer med masteren. Spesielt når man jobber parallelt i forskjellige brancher merket vi at det er en fordel å jobbe sammen og reviewe hverandres pull requests.

* Ettersom spillet ble mer omfattende ble vi bedre på å fordele oppgaver. Det gjorde at det ble lettere å jobbe på egenhånd i mellom møtene og gruppetimene.

* I starten var vi ikke så flinke til å dele inn oppgaver i små og konkrete oppgaver, dette er også noe vi ble bedre på ettersom spiller ble mer omfattende. Da ble det lettere å kunne forholde seg til en konkret ting om gangen.
* Vi ble til dels også gode, og fikk forståelse for, ulike aspekter ved rammeverket vi ble tildelt (LibGDX). På grunn av denne utviklingen slet vi med å hjelpe hverandre med hverandres ulike ansvarsområder. Litt etter litt ble forståelsen for rammeverket helhetlig forbedret, og vi kunne hjelpe hverandre mer. Vi skulle gjerne vært flinkere til å forklare hvordan ting fungerte.

##Hva har fungert best, og hvorfor? (Hva er dere mest fornøyde med?) 
* Vi er mest fornøyd med fremgangen vi har hatt gjennom hele prosjektet. De fleste i teamet hadde aldri brukt github før, og alle har utviklet en god forståelse for hvordan det brukes. LibGDX var helt nytt for oss, også her har vi hatt veldig stor fremgang. Utover i prosjektet har vi i tillegg merket at samarbeidet har blitt bedre, både i person på møtene og ved bruk av github.

## Hvis dere skulle fortsatt med prosjektet, hva ville dere justert?
* Om vi skulle fortsette med prosjektet ville vi prøvd å bli bedre på å bruke projectboardet på github. Vi hadde da fått en bedre oversikt over hvilke features som skal legges til og eventuelle bugs som skulle fikses. Det hadde nok også blitt lettere å gå videre til en ny oppgave når man blir ferdig med den man jobber med, og dermed effektivisert arbeidet.
* Vi ville også lagt mer vekt på tester, det er noe som har blitt litt nedprioritert så langt i utviklingsprosessen. Siden vi måtte lære oss libGDX samtidig som vi jobbet med prosjektet ble det konstant gjort forbedringer og justeringer på hvordan ting ble løst i koden ettersom vi ble tryggere på libGDX. Derfor tror vi ikke at TDD (Test-driven Development) var noe som hadde vært naturlig å ta i bruk, men vi har definitivt forbedringspotensiale når det kommer til å skrive tester.
* Vi ville også ha flyttet kommunikasjonen over fra messenger til slack. Vi synes at messenger har fungert som en god kommunikasjons-kilde gjennom prosjektet, men ved videre utvikling ville vi tatt i bruk slack. 

## Hva er det viktigste dere har lært?
* Det viktigste vi har lært er viktigheten av kommunikasjon når man jobber med slike prosjekter. God kommunikasjon hjelper til å øke effektivitet og kvalitet av arbeidet. Naturligvis blir også samarbeidet mye bedre hvis kommunikasjonen er god. 

## Hvilke krav har vi?
* Man må kunne spille en komplett runde 
* Man må kunne vinne spillet spillet ved å besøke siste flagg (fullføre et spill) 
* Det skal være lasere på brettet 
* Fungerende samlebånd på brettet som flytter robotene 
* Det skal være hull på brettet 
* Lokal multiplayer med støtte til opptil 8 spillere.
* Kollisjon mellom spillere
* Fungerende “dum” CPU slik at det går ann å spille alene.
* Skademekanismer (spilleren får færre kort ved skade og kort blir låst i minnet)
* Fungerende laser
* Power down
* Fungerende gyroer på brettet som flytter robotene 
* Vinner hvis man er den eneste som lever

##Legg til liste over ferdigstilte krav.
* Man må kunne spille en komplett runde 
* Man må kunne vinne spillet spillet ved å besøke siste flagg (fullføre et spill) 
* Det skal være lasere på brettet 
* Fungerende samlebånd på brettet som flytter robotene 
* Lokal multiplayer med støtte til opptil 8 spillere.
* Fungerende “dum” CPU slik at det går ann å spille alene.
* Skademekanismer (spilleren får færre kort ved skade og kort blir låst i minnet)
* Funksjonaliteten til Laser fungerer, men den vises ikke.
* Power down
* “Wrenches” er checkpoints.
* Fungerende gyroer på brettet som flytter robotene 
* “Wrenches” er checkpoints.
* Vinner hvis man er den eneste som lever

###Kjente feil og svakheter: 
Eksplosjoner spilles av på feil tidspunkt. De fungerer når man går rundt med piltastene, men ikke når actor flyttes med kort.
Actor hopper rett til den endelige destinasjonen i stedet for å gå dit steg for steg.
Spillet kan krasje når man trykker der det pleide å være et kort.
Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett å teste koden. Under vurdering kommer koden også til å brukertestes.
Produktet skal fungerer på Linux ( Ubuntu / Debian ), Windows og OSX.
Dokumentér også hvordan testene skal kjøres.
Kodekvalitet og testdekning vektlegges. Merk at testene dere skriver skal brukes i produktet.
Legg også ved et klassediagram som viser de viktige delene av koden.

### Referat
### 3.april:
Oppmøte: Karen, Halvor, Kristian, Jonas Hals
Karen: Jobbet sammen med Halvor
Halvor: Laser optimering
Kristian: ActivePlayer melding fix, jobbet med ny meny.

###10.april:
Så sammen over videre krav i oblig 5. 
Oppmøte: Karen, Halvor, Kristian, Jonas Haneborg, Jonas Hals
Referat:
Karen: jobbet med runder
Halvor: start på fase-implementering
Kristian:
Jonas Hals: Jobbet med nettverk-mutliplayer

###22.april:
Oppmøte: Karen, Halvor, Kristian, Jonas Haneborg, Jonas Hals
Møttes for å planlegge videre drift. Delte inn i oppgaver og fordelte arbeidsoppgaver. 

### 24.april:
Oppmøte: Karen, Halvor, Kristian, Jonas Haneborg, Jonas Hals
Karen: jobbet med å prøve å visualisere laseren
Halvor: forklaring av faser
Kristian: Nye tanks modeller.
Jonas: Jobbet på skade, færre kort og låste registre 
Jonas Hals: Jobbet med nettverk-multiplayer

###1.mai:
Gjennomgang over rette-skjema fra oblig 4. 
Oppmøte: Karen, Halvor, Kristian, Jonas Haneborg
Karen: Jobbet med tester
Halvor: Jobbet med laser, fremvisning av den. Innså at det var umulig
Kristian: Jobbet med lokal multiplayer. Valg av antall spillere og CPU’er.
Jonas Haneborg: Jobbet videre på skade, færre kort og låste registre
  
### 3.mai:
Oppmøte: Karen, Halvor, Kristian, Jonas Haneborg
Karen: Par-programmerte med Halvor for å få til rundene og laget nytt utkast av klassediagram. Skrev på dokumentet til innlevering.
Halvor: fullføring av faser, par-programmering med Karen.
Kristian: Jobbet med å gjøre ferdig innlevering.
Jonas: Ferdigstille oppgaven. 


## Klassediagram

![Klassediagram](https://raw.githubusercontent.com/inf112-v19/WALL-E/master/Deliverables/klassediagram_utkast2.png)
  