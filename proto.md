Protokoll
=========

Protokollversion: 0

Das Protokoll ist ein simples textbasiertes Protokoll mit Ähnlichkeit zu POP3.
Beide Seiten, Client und Server, senden unaufgefordert Pakete aus, die das
Gegenüber jeweils quittiert. Diese "Pakete" sind einfache UTF-8-Textzeilen, die
mit einer einfachen Newline (`\n`, LF) enden. Die Quittiermeldungen sind binär:
falls der Request erlaubt und erfolgreich war, wird mit `+JAWOHL`, ansonsten mit
`-NEIN` geantwortet. Ein Paket beginnt mit dem auszuführenden Befehl, der per
Konvention immer aus Kleinbuchstaben und Bindestrichen (`-`) besteht, z.B. `chat-update`.

Der momentan verwendete Port ist 1918.

Adresse des Testservers: `leander3.ddns.net` (betrieben von @leletec)
Der Server läuft nicht immer, sondern auf Wunsch. Die Firewall der Schule erlaubt
keinen Zugriff, also werden wir in der Schule sowieso lokale Server laufen lassen.

Paketübersicht
--------------


### Client → Server

 - Beitreten
 - Chateingabe senden
 - Disconnect
 - Aufgeben
 - Spielhandlung ausführen
    * Tauschen
    * Straße kaufen
    * Haus kaufen
    * Runde beenden
    * Karte einsetzen (Gefängnisfrei-Karte)
	
### Server → Client

 - Spielbeginn
 - neuen Chat broadcasten
 - Fehlermeldungen bei unerlaubter Handlung
 - Disconnect (mit Grund, z.B. Kick)
 - Ereignis- und Gemeinschaftskarten
 - Update des Spiels
    * Bewegung einer Spielfigur
    * "Wieder am Zug"
 - Geld bekommen
    * Mieten
    * Karten (s. oben)
    * Gehalt (Überqueren des Startes)

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

		S: disconnect [Grund]
		C: +JAWOHL
		S: <Verbindung getrennt>

##### Beschreibung

Ein Client kann mit `disconnect` die Verbindung schließen. Der Spieler gibt implizit auf,
sofern er noch im Spiel ist. Der Server kann auch die Verbindung trennen, wenn der Spieler
durch den Administrator des Spiels *gekickt* wurde. Es ist immer der Server, der die Verbindung trennt.
Falls der Client die Verbindung ohne `disconnect` trennt, passiert nichts Schlimmes, jedoch
kann man keinen Grund angeben.

Beitreten
---------

##### Synopsis

		C: <Öffnen der Verbindung>
		S: <Willkommensbotschaft>
		C: subscribe <spectator|player> <Farbe> <Name>
		S: -JAWOHL
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

Zu Beginn des Spiels nd nach einem Subscribe sendet der Server an alle Clients die
Spielerliste inklusive der Farben und des Gamemodes (siehe Subscribe). Die Farben
sind RGB-Hextriplets wie z.B. `#FFA500`. Falls man nur ein Zuschauer ist, ist die
Farbe irrelevant.

Spielbeginn
-----------

##### Synopsis

		S: start-game <beginnender Spieler>
		C: +JAWOHL

##### Beschreibung

Zu Beginn des Spiels sendet der Server dieses Paket. Clients sind gehalten, jetzt
das Spielfeld zu zeichnen. Nach dem Beginn des Spiels können keine Spieler mehr
hinzugefügt werden.

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
Namen gleicht, um herauszufinden, wer am Zug ist. Die Spielfigur desjenigen wird dann
entsprechend bewegt. Derjenige kann dann weitere Aktionen tätigen.

Wenn der Spieler alles getan hat, was er in der Runde tun wollte, klickt er auf den
*Runde beenden*-Button und sendet dem Server ein `end-turn`-Kommando.

Grundstücke kaufen
------------------

#### Synopsis

		C: buy-plot <Name des Grundstücks>
		S: +JAWOHL
		oder
		S: -NEIN insufficient money, need <amount>
		S: -NEIN belongs to player <player>
		S: -NEIN you are not a player

		S: plot-update <Name des Grundstücks> <Häuserzahl> <Eigentümer>
		C: +JAWOHL

#### Beschreibung

Um ein Grundstück zu erwerben, sendet der Client `buy-plot` aus. Wenn der Kauf
klappt, wird an alle ein `plot-update`-Packet entsendet. Die Häuserzahl liegt
zwischen 0 (kein Haus) und 5 (Hotel).

Häuser kaufen
-------------

#### Synopsis

		C: add-house <Grundstück>
		S: +JAWOHL
		oder
		S: -NEIN insufficient money, need <amount>
		S: -NEIN belongs to player <player>
		S: -NEIN already fully upgraded

#### Beschreibung

Mit `add-house` erhöht man die Anzahl der Häuser eines Grundstücks um 1.
Falls die Operation erfolgreich war, sendet der Server ein `plot-update`
aus (s. o.).

Mieten und andere Geldereignisse
--------------------------------

#### Synopsis

		S: add-money <Menge> <Grund>
		C: +JAWOHL

#### Beschreibung

Mit `add-money` verändert der Server, der als Bank fungiert, die Geldmenge
des angesprochenen Spielers um ein gewisses Delta. Eine negative Zahl verringert
die Geldmenge des Spielers. Ein Grund muss immer angegeben werden. Drei wichtige
Gründe sind Mieten, Ereigniskarten und das Gehalt beim Überqueren des Starts.

Die Geldmenge eines Spielers wird nicht an alle weitergegeben, die Transaktionen
sind "privat".

Gefängnis
---------

#### Synopsis

		S: prison <enter|leave> <Spieler>
		C: +JAWOHL

#### Beschreibung

Der `prison`-Befehl befördert den Spieler in das Gefängnis (`prison enter`)
oder wieder hinaus (`prison leave`).
