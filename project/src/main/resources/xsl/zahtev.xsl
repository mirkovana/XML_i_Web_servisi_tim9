<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:za="http://www.projekat.org/zahtev"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
     <head>
        <style>
          p.indent {
            text-indent: 12px;
          }
          h1.headertekst {
            text-align: center;
          }
          h2.headertekst {
            text-align: center;
          }
          h3.headertekst {
            text-align: center;
          }
          h4.headertekst {
            text-align: center;
          }
          h5.headertekst {
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
    <h2 class="headertekst"><xsl:value-of select="za:zahtev/za:naslov/."/></h2>
    <p class="indent"><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:uvod/."/></p>
    <ul>
      <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='1']/."/></p>
    </ul>
    <ul>
      <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='2']/."/></p>
    </ul>
    <ul>
      <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='3']/."/></p>
    </ul>
    <ul>
      <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/text()"/></p>
      <ul>
         <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/za:lista/za:stavka[@za:id='5']"/></p>
      </ul>
      <ul>
        <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/za:lista/za:stavka[@za:id='6']"/></p>
      </ul>
      <ul>
        <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/za:lista/za:stavka[@za:id='7']"/></p>
      </ul>
      <ul>
        <p><input type="checkbox"/><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/za:lista/za:stavka[@za:id='8']"/></p>
      </ul>
    </ul>
    <p class="indent">Ovaj zahtev se odnosi na sledece informacije:</p>
    <p class="indent">
      <u>
        <xsl:value-of select="za:zahtev/za:tekst_zahteva/za:informacije/."/>
      </u>
    </p> 

    <p style="text-align:left;">
      U <u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:mesto_datum/za:mesto/."/></u><br></br> 
      Datuma <u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:mesto_datum/za:datum/."/></u>
      <span style="float:right;">
        Ime: <u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:podnosilac/za:osoba/."/></u> <br></br>
        Adresa: <u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:podnosilac/za:adresa/."/></u> <br></br>
        Drugi podaci: <u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:podnosilac/za:drugi_podaci/."/></u> <br></br>
        Potpis: <u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:podnosilac/za:potpis/."/> </u>
      </span>
    </p>
    <br></br><br></br>
  _________________________________________________________________
    <p class="indent">*U kućici označiti koja zakonska prava na pristup informacijama želite da ostvarite.</p>
    <p class="indent">**U kućici označiti način dostavljanja kopije dokumenata.</p>
    <p class="indent">***Kada zahtevate drugi način dostavljanja obavezno upisati koji način dostavljanja zahtevate.</p>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

