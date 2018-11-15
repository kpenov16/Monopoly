# Monopoly
CDIO part 3 - Monopoly 

Krav: alle lige nu  


prioritet:  
- h - høj;
- l - lav;

Funktionelle krav:
------------------
h
Når en spiller lander på et ledigt felt, 
køber spilleren feltet af banken og bliver ejer af dette felt.

h
Når en spiller lander på et eget felt, betaler spilleren ikke en rente. 

h
Når en spiller lander på et felt ejeret af en anden spiller, 
betaler spilleren en rente til spilleren der ejer fltet.
l
Hvis ejeren ejer begge felter af det samme fave er renten dobbelt. 

l
Købsprisen og renten er forskellige for de forskellige felter.

l
Feltet skifter skilt fra "til salg" med salgpris 
til "solgt" med ejernavn og rentepris.

Felter skal have faver og rækkefølgen: 
- først brættefelt Start (spiller modtager 2kr hver gang passer eller står på Start)
- to felter af det samme fave og en chanceflet(specifike instruktioner) og to felter af det samme fave
- anden brættefelt på besøg i fengsel (ingen efekt for spilleren)
- to felter af det samme fave og en chanceflet(specifike instruktioner) og to felter af det samme fave
- endnu en brættefelt gratis parkering (ingen efekt for spilleren) 
- to felter af det samme fave og en chanceflet og to felter af det samme fave
- sidste brættefelt gå i fengsel(mere info kommer)   

(en Start, to felter med fave og chancefelt har prioritet høj, de andre felter har lav) 
(der mangler mere info om felterne med faver og chancefleterne)

(ordbog: player, bank, owner, property price, property rent, property)
----------------------------------------------------------------------

h 
Et spil mellem 2-4 spillere.

h
Spiller slår på skift med 1 terning 
(det er ikke sikkert at man kan gøre det med GUI-en, den vider altid 2 terninger)

h
Spiller får tal fra 1 til 6.

h
En spiller starter med 1000 kr i sin pengebeholdning.

h
Spilleren med flereste kr i sin pengebeholdning vinder,
når en af de andre spiller ikke har flere penge.

h
Spillerens pengebeholdning skal aldrig går i minus.


Ikke funktionelle krav:
-----------------------
Man skal vise at man kan bruge klasser, 
relationer og GRASP.
Man skal kunne spille på maskinerne i databar på DTU
Spillet skal kunne "let"(det er ikke præcis nok) oversættes til andre sprog.
Man skal kunne "let"(det er ikke præcis nok) udskifte til andre terninger.
Spilleren skal kunne bruge sin pengebeholdning i andre spil.


Kode anbefelinger: copy paste
-----------------------------
- der skal laves passende konstruktører;
- der skal laves passende get og set metoder;
- der skal laves passende toString metoder;
- der skal laves en klasse GameBoard der kan indeholde alle felterne i et array;
- der skal tilføjes en toString metode der udskriver alle felterne i arrayet;
- der skal laves det spil kunden har bedt om med de klasser;
- GUI'en skal benytes;
