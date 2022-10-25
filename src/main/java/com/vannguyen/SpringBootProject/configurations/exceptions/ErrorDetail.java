package com.vannguyen.SpringBootProject.configurations.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class ErrorDetail {
    private Date timestamp;
    private String message;
    private String detail;
}
