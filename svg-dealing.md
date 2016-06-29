HILFE! Ich hab eine SVG!
========================
Warum SVG?
----------
Es ist eine vektorbasierte Grafik. Das heißt, man kann sie beliebig groß und klein machen,
ohne, dass sie unscharf wird. Die Elemente sind relativ selbsterklärend. Elemente, die man
verändern kann, wurden angegeben.

SVG-Dateien bearbeiten
----------------------
Zuallererst: GIMP downloaden. Photoshop in günstig.

Man kann SVG-Dateien nicht direkt bearbeiten. Im Programm GIMP muss man diese zuerst als
"Pfad" importieren. Falls im rechten Programmdrittel kein Pfad-Dock vorhanden ist, muss
man ihn erst mit Fenster → Andockbare Docks → Pfade einblenden. Danach Rechtsklick → Pfad
imporiteren → SVG-Datei auswählen ⇒ Fertig. Zumindest fast. Nun kann man mit dem Pfad-Tool
die zuvor eingeblendeten Pfade bearbeiten. Die Modifikatoren `STRG` und `ALT` ermöglichen das
Bearbeiten bzw. Verschieben von Punkten. Das sollte aber eigentlich nicht nötig sein, da
die Grafiken soweit mehr oder weniger passen. Viel wichtiger ist...

Straßennamen bearbeiten
-----------------------
Die Dateien [field_trainstation.svg](graphics/svg/field_trainstation.svg) und [field_street.svg](graphics/svg/field_street.svg) (sowie [field_elecricity.svg] und [field_waterpump.svg])
sollen bearbeitet werden. Hier kann man die **Straßenfarbe**, den **Straßennamen** und den **Kaufpreis** festlegen.

Die `text`-Felder  `Farbe`, `Einzeilig`, `Zweizeilig` und `Preis` sind für die Bearbeitung gedacht
und befinden sich jeweils am Datei-Ende. Umlaute etc. werden u. U. nicht korrekt angezeigt, weswegen
die entsprechenden HTML-Codes dafür ebenfalls am Ende dieser Hilfe-Datei zu finden sind.

### Hier mal ein paar Beispiele:

`fill="..."` beschreibt die Farbe. Hier kann man Farben (weiter unten im Text) einfügen
```SVG
<rect id = "color"
	x="2" y="2" width="300" height="93" fill="#d01f26"
/>
```

*Weimarer Strasse* wird nun als einzeiliger Straßenname angezeigt.
Bei kurzen Namen reicht das völlig aus.
```SVG
<text id = "oneliner"
	x="150" y="195" font-size="30px" fill="##231f20"
	style="stroke: #000000" text-anchor="middle">
	Weimarer Strasse
</text>
```

*Weimarer Straße* wird nun als zweizeiliger Straßenname angezeigt.
Hilfreich bei längeren Namen.
```SVG
<text id = "twoliner"
	font-size="30px" fill="##231f20"
	style="stroke: #000000" text-anchor="middle">
	<tspan x="150" y="182">
		Weimarer
	</tspan>
	<tspan x="150" y="212">
		Stra&#223;e
	</tspan>
</text>
```

Das Grundstück kostet hier *7200 RM*. Der Preis wird immer folgendermaßen angegeben:
`RM X`, wobei `X` der Preis ist.
```SVG
<text id = "price"
	x="150" y="480" font-family="Monopoly" font-size="33px" text-anchor="middle">
	RM 7200
</text>
```

Verwendete Farben und hilfreiche HTML-Codes
-------------------------------------------

Bei den Straßennamen ist es sehr wichtig, die Umlaute zu ersetzen, da nicht jedes
Programm Umlaute anzeigen kann. Gimp, zum Beispiel versagt hier; Firefox versagt
schon bei der ``transform``-Methode.
(Fachschaft Kunst approoved.)

Allgemeine Farben |Code           |
------------------|---------------|
Schrift, Outlines | ```#231f20``` |
Hintergrundfarbe  | ```#f7f7cf``` |
Schwarz           | ```#000000``` |

Eckfelder        |Code           |
-----------------|---------------|
Gefängnis-Orange | ```#ca4a24``` |
Polizist-Blau    | ```#428db7``` |
Los-Pfeil-Rot    | ```#ed1c24``` |
Frei-Parken-Rot  | ```#ee2d2b``` |

