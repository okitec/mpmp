Protokoll
=========

Protokollversion: 0

Das Protokoll ist ein simples textbasiertes Protokoll mit Ähnlichkeit zu POP3.
Beide Seiten, Client und Server, senden unaufgefordert Pakete aus, die das
Gegenüber jeweils quittiert.

Der momentan verwendete Port ist 1918.

Paketübersicht
--------------


### Client → Server

 - Tauschen
 - Straße kaufen
 - Haus kaufen
 - Aufgeben
 - Runde beenden
 - Karte einsetzen (Gefängnisfrei-Karte)
 - Chateingabe senden
 - Disconnect

	
### Server → Client

 - Gesamtchat senden
 - Fehlermeldungen bei unerlaubter Handlung
 - Disconnect (mit Grund (z.B. Kick))
 - Quittiermeldungen (s. oben)
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

Beispiel:

	C: chat Des is hier doch alles Schiebung!
	S: +JAWOHL
	S: chat-update 4 messages (one per line)
	S: (Oskar) foo
	S: (Simon) bar
	S: (Dani) Oki is doof
	S: (Leander) Des is hier doch alles Schiebung!
	C: +JAWOHL
	C: selfdestruct
	S: -NEIN
