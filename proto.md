Protokoll
=========

Protokollversion: 4

Das Protokoll ist ein simples textbasiertes Protokoll mit Ähnlichkeit zu POP3.
Beide Seiten, Client und Server, senden unaufgefordert Pakete aus, die das
Gegenüber jeweils quittiert. Diese "Pakete" sind einfache UTF-8-Textzeilen, die
mit einer einfachen Newline (`\n`, LF) enden. Die Quittiermeldungen sind binär:
falls der Request erlaubt und erfolgreich war, wird mit `+JAWOHL`, ansonsten mit
`-NEIN` geantwortet. Ein Paket beginnt mit dem auszuführenden Befehl, der per
Konvention immer aus Kleinbuchstaben und Bindestrichen (`-`) besteht, z.B. `chat-update`.

Der momentan verwendete Port ist `1918`.

Adresse eines Testservers: `leander3.ddns.net` (betrieben von @leletec)
Falls er läuft, ist er nicht auf der aktuellsten Version, weil oki noch kein
Skript zum automatischen Deployen geschrieben hat.

Paketübersicht
==============

### Client → Server

Pakete vom Client zum Server informieren diesen über Aktionen des Clients. Sie
werden häufig dann gesendet, wenn der User auf der GUI Buttons o.ä. betätigt.

##### Nicht-Gameplay

Befehl       | Beschreibung
-------------|-------------
`chat`       | Chatmeldung
`disconnect` | "Offizielle" Trennung der Verbindung
`subscribe`  | Client registriert Namen, Farbe und Spielmodus
`whisper`    | Client sendet private Nachricht an einen anderen Spieler oder Zuschauer

##### Gameplay

Befehl       | Beschreibung
-------------|-------------
`add-house`  | Spieler kauft Haus
`buy-plot`   | Spieler kauft Grundstück
`end-turn`   | Spieler beendet seine Runde
`hypothec`   | Spieler belastet ein Grundstück hypothekarisch oder hebt Hypothek auf
`make-offer` | Spieler macht einen Vorschlag in einer Auktion
`ragequit`   | Spieler gibt auf und wird Zuschauer
`rm-house`   | Spieler verkauft Haus
`start-game` | Spieler erzwingt Spielbeginn
`unjail`     | Spieler benutzt Geld oder Karte, um freizukommen

### Server → Client

Der Server hält das autorative Model, d.h. der Server hat in Gameplayfragen immer recht.
Die Clients cachen dieses Model einfach nur; die meisten Pakete vom Server zum Client
dienen dazu, alle Clients auf dem aktuellen Stand zu halten. Sie `*-update`-Pakete werden
an alle Clients gesendet.

##### Nicht-Gameplay

Befehl              | Beschreibung
--------------------|-------------
`chat-update`       | Chatmeldung
`clientlist-update` | Spielerliste: echte Spieler und Zuschauer

##### Gameplay

Befehl              | Beschreibung
--------------------|-------------
`auction-plot`      | Ersteigerung eines Grundstücks
`money-update`      | Änderung am Vermögen eines Spielers
`plot-update`       | Änderung am Besitztum, Häuserzahl, Hypothekenstatus
`pos-update`        | Positionsänderung
`prison`            | Spieler kommt ins Gefängnis oder wieder raus
`show-transaction`  | Grund und Höhe einer Transaktion
`turn-update`       | Neue Runde: nächster Spieler und Würfelergebnis wird gesendet

Detaildokumentation
===================

Chat
----

##### Synopsis

		C: chat <Textzeile>
		S: +JAWOHL

		S: chat-update (Sender) <Nachricht>
		C: +JAWOHL
		
##### Beschreibung

Mit `chat` wird eine Chatnachricht an den Server gesendet, der sie speichert, den Sendernahmen anfügt
und an alle Clients mithilfe von `chat-update` sendet, inklusive dem originalen Sender. Die Clients
sind dann gehalten, ihre Chatbox zu aktualisieren. Eine Chatnachricht hat per Konvention folgendes Aussehen:

		(oki) Das ist eine Chatnachricht.

Disconnect (Trennen der Verbindung)
-----------------------------------

##### Synopsis

		C: disconnect [Grund]
		S: +JAWOHL
		S: <Verbindung getrennt>

##### Beschreibung

