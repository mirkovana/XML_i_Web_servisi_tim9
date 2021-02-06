<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:za="http://www.projekat.org/zahtev"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
     <head>
        <style>
          body {
            margin:100px;
          }
          p.indent {
            text-indent: 24px;
          }
          h2.headertekst {
            text-align: center;
          }
          .center {
            text-align: center;
          }
          hr {
            border-top:2px dotted #000;
            margin-left:100px;
            margin-right:100px;
          }
        </style>
      </head>
  <body>
    <div class="center">
      <xsl:value-of select="za:zahtev/za:institucija/."/>
      <br></br>
      <hr></hr>
      (naziv i sediste organa kome se zahtev upucuje)
    </div>
    <h2 class="headertekst">Zahtev za pristupanje informacijama</h2>
    <p class="indent">Na osnovu člana 15. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja („Službeni glasnik RS“, br. 120/04, 54/07, 104/09 i 36/10) od gore navedenog organa zahtevam:*</p>
    <ul>
      <p><input type="checkbox"/>obaveštenje da li poseduje traženu informaciju;</p>
    </ul>
    <ul>
      <p><input type="checkbox"/>uvid u dokument koji sadrži traženu informaciju;</p>
    </ul>
    <ul>
      <p><input type="checkbox"/>kopiju dokumenta koji sadrži traženu informaciju;</p>
    </ul>
    <ul>
      <p><input type="checkbox"/>dostavljanje kopije dokumenta koji sadrži traženu informaciju:** </p>
      <ul>
         <p><input type="checkbox"/>postom</p>
      </ul>
      <ul>
        <p><input type="checkbox"/>elektronskom postom</p>
      </ul>
      <ul>
        <p><input type="checkbox"/>faksom</p>
      </ul>
      <ul>
        <p><input type="checkbox"/>na drugi nacin***:<u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/za:lista/za:stavka[@za:id='8']/za:drugi_nacin/."/></u></p>
      </ul>
    </ul>
    <p class="indent">Ovaj zahtev se odnosi na sledece informacije:</p>
    <p class="indent">
      <u>
        <xsl:value-of select="za:zahtev/za:tekst_zahteva/za:informacije/."/>
      </u>
    </p> 

    <p style="text-align:left;">
      U <u><xsl:value-of select="za:zahtev/za:mesto_datum/za:mesto/."/></u><br></br> 
      Datuma <u><xsl:value-of select="za:zahtev/za:mesto_datum/za:datum/."/></u>
      <span style="float:right;">
        Ime: <u><xsl:value-of select="za:zahtev/za:podnosilac/za:osoba/."/></u> <br></br>
        Adresa: <u><xsl:value-of select="za:zahtev/za:podnosilac/za:adresa/."/></u> <br></br>
        Drugi podaci: <u><xsl:value-of select="za:zahtev/za:podnosilac/za:drugi_podaci/."/></u> <br></br>
        Potpis: <u><xsl:value-of select="za:zahtev/za:podnosilac/za:potpis/."/> </u>
      </span>
    </p>
    <br></br><br></br>
   ___________________________________________________________________________
    <p class="indent">*U kućici označiti koja zakonska prava na pristup informacijama želite da ostvarite.</p>
    <p class="indent">**U kućici označiti način dostavljanja kopije dokumenata.</p>
    <p class="indent">***Kada zahtevate drugi način dostavljanja obavezno upisati koji način dostavljanja zahtevate.</p>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

