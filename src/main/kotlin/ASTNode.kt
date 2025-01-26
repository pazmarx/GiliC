sealed class ASTNode

data class ProgramNode(val statements: List<StatementNode>) : ASTNode()

sealed class StatementNode : ASTNode()

data class PrintStatementNode(
    val expression: ExpressionNode
) : StatementNode()

data class LetStatementNode(
    val identifier: String,
    val expression: ExpressionNode
) : StatementNode()

data class AssignStatementNode(
    val identifier: String,
    val expression: ExpressionNode
) : StatementNode()

sealed class ExpressionNode : ASTNode()

enum class BinaryOperator {
    PLUS, MINUS, MULTIPLY, DIVIDE
}

data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: BinaryOperator,
    val right: ExpressionNode
) : ExpressionNode()

data class NumberLiteralNode(
    val value: Double
) : ExpressionNode()

data class IdentifierNode(
    val name: String
) : ExpressionNode()

data class ParenExpressionNode(
    val inner: ExpressionNode
) : ExpressionNode()

