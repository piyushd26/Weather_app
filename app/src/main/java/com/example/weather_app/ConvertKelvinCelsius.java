package com.example.weather_app;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/*
 * This basic java example source code
 * reads temperature in Kelvin from console input
 * Java Program to convert Kelvin to Degree Celsius
 */

public class ConvertKelvinCelsius {



    public Float KtoC(Float kelvin)
    {
        Float celsius = (kelvin - 273.15F);

        return celsius;
    }

}