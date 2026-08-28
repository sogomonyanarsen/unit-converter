package com.sogomonyanarsen.unitconverter.controller;

import com.sogomonyanarsen.unitconverter.model.ConversionRequest;
import com.sogomonyanarsen.unitconverter.model.ConversionResult;
import com.sogomonyanarsen.unitconverter.service.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    private final ConversionService conversionService;

    public WebController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/length")
    public String length() {
        return "length";
    }

    @PostMapping("/length")
    public String processLengthForm(@ModelAttribute ConversionRequest conversionRequest, Model model) {
        conversionRequest.setMode("length");
        ConversionResult result = conversionService.convert(conversionRequest);

        model.addAttribute("msg", result.getResult());
        return "length";
    }

}
