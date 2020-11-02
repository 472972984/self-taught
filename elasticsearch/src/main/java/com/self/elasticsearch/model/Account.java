/**
 * Copyright 2020 bejson.com
 */
package com.self.elasticsearch.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Account {

    private int account_number;
    private int balance;
    private String firstname;
    private String lastname;
    private int age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;


}