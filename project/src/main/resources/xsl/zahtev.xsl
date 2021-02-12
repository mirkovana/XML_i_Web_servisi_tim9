<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:za="http://www.projekat.org/zahtev"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
     <head>
        <style>
          body {
          	margin-top:10px;
            margin-left:25px;
            margin-right:25px;
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
            margin-left:200px;
            margin-right:200px;
          }
        </style>
      </head>
  <body>
    <div class="center">
      <xsl:value-of select="za:zahtev/za:institucija/."/>
      <br></br>
      <hr></hr>
      (naziv i sedište organa kome se zahtev upućuje)
    </div>
    <h2 class="headertekst">Zahtev za pristupanje informacijama</h2>
    <p class="indent">Na osnovu člana 15. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja („Službeni glasnik RS“, br. 120/04, 54/07, 104/09 i 36/10) od gore navedenog organa zahtevam:*</p>
    <ul>
      <p>
        <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[1]/@za:checked = 'true'">
        	<span font-size="12pt">☑</span>
    		</xsl:if>
       	<xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[1]/@za:checked = 'false'">
        	<span font-size="12pt">☐</span>
    		</xsl:if>
  			obaveštenje da li poseduje traženu informaciju;
      </p>
    </ul>
    <ul>
      <p>
        <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[2]/@za:checked = 'true'">
        	<span font-size="12pt">☑</span>
    		</xsl:if>
       	<xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[2]/@za:checked = 'false'">
        	<span font-size="12pt">☐</span>
    		</xsl:if>
				uvid u dokument koji sadrži traženu informaciju;
      </p>
    </ul>
    <ul>
      <p>
        <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[3]/@za:checked = 'true'">
        	<span font-size="12pt">☑</span>
    		</xsl:if>
       	<xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[3]/@za:checked = 'false'">
        	<span font-size="12pt">☐</span>
    		</xsl:if>
        kopiju dokumenta koji sadrži traženu informaciju;
      </p>
    </ul>
    <ul>
      <p>
        <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/@za:checked = 'true'">
        	<span font-size="12pt">☑</span>
    		</xsl:if>
       	<xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/@za:checked = 'false'">
        	<span font-size="12pt">☐</span>
    		</xsl:if>
        dostavljanje kopije dokumenta koji sadrži traženu informaciju:** 
      </p>
      <ul>
         <p>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[1]/@za:checked = 'true'">
            <span font-size="12pt">☑</span>
          </xsl:if>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[1]/@za:checked = 'false'">
            <span font-size="12pt">☐</span>
          </xsl:if>
           poštom
        </p>
      </ul>
      <ul>
        <p>
	        <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[2]/@za:checked = 'true'">
            <span font-size="12pt">☑</span>
          </xsl:if>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[2]/@za:checked = 'false'">
            <span font-size="12pt">☐</span>
          </xsl:if>
          elektronskom poštom
        </p>
      </ul>
      <ul>
        <p>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[3]/@za:checked = 'true'">
            <span font-size="12pt">☑</span>
          </xsl:if>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[3]/@za:checked = 'false'">
            <span font-size="12pt">☐</span>
          </xsl:if>
          faksom
        </p>
      </ul>
      <ul>
        <p>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[4]/@za:checked = 'true'">
            <span font-size="12pt">☑</span>
          </xsl:if>
          <xsl:if test="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[4]/za:lista/za:stavka[4]/@za:checked = 'false'">
            <span font-size="12pt">☐</span>
          </xsl:if>
          na drugi način***:<u><xsl:value-of select="za:zahtev/za:tekst_zahteva/za:lista/za:stavka[@za:id='4']/za:lista/za:stavka[@za:id='8']/za:drugi_nacin/."/></u></p>
      </ul>
    </ul>
    <p class="indent">Ovaj zahtev se odnosi na sledeće informacije:</p>
    <p class="indent">
      <u>
        <xsl:value-of select="za:zahtev/za:tekst_zahteva/za:informacije/."/>
      </u>
    </p> 

    <p style="text-align:left;">
      U <u><xsl:value-of select="za:zahtev/za:mesto_datum/za:mesto/."/></u><br></br> 
      Datuma <u><xsl:value-of select="za:zahtev/za:mesto_datum/za:datum/."/></u>
      <span style="float:right;">
        <table>
          <tr>
            <td>Ime i prezime:</td>
            <td><u><xsl:value-of select="za:zahtev/za:podnosilac/za:osoba/."/></u> </td>
          </tr>
          <tr>
          	<td>Adresa:</td>
            <td><u><xsl:value-of select="za:zahtev/za:podnosilac/za:adresa/."/></u></td>
          </tr>
          <tr>
          	<td>Drugi podaci:</td>
          	<td><u><xsl:value-of select="za:zahtev/za:podnosilac/za:drugi_podaci/."/></u></td>
          </tr>
          <tr>
          	<td>Potpis:</td>
          	<td><u><xsl:value-of select="za:zahtev/za:podnosilac/za:potpis/."/> </u></td>
          </tr>
        </table>
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