Ein Client kann mit `disconnect` die Verbindung schließen. Der Spieler gibt implizit auf,
sofern er noch im Spiel ist. Es ist immer der Server, der die Verbindung trennt.
Falls der Client die Verbindung ohne `disconnect` trennt, passiert nichts Schlimmes, jedoch
kann man keinen Grund angeben.

Beitreten
---------

##### Synopsis

		C: <Öffnen der Verbindung>
		S: <Willkommensbotschaft>
		C: subscribe <spectator|player> <Farbe> <Name>
		S: +JAWOHL
		oder
		S: -NEIN Name already taken!
		S: -NEIN The admin barred you from entering the game.

##### Beschreibung

Um einem Spiel beizutreten, sendet der frisch verbundene Client `subscribe`
mit Angabe des Spielmodus, dem Farb-Hex-Triplet (HTML-Style, also z.B. `#FFA500`)
und dem Namen, der den Rest der Zeile ausmacht. Der Name darf somit Leerzeichen
enthalten. Es gibt zwei Spielmodi:

Modus       | Bedeutung
------------|----------
`spectator` | Zuschauer, kann chatten und das Spiel betrachten
`player`    | echter Spieler

Wenn der Admin des Spiels nicht einverstanden ist, sendet der Server anstatt eines
`+JAWOHL` ein `-NEIN`. Bei Erfolg wird die gesamte Spielerliste ausgegeben (vgl.
Spielbeginn). Um seinen Namen, seinen Modus oder seine Farbe zu ändern, sendet
ein bereits subscribter Client ebenfalls `subscribe` mit Angabe des neues Spielmodus
und Namen (s. oben).

Aufgeben
--------

##### Synopsis

		C: ragequit
		S: +JAWOHL

##### Beschreibung

Ein Spieler kann mit `ragequit` aufgeben. Er empfängt jedoch weiterhin Updates über das
Spiel und ist somit ein Zuschauer (*Spectator*).

Spieler- bzw. Clientliste
-------------------------

##### Synopsis

		S: clientlist-update <Anzahl Clients>
		S: <Farbe>: <Gamemode>: <Name von Client 1>
		S: <Farbe>: <Gamemode>: <...>
		C: +JAWOHL

##### Beschreibung

Nach einem Subscribe sendet der Server an alle Clients die Spielerliste inklusive
der Farben und des Gamemodes (siehe Subscribe). Die Farben sind RGB-Hextriplets wie
z.B. `#FFA500`. Falls man nur ein Zuschauer ist, ist die Farbe irrelevant.

Spielbeginn
-----------

##### Synopsis

		C: start-game
		S: +JAWOHL

##### Beschreibung

Ein Client, der das Spiel beginnen will, sendet dieses Paket. Momentan sind keine
Restriktionen eingebaut: jeder kann das Spiel starten.

Rundenende und -anfang
----------------------

#### Synopsis

		S: turn-update <Gesamtwürfelsumme> <Anzahl an Paschs> <Spieler am Zug>
		C: +JAWOHL

		C: end-turn
		S: +JAWOHL

#### Beschreibung

Zu Beginn einer Runde wird sofort gewürfelt. Falls es ein Pasch war, wird wieder gewürfelt,
wobei man beim dritten Pasch ins Gefängnis kommt. Die Anzahl an Paschs wird zusammen mit
der Gesamtsumme bei `turn-update` mitgeliefert. Die Clients schauen, ob ihr Name dem angegebenen
Namen gleicht, um herauszufinden, wer am Zug ist. Derjenige kann dann weitere Aktionen
tätigen. Der eigentliche Zug wird durch `pos-update` durchgeführt.

Wenn der Spieler alles getan hat, was er in der Runde tun wollte, klickt er auf den
*Runde beenden*-Button und sendet dem Server ein `end-turn`-Kommando.

Grundstücke
-----------

