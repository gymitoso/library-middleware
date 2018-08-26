package br.com.library.middleware.web.rest;

import br.com.library.middleware.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller to upload files.
 */
@RestController
@RequestMapping("/api")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload-authors-file")
    public ResponseEntity<String> submitAuthors(@RequestParam("authorsFile") MultipartFile file) throws IOException {
        if (file.isEmpty() || (!file.getContentType().equals("text/plain") && !file.getContentType().equals("application/json"))) {
            return new ResponseEntity<>("Invalid File", HttpStatus.BAD_REQUEST);
        }

        if (file.getContentType().equals("application/json")) {
            this.uploadService.sendAuthorsJson(new String(file.getBytes(), "UTF-8"));
        } else {
            this.uploadService.sendAuthorsTxt(new String(file.getBytes(), "UTF-8"));
        }

        return new ResponseEntity<>("File send", HttpStatus.OK);
    }

    @PostMapping("/upload-books-file")
    public ResponseEntity<String> submitBooks(@RequestParam("booksFile") MultipartFile file) throws IOException {
        if (file.isEmpty() || (!file.getContentType().equals("text/plain") && !file.getContentType().equals("application/json"))) {
            return new ResponseEntity<>("Invalid File", HttpStatus.BAD_REQUEST);
        }

        if (file.getContentType().equals("application/json")) {
            this.uploadService.sendBooksJson(new String(file.getBytes(), "UTF-8"));
        } else {
            this.uploadService.sendBooksTxt(new String(file.getBytes(), "UTF-8"));
        }

        return new ResponseEntity<>("File send", HttpStatus.OK);
    }

}
