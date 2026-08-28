package com.sogomonyanarsen.unitconverter.service;

import com.sogomonyanarsen.unitconverter.model.ConversionRequest;
import com.sogomonyanarsen.unitconverter.model.ConversionResult;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {
    public ConversionResult convert(ConversionRequest conversionRequest) {
        String mode = conversionRequest.getMode();
        if(mode.equalsIgnoreCase("length")) {
            return convertLength(conversionRequest);
        }
        else if(mode.equalsIgnoreCase("temperature")) {
            return convertTemp(conversionRequest);
        }
        else if(mode.equalsIgnoreCase("weight")) {
            return convertWeight(conversionRequest);
        }

        return new ConversionResult("Invalid conversion mode");
    }

    private ConversionResult convertLength(ConversionRequest conversionRequest) {
        double startFactor = getLengthFactor(conversionRequest.getStartUnit());
        double targetFactor = getLengthFactor(conversionRequest.getTargetUnit());

        double valInMeters = conversionRequest.getConvertable() * startFactor;
        double result = valInMeters / targetFactor;

        String resStr = String.format("%.2f %s = %.2f %s", conversionRequest.getConvertable(), conversionRequest.getStartUnit(), result, conversionRequest.getTargetUnit());

        return new ConversionResult(resStr);
    }

    private ConversionResult convertTemp(ConversionRequest conversionRequest) {
        double startValue = conversionRequest.getConvertable();
        String startUnit = conversionRequest.getStartUnit().toLowerCase();
        String targetUnit = conversionRequest.getTargetUnit().toLowerCase();

        double valInCelsius = switch(startUnit) {
            case "fahrenheit" -> (startValue - 32) * 5 / 9;
            case "kelvin" -> startValue - 273.15;
            default -> startValue;
        };
        double result = switch(targetUnit) {
            case "fahrenheit" -> valInCelsius * 9 / 5 + 32;
            case "kelvin" -> valInCelsius + 273.15;
            default -> valInCelsius;
        };

        String resStr = String.format("%.2f %s = %.2f %s", startValue, startUnit, result, targetUnit);
        return new ConversionResult(resStr);
    }

    private ConversionResult convertWeight(ConversionRequest conversionRequest) {
        double startValue = conversionRequest.getConvertable();
        String startUnit = conversionRequest.getStartUnit().toLowerCase();
        String targetUnit = conversionRequest.getTargetUnit().toLowerCase();

        double valInKilograms = switch(startUnit) {
            case "pound" -> startValue * 0.453592;
            case "ounce" -> startValue * 0.0283495;
            case "gram" -> startValue * 0.001;
            case "milligram" -> startValue * 0.000001;
            default -> startValue;
        };
        double result = switch(targetUnit) {
            case "pound" -> valInKilograms / 0.453592;
            case "ounce" -> valInKilograms / 0.0283495;
            case "gram" -> valInKilograms / 0.001;
            case "milligram" -> valInKilograms / 0.000001;
            default -> valInKilograms;
        };

        String resStr = String.format("%.2f %s = %.2f %s", startValue, startUnit, result, targetUnit);
        return new ConversionResult(resStr);
    }

    private double getLengthFactor(String unit) {
        return switch(unit.toLowerCase()) {
            case "millimeter" -> 0.001;
            case "centimeter" -> 0.01;
            case "kilometer" -> 1000.0;
            case "inch" -> 0.0254;
            case "foot" -> 0.3048;
            case "yard" -> 0.9144;
            case "mile" -> 1609.344;
            default -> 1.0;
        };
    }
}
