<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:zo="http://www.projekat.org/zalbanaodluku"
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
   <h4 class="headertekst">
ZALBA PROTIV ODLUKE ORGANA VLASTI KOJOM JE <br></br><u>ODBIJEN ILI ODBACEN ZAHTEV</u> ZA PRISTUP INFORMACIJI
   </h4>
   <p><b>Povereniku za informacije od javnog znacaja i zastitu podataka o licnosti</b></p>
   <p>Adresa za postu:Beograd, Bulevar kralja aleksandra br.15</p>
  <h3 class="headertekst">ZALBA</h3>
  <div class="center">
<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:podnosilac_zalbe/zo:ime/."/>,<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:podnosilac_zalbe/zo:prezime/."/>,<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:podnosilac_zalbe/zo:adresa/."/>

  <hr></hr>
(Ime, prezime, odnosno naziv, adresa i sediste zalioca)
  </div>
  <p class="center"> protiv resenja-zakljucka</p>
  <div class="center">
<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:organ_koji_je_doneo_odluku/."/>
    <hr></hr>
    (Naziv organa koji je doneo odluku)
  </div>
  <p>
Broj: <xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:broj_zalbe/."/> od <xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:datum/."/> godine.
  </p>
<p class="indent">
 <xsl:value-of select="zo:zalba_na_odluku/zo:sadrzaj_zalbe/."/>
</p>

<p style="text-align:left;">
      U <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_mestu_i_datumu_podnosenja_zalbe/zo:mesto/."/></u><br></br> 
      Datuma <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_mestu_i_datumu_podnosenja_zalbe/zo:datum/."/></u>
      <span style="float:right;">
        Ime: <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:ime"/> <xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:prezime"/></u> <br></br>
        Adresa: <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:adresa"/></u> <br></br>
        Drugi podaci: <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:drugi_podaci_za_kontakt"/></u> <br></br>
        Potpis: <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:potpis"/></u>
      </span>
    </p>
    <br></br><br></br>
<p class="indent"><b>Napomena:</b></p>
<xsl:for-each select="zo:zalba_na_odluku/zo:napomena/zo:tacka">
<ul>
          <p><li> <xsl:value-of select="."/> </li></p>

</ul>
        </xsl:for-each>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

