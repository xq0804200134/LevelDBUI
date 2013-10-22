<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="html" indent="yes" encoding="ISO-8859-1" />
	<xsl:include href="common.xsl" />
	<xsl:include href="xalan-ext.xsl" />

	<xsl:param name="html.stylesheet">stylesheet.css</xsl:param>
	<xsl:param name="html.stylesheet.type">text/css</xsl:param>
	<xsl:param name="head.title">index.title</xsl:param>
	<xsl:template name="message">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr class="clearline">
				<td width="50%" align="left" class="mbean_row">
					<xsl:value-of select="message/value" />
				</td>
			</tr>
		</table>
	</xsl:template>

	<!-- Main template -->
	<xsl:template match="/">
		<html>
			<xsl:call-template name="head" />
			<body>
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr width="100%">
						<td>
							<xsl:call-template name="toprow" />
							<xsl:call-template name="tabs">
								<xsl:with-param name="selection">message</xsl:with-param>
							</xsl:call-template>
							
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td class="page_title">
										<xsl:value-of select="message/type" />
									</td>
								</tr>
							</table>
							<xsl:call-template name="message" />
							<xsl:call-template name="bottom" />
						</td>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>

