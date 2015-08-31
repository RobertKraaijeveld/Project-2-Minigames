------------------------------------------------------------------------
TEAM HYDRA
PROJECT 2: SERIOUS GAME
--------------------------------------------------------------------------
AUTEURS:
- Robin Bakker (0898539@hr.nl)
- Robert Kraaijeveld (0890289@hr.n)
- Cees-Jan Nolen (0902130@hr.nl)
- Gerben Dol (0900004@hr.nl)
- William de Visser (0890786@hr.nl)
------------------------------------------------------------------------
PROJECT TITEL: SERIOUS GAME
DATUM: Februari 2015
------------------------------------------------------------------------

PROJECT OMSCHRIJVING: 

Het doel van het spel is het stimuleren van jongeren om de haven als werkveld te zien door ze kennis te
laten maken met de beroepen van de haven. Deze visie wordt gewaarborgd door de product owner (PO).
Daarnaast zijn de twee epics hieronder leidraad voor het sturen van de inhoud van een groot gedeelte van
het spel. De doelgroep van het spel zijn jongeren/jong volwassenen (14-23 jaar).

Epics (of features)
Voor het spel zijn een aantal onderdelen gegeven. Daarnaast mag je zelf twee epics (mini-games)
introduceren. Eventueel mag je nog een vijfde epic in overleg met je PO bespreken. De epics van de
opdrachtgever zijn hieronder beschreven. Ga zelf op zoek naar bronnen om jezelf in te lezen in de
werkzaamheden van de beroepen…

Epic: menu
De basis van het spel is een high-level view waar de haven gevisualiseerd wordt. Dit vormt het startpunt
voor de mini-games. Ondanks het mini-game concept is het geheel wel een geïntegreerd spel. Alle minigames
zijn speelbaar, d.w.z. het spel is pas ‘uitgespeeld’ als alle mini-games succesvol zijn doorgelopen.
Het spelverloop kan op twee manieren uitgewerkt worden, de volgorde waarin je de mini-games speelt kan
willekeurig zijn of als verhaallijn waarbij de mini-games de loop van het spel beïnvloeden.

Epic: mini-game controlecentrum
In deze mini-game neemt de speler de rol aan van coördinator voor de instroom en doorstroom van
schepen in de haven. De speler ziet de monding van de haven. Nieuwe schepen komen de haven
binnenvaren. Aan schepen wordt een loods toegekend die de schepen begeleid naar een ligplaats. Nadat
schepen gelost hebben en/of geladen zijn kunnen ze de haven uitgeloosd worden. De nieuwe schepen
komen steeds iets sneller aan bij de haven. De uitdaging voor de speler is het onder toenemende druk
coördineren van de scheepvaart.

Epic: mini-game vracht overslaan
In deze mini-game maakt de speler kennis met de beroepen binnen vracht overslag (bijvoorbeeld
kraanmachinist). De aanvoer van vracht wordt gedaan door schepen, de afvoer van vracht wordt gedaan
door transportvoertuigen zoals trein of vrachtwagen. De containers op een boot hebben echter niet altijd
de optimale volgorde hiervoor. De (bestemming van) transportvoertuig bepaalt de aanvraag, dus wat er
van de schepen wordt gehaald. Bij het verplaatsen van containers moet rekening worden gehouden met
de balans van het schip. Containers kunnen op het schip ook niet eindeloos opgestapeld worden
(maximale hoogte). Containers hebben ook verschillende afmetingen. Er is een computertegenstander die
tegelijkertijd ook de vracht van een schip wegwerkt, je doel is om de computertegenstander bij te houden.
Wie als eerste het schip leeg krijgt, heeft gewonnen. De uitdaging zit in het speelbaar houden van het spel
door de moeilijkheid (snelheid/complexiteit) te beïnvloeden. Zo krijgt de speler een competitief gevoel om
van de computertegenstander te winnen.

