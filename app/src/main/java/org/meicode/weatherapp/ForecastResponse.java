package org.meicode.weatherapp;

import java.util.List;

public class ForecastResponse {
    private List<Forecast> list;

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }
}

