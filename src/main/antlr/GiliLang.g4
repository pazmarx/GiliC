grammar GiliLang;

// Parser Rules
program
    : statement+ EOF
    ;

statement
    : PRINT '(' expression ')' ';'
    | LET IDENTIFIER '=' expression ';'
    | IDENTIFIER '=' expression ';'
    ;

expression
    : expression ('+' | '-') term
    | term
    ;

term
    : term ('*' | '/') factor
    | factor
    ;

factor
    : NUM
    | IDENTIFIER
    | '(' expression ')'
    ;

// Lexer Rules
PRINT
    : 'print'
    ;

LET
    : 'let'
    ;

IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

NUM
    : DIG+ ('.' DIG+)?
    ;

DIG
    : [0-9]
    ;

WS
    : [ \t\r\n]+ -> skip
    ;