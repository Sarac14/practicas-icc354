package com.example.practica3.Entidades;

public class SensorData {
    private String fechaGeneracion;
    private int idDispositivo;
    private double temperatura;
    private double humedad;

    public SensorData() {
    }

    public SensorData(String fechaGeneracion, int idDispositivo, double temperatura, double humedad) {
        this.fechaGeneracion = fechaGeneracion;
        this.idDispositivo = idDispositivo;
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }
}
