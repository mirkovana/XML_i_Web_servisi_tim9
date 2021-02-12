<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:zo="http://www.projekat.org/zalbanaodluku"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <head>
        <style>
          body {
            margin:25px;
          }
          p.indent {
            text-indent: 24px;
          }
          h3.headertekst {
          text-align: center;
          }
          h4.headertekst {
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
    <h4 class="headertekst">ŽALBA PROTIV ODLUKE ORGANA VLASTI KOJOM JE <br></br><u>ODBIJEN ILI ODBAČEN ZAHTEV</u> ZA PRISTUP INFORMACIJI</h4>
	<p><b>Povereniku za informacije od javnog značaja i zaštitu podataka o ličnosti</b></p>
	<p>Adresa za poštu: Beograd, Bulevar kralja Aleksandra br.15</p>
	<h3 class="headertekst">ŽALBA</h3>
	<div class="center">
		<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:podnosilac_zalbe/zo:ime/."/>,<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:podnosilac_zalbe/zo:prezime/."/>,<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:podnosilac_zalbe/zo:adresa/."/>
		<hr></hr>
	    (Ime, prezime, odnosno naziv, adresa i sedište zalioca)
	</div>
	<p class="center"> protiv rešenja-zaključka</p>
	<div class="center">
		<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:organ_koji_je_doneo_odluku/."/>
	    <hr></hr>
	    (Naziv organa koji je doneo odluku)
	</div>
	<p>Broj: 
	 <a><xsl:attribute name="href">
          <xsl:text>http://localhost:8080/api/request/html/</xsl:text>
		  <xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:broj_zalbe/."/> 
        </xsl:attribute>
		<xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:broj_zalbe/."/> 
        </a>
	od <xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_zalbi/zo:datum/."/> godine.</p>
	<p class="indent">
		Navedenom odlukom organa vlasti (rešenjem, zaključkom, obaveštenjem u pisanoj formi sa 
        elementima odluke) , suprotno zakonu, odbijen-odbačen je moj zahtev koji sam podneo/la-uputio/la
        dana <xsl:value-of select="zo:zalba_na_odluku/zo:sadrzaj_zalbe/zo:datum/."/>  godine i tako mi uskraćeno-onemogućeno ostvarivanje ustavnog i zakonskog 
        prava na slobodan pristup informacijama od javnog značaja. Odluku pobijam u celosti, odnosno u 
        delu kojim <xsl:value-of select="zo:zalba_na_odluku/zo:sadrzaj_zalbe/zo:zbog_cega_se_pobija_odluka/."/> jer nije zasnovana na Zakonu o slobodnom pristupu informacijama od javnog značaja.
        Na osnovu iznetih razloga, predlažem da Poverenik uvaži moju žalbu, poništi odluka prvostepenog organa i omogući mi pristup traženoj/im informaciji/ma.
        Žalbu podnosim blagovremeno, u zakonskom roku utvrđenom u 
        članu 22. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja.
	</p>
	<p style="text-align:left;">
	  U <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_mestu_i_datumu_podnosenja_zalbe/zo:mesto/."/></u><br></br> 
	  Datuma <u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_mestu_i_datumu_podnosenja_zalbe/zo:datum/."/></u>
	  <span style="float:right;">
	  	<table>
	  		<tr>
	  			<td>Ime:</td>
	  			<td><u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:ime"/></u></td>
	  		</tr>
	  		<tr>
	  			<td>Prezime:</td>
	  			<td><u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:prezime"/></u></td>
	  		</tr>
	  		<tr>
	  			<td>Adresa:</td>
	  			<td><u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:adresa"/></u></td>
	  		</tr>
	  		<tr>
	  			<td>Drugi podaci:</td>
	  			<td><u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:drugi_podaci_za_kontakt"/></u></td>
	  		</tr>
	  		<tr>
	  			<td>Potpis:</td>
	  			<td><u><xsl:value-of select="zo:zalba_na_odluku/zo:podaci_o_podnosiocu_zalbe/zo:potpis"/></u></td>
	  		</tr>
	  	</table>
	  </span>
	</p>
	<br></br><br></br><br></br><br></br>
	<p class="indent"><b>Napomena:</b></p>
	<ul>
		<p><li>U žalbi se mora navesti odluka koja se pobija (rešenje, zaključak, obaveštenje), 
          naziv organa koji je odluku doneo, kao i broj i datum odluke. 
          Dovoljno je da žalilac navede u žalbi u kom pogledu je nezadovoljan odlukom,
          s tim da žalbu ne mora posebno obrazložiti. 
          Ako žalbu izjavljuje na ovom obrascu, dodatno obrazloženje može posebno priložiti.</li></p>
	</ul>
	<ul>
		<p><li>Uz žalbu obavezno priložiti kopiju podnetog zahteva i dokaz o njegovoj 
          predaji-upućivanju organu kao i kopiju odluke organa koja se osporava žalbom.</li></p>
	</ul>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>