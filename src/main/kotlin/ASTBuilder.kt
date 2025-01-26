class ASTBuilder : GiliLangBaseVisitor<ASTNode>() {

    override fun visitProgram(ctx: GiliLangParser.ProgramContext): ASTNode {
        val statements = ctx.statement().map { visit(it) as StatementNode }
        return ProgramNode(statements)
    }

    override fun visitStatement(ctx: GiliLangParser.StatementContext): ASTNode {
        return when {
            // 'print' '(' expression ')' ';'
            ctx.PRINT() != null -> {
                val expr = visit(ctx.expression()) as ExpressionNode
                PrintStatementNode(expr)
            }
            // 'let' IDENTIFIER '=' expression ';'
            ctx.LET() != null -> {
                val ident = ctx.IDENTIFIER().text
                val expr = visit(ctx.expression()) as ExpressionNode
                LetStatementNode(ident, expr)
            }
            // IDENTIFIER '=' expression ';'
            else -> {
                val ident = ctx.IDENTIFIER().text
                val expr = visit(ctx.expression()) as ExpressionNode
                AssignStatementNode(ident, expr)
            }
        }
    }

    override fun visitExpression(ctx: GiliLangParser.ExpressionContext): ASTNode {
        if (ctx.expression().isEmpty)
            return visit(ctx.term())

        val left = visit(ctx.expression()) as ExpressionNode
        val operator = when (val operatorText = ctx.getChild(1).text) {
            "+" -> BinaryOperator.PLUS
            "-" -> BinaryOperator.MINUS
            else -> error("Unexpected operator in expression: $operatorText")
        }
        val right = visit(ctx.term()) as ExpressionNode
        return BinaryExpressionNode(left, operator, right)
    }

    override fun visitTerm(ctx: GiliLangParser.TermContext): ASTNode {
        if (ctx.factor().isEmpty)
            return visit(ctx.factor())

        val left = visit(ctx.term()) as ExpressionNode
        val operator = when (val operatorText = ctx.getChild(1).text) {
            "*" -> BinaryOperator.MULTIPLY
            "/" -> BinaryOperator.DIVIDE
            else -> error("Unexpected operator in expression: $operatorText")
        }
        val right = visit(ctx.factor()) as ExpressionNode
        return BinaryExpressionNode(left, operator, right)
    }
}