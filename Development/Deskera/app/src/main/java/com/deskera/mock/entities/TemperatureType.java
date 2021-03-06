package com.deskera.mock.entities;

public enum TemperatureType {
    DEFAULT(0),
    Celsius(1),
    Fahrenheit(2);
    final int id;

    TemperatureType(int id) {
        this.id = id;
    }
}
