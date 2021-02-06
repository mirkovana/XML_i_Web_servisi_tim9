<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:zc="http://www.projekat.org/zalbazbogcutanja"
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
          h3.headertekst {
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
   	  <p class="center"><b>ŽALBA KADA ORGAN VLASTI NIJE POSTUPIO/ NIJE POSTUPIO U CELOSTI / PO ZAHTEVU<br></br>TRAŽIOCA U ZAKONSKOM ROKU (ĆUTANjE UPRAVE)</b></p>
	  <p><b>Povereniku za informacije od javnog znacaja i zastitu podataka o licnosti</b></p>
	  <p>Adesa za postu: Beograd, Bulevara kralja Aleksandra br.15</p>
	  <br></br>
	  <p>U skladu sa clanom 22.Zakona o slobodnom pristupu informacijama od javnog znacaja podnosim:</p>
	  <br></br>
	  <h3 class="headertekst">ZALBU</h3>
	  <p class="center">protiv</p>
	  <p class="center"><xsl:value-of select="zc:zalba_cutanje/zc:telo_zalbe/zc:naziv_organa/."/></p>
	  <hr></hr>
	  <p class="center">(navesti naziv organa)</p>
	  <p class="center">zbog toga sto organ vlasti nije:</p>
	  <div class="center">
	    <xsl:for-each select="zc:zalba_cutanje/zc:telo_zalbe/zc:razlozi/zc:razlog">
	      <xsl:if test="./@podvuceno = 'true'">
	        <b><u><xsl:value-of select="."/>/</u></b>
	      </xsl:if> 
	      <xsl:if test="./@podvuceno = 'false'">
	        <b><xsl:value-of select="."/>/</b>
	      </xsl:if> 
	    </xsl:for-each>
	    <p class="center">(podvuci zbog cega se izjavljuje zalba)</p>
	  </div>
	  po mom zahtevu za slobodan pristup informacijama od javnog značaja koji sam podneo tom organu dana
	  <u>
	    <xsl:value-of select="zc:zalba_cutanje/zc:telo_zalbe/zc:datum/."/>
	  </u> 
	   godine, a kojim sam tražio/la da mi se u skladu sa Zakona o slobodnom pristupu informacijama od javnog značaja omogući uvid- kopija dokumenta koji sadrži informacije o /u vezi sa :
	   <p class="center">
	   	    <xsl:value-of select="zc:zalba_cutanje/zc:telo_zalbe/zc:podaci_o_zahtevu_i_informaciji/."/>
	   
	   </p>
	  
	  <p>.....................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................
                               <p class="center">(navesti podatke o zahtevu i informaciji/ama)</p> 
	  </p>
	  <p class="indent">
	    Na osnovu iznetog, predlažem da Poverenik uvaži moju žalbu i omogući mi pristup traženoj/im informaciji/ma.
	  </p>
	  <p class="indent">
	    Kao dokaz , uz žalbu dostavljam kopiju zahteva sa dokazom o predaji organu vlasti
	  </p>
	  <p class="indent">
	    <b>Napomena:</b>Kod žalbe zbog nepostupanju po zahtevu u celosti, treba priložiti i dobijeni odgovor organa vlasti.
	  </p>
	  <p style="text-align:left;">
		  U <u><xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_mestu_i_datumu_podnosenja_zalbe/zc:mesto/."/></u><br></br> 
		  Datuma <xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_mestu_i_datumu_podnosenja_zalbe/zc:datum/."/>
	  	  <span style="float:right;">
	  	  	<table>
		  		<tr>
		  			<td>Ime:</td>
		  			<td><u><xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:ime/."/></u></td>
		  		</tr>
		  		<tr>
		  			<td>Prezime:</td>
		  			<td><u><xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:prezime/."/></u></td>
		  		</tr>
		  		<tr>
		  			<td>Potpis:</td>
		  			<td><u><xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:potpis/."/></u></td>
		  		</tr>
		  		<tr>
		  			<td>Adresa:</td>
		  			<td>
			  			<u>
					      <xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:adresa/zc:grad/."/>,
					      <xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:adresa/zc:ulica/."/>,
					      <xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:adresa/zc:broj/."/>,
					    </u>
		  			</td>
		  		</tr>
				<tr>
					<td>Drugi podaci za kontakt:</td>
					<td><u><xsl:value-of select="zc:zalba_cutanje/zc:podaci_o_podnosiocu_zalbe/zc:drugi_podaci_za_kontakt/."/></u></td>
				</tr>
		  	</table>  
	  	  </span>	
	  </p> 
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

