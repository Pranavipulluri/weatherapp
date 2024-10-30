package org.meicode.weatherapp;

public class Forecast {
    private long dt; // Unix timestamp
    private Main main;

    public long getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }
}
