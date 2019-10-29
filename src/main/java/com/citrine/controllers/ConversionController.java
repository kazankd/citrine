package com.citrine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citrine.services.SiConversionService;
import com.citrine.viewmodels.Unit;

@Controller
public class ConversionController {

    @Autowired
    private SiConversionService siConversionService;


    @GetMapping("/units/si")
    @ResponseBody
    public Unit convertToSi(@RequestParam(name = "units") String expression) {
        return siConversionService.convertToSi(expression);
    }
}