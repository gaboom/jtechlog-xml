<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Catalog</h2>
                <table>
                    <tr>
                        <th>ISBN</th>
                        <th>Title</th>
                    </tr>
                    <xsl:for-each select="catalog/book">
                        <tr>
                            <td><xsl:value-of select="@isbn10"/></td>
                            <td><xsl:value-of select="title"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>