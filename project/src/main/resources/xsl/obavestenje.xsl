<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 xmlns:ob="http://www.ftn.uns.ac.rs/obavestenje"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>

        <head>
            <style>
                h1 {
                    text-align: center;
                }

                h3 {
                    text-align: center;
                }

                .tekst {
                    text-align: center;
                }
            </style>
        </head>

        <body>
            <table class="opste_informacije">

                <b> Naziv organa: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_organu/ob:naziv" />
                <br />

                <b>Sediste organa: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_organu/ob:sediste" />
                <br />

                <b>Broj predmeta: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:broj_predmeta" />
                <br />

                <b>Datum: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:datum" />
                <br />
                <br />

                <b>Ime podnosioca: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:ime" />
                <br />

                <b>Prezime podnosioca: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:prezime" />
                <br />

                <b>Naziv zahteva: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:naziv_zahteva" />
                <br />

                <b>Adresa: </b>
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:adresa/ob:naziv_ulice" />
                &#160;
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:adresa/ob:broj_ulice" />
                &#160;
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:adresa/ob:grad" />
                &#160;
                <xsl:value-of select="ob:obavestenje/ob:opste_informacije/ob:podaci_o_podnosiocu/ob:adresa/ob:postanski_broj" />
                <br />

                <h1>
                    <xsl:value-of select="ob:obavestenje/ob:naslov" />
                </h1>
                <h3>
                    <xsl:value-of select="ob:obavestenje/ob:podnaslov" />
                </h3>
                <p class="tekst">
                    Na osnovu člana 16. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja, postupajući
                    po vašem zahtevu za slobodan pristup informacijama od
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:godina" /> god., kojim ste tražili uvid u dokument/e sa
                    informacijama o / u vezi sa:
                    <xsl:value-of select="obavestenje/telo/opis" /> obaveštavamo vas da dana
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:datum" />

                    <!-- <xsl:value-of select="obavestenje/telo/vreme" /> -->
                    časova, odnosno u vremenu od
                    <!-- <xsl:value-of select="obavestenje/telo/od" /> -->
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

                <p class="tekst">
                    Tom prilikom, na vaš zahtev, može vam se izdati i kopija dokumenta sa traženom informacijom.
                </p>

                <p class="tekst">
                    Troškovi su utvrđeni Uredbom Vlade Republike Srbije („Sl. glasnik RS“, br. 8/06), i to: kopija
                    strane A4 formata iznosi 3 dinara, A3 formata 6 dinara, CD 35 dinara, diskete 20 dinara, DVD 40
                    dinara, audio-kaseta – 150 dinara, video-kaseta 300 dinara, pretvaranje jedne strane dokumenta iz
                    fizičkog u elektronski oblik – 30 dinara.
                </p>

                <p class="tekst">
                    Iznos ukupnih troškova izrade kopije dokumenta po vašem zahtevu iznosi
                    <xsl:value-of select="ob:obavestenje/ob:telo/ob:iznos" /> dinara i uplaćuje se na žiro-račun Budžeta
                    Republike Srbije br. 840-742328-843-30, s pozivom na broj 97 – oznaka šifre opštine/grada gde se
                    nalazi organ vlasti (iz Pravilnika o uslovima i načinu vođenja računa – „Sl. glasnik RS“, 20/07...
                    40/10).
                </p>

                <br />

                    <b>Dostavljeno: </b>
                <xsl:if test="ob:obavestenje/ob:dostavljeno = 'Imenovanom'">
                    <br/>
                    &#x3000;<b><i>1. Imenovanom</i></b>
                    <br/>
                    &#x3000;2. Arhivi
                </xsl:if>
                <xsl:if test="ob:obavestenje/ob:dostavljeno = 'Arhivi'">
                    <br/>
                    &#x3000;1. Imenovanom
                    <br/>
                    &#x3000;<b><i>2. Arhivi</i></b>   
            </xsl:if>
               
            </table>
        </body>

        </html>
    </xsl:template>
</xsl:stylesheet>