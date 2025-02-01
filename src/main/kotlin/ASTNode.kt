sealed class ASTNode (
    open val line: Int,
    open val column: Int
)

data class ProgramNode(
    val statements: List<StatementNode>,
    override val line: Int,
    override val column: Int
) : ASTNode(line, column)

sealed class StatementNode(
    override val line: Int,
    override val column: Int
) : ASTNode(line, column)

data class PrintStatementNode(
    val expression: ExpressionNode,
    override val line: Int,
    override val column: Int
) : StatementNode(line, column)

data class LetStatementNode(
    val identifier: String,
    val expression: ExpressionNode,
    override val line: Int,
    override val column: Int
) : StatementNode(line, column)

data class AssignStatementNode(
    val identifier: String,
    val expression: ExpressionNode,
    override val line: Int,
    override val column: Int
) : StatementNode(line, column)

sealed class ExpressionNode(
    override val line: Int,
    override val column: Int
) : ASTNode(line, column)

enum class BinaryOperator {
    PLUS, MINUS, MULTIPLY, DIVIDE
}

data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: BinaryOperator,
    val right: ExpressionNode,
    override val line: Int,
    override val column: Int
) : ExpressionNode(line, column)

data class NumberLiteralNode(
    val value: Double,
    override val line: Int,
    override val column: Int
) : ExpressionNode(line, column)

data class IdentifierNode(
    val identifier: String,
    override val line: Int,
    override val column: Int
) : ExpressionNode(line, column)

data class ParenExpressionNode(
    val inner: ExpressionNode,
    override val line: Int,
    override val column: Int
) : ExpressionNode(line, column)

