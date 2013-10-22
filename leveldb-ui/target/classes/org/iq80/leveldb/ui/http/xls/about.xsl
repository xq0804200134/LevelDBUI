<?xml version="1.0"?>
<!--
 Copyright (C) The MX4J Contributors.
 All rights reserved.

 This software is distributed under the terms of the MX4J License version 1.0.
 See the terms of the MX4J License in the documentation provided with this software.

 Author: Carlos Quiroz (tibu@users.sourceforge.net)
 Revision: $Revision: 1.2 $
																																					-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:output method="html" indent="yes" encoding="ISO-8859-1"/>
   <xsl:include href="common.xsl"/>
   <xsl:include href="xalan-ext.xsl"/>

   <xsl:param name="html.stylesheet">stylesheet.css</xsl:param>
   <xsl:param name="html.stylesheet.type">text/css</xsl:param>
   <xsl:param name="head.title">about.title</xsl:param>

   <!-- Main template -->
   <xsl:template match="/">
      <html>
         <xsl:call-template name="head"/>
         <body>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <tr width="100%">
                  <td>
                     <xsl:call-template name="toprow"/>
                     <xsl:call-template name="tabs">
                        <xsl:with-param name="selection">about</xsl:with-param>
                     </xsl:call-template>
                     <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr class="about">
                           <td colspan="2">
                              <h1 align="center" class="about">
                                 <xsl:variable name="str.url">
                                    <xsl:call-template name="str">
                                       <xsl:with-param name="id">about.main.url</xsl:with-param>
                                    </xsl:call-template>
                                 </xsl:variable>
                                 <a href="{$str.url}">LevelDb-UI</a>
                              </h1>
                           </td>
                        </tr>
                        <tr class="about">
                           <td colspan="2">
                              <h1 align="center" class="about">
                                 <xsl:variable name="str.url1">
                                    <xsl:call-template name="str">
                                       <xsl:with-param name="id">common.site</xsl:with-param>
                                    </xsl:call-template>
                                 </xsl:variable>
                                 Built based on the <a href="{$str.url1}">Level(java)</a> specification
                              </h1>
                           </td>
                        </tr>
                        
                        
                     </table>
                     <xsl:call-template name="bottom"/>
                  </td>
               </tr>
            </table>
         </body>
      </html>
   </xsl:template>
</xsl:stylesheet>