#### Synopsis

		C: buy-plot <Name des Grundstücks>
		S: +JAWOHL
		oder
		S: -NEIN 131 insufficient money, need <amount>
		S: -NEIN 136 belongs to player <player>
		S: -NEIN you are not a player
		S: show-transaction <Preis> Buy plot <Grundstück>
		S: money-update ...
		C: +JAWOHL

		C: sell-plot <Name des Grundstücks> @<Käufer> <Preis>
		S: +JAWOHL
		oder
		S: -NEIN 136 belongs to player <player>
		S: -NEIN 137 can't sell plot with houses on it

		S: auction-plot <Name des Grundstücks> <Preis> <Höchstbietender>
		C: +JAWOHL
		C: make-offer <Name des Grundstücks> <Preis>
		S: +JAWOHL
		oder
		C: -NEIN I don't wanna pay that much.

		C: hypothec <yes|no> <Name des Grundstücks>
		S: +JAWOHL

		S: plot-update <Name des Grundstücks> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>
		C: +JAWOHL

#### Beschreibung

Um ein Grundstück zu erwerben, sendet der Client `buy-plot` aus. Wenn der Kauf
klappt, wird an alle ein `plot-update`-Packet entsendet. Die Häuserzahl liegt
zwischen 0 (kein Haus) und 5 (Hotel). Ein unbebautes Grundstück kann mit `sell-plot`
an einen anderen Spieler zu einem vereinbarten Preis verkauft werden. Wenn derjenige
auf dem Grundstück dieses nicht kaufen will, wird es mit `auction-plot` und `make-offer`
versteigert. Ob ein Grundstück hypothekarisch belastet ist, wird mit `hypothec`
verändert. Die Bank sendet dann entsprechende `show-transaction`s und `money-update`s.

**Kleiner Hack**: Bei `sell-plot` wird deswegen ein `@` vor den Käufer gestellt, um Käufer
und Grundstück zu unterscheiden. Natürlich könnte man auch einfach den Preis in die
Mitte stellen. Das Tolle ist, dass die `@`-Syntax bereits verstanden wird, weil `whisper`
diese braucht.


Häuser kaufen und verkaufen
---------------------------

#### Synopsis

		C: add-house <Grundstück>
		S: +JAWOHL
		oder
		S: -NEIN 131 insufficient money, need <amount>
		S: -NEIN 136 belongs to player <player>
		S: -NEIN already fully upgraded
		S: -NEIN 135 unbalanced plot group
		S: show-transaction <Preis> Buy house for plot <Grundstück>
		S: money-update ...
		C: +JAWOHL

		C: rm-house <Grundstück>
		S: +JAWOHL
		S: show-transaction <halber Hauserwerbspreis> sold house to bank
		S: money-update ...
		oder
		S: -NEIN 135 unbalanced plot group
		<XXX add missing>

#### Beschreibung

Mit `add-house` erhöht man die Anzahl der Häuser eines Grundstücks um 1.
Falls die Operation erfolgreich war, sendet der Server ein `plot-update`
aus (s. o.). `rm-house` ermöglicht es, ein Haus an die Bank für den halben
Preis zurückzugeben.

Mieten und andere Geldereignisse
--------------------------------

#### Synopsis

		S: show-transaction <Menge> <Grund>
		C: +JAWOHL

		S: money-update <Cash> <Hypothekengeld> <Spieler>
		C: +JAWOHL

#### Beschreibung

Mit `show-transaction` notifiziert der als Bank fungierende Server den Client über eine
Geldmengenänderung des angesprochenen Spielers. Ein Grund muss immer angegeben werden.
Wichtige Gründe sind Mieten, Ereigniskarten, das Gehalt beim Überqueren des Starts sowie
Häuser- und Grundstückskäufe. Die eigentliche Veränderung des gespeicherten Wertes geschieht
durch das an alle versendete `money-update`.

Die Geldmenge eines Spielers wird im Folgenden mit `money-update` an alle weitergegeben.
Die Trennung zwischen `show-transaction` und `money-update` ist nötig, da bei Ersterem
ein Grund genannt werden muss, der den Rest der Zeile einnimmt, sodass wir den Grund
nicht von einem Spielernamen unterscheiden könnten (wir verwenden keine Quotes).

Gefängnis
---------

#### Synopsis

		S: prison <enter|leave> <Spieler>
		C: +JAWOHL

		C: unjail <card|money>
		S: +JAWOHL
		oder
		S: -NEIN 131 insufficient money, need <amount>
		S: -NEIN 132 no unjail card

#### Beschreibung

