package com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Model;

import org.springframework.data.annotation.Id;

public record AccountProfile(@Id Integer id, String name, String surname, String Link) {

}
