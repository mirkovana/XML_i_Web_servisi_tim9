<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
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
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_organu/naziv" />
                <br />

                <b>Sediste organa: </b>
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_organu/sediste" />
                <br />

                <b>Broj predmeta: </b>
                <xsl:value-of select="obavestenje/opste_informacije/broj_predmeta" />
                <br />

                <b>Datum: </b>
                <xsl:variable name="dan1"  select="obavestenje/opste_informacije/datum/dan" />
                <xsl:variable name="noviDAN1" select="translate($dan1,'-','')"/>
                <xsl:value-of select="$noviDAN1"/>.

                <xsl:variable name="mesec1"  select="obavestenje/opste_informacije/datum/mesec" />
                <xsl:variable name="noviMESEC1" select="translate($mesec1,'-','')"/>
                <xsl:value-of select="$noviMESEC1"/>.

                <xsl:value-of select="obavestenje/opste_informacije/datum/godina" />.
                <br />
                <br />

                <b>Ime podnosioca: </b>
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/ime" />
                <br />

                <b>Prezime podnosioca: </b>
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/prezime" />
                <br />

                <b>Naziv zahteva: </b>
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/naziv_zahteva" />
                <br />

                <b>Adresa: </b>
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/adresa/naziv_ulice" />
                &#160;
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/adresa/broj_ulice" />
                &#160;
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/adresa/grad" />
                &#160;
                <xsl:value-of select="obavestenje/opste_informacije/podaci_o_podnosiocu/adresa/postanski_broj" />
                <br />

                <h1>
                    <xsl:value-of select="obavestenje/naslov" />
                </h1>
                <h3>
                    <xsl:value-of select="obavestenje/podnaslov" />
                </h3>
                <p class="tekst">
                    Na osnovu člana 16. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja, postupajući
                    po vašem zahtevu za slobodan pristup informacijama od
                    <xsl:value-of select="obavestenje/telo/godina" /> god., kojim ste tražili uvid u dokument/e sa
                    informacijama o / u vezi sa:
                    <xsl:value-of select="obavestenje/telo/opis" /> obaveštavamo vas da dana
                    <xsl:variable name="dan"  select="obavestenje/telo/datum/dan" />
                    <xsl:variable name="noviDAN" select="translate($dan,'-','')"/>
                    <xsl:value-of select="$noviDAN"/>.

                    <xsl:variable name="mesec"  select="obavestenje/telo/datum/mesec" />
                    <xsl:variable name="noviMESEC" select="translate($mesec,'-','')"/>
                    <xsl:value-of select="$noviMESEC"/>.

                    <xsl:value-of select="obavestenje/telo/datum/godina" />. u
                    <xsl:variable name="vreme"  select="obavestenje/telo/vreme" />
                    <xsl:variable name="vremeNovo"  select=" substring($vreme, 1, 5)" />
                    <xsl:value-of select="$vremeNovo"/>

                    <!-- <xsl:value-of select="obavestenje/telo/vreme" /> -->
                    časova, odnosno u vremenu od
                    <!-- <xsl:value-of select="obavestenje/telo/od" /> -->
                    <xsl:variable name="od"  select="obavestenje/telo/od" />
                    <xsl:variable name="odNovo"  select=" substring($od, 1, 5)" />
                    <xsl:value-of select="$odNovo"/>
                    do
                    <!-- <xsl:value-of select="obavestenje/telo/do" /> -->
                    <xsl:variable name="do"  select="obavestenje/telo/do" />
                    <xsl:variable name="doNovo"  select=" substring($do, 1, 5)" />
                    <xsl:value-of select="$doNovo"/>
                    časova, u prostorijama organa u
                    <xsl:value-of select="obavestenje/telo/adresa_organa/naziv_ulice" /> br.
                    <xsl:value-of select="obavestenje/telo/adresa_organa/broj_ulice" /> kancelarija br.
                    <xsl:value-of select="obavestenje/telo/adresa_organa/broj_kancelarije" />
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
                    <xsl:value-of select="obavestenje/telo/iznos" /> dinara i uplaćuje se na žiro-račun Budžeta
                    Republike Srbije br. 840-742328-843-30, s pozivom na broj 97 – oznaka šifre opštine/grada gde se
                    nalazi organ vlasti (iz Pravilnika o uslovima i načinu vođenja računa – „Sl. glasnik RS“, 20/07...
                    40/10).
                </p>

                <br />

                    <b>Dostavljeno: </b>
                <xsl:if test="obavestenje/dostavljeno = 'Imenovanom'">
                    <br/>
                    &#x3000;<b><i>1. Imenovanom</i></b>
                    <br/>
                    &#x3000;2. Arhivi
                </xsl:if>
                <xsl:if test="obavestenje/dostavljeno = 'Arhivi'">
                    <br/>
                    &#x3000;1. Imenovanom
                    <br/>
                    &#x3000;<b></i>2. Arhivi</i></b>   
            </xsl:if>
               
            </table>
        </body>

        </html>
    </xsl:template>
</xsl:stylesheet>