package compilador;

import javafx.beans.property.SimpleStringProperty;

public class ErrorFila {
    private SimpleStringProperty tipo, linea, columna, descripcion;

    public ErrorFila(String tipo, String linea,
                     String columna, String descripcion) {
        this.tipo        = new SimpleStringProperty(tipo);
        this.linea       = new SimpleStringProperty(linea);
        this.columna     = new SimpleStringProperty(columna);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public String getTipo()        { return tipo.get(); }
    public String getLinea()       { return linea.get(); }
    public String getColumna()     { return columna.get(); }
    public String getDescripcion() { return descripcion.get(); }
}