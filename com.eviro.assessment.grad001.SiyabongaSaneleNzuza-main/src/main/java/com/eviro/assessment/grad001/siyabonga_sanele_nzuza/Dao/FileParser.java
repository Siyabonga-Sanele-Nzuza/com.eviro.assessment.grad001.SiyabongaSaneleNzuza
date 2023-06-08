package com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Dao;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public interface FileParser {
    void parseCSV(File csvFile);

    File convertCSVDataToImage(String base64ImageData);

    URI createImageLink(File fileImage) throws URISyntaxException, MalformedURLException;
}
