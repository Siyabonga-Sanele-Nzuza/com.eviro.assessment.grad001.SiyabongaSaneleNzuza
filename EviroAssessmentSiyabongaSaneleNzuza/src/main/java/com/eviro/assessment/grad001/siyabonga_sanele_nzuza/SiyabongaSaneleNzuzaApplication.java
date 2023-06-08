package com.eviro.assessment.grad001.siyabonga_sanele_nzuza;

import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Dao.AccountProfileRepository;
import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Model.AccountProfile;
import com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Persistence.ImageDB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;
import java.sql.SQLException;

@SpringBootApplication
public class SiyabongaSaneleNzuzaApplication {

    static ImageDB imageDB = new ImageDB();

    public static void main(String[] args) throws URISyntaxException, SQLException {
        imageDB.initDB();
        SpringApplication.run(SiyabongaSaneleNzuzaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountProfileRepository repository){
        return args -> {

            for (AccountProfile accountProfile: imageDB.listOfImages) {
                repository.save( accountProfile );
            }

        };
    }
    
}
