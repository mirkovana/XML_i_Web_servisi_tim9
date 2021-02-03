<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 xmlns:ob="http://www.projekat.org/obavestenje"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>

        <head>
            <style>
                body {
	              margin:25px;
	            }
                h1 {
                    text-align: center;
                }

                h3 {
                    text-align: center;
                }

                .tekst {
                    text-align: center;
                }
                p.indent {
		           text-indent: 24px;
		        }
            </style>
        </head>

        <body>
            <table>
				<tr>
					<td><b> Naziv organa: </b></td>
					<td><xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_organu/ob:naziv/." /></td>
				</tr>
				<tr>
					<td><b>Sediste organa: </b></td>
					<td><xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_organu/ob:sediste/."/></td>
				</tr>
				<tr>
					<td><b>Broj predmeta: </b></td>
					<td>
						<a>
		         			<xsl:attribute name="href">
		           				<xsl:text>http://localhost:8070/api/request/html/</xsl:text>
		          	   			<xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:broj_predmeta" />
		              		</xsl:attribute>
		  					<xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:broj_predmeta" />
						</a>
					</td>
				</tr>
				<tr>
					<td><b>Datum: </b></td>
					<td><xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:datum/."/></td>
				</tr>
				<tr>
					<td><b>Ime podnosioca: </b></td>
					<td><xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:ime/."/></td>
				</tr>
               	<tr>
					<td><b>Prezime podnosioca: </b></td>
					<td><xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:prezime/."/></td>
				</tr>
				<tr>
					<td><b>Adresa: </b></td>
					<td>
						<xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:adresa/ob:naziv_ulice" />
		                &#160;
		                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:adresa/ob:grad" />
					</td>
				</tr>
			</table>

                <h1>
                   O B A V E S T E NJ E
                </h1>
                <h3>
                    o stavljanju na uvid dokumenta koji sadrži traženu informaciju i o izradi kopije
                </h3>
                <p class="indent">
                    Na osnovu člana 16. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja, postupajući
                    po vašem zahtevu za slobodan pristup informacijama od
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:godina" /> god., kojim ste tražili uvid u dokument/e sa
                    informacijama o / u vezi sa:
                    <xsl:value-of select="obavestenje/telo/opis" /> obaveštavamo vas da dana
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:datum" />
                    časova, odnosno u vremenu od
                    <xsl:variable name="od"  select="ob:obavestenje/ob:telo/ob:od" />
                    <xsl:variable name="odNovo"  select=" substring($od, 1, 5)" />
                    <xsl:value-of select="$odNovo"/>
                    do
                    <!-- <xsl:value-of select="obavestenje/telo/do" /> -->
                    <xsl:variable name="do"  select="ob:obavestenje/ob:telo/ob:do" />
                    <xsl:variable name="doNovo"  select=" substring($do, 1, 5)" />
                    <xsl:value-of select="$doNovo"/>
                    časova, u prostorijama organa u
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:adresa_organa/ob:naziv_ulice" /> br.
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:adresa_organa/ob:broj_ulice" /> kancelarija br.
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:adresa_organa/ob:broj_kancelarije" />
                    možete izvršiti uvid u dokument/e u kome je sadržana tražena informacija.
                </p>

                <p class="indent">
                    Tom prilikom, na vaš zahtev, može vam se izdati i kopija dokumenta sa traženom informacijom.
                </p>

                <p class="indent">
                    Troškovi su utvrđeni Uredbom Vlade Republike Srbije („Sl. glasnik RS“, br. 8/06), i to: kopija
                    strane A4 formata iznosi 3 dinara, A3 formata 6 dinara, CD 35 dinara, diskete 20 dinara, DVD 40
                    dinara, audio-kaseta – 150 dinara, video-kaseta 300 dinara, pretvaranje jedne strane dokumenta iz
                    fizičkog u elektronski oblik – 30 dinara.
                </p>

                <p class="indent">
                    Iznos ukupnih troškova izrade kopije dokumenta po vašem zahtevu iznosi
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:iznos" /> dinara i uplaćuje se na žiro-račun Budžeta
                    Republike Srbije br. 840-742328-843-30, s pozivom na broj 97 – oznaka šifre opštine/grada gde se
                    nalazi organ vlasti (iz Pravilnika o uslovima i načinu vođenja računa – „Sl. glasnik RS“, 20/07...
                    40/10).
                </p>

                <br/>

                    <b>Dostavljeno: </b>
                    <br/>
                    <b><i>1. Imenovanom</i></b>
                    <br/>
                    <b><i>2. Arhivi</i></b>
           
            <br/>
            Potpis ovlascenog lica: <u><xsl:value-of select="ob:obavestenje/ob:potpis/."/></u>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>