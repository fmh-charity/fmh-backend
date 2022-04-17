package ru.iteco.cucumber.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserShortInfoDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private boolean isAdmin;

}
