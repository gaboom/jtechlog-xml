package jtechlog.xml.jaxb;

import org.junit.Test;

import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JaxbTest {

    @Test
    public void testParse() {
        // When
        Catalog catalog = new JaxbApi()
                .parse(new StreamSource(JaxbTest.class.getResourceAsStream("/catalog.xml")));
        // Then
        assertEquals(3, catalog.getBooks().size());
        assertEquals("Java and XML", catalog.getBooks().get(0).getTitle());
        assertEquals("059610149X", catalog.getBooks().get(0).getIsbn10());
    }

    @Test
    public void testWrite() {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new JaxbApi().write(catalog);

        // Then
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<catalog>\n" +
                "    <book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "    </book>\n" +
                "    <book isbn10=\"1590597060\">\n" +
                "        <title>Pro XML Development with Java Technology</title>\n" +
                "    </book>\n" +
                "</catalog>\n", xml);
    }

    private Catalog createCatalog(Book... books) {
        Catalog catalog = new Catalog();
        if (catalog.getBooks() == null) {
            catalog.setBooks(new ArrayList<Book>());
        }
        for (Book book: books) {
            catalog.getBooks().add(book);
        }
        return catalog;
    }

    private Book createBook(String title, String isbn10) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn10(isbn10);
        return book;
    }
}
