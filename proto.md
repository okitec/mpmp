Protokoll
=========

Protokollversion: 0

Verwendung von HTTP: n

Port: 1918


Paketeübersicht
---------------

### Client -> Server

 - Tauschen
 - Straße kaufen
 - Haus kaufen
 - Aufgeben
 - Runde beenden
 - Karte einsetzen (Gefängnisfrei-Karte)
 - Chateingabe senden
 - Disconnect

	
### Server -> Client

 - Gesamtchat senden
 - Fehlermeldungen bei unerlaubter Handlung
 - Disconnect (mit Grund (z.B. Kick))
 - Quittiermeldungen (s. oben)
 - Ereignis- & Gemeinschaftskarten
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
	S: chat-update 3 messages
	S: (Oskar) foo
	S: (Simon) bar
	S: (Dani) Oki is doof
	S: (Leander) Des is hier doch alles Schiebung!
	C: +JAWOHL
	C: selfdestruct
	S: -NEIN
