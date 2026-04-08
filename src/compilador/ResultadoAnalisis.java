package compilador;

import java.util.List;

public class ResultadoAnalisis {
    private List<String[]> tokens;
    private List<String[]> errores;
    private String arbol;

    public ResultadoAnalisis(List<String[]> tokens,
                             List<String[]> errores,
                             String arbol) {
        this.tokens  = tokens;
        this.errores = errores;
        this.arbol   = arbol;
    }

    public List<String[]> getTokens()  { return tokens; }
    public List<String[]> getErrores() { return errores; }
    public String getArbol()           { return arbol; }
}