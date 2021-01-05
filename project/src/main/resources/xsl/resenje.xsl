<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:res="http://www.projekat.org/resenje"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  >
  <xsl:template match="/">
    <html>
      <head>
        <style>
          p.indent {
            text-indent: 12px;
          }
          h2.headertekst {
            text-align: center;
          }
          h4.headertekst {
            text-align: center;
          },
        </style>
      </head>
      <body>
        <xsl:if test="res:zalba/@status = 'osnovana'">  
          Resenje kada je zalba osnovana nalaze se:<br></br>
        </xsl:if> 
        <xsl:if test="res:zalba/@status = 'neosnovana'">
          Resenje: odbija se kao neosnovana:<br></br>
        </xsl:if>
        <xsl:if test="res:zalba/@status = 'odbijena'">  
          Resenje - odbija se:<br></br>
        </xsl:if> 
        <xsl:if test="res:zalba/@status = 'ponistena'">  
          Resenje - ponistava se:<br></br>
        </xsl:if> 
        
        <p style="text-align:left;">
          Broj: <xsl:value-of select="res:zalba/@broj"/>
          <span style="float:right;">
            Datum: <xsl:value-of select="res:zalba/@datum"/>
          </span>
        </p>
        <xsl:value-of select="res:zalba/res:uvod/res:paragraf/."/>
        <br></br>
        
        <xsl:if test="res:zalba/res:sadrzaj/res:resenje/res:naslov/@res:nivo = '1'">
          <h1 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:resenje/res:naslov/."/></h1>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:resenje/res:naslov/@res:nivo = '2'">
          <h2 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:resenje/res:naslov/."/></h2>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:resenje/res:naslov/@res:nivo = '3'">
          <h3 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:resenje/res:naslov/."/></h3>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:resenje/res:naslov/@res:nivo = '4'">
          <h4 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:resenje/res:naslov/."/></h4>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:resenje/res:naslov/@res:nivo = '5'">
          <h5 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:resenje/res:naslov/."/></h5>
        </xsl:if> 
        
        <xsl:for-each select="res:zalba/res:sadrzaj/res:resenje/res:paragraf">
          <p class="indent"><xsl:value-of select="."/></p>
        </xsl:for-each>
        
        <xsl:if test="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/@res:nivo = '1'">
          <h1 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/."/></h1>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/@res:nivo = '2'">
          <h2 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/."/></h2>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/@res:nivo = '3'">
          <h3 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/."/></h3>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/@res:nivo = '4'">
          <h4 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/."/></h4>
        </xsl:if> 
        <xsl:if test="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/@res:nivo = '5'">
          <h5 class="headertekst"><xsl:value-of select="res:zalba/res:sadrzaj/res:obrazlozenje/res:naslov/."/></h5>
        </xsl:if> 
        
        <xsl:for-each select="res:zalba/res:sadrzaj/res:obrazlozenje/res:paragraf">
          <p class="indent"><xsl:value-of select="."/></p>
        </xsl:for-each>
        
        <p style="text-align:right;">
          POVERENIK:
          <xsl:value-of select="res:zalba/res:sadrzaj/res:poverenik/."/>
        </p>        
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>