Der `prison`-Befehl befördert den Spieler in das Gefängnis (`prison enter`)
oder wieder hinaus (`prison leave`). Damit wird nur der Status des Spielers visuell geändert;
die Bewegung auf das Gefängnisfeld findet durch `pos-update` statt. Mit dem `unjail`-Befehl
hat der Spieler die Möglichkeit, seine Gefängnis-Frei-Karte einzusetzen oder den fixen Betrag
zu zahlen, um freizukommen. Das *passive* Freikommen durch Pasch passiert im Server und wird
nicht durch `unjail` initiiert.

Flüstern
--------

#### Synopsis

		C: whisper <Spieler> <Nachricht>
		S: +JAWOHL
		S: chat-update <Nachricht>
		C: +JAWOHL
		
#### Beschreibung

Der `whisper`-Befehl ermöglicht es einem Spieler, einen anderen direkt anzuschreiben,
ohne dass dies von den anderen Spielern bemerkt wird.

Ereigniskarte ziehen
--------------------

#### Synopsis

		S: eventcard <Text>
		C: +JAWOHL
		oder
		C: -NEIN 133 Please gimme a different card!
		S: +JAWOHL
		oder
		C: -NEIN 133 Please gimme a different card!
		S: -NEIN 134 You cannot take a different card.
		C: +JAWOHL

#### Beschreibung

`eventcard` wird nur an den Spieler gesendet, der gerade eine Ereigniskarte ziehen musste.
Enthalten ist nur der Text der Karte; die Aktion wird, sofern möglich, vom Server sofort
durchgeführt. Falls man die Möglichkeit hat, statt der Aktion eine andere Karte zu ziehen
("Tun Sie das und das oder nehmen Sie eine Gemeinschaftskarte"), returnt der Client mit
einem `-NEIN 133`. Falls das nicht möglich ist, beschwert sich der Server, was der Client
akzeptieren muss.

Bewegung
--------

#### Synopsis

		S: pos-update <Zielposition> <Spieler>
		C: +JAWOHL

##### Beschreibung

Dieses Paket teleportiert den Spieler auf das Feld mit dieser Position. Position 0 ist das
Los-Feld, daraufhin wird im Uhrzeigersinn gezählt. Bei jeder Bewegung wird dieses Paket
an alle gesendet.

Errorhandling
-------------

Tritt ein Fehler bei einem Befehl auf, wird dies *meist* durch einen Fehlercode quittiert;
diese sind von den *SMTP Reply-Codes* inspiriert. Aufgrund von `+JAWOHL` benötigen wir hier
keine positiven Werte.

1. Ziffer: Dauerhaftigkeit des Fehlers
2. Ziffer: Ort des Fehlers
3. Ziffer: genauere Einteilung

Code | Beschreibung
-----|-------------
 **1yz** | vorübergehendes Fehlschlagen; der Befehl kann zu einem anderen Zeitpunkt funktionieren.
 **13z** | temp. Gameplay-Fehler
 131 | nicht genug Geld
 132 | keine Gefängnis-Frei-Karte vorhanden, die benutzt werden kann
 133 | Client will eine neue Ereigniskarte ziehen
 134 | Gesuch des Clients auf eine neue Karte wird abgelehnt
 135 | unbalancierte Fatbgruppe: Häuseranzahl zu unterschiedlich
 136 | gehört einem anderen Spieler
 137 | Grundstücke mit Häusern können nicht verkauft werden
 **2yz** | permanentes Fehlschlagen; der Befehl kann nie funktionieren
 **20z** | allgemeiner Fehler, kann in verschiedenen Bereichen auftreten
 201 | unerwartetes End-of-File
 **21z** | Beitreten und Clientlist-Updates
 211 | gewählter Name bereits vergeben
 212 | `clientlist-update` mit falscher Syntax
 213 | `clientlist-update` mit falschen Listenzeilen
 214 | `subscribe` mit falscher Syntax
 215 | Spieler existiert nicht
 **22z** | Chatten, Flüstern
 221 | nicht subscribter Client will chatten
 223 | `whisper` mit falscher Syntax
 **23z** | Gameplay
 **3yz** | Protokollfehler
 301 | Paketbefehl hat falsche Argumente
 302 | unerwarteter interner Fehler
 303 | unerwartetes EOF
 304 | Befehl existiert nicht
 311 | Zeilensyntax von `clientlist-update` falsch
 333 | angegebenes Grundstück existiert nicht
