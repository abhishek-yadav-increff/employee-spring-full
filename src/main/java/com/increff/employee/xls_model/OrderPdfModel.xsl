<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- Attribute used for table border -->
    <xsl:attribute-set name="tableBorder">
        <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="orderXmlForm">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21.0cm" margin-top="0.8in" margin-bottom="0.8in" margin-left="0.8in" margin-right="0.8in">
                    <fo:region-body margin-bottom="21.25pt" />
                    <fo:region-after extent="2in" display-align="after"></fo:region-after>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" font-size="8pt" font-family="Helvetica">
                        For questions concerning this invoice please contact
                    </fo:block>
                    <fo:block text-align="center" font-size="9pt" font-family="Helvetica" font-weight="bold">
                        info@increff.com
                    </fo:block>
                    <fo:block text-align="center" font-size="8pt" font-family="Helvetica">
                        <fo:basic-link external-destination="https://www.increff.com">
                            www.increff.com
                        </fo:basic-link>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <fo:inline-container vertical-align="top" inline-progression-dimension="74%">
                            <fo:block>
                                <fo:external-graphic src="url(file:/home/abhk943/Documents/increff/employee-spring-full2/src/main/resources/com/increff/employee/increff.jpg)" content-width="2.00in" />
                            </fo:block>
                        </fo:inline-container>
                        <fo:inline-container vertical-align="top" inline-progression-dimension="24%">
                            <fo:block font-family="arial" font-size="28px">INVOICE
                            </fo:block>
                        </fo:inline-container>
                    </fo:block>
                    <fo:block>
                        <fo:inline-container inline-progression-dimension="60%">
                            <fo:block linefeed-treatment="preserve">
                            INCREFF
                            2nd floor, Enzyme Tech Park,
                            behind Star Bazaar No-1113,
                            Bengaluru, Karnataka 560102
                            info@increff.com
                            </fo:block>
                        </fo:inline-container>
                        <fo:inline-container vertical-align="bottom" inline-progression-dimension="39%">
                            <fo:block font-size="16pt" font-family="Helvetica" font-weight="bold">
                                Order ID:
                                <xsl:value-of select="id" />
                            </fo:block>
                            <fo:block font-size="16pt" font-family="Helvetica" font-weight="bold">
                                Date:
                                <xsl:value-of select="date" />
                            </fo:block>
                        </fo:inline-container>
                    </fo:block>
                    <fo:block>&#160;</fo:block>
                    <fo:block>&#160;</fo:block>
                    <fo:block font-size="10pt">
                        <fo:table table-layout="fixed" width="100%" border-collapse="separate" xsl:use-attribute-sets="myBorder">
                            <fo:table-column column-width="2.5cm" />
                            <fo:table-column column-width="8cm" />
                            <fo:table-column column-width="2cm" />
                            <fo:table-column column-width="2cm" />
                            <fo:table-column column-width="2.5cm" />
                            <fo:table-header>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                    <fo:block margin-left="1mm" font-weight="bold">Name</fo:block>
                                </fo:table-cell>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                    <fo:block margin-left="1mm" font-weight="bold">Barcode</fo:block>
                                </fo:table-cell>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                    <fo:block margin-left="1mm" font-weight="bold">MRP</fo:block>
                                </fo:table-cell>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                    <fo:block margin-left="1mm" font-weight="bold">Quantity</fo:block>
                                </fo:table-cell>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                    <fo:block margin-left="1mm" font-weight="bold">Selling Price</fo:block>
                                </fo:table-cell>
                            </fo:table-header>
                            <fo:table-body>
                                <xsl:apply-templates select="items" />
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    <!-- <fo:block>&#160;</fo:block><fo:block>&#160;</fo:block> -->
                    <fo:block text-align="center">
                        <fo:inline-container inline-progression-dimension="83%">
                            <fo:block font-size="16pt" font-family="Helvetica" font-weight="bold">
                                <!-- <fo:block>&#160;</fo:block><fo:block>&#160;</fo:block> -->
                                <!-- THANK YOU -->
                            </fo:block>
                        </fo:inline-container>
                        <fo:inline-container vertical-align="top" inline-progression-dimension="30%">
                            <fo:block font-size="12pt" font-weight="bold" text-align="left">
                                TOTAL:
                                <xsl:value-of select="total" />
                            </fo:block>
                        </fo:inline-container>
                    </fo:block>
                    <fo:block text-align="center" font-size="16pt" font-family="Helvetica" font-weight="bold">
                        <fo:block>&#160;</fo:block>
                        <fo:block>&#160;</fo:block>
                        THANK YOU
                    </fo:block>
                </fo:flow>

            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="items">
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block margin-left="1mm">
                    <xsl:value-of select="name" />
                </fo:block>
            </fo:table-cell>

            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block margin-left="1mm">
                    <xsl:value-of select="productBarcode" />
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block margin-left="1mm">
                    <xsl:value-of select="mrp" />
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block margin-left="1mm">
                    <xsl:value-of select="quantity" />
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block margin-left="1mm">
                    <xsl:value-of select="sellingPrice" />
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    <xsl:attribute-set name="myBorder">
        <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
</xsl:stylesheet>