package jtechlog.xml.jaxp;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class StaxCursorApi {

    public List<Book> parse(Source source) {
        List<Book> catalog = new ArrayList<>();
        try {
            XMLInputFactory f = XMLInputFactory.newInstance();
            XMLStreamReader r = f.createXMLStreamReader(source);
            Book book = null;
            while (r.hasNext()) {                
                if (r.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    if ("book".equals(r.getName().getLocalPart())) {
                        book = new Book();
                        catalog.add(book);
                        book.setIsbn10(r.getAttributeValue(null, "isbn10"));
                    }
                    else if ("title".equals(r.getName().getLocalPart())) {
                        book.setTitle(r.getElementText());
                    }
                }
                r.next();
            }

        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error parsing xml", xse);
        }
        return catalog;
    }

    public String write(List<Book> catalog) {
        StringWriter sw = new StringWriter();
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(sw);
            writer.writeStartDocument();
            writer.writeStartElement("catalog");
            for (Book book: catalog) {
                writer.writeStartElement("book");
                writer.writeAttribute("isbn10", book.getIsbn10());
                writer.writeStartElement("title");
                writer.writeCharacters(book.getTitle());
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.flush();
        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error writing xml", xse);
        }
        return sw.toString();
    }

}