Epic: mini-game 3,4
Door de groep zelf in te vullen. Je werkt twee epics uit in user stories. Deze worden ter goedkeuring
aangeboden in het tweede overleg met de PO. De PO kan hierbij aanwijzingen geven. Ga zelf op zoek
naar documentatie over de casus en gebruik dit als inspiratie voor je nieuwe mini-games.

TECHNISCHEOMSCHRIJVING PER GAME
------------------------------
EPIC 1: CONTROLECENTRUM
**
 * ----------------------
 * EPIC 1: SCHEPEN LOODSEN
 * ------------------------
 * De speler ziet een grote monding van een haven.
 * Van de rechterkant van het beeld komen er om de 4 seconden schepen binnenvaren,
 * die uitzichzelf langzaam bewegen.
 * De speler kan met de linkermuisknop een schip tegelijk selecteren, en dit geselecteerde schip
 * vervolgens besturen met de WASD knoppen.
 * De speler moet de schepen naar de dokken leiden, die aangegeven zijn door grijze stukken aan de kade.
 * Als boten op elkaar of op het land botsen is het game over.
 * 
 * ---------------
 * Controls
 * ---------------
 * LMB: Boot selecteren.
 * RMB: Geselecteerde boot de-selecteren.
 * W: Geselecteerde boot naar boven besturen.
 * S: Geselecteerde boot naar onder besturen.
 * A: Geselecteerde boot naar rechts besturen.
 * D: Geselecteerde boot naar links besturen.
 * 
 * ---------------
 * Technische opzet:
 * ----------------
 * Bovenin de code worden de variabelen en methodes gedeclareerd.
 * De Act() methode bevat de actiecode.
 * Onderin staat alle teken-code, plus enkele losse methodes.
 * 
 * Wij hebben gebruik gemaakt van de Bibliotheek van Dr. Maggiore.
 * Deze library maakt gebruik van Vectors en Textures: Een vector is een coordinaat in de spelwereld,
 * een texture is zoals de naam het zegt een afbeelding, die op de positie van een Vector getekend kan worden.
 * De Act() code specificeert als het ware de condities waaraan de teken-code zich moet houden.
 * 
 * Voor de logica van het spel is deels gebruik gemaakt van wat wij de 0, 1, 2 methode noemen.
 * De 0, 1 en 2 waarden van een bepaalde variabele bepalen dan de status van een bepaalde entiteit.
 * Voor het grootste gedeelte zijn de verschillende functies van het spel echter relatief gescheiden
 * van elkaar uitgevoerd, zoals het spawnen en het selecteren v.d. schepen etc.
 * 
 * Ook hebben wij zoveel mogelijk eigenschappen van de spelwereld in arrays gestopt.
 * 

