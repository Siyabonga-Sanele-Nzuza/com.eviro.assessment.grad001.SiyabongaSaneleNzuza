package com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Controller;

import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Dao.AccountProfileRepository;
import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Persistence.ImageDB;
import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Model.AccountProfile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;


@RestController
@RequestMapping("/v1/api/image")
public class ImageController {

    private final AccountProfileRepository accountProfileRepository;

    static ImageDB imageDB = new ImageDB();
    public ImageController(AccountProfileRepository accountProfileRepository) {
        this.accountProfileRepository = accountProfileRepository;
    }

    @GetMapping(value = "/{name}/{surname}")
    public ResponseEntity<FileSystemResource> getHttpImgLink(@PathVariable String name,
                                                             @PathVariable String surname)
            throws SQLException, IOException {


        Iterable<AccountProfile> profiles = accountProfileRepository.findAll();

        URL url1 = imageDB.getImageLinkFromDataBase( profiles, name, surname );

        FileSystemResource resource =
                new FileSystemResource(url1.getPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(resource.contentLength());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }


}
