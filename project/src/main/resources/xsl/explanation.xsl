<xsl:stylesheet version="1.0"
xmlns:ex="http://www.projekat.org/explanation"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <style>
                    body {
                        margin: 25px;
                    }
                    p.indent {
                        text-indent: 24px;
                    }
                    h3.headertekst {
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <h3 class="headertekst">OBRAŽLOŽENJE ORGANA VLASTI POVODOM ŽALBE ZBOG ODBIJANJA ZAHTEVA ZA PRISTUP
                    INFORMACIJAMA OD JAVNOG ZNAČAJA</h3>
                <p class="indent">
                    Povereniku za informacije od javnog značaja i zaštitu podataka o licnosti
                </p>
                <p class="indent">
                    Adresa za poštu: Beograd, Bulevara kralja Aleksandra br.15
                </p>
                <p class="indent">Obrazloženje:</p>
                <p class="indent">
                    <xsl:value-of select="ex:explanation/ex:text/." />
                </p>
                <table style="text-align:left; margin-left: 20px;">
                    <tr>
                        <td>Službenik:</td>
                    </tr>
                    <tr>
                        <td>Ime:</td>
                        <td>
                            <u><xsl:value-of select="ex:explanation/ex:ime/." /></u>
                        </td>
                    </tr>
                    <tr>
                        <td>Prezime:</td>
                        <td>
                            <u><xsl:value-of select="ex:explanation/ex:prezime/." /></u>
                        </td>
                    </tr>
                    <tr>
                        <td>Potpis:</td>
                        <td>
                            <u><xsl:value-of select="ex:explanation/ex:potpis/." /></u>
                        </td>
                    </tr>
                </table>
                <br></br>
                <div style="text-align:left; margin-left: 20px;">
	                U <u><xsl:value-of select="ex:explanation/ex:mesto/." /></u> dana
	                <u><xsl:value-of select="ex:explanation/ex:datum/." /></u>                
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>