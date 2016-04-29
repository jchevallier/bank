package com.sfeir.bank.services;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeService {

    public Date now() {
        return new Date();
    }

}
