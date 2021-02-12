<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>

        <head>
            <style>
                body {
                    margin: 25px;
                }

                p.indent {
                    text-indent: 12px;
                }

                h3.headertekst {
                    text-align: center;
                }

                table,
                th,
                td {
                    border: 1px solid black;
                    border-collapse: collapse;
                    text-align: center;
                }

                .center {
                    margin-top: 20px;
                    margin-left: auto;
                    margin-right: auto;
                }
            </style>
        </head>

        <body>
            <h3 class="headertekst">IZVEŠTAJ</h3>
            <p class="indent">
                Povereniku za informacije od javnog značaja i zaštitu podataka o licnosti
            </p>
            <p class="indent">
                Adresa za poštu: Beograd, Bulevara kralja Aleksandra br.15
            </p>
            <p class="indent">
                Datum:
                <xsl:value-of select="izvestaj/@datum/." />
            </p>
            <p class="indent">
                Službenik:
                <xsl:value-of select="izvestaj/@user/." />
            </p>
            <p class="indent">
                <b>1.Zahtevi:</b>
                <table class="center">
                    <thead>
                        <th>Red.broj</th>
                        <th>Tražilac Informacije</th>
                        <th>Br. podnetih zahteva</th>
                        <th>Br.usvojenih zahteva</th>
                        <th>Br. odbačenih zahteva</th>
                        <th>Br. odbijenih zahteva</th>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <xsl:value-of select="izvestaj/@id/." />
                            </td>
                            <td>Građani</td>
                            <td>
                                <xsl:value-of select="izvestaj/zahtevi/brojPodnetih/." />
                            </td>
                            <td>
                                <xsl:value-of select="izvestaj/zahtevi/brojUsvojenih/." />
                            </td>
                            <td>
                                <xsl:value-of select="izvestaj/zahtevi/brojOdbacenih/." />
                            </td>
                            <td>
                                <xsl:value-of select="izvestaj/zahtevi/brojOdbijenih/." />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </p>
            <p class="indent">
                <b>2.Žalbe:</b>
                <table class="center">
                    <thead>
                        <th>Red.broj</th>
                        <th>Tražilac Informacije</th>
                        <th>Ukupan broj izjavljenih žalbi</th>
                        <th>Br. žalbi zbog nepostupanja po zahtevu</th>
                        <th>Br. žalbi zbog odbijanja zahteva</th>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <xsl:value-of select="izvestaj/@id/." />
                            </td>
                            <td>Građani</td>
                            <td>
                                <xsl:value-of select="izvestaj/zalbe/brojPodnetih/." />
                            </td>
                            <td>
                                <xsl:value-of select="izvestaj/zalbe/brojZbogNepostupanja/." />
                            </td>
                            <td>
                                <xsl:value-of select="izvestaj/zalbe/brojZbogOdbijanja/." />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </p>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>