package com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Persistence;

import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Dao.FileParser;
import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Model.AccountProfile;
import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Model.Image;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;


public class ImageDB implements FileParser {

    private static String dataDir = "src/main/resources/1672815113084-GraduateDev_AssessmentCsv_Ref003.csv";
    static final int NAME_COLUMN = 0;
    static final int SURNAME_COLUMN = 1;
    static final int IMAGE_FORMAT_COLUMN = 2;
    static final int IMAGE_ENCODE_COLUMN = 3;
    public Set<AccountProfile> listOfImages;

    public ImageDB() {}

    @Override
    public void parseCSV(File csvFile) {
        try (final LineNumberReader in = new LineNumberReader(new FileReader(csvFile))) {
            in.readLine();
            listOfImages = in.lines()
                    .map(this::splitString)
                    .map(this::asAccountProfile)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public File convertCSVDataToImage(String base64ImageData) {

        String[] information = base64ImageData.split(",");

        byte[] data = Base64.getDecoder().decode(information[IMAGE_ENCODE_COLUMN - 1]);

        String outPutFile = "imagesDB/" +
                information[NAME_COLUMN] + information[IMAGE_FORMAT_COLUMN - 1];

        try (OutputStream stream = new FileOutputStream(outPutFile)) {
            stream.write(data);
            return new File(outPutFile);
        } catch (Exception e) {
            System.err.println("Couldn't write to file...");
        }
        return null;
    }

    @Override
    public URI createImageLink(File fileImage) throws URISyntaxException, MalformedURLException {
        return fileImage.toURI();
    }

    String[] splitString(String aCsvLine) {
        return aCsvLine.trim().split(",");
    }

    AccountProfile asAccountProfile(String[] values) {

        Image image = new Image(values[NAME_COLUMN], values[SURNAME_COLUMN],
                values[IMAGE_FORMAT_COLUMN], values[IMAGE_ENCODE_COLUMN]);

        File file = convertCSVDataToImage(base64StringCreater(image.getName(),
                image.getImgFormat(), image.getImgEncode()));


        String imageLink = null;
        try {
            imageLink = createImageLink(file).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        AccountProfile profile =  new AccountProfile(null ,image.getName(),image.getSurname().split(" ")[0],imageLink);

        return profile;
    }

    private String base64StringCreater(String name, String format, String encode) {
        return name + "," + format + "," + encode;
    }

    public void initDB() throws URISyntaxException {
        File dataFile = new File(dataDir);
        parseCSV(dataFile);
    }
    public URL getImageLinkFromDataBase(Iterable<AccountProfile> profiles, String name, String surname) throws MalformedURLException {

        for (AccountProfile profile: profiles) {

            if(profile.name().toLowerCase().equals(name) && profile.surname().toLowerCase().equals(surname)){
                return new URL(profile.Link());
            }
        }
        return null;
    }
}
