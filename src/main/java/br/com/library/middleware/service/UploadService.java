package br.com.library.middleware.service;

import br.com.library.middleware.domain.Author;
import br.com.library.middleware.domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UploadService {

    private final String url = "https://bibliapp.herokuapp.com/api";

    private final HttpService httpService;

    private ObjectMapper mapper = new ObjectMapper();

    public UploadService(HttpService httpService) {
        this.httpService = httpService;
    }

    public void sendAuthorsJson(String json) {
        try {
            List<Author> authors = mapper.readValue(json, new TypeReference<List<Author>>() {
            });
            authors.forEach((author) -> {
                try {
                    this.httpService.doPostRequest(this.url + "/authors", mapper.writeValueAsString(author));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAuthorsTxt(String txt) {
        String[] lines = txt.split(";");
        Author author;
        for(int i=0; i<lines.length; i++) {
            if (lines[i].trim().length() > 0) {
                String[] atributes = lines[i].split(",");
                if (atributes.length == 2
                        && atributes[0].trim().substring(0, 10).equals("firstName:")
                        && atributes[1].trim().substring(0, 9).equals("lastName:") ) {
                    author = new Author(
                            atributes[0].trim().substring(10, atributes[0].trim().length()),
                            atributes[1].trim().substring(9, atributes[1].trim().length())
                    );
                    try {
                        this.httpService.doPostRequest(this.url + "/authors", mapper.writeValueAsString(author));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void sendBooksJson(String json) {
        try {
            List<Book> books = mapper.readValue(json, new TypeReference<List<Book>>() {
            });
            books.forEach((book) -> {
                try {
                    this.httpService.doPostRequest(this.url + "/books", mapper.writeValueAsString(book));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBooksTxt(String txt) {
        String[] lines = txt.split(";");
        Book book;
        for(int i=0; i<lines.length; i++) {
            if (lines[i].trim().length() > 0) {
                String[] atributes = lines[i].split(",");
                if (atributes.length == 2
                        && atributes[0].trim().substring(0, 6).equals("title:")
                        && atributes[1].trim().substring(0, 9).equals("authorId:") ) {
                    book = new Book(
                            atributes[0].trim().substring(6, atributes[0].trim().length()),
                            new Long(atributes[1].trim().substring(9, atributes[1].trim().length()))
                    );
                    try {
                        this.httpService.doPostRequest(this.url + "/books", mapper.writeValueAsString(book));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
