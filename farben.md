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

Alle Eingaben werden zentriert dargestellt. Für die Darstellung ist die Monopoly-Schriftart notwendig.
Die benötigten Dateien liegen (wahrscheinlich illegal) in [graphics/fonts/](graphics/fonts)

### Hier mal ein paar Beispiele:

`fill="..."` beschreibt die Farbe. Hier kann man Farben (weiter unten im Text) einfügen
```SVG
<rect id = "Farbe"
	x="2" y="2" width="300" height="93" fill="#d01f26"
/>
```

*Weimarer Strasse* wird nun als einzeiliger Straßenname angezeigt.
Bei kurzen Namen reicht das völlig aus.
```SVG
<text id = "Einzeilig"
	x="150" y="195" font-family="Monopoly" font-size="30px" fill="##231f20"
	style="stroke: #000000" text-anchor="middle">
	Weimarer Strasse
</text>
```

*Weimarer Straße* wird nun als zweizeiliger Straßenname angezeigt.
Hilfreich bei längeren Namen.
```SVG
<text id = "Zweizeilig"
	font-family="Monopoly" font-size="30px" fill="##231f20"
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
<text id = "Preis"
	x="150" y="480" font-family="Monopoly" font-size="33px" text-anchor="middle">
	RM 7200
</text>
```

Verwendete Farben und hilfreiche HTML-Codes
-------------------------------------------

Fachschaft Kunst approoved.

Allgemeine Farben | Code
----------------- | ----
Schrift, Outlines | ```#231f20```
Hintergrundfarbe | ```#f7f7cf```

Eckfelder | Code   
--------- | ----
Gefängnis-Orange | ```#ca4a24```
Polizist-Blau | ```#428db7```
Los-Pfeil-Rot | ```#ed1c24```
Frei-Parken-Rot | ```#ee2d2b```

Straßen | Code
------- | ----
Dunkles Lila | ```#30041c```
Helles Blau | ```#91c3d8```
Helles Lila | ```#860459```
Orange | ```#de5126```
Rot | ```#d01f26```
Gelb | ```#fbe821```
Grün | ```#168140```
Dunkles Blau | ```#183a66```

Umlaute | Code
------- | ----
Ä  | ```&#196;```
ä  | ```&#228;```
Ö  | ```&#214;```
ö  | ```&#246;```
Ü  | ```&#220;```
ü  | ```&#252;```
ß  | ```&#223;```
§  | ```&#167;```
®  | ```&#174;```
©  | ```&#169;```