Straßen      |Code           |
-------------|---------------|
Dunkles Lila | ```#30041c``` |
Helles Blau  | ```#91c3d8``` |
Helles Lila  | ```#860459``` |
Orange       | ```#de5126``` |
Rot          | ```#d01f26``` |
Gelb         | ```#fbe821``` |
Grün         | ```#168140``` |
Dunkles Blau | ```#183a66``` |

Umlaute |Code          |
--------|--------------|
Ä       | ```&#196;``` |
ä       | ```&#228;``` |
Ö       | ```&#214;``` |
ö       | ```&#246;``` |
Ü       | ```&#220;``` |
ü       | ```&#252;``` |
ß       | ```&#223;``` |
§       | ```&#167;``` |
®       | ```&#174;``` |
©       | ```&#169;``` |

Straßennamen
------------

Hier eine Liste der Straßennamen mit zugehörigen Farb-Codes und zusätzlich in "SVG-Sprache" (ohne Umlaute):

Straßenname              |Kaufpreis |ohne Haus |1 Haus |2 Häuser |3 Häuser  |4 Häuser |Hotel  |Preis für ein Haus  |Farbe      |
-------------------------|----------|----------|-------|---------|----------|---------|-------|--------------------|-----------|
Küstenbahndamm           |1200      |40        |200    |600      |1800      |3200     |5000   |1000                |blau       |
Zimmererstraße           |1200      |80        |400    |1200     |3600      |6400     |9000   |1000                |blau       |
Wasserschifffahrtsamt    |2000      |120       |600    |1800     |5400      |8000     |11000  |1000                |türkis     |
August-Bebel-Straße      |2000      |120       |600    |1800     |5400      |8000     |11000  |1000                |türkis     |
Am Eisenbahndock         |2400      |160       |800    |2000     |6000      |9000     |12000  |1000                |türkis     |
Faldernstraße            |2800      |200       |1000   |2000     |6000      |9000     |12000  |2000                |lila       |
Sieges-Alee              |2800      |200       |1000   |3000     |9000      |12500    |15000  |2000                |lila       |
Neue Straße              |3200      |240       |1200   |3600     |10000     |14000    |18000  |2000                |lila       |
Gorch-Fock-Straße        |3600      |280       |1400   |4000     |11000     |15000    |19000  |2000                |orange     |
Am Burggraben            |3600      |280       |1400   |4000     |11000     |15000    |19000  |2000                |orange     |
Bollwerkstraße           |4000      |320       |1600   |4400     |12000     |16000    |20000  |2000                |orange     |
Philosophenweg           |4400      |360       |1800   |5000     |14000     |17500    |21000  |3000                |rot        |
Otto-von-Bismarck-Straße |4400      |360       |1800   |5000     |14000     |17500    |21000  |3000                |rot        |
Friedrich-Ebert-Straße   |4800      |400       |2000   |6000     |15000     |18500    |22000  |3000                |rot        |
Schützenstraße           |5200      |440       |2200   |6600     |16000     |19500    |23000  |3000                |gelb       |
Hafenstraße              |5200      |440       |2200   |6600     |16000     |19500    |23000  |3000                |gelb       |
Hindenburg-Straße        |5600      |480       |2400   |7200     |17000     |20500    |24000  |3000                |gelb       |
Jahnstraße               |6000      |520       |2600   |7800     |18000     |22000    |25500  |4000                |grün       |
Karl-Marx-Straße         |6000      |520       |2600   |7800     |18000     |22000    |25500  |4000                |grün       |
Am Bollwerk              |6400      |560       |3000   |9000     |20000     |24000    |28000  |4000                |grün       |
Pariserplatz             |7000      |700       |3500   |10000    |22000     |26000    |30000  |4000                |dunkelblau |
Triumphstraße            |8000      |1000      |4000   |12000    |28000     |34000    |40000  |4000                |dunkelblau |

Und die Bahnhöfe nicht zu vergessen (nicht fertig!):

Bahnhofsname     |Kaufpreis |1 BH |2BH  |3BH  |4BH  |
-----------------|----------|-----|-----|-----|-----|
Central-Bahnhof  |4000      |500  |1000 |2000 |4000 |
Anhalter Bahnhof |4000      |500  |1000 |2000 |4000 |
Bahnhof Weimar   |4000      |500  |1000 |2000 |4000 |
Flügel-Bahnhof   |4000      |500  |1000 |2000 |4000 |

Quellen
-------
https://www.edvsz.hs-osnabrueck.de/fileadmin/user/koller/dokumentation/intern/programmierung/C/prog2_Cpp/regeln.html
