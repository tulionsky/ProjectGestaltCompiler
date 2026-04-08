grammar ProjectGestalt;

// ════════════════════════════════════════
//   REGLAS DEL PARSER (Sintáctico)
// ════════════════════════════════════════

// Programa principal
programa
    : 'gestalt' IDENTIFICADOR 'engage' sentencia* 'disengage' 'replicant'
    ;

// Tipos de sentencias válidas
sentencia
    : declaracionVariable
    | declaracionArreglo
    | asignacion
    | impresion
    | lectura
    | condicional
    | cicloMission
    | cicloSortie
    | llamadaFuncion
    | retorno
    | halt
    | declaracionFuncion
    ;

// unit pod x := 5;
declaracionVariable
    : tipo IDENTIFICADOR ':=' expresion ';'
    ;

// yorha pascal nombres[5];
declaracionArreglo
    : 'yorha' tipo IDENTIFICADOR '[' ENTERO ']' ';'
    ;

// x := 10;
asignacion
    : IDENTIFICADOR ':=' expresion ';'
    | IDENTIFICADOR '[' expresion ']' ':=' expresion ';'
    ;

// report(x);
impresion
    : 'report' '(' expresion ')' ';'
    ;

// scan(x);
lectura
    : 'scan' '(' IDENTIFICADOR ')' ';'
    ;

// directive (x > 5) engage ... disengage
condicional
    : 'directive' '(' expresion ')' 'engage' sentencia* 'disengage'
      ('otherwise' 'engage' sentencia* 'disengage')?
    ;

// mission (x < 10) engage ... disengage
cicloMission
    : 'mission' '(' expresion ')' 'engage' sentencia* 'disengage'
    ;

// sortie (unit pod i := 0; i < 10; i := i + 1) engage ... disengage
cicloSortie
    : 'sortie' '(' declaracionVariable expresion ';' asignacionSortie ')'
      'engage' sentencia* 'disengage'
    ;

asignacionSortie
    : IDENTIFICADOR ':=' expresion
    ;

// model nombreFuncion(pod x, pascal y) engage ... disengage
declaracionFuncion
    : ('model' | 'weiss') IDENTIFICADOR '(' parametros? ')'
      'engage' sentencia* 'disengage'
    ;

parametros
    : tipo IDENTIFICADOR (',' tipo IDENTIFICADOR)*
    ;

// llamada: nombreFuncion(arg1, arg2);
llamadaFuncion
    : IDENTIFICADOR '(' argumentos? ')' ';'
    ;

argumentos
    : expresion (',' expresion)*
    ;

// glory x + 1;
retorno
    : 'glory' expresion ';'
    ;

// halt;
halt
    : 'halt' ';'
    ;

// ── Tipos de datos ──
tipo
    : 'pod'    // entero
    | 'faith'  // decimal
    | 'pascal' // string
    | 'nier'   // booleano
    ;

// ════════════════════════════════════════
//   EXPRESIONES (con precedencia)
// ════════════════════════════════════════

expresion
    : expresion ('*' | '/' | '%') expresion   # exprMulDiv
    | expresion ('+' | '-') expresion          # exprSuma
    | expresion ('>' | '<' | '>=' | '<='
               | '==' | '!=') expresion        # exprComparacion
    | expresion ('&&' | '||') expresion        # exprLogica
    | '!' expresion                            # exprNegacion
    | '(' expresion ')'                        # exprParentesis
    | IDENTIFICADOR '[' expresion ']'          # exprArreglo
    | IDENTIFICADOR '(' argumentos? ')'        # exprLlamada
    | IDENTIFICADOR                            # exprId
    | ENTERO                                   # exprEntero
    | DECIMAL                                  # exprDecimal
    | CADENA                                   # exprCadena
    | 'true'                                   # exprVerdadero
    | 'false'                                  # exprFalso
    ;

// ════════════════════════════════════════
//   REGLAS DEL LEXER (Léxico)
// ════════════════════════════════════════

// Palabras reservadas — Control
GESTALT     : 'gestalt' ;
REPLICANT   : 'replicant' ;
ENGAGE      : 'engage' ;
DISENGAGE   : 'disengage' ;

// Palabras reservadas — Variables y funciones
UNIT        : 'unit' ;
MODEL       : 'model' ;
YORHA       : 'yorha' ;
WEISS       : 'weiss' ;
GLORY       : 'glory' ;

// Palabras reservadas — Tipos
POD         : 'pod' ;
FAITH       : 'faith' ;
PASCAL      : 'pascal' ;
NIER        : 'nier' ;

// Palabras reservadas — Flujo
DIRECTIVE   : 'directive' ;
OTHERWISE   : 'otherwise' ;
MISSION     : 'mission' ;
SORTIE      : 'sortie' ;
HALT        : 'halt' ;

// Palabras reservadas — E/S
REPORT      : 'report' ;
SCAN        : 'scan' ;

// Booleanos
TRUE        : 'true' ;
FALSE       : 'false' ;

// Identificadores
IDENTIFICADOR : [a-zA-Z_][a-zA-Z0-9_]* ;

// Números
ENTERO   : [0-9]+ ;
DECIMAL  : [0-9]+ '.' [0-9]+ ;

// Cadenas
CADENA : '"' (~["\r\n])* '"' ;

// Comentarios
COMENTARIO_LINEA   : '//' ~[\r\n]* -> skip ;
COMENTARIO_BLOQUE  : '/*' .*? '*/' -> skip ;

// Espacios ignorados
WS : [ \t\r\n]+ -> skip ;