package compilador;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class AnalizadorService {

    public static ResultadoAnalisis analizar(String codigo) {
        List<String[]> tokens  = new ArrayList<>();
        List<String[]> errores = new ArrayList<>();
        String arbol = "";

        try {
            CharStream input = CharStreams.fromString(codigo);

            // ── Análisis Léxico ──
            ProjectGestaltLexer lexer = new ProjectGestaltLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MiErrorListener("Léxico", errores));

            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            tokenStream.fill();

            int i = 1;
            for (Token t : tokenStream.getTokens()) {
                if (t.getType() != Token.EOF) {
                    String tipo = ProjectGestaltLexer.VOCABULARY
                            .getSymbolicName(t.getType());
                    if (tipo == null) tipo = "DESCONOCIDO";
                    tokens.add(new String[]{
                            String.valueOf(i++),
                            t.getText(),
                            tipo,
                            String.valueOf(t.getLine()),
                            String.valueOf(t.getCharPositionInLine())
                    });
                }
            }

            // ── Análisis Sintáctico ──
            ProjectGestaltParser parser = new ProjectGestaltParser(tokenStream);
            parser.removeErrorListeners();
            parser.addErrorListener(new MiErrorListener("Sintáctico", errores));

            ParseTree tree = parser.programa();

            arbol = "=== ÁRBOL DE DERIVACIÓN ===\n\n"
                    + formatearArbol(tree, parser, 0);

        } catch (Exception ex) {
            errores.add(new String[]{"Fatal", "0", "0", ex.getMessage()});
            arbol = "Error al procesar: " + ex.getMessage();
        }

        return new ResultadoAnalisis(tokens, errores, arbol);
    }

    private static String formatearArbol(ParseTree tree,
                                         ProjectGestaltParser parser,
                                         int nivel) {
        StringBuilder sb = new StringBuilder();
        String indent = "    ".repeat(nivel);

        if (tree instanceof TerminalNode) {
            sb.append(indent).append("● ").append(tree.getText()).append("\n");
        } else {
            String regla = tree.getClass().getSimpleName()
                    .replace("Context", "");
            sb.append(indent).append("▸ ").append(regla).append("\n");
            for (int i = 0; i < tree.getChildCount(); i++) {
                sb.append(formatearArbol(tree.getChild(i), parser, nivel + 1));
            }
        }
        return sb.toString();
    }
}