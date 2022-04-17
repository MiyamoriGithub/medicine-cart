package com.daniel.cart.controller;

import com.daniel.cart.service.DrugService;
import com.daniel.cart.service.impl.DrugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DrugController {

    private final DrugService service;

    @Autowired
    public DrugController(DrugService service) {
        this.service = service;
    }

//    @RequestMapping("/drug")
//    public ModelAndView findAllDrug() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("drugs", service.findAll());
//        modelAndView.setViewName("drug");
//        return modelAndView;
//    }
}
