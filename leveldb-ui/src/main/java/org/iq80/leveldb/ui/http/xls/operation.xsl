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
   <xsl:param name="head.title">operation.title</xsl:param>

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
                        <xsl:with-param name="selection">operation</xsl:with-param>
                     </xsl:call-template>
                     <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                           <form action="/query">
                              <td class="page_title">
                              	key:
                                 <input type="input" name="queryKey"/>
                                 <input type="submit" value="query"/>
                              </td>
                           </form>
                        </tr>
                        <tr>
                        	<td><hr></hr></td>
                        </tr>
                        <tr>
                           <form action="/delete">
                              <td class="page_title">
                              	key:
                                 <input type="input" name="deleteKey"/>
                                 <input type="submit" value="delete"/>
                              </td>
                           </form>
                        </tr>
                        <tr>
                        	<td><hr></hr></td>
                        </tr>
                        <tr>
                           <form action="/set">
                              <td class="page_title">
                                key:
                                 <input type="input" name="setKey"/>
                                 value
                                 <input type="input" name="setValue"/>
                                 <input type="submit" value="set"/>
                              </td>
                           </form>
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

