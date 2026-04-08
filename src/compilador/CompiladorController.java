package compilador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class CompiladorController implements Initializable {

    @FXML private TextArea editorCodigo;
    @FXML private TextArea areaResultado;
    @FXML private Label    labelEstado;

    @FXML private TableView<TokenFila>           tablaTokens;
    @FXML private TableColumn<TokenFila, String> colNo;
    @FXML private TableColumn<TokenFila, String> colToken;
    @FXML private TableColumn<TokenFila, String> colTipo;
    @FXML private TableColumn<TokenFila, String> colLinea;
    @FXML private TableColumn<TokenFila, String> colColumna;

    @FXML private TableView<ErrorFila>           tablaErrores;
    @FXML private TableColumn<ErrorFila, String> colETipo;
    @FXML private TableColumn<ErrorFila, String> colELinea;
    @FXML private TableColumn<ErrorFila, String> colECol;
    @FXML private TableColumn<ErrorFila, String> colEDesc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNo.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colToken.setCellValueFactory(new PropertyValueFactory<>("token"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colLinea.setCellValueFactory(new PropertyValueFactory<>("linea"));
        colColumna.setCellValueFactory(new PropertyValueFactory<>("columna"));

        colETipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colELinea.setCellValueFactory(new PropertyValueFactory<>("linea"));
        colECol.setCellValueFactory(new PropertyValueFactory<>("columna"));
        colEDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    @FXML
    private void ejecutarAnalisis() {
        String codigo = editorCodigo.getText().trim();
        if (codigo.isEmpty()) {
            setEstado("⚠  El editor está vacío", "#fab387");
            return;
        }
        setEstado("Analizando...", "#f9e2af");

        ResultadoAnalisis resultado = AnalizadorService.analizar(codigo);

        areaResultado.setText(resultado.getArbol());

        ObservableList<TokenFila> tokens = FXCollections.observableArrayList();
        for (String[] t : resultado.getTokens())
            tokens.add(new TokenFila(t[0], t[1], t[2], t[3], t[4]));
        tablaTokens.setItems(tokens);

        ObservableList<ErrorFila> errores = FXCollections.observableArrayList();
        for (String[] e : resultado.getErrores())
            errores.add(new ErrorFila(e[0], e[1], e[2], e[3]));
        tablaErrores.setItems(errores);

        if (resultado.getErrores().isEmpty())
            setEstado("Análisis exitoso — Sin errores", "#a6e3a1");
        else
            setEstado(resultado.getErrores().size()
                    + " error(es) encontrado(s)", "#f38ba8");
    }

    @FXML
    private void limpiar() {
        editorCodigo.clear();
        areaResultado.clear();
        tablaTokens.getItems().clear();
        tablaErrores.getItems().clear();
        setEstado("Listo", "#a6e3a1");
    }

    @FXML
    private void cargarEjemplo() {
        editorCodigo.setText("""
                gestalt Programa engage
                    unit pod vida := 100;
                    unit pascal nombre := "2B";
                    report(nombre);
                    directive (vida > 50) engage
                        report("Unidad operativa");
                    disengage otherwise engage
                        report("Unidad comprometida");
                    disengage
                    mission (vida > 0) engage
                        vida := vida - 10;
                    disengage
                disengage replicant
                """);
    }

    private void setEstado(String msg, String color) {
        labelEstado.setText(msg);
        labelEstado.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 13px;");
    }
}