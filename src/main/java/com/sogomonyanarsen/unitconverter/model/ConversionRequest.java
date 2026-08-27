package com.sogomonyanarsen.unitconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversionRequest {
    private String mode;
    private double convertable;
    private String startUnit, targetUnit;
}
