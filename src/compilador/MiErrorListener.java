package compilador;

import org.antlr.v4.runtime.*;
import java.util.List;

public class MiErrorListener extends BaseErrorListener {

    private String tipoAnalisis;
    private List<String[]> tablaErrores;

    public MiErrorListener(String tipo, List<String[]> tabla) {
        this.tipoAnalisis = tipo;
        this.tablaErrores = tabla;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        tablaErrores.add(new String[]{
                tipoAnalisis,
                String.valueOf(line),
                String.valueOf(charPositionInLine),
                msg
        });
    }
}