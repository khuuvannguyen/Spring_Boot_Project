package com.vannguyen.SpringBootProject.application.requests;

import com.vannguyen.SpringBootProject.domain.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class AccountRequest {
    private String username = "";
    private String password = "";
    private String roles = "";
}
