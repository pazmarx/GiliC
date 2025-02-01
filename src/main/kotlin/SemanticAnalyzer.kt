class SemanticAnalyzer {

    private val symbols = SymbolTable()

    fun checkProgram(program: ProgramNode): List<String> {
        val errors = mutableListOf<String>()
        for (statement in program.statements) {
            errors.addAll(checkStatement(statement))
        }
        return errors
    }

    private fun checkStatement(statement: StatementNode): List<String> {
        val errors = mutableListOf<String>()
        when (statement) {
            is PrintStatementNode -> {
                errors.addAll(checkExpression(statement.expression))
            }

            is LetStatementNode -> {
                if (symbols.isDeclared(statement.identifier)) {
                    val msg = "line ${statement.line}:${statement.column} Variable '${statement.identifier}' is already declared."
                    System.err.println(msg)
                    errors.add(msg)
                }
                errors.addAll(checkExpression(statement.expression))
                symbols.declareVariable(statement.identifier)
            }

            is AssignStatementNode -> {
                if (!symbols.isDeclared(statement.identifier)) {
                    val msg = "line ${statement.line}:${statement.column} Variable '${statement.identifier}' is not declared."
                    System.err.println(msg)
                    errors.add(msg)
                }
                errors.addAll(checkExpression(statement.expression))
            }
        }
        return errors
    }

    private fun checkExpression(expression: ExpressionNode): List<String> {
        val errors = mutableListOf<String>()
        when (expression) {
            is BinaryExpressionNode -> {
                errors.addAll(checkExpression(expression.left))
                errors.addAll(checkExpression(expression.right))
            }

            is NumberLiteralNode -> {}
            is IdentifierNode -> {
                if (!symbols.isDeclared(expression.identifier)) {
                    val msg = "line ${expression.line}:${expression.column} Variable '${expression.identifier}' is not declared."
                    System.err.println(msg)
                    errors.add(msg)
                }
            }

            is ParenExpressionNode -> {
                errors.addAll(checkExpression(expression.inner))
            }
        }
        return errors
    }
}

private class SymbolTable {
    private val declaredVars = mutableSetOf<String>()

    fun declareVariable(name: String) {
        declaredVars.add(name)
    }

    fun isDeclared(name: String): Boolean {
        return declaredVars.contains(name)
    }
}
