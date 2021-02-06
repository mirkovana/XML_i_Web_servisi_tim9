<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:res="http://www.projekat.org/resenje"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  >
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
          Broj:
          <a>
         	<xsl:attribute name="href">
           		<xsl:text>http://localhost:8070/api/request/html/</xsl:text>
          	 	<xsl:value-of select="res:zalba/@broj"/>
          	</xsl:attribute>
          	<xsl:value-of select="res:zalba/@broj"/>
          </a>
          <span style="float:right;">
            Datum: <xsl:value-of select="res:zalba/@datum"/>
          </span>
        </p>
        <xsl:value-of select="res:zalba/res:uvod/res:paragraf/."/>
        <br></br>
        
        <h3 class="headertekst">RESENJE</h3>
        
        <xsl:for-each select="res:zalba/res:sadrzaj/res:resenje/res:paragraf">
          <p class="indent"><xsl:value-of select="."/></p>
        </xsl:for-each>
        
        <h3 class="headertekst">OBRAZLOZENJE</h3>
        
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

