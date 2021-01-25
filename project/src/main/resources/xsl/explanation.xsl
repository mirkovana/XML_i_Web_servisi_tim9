<xsl:stylesheet version="1.0"
xmlns:ex="http://www.projekat.org/explanation"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <style>
                    body {
                        margin: 100px;
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
                    INFORMACIJAMA OD JAVNOG ZNACAJA</h3>
                <p class="indent">
                    Povereniku za informacije od javnog znacaja i zastitu podataka o licnosti
                </p>
                <p class="indent">
                    Adesa za postu: Beograd, Bulevara kralja Aleksandra br.15
                </p>
                <p class="indent">Obrazlozenje:</p>
                <p class="indent">
                    <xsl:value-of select="ex:explanation/ex:text/." />
                </p>
                <table style="text-align:left; margin-left: 20px;">
                    <tr>
                        <td>Sluzbenik:</td>
                    </tr>
                    <tr>
                        <td>Ime:</td>
                        <td>
                            <xsl:value-of select="ex:explanation/ex:ime/." />
                        </td>
                    </tr>
                    <tr>
                        <td>Prezime:</td>
                        <td>
                            <xsl:value-of select="ex:explanation/ex:prezime/." />
                        </td>
                    </tr>
                    <tr>
                        <td>Potpis:</td>
                        <td>
                            <xsl:value-of select="ex:explanation/ex:potpis/." />
                        </td>
                    </tr>
                    <tr>
                        <td>U
                            <xsl:value-of select="ex:explanation/ex:mesto/." />
                        </td>
                        <td>dana
                            <xsl:value-of select="ex:explanation/ex:datum/." />
                        </td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>