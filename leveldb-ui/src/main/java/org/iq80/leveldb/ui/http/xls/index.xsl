<?xml version="1.0"?>
<!-- Copyright (C) The MX4J Contributors. All rights reserved. This software 
	is distributed under the terms of the MX4J License version 1.0. See the terms 
	of the MX4J License in the documentation provided with this software. Author: 
	Carlos Quiroz (tibu@users.sourceforge.net) Revision: $Revision: 1.2 $ -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="html" indent="yes" encoding="ISO-8859-1" />
	<xsl:include href="common.xsl" />
	<xsl:include href="xalan-ext.xsl" />

	<xsl:param name="html.stylesheet">
		stylesheet.css
	</xsl:param>
	<xsl:param name="html.stylesheet.type">
		text/css
	</xsl:param>
	<xsl:param name="head.title">
		index.title
	</xsl:param>

	<xsl:template name="memTable">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="7" width="100%" class="mbeans">
					memTable(display the top 10)
				</td>
			</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr class="darkline">
				<td width="20%">
					<div class="tableheader">
						startKey
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						endKey
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						size
					</div>
				</td>
				<td />
			</tr>
			<tr class="clearline">
				<td width="20%" align="left" class="mbean_row">
					<xsl:value-of select="dbInfo/memTableInfo/startKey" />
				</td>
				<td align="left" class="mbean_row">
					<xsl:value-of select="dbInfo/memTableInfo/endKey" />
				</td>
				<td align="left" class="mbean_row">
					<xsl:value-of select="dbInfo/memTableInfo/size" />
				</td>
				<td></td>
			</tr>
			<tr class="darkline">
				<td width="20%">
					<div class="tableheader">
						key
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						value
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						type
					</div>
				</td>
				<td width="*">
					<div class="tableheader">
						sequenceNumber
					</div>
				</td>
			</tr>
			<xsl:for-each select="dbInfo/memTableInfo/memTableList">
				<xsl:variable name="classtype">
					<xsl:if test="(position() mod 2)=1">
						clearline
					</xsl:if>
					<xsl:if test="(position() mod 2)=0">
						darkline
					</xsl:if>
				</xsl:variable>
				<tr class="{$classtype}">
					<td width="20%" align="left" class="mbean_row">
						<xsl:value-of select="key" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="value" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="type" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="sequenceNumber" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<xsl:template name="immutableMemTable">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="7" width="100%" class="mbeans">
					immutableMemTable(display the top 10)
				</td>
			</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr class="darkline">
				<td width="20%">
					<div class="tableheader">
						startKey
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						endKey
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						size
					</div>
				</td>
				<td />
			</tr>
			<tr class="clearline">
				<td width="20%" align="left" class="mbean_row">
					<xsl:value-of select="dbInfo/immutableMemTable/startKey" />
				</td>
				<td align="left" class="mbean_row">
					<xsl:value-of select="dbInfo/immutableMemTable/endKey" />
				</td>
				<td align="left" class="mbean_row">
					<xsl:value-of select="dbInfo/immutableMemTable/size" />
				</td>
				<td></td>
			</tr>
			<tr class="darkline">
				<td width="20%">
					<div class="tableheader">
						key
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						value
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						type
					</div>
				</td>
				<td width="*">
					<div class="tableheader">
						sequenceNumber
					</div>
				</td>
			</tr>
			<xsl:for-each select="dbInfo/immutableMemTable/memTableList">
				<xsl:variable name="classtype">
					<xsl:if test="(position() mod 2)=1">
						clearline
					</xsl:if>
					<xsl:if test="(position() mod 2)=0">
						darkline
					</xsl:if>
				</xsl:variable>
				<tr class="{$classtype}">
					<td width="20%" align="left" class="mbean_row">
						<xsl:value-of select="key" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="value" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="type" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="sequenceNumber" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	
	<xsl:template name="level0FileMeta">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="7" width="100%" class="mbeans">
					level0FileMeta
				</td>
			</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr class="darkline">
				<td width="20%">
					<div class="tableheader">
						FileNumber
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						FileSize
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						startKey
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						endKey
					</div>
				</td>
			</tr>
			<xsl:for-each select="dbInfo/level0FileMeta">
				<xsl:variable name="classtype">
					<xsl:if test="(position() mod 2)=1">
						clearline
					</xsl:if>
					<xsl:if test="(position() mod 2)=0">
						darkline
					</xsl:if>
				</xsl:variable>
				<tr class="{$classtype}">
					<td width="20%" align="left" class="mbean_row">
						<xsl:value-of select="number" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="fileSize" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="smallest" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="largest" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<xsl:template name="levelsFileMeta">
	<xsl:for-each select="dbInfo/levelsFileMeta/item">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="7" width="100%" class="mbeans">
					level-<xsl:value-of select="@key"/>-FileMeta
				</td>
			</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr class="darkline">
				<td width="20%">
					<div class="tableheader">
						FileNumber
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						FileSize
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						startKey
					</div>
				</td>
				<td width="20%">
					<div class="tableheader">
						endKey
					</div>
				</td>
			</tr>
			<xsl:for-each select="value">
				<xsl:variable name="classtype">
					<xsl:if test="(position() mod 2)=1">
						clearline
					</xsl:if>
					<xsl:if test="(position() mod 2)=0">
						darkline
					</xsl:if>
				</xsl:variable>
				<tr class="{$classtype}">
					<td width="20%" align="left" class="mbean_row">
						<xsl:value-of select="number" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="fileSize" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="smallest" />
					</td>
					<td align="left" class="mbean_row">
						<xsl:value-of select="largest" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:for-each>
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
								<xsl:with-param name="selection">index</xsl:with-param>
							</xsl:call-template>
							<xsl:variable name="query">
								<xsl:call-template name="str">
									<xsl:with-param name="id">index.query</xsl:with-param>
								</xsl:call-template>
							</xsl:variable>
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td class="page_title">
										<xsl:call-template name="str">
											<xsl:with-param name="id">index.title</xsl:with-param>
										</xsl:call-template>
									</td>
									<form action="dbview">
										<td align="right" class="page_title">
											<xsl:call-template name="str">
												<xsl:with-param name="id">index.query.name</xsl:with-param>
											</xsl:call-template>
											<input type="text" name="querynames" />
											<input type="submit" value="{$query}" />
										</td>
									</form>
								</tr>
							</table>
							<xsl:call-template name="memTable" />
							<xsl:call-template name="immutableMemTable" />
							<xsl:call-template name="level0FileMeta" />
							<xsl:call-template name="levelsFileMeta" />
							
							<xsl:call-template name="bottom" />
						</td>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>

