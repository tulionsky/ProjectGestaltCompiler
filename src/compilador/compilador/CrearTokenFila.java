package compilador;

import javafx.beans.property.SimpleStringProperty;

public class TokenFila {
    private SimpleStringProperty numero, token, tipo, linea, columna;

    public TokenFila(String numero, String token, String tipo,
                     String linea, String columna) {
        this.numero  = new SimpleStringProperty(numero);
        this.token   = new SimpleStringProperty(token);
        this.tipo    = new SimpleStringProperty(tipo);
        this.linea   = new SimpleStringProperty(linea);
        this.columna = new SimpleStringProperty(columna);
    }

    public String getNumero()  { return numero.get(); }
    public String getToken()   { return token.get(); }
    public String getTipo()    { return tipo.get(); }
    public String getLinea()   { return linea.get(); }
    public String getColumna() { return columna.get(); }
}