EPIC 2: VRACHT OVERSLAAN
* EPIC 2: VRACHT OVERSLAAN
 * -------------------------
 * -------------------------
 * In dit spel moet de speler containers op vrachtwagens laden.
 * De containers en de vrachtwagens en de containers hebben verschillende kleuren.
 * De vrachtwagens worden random uitgekozen en komen 1 voor 1 binnen.
 * De speler moet dan met behulp van de kraan de container met de corresponderende kleur pakken, 
 * en op de vrachtwagen laden.
 * 
 * Er loopt echter een klok van 2 minuten: als de speler binnen die tijd niet alle containers heeft
 * ingeladen is het game over.
 * Ook is er een silhouet van een schip zichtbaar rechtsbovenin, wat de balans van het schip voorstelt.
 * De speler moet dus ook door zorgvuldig te kiezen welke containers hij pakt het schip in balans houden.
 * Lukt dit hem niet, dan worden er punten afgetrokken.
 * 
 * -----------------------------------------
 * Controls:
 * 
 * Omhoog: Binnenframe kraan beweegt omhoog.
 * Omlaag: Binnenframe kraan beweegt omlaag.
 * Links: Buitenframe kraan beweegt naar links.
 * Rechts: Buitenframe kraan beweegt naar rechts.
 * Spatiebalk: Container vastpakken.
 * 
 * ------------------------------------------
 * Technische opzet:
 * Bovenin de code worden de variabelen en methodes gedeclareerd.
 * De Act() methode bevat de actiecode.
 * Onderin staat alle teken-code, plus enkele losse methodes.
 * 
 * Wij hebben gebruik gemaakt van de Bibliotheek van Dr. Maggiore.
 * Deze library maakt gebruik van Vectors en Textures: Een vector is een coordinaat in de spelwereld,
 * een texture is zoals de naam het zegt een afbeelding, die op de positie van een Vector getekend kan worden.
 * De Act() code specificeert als het ware de condities waaraan de teken-code zich moet houden.
 * 
 * Voor de logica van het spel is veel gebruik gemaakt van wat wij de 0, 1, 2 methode noemen.
 * De 0, de 1 en de 2 staan hierbij voor 3 verschillende statussen die een bepaalde entiteit,
 * zoals een container of een truck in kunnen nemen.
 * 
 * Ook hebben wij enkele losse methodes voor bv. het laten aanrijden van de trucks,
 * die aangeroepen worden in combinatie met de 0 1 2 methode.

EPIC 3: INVOERCONTROLE

 * EPIC 3: INVOERCONTROLE
 * -------------------------
 * -------------------------
 * De speler krijgt een lopende band met in het midden een xraymachine te zien.
 * Er komen paketten voorbij. Als de paketten langs de xraymachine komen kan de speler kort hun inhoud zien.
 * Aan de hand van de verboden voorwerpenlijst moet de speler bepalen of de inhoud toegestaan (groene knop),
 * of niet toegestaan (rode knop) is.
 * 
 * De speler krijgt na zijn beslissing in een alert te zien of hij de goede of foute keuze heeft gemaakt.
 * Daarna wordt de koffer in een verzegeld pakket gestopt, met een toegestaan/niet toegestaan stempel 
 * die gelijk is aan de eerdere keuze v.d. speler.
 * 
 * De speler mag maximaal 3x een fout maken, anders is het gameover.
 * 
 * Er zijn 4 levels. In het eerste level komen 4 paketten langs, in ieder volgend level wordt
 * het aantal paketten verhoogd met 2.
 * -----------------------------------------
 * Controls:
 * 
 * LMB: Klikken op de toegestaan/niet toegestaan knop
 * 
 * ------------------------------------------
 * Technische opzet:
 * Bovenin de code worden de variabelen en methodes gedeclareerd.
 * De Act() methode bevat de actiecode.
 * Onderin staat alle teken-code, plus enkele losse methodes.
 * 
 * Wij hebben gebruik gemaakt van de Bibliotheek van Dr. Maggiore.
 * Deze library maakt gebruik van Vectors en Textures: Een vector is een coordinaat in de spelwereld,
 * een texture is zoals de naam het zegt een afbeelding, die op de positie van een Vector getekend kan worden.
 * De Act() code specificeert als het ware de condities waaraan de teken-code zich moet houden.
 * 
 * * Voor de logica van het spel is veel gebruik gemaakt van wat wij de 0, 1, 2 methode noemen.
 * De 0, de 1 en de 2 staan hierbij voor 3 verschillende statussen die een bepaalde entiteit,
 * zoals een container of een truck in kunnen nemen.
 * 
 * Ook hebben wij enkele losse methodes voor bv. het uitkiezen van de koffers en hun inhoud.
 * die aangeroepen worden in combinatie met de 0 1 2 methode. In dit spel staan deze methodes
 * wat meer op de voorgrond dan in bv. Epic 2.

----------------------------------------------
MET DANK AAN
-Dhr. Stelian Paraschiv: Product Owner
-Mevr. Wendy Kleij: SLC en Tutor
-Dr. Giuseppe Maggiore: Development docent