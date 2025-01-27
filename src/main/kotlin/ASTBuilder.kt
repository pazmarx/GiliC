class ASTBuilder : GiliLangBaseVisitor<ASTNode>() {

    override fun visitProgram(ctx: GiliLangParser.ProgramContext): ASTNode {
        val statements = ctx.statement().map { visit(it) as StatementNode }
        return ProgramNode(statements, ctx.start.line, ctx.start.charPositionInLine)
    }

    override fun visitStatement(ctx: GiliLangParser.StatementContext): ASTNode {
        return when {
            ctx.PRINT() != null -> {
                val expr = visit(ctx.expression()) as ExpressionNode
                PrintStatementNode(expr, ctx.start.line, ctx.start.charPositionInLine)
            }
            ctx.LET() != null -> {
                val ident = ctx.IDENTIFIER().text
                val expr = visit(ctx.expression()) as ExpressionNode
                LetStatementNode(ident, expr, ctx.start.line, ctx.start.charPositionInLine)
            }
            else -> {
                val ident = ctx.IDENTIFIER().text
                val expr = visit(ctx.expression()) as ExpressionNode
                AssignStatementNode(ident, expr, ctx.start.line, ctx.start.charPositionInLine)
            }
        }
    }

    override fun visitExpression(ctx: GiliLangParser.ExpressionContext): ASTNode {
        if (ctx.expression() == null)
            return visit(ctx.term())

        val left = visit(ctx.expression()) as ExpressionNode
        val operator = when (val operatorText = ctx.getChild(1).text) {
            "+" -> BinaryOperator.PLUS
            "-" -> BinaryOperator.MINUS
            else -> error("Unexpected operator in expression: $operatorText")
        }
        val right = visit(ctx.term()) as ExpressionNode
        return BinaryExpressionNode(left, operator, right, ctx.start.line, ctx.start.charPositionInLine)
    }

    override fun visitTerm(ctx: GiliLangParser.TermContext): ASTNode {
        if (ctx.term() == null){
            return visit(ctx.factor())
        }

        val left = visit(ctx.term()) as ExpressionNode
        val operator = when (val operatorText = ctx.getChild(1).text) {
            "*" -> BinaryOperator.MULTIPLY
            "/" -> BinaryOperator.DIVIDE
            else -> error("Unexpected operator in expression: $operatorText")
        }
        val right = visit(ctx.factor()) as ExpressionNode
        return BinaryExpressionNode(left, operator, right, ctx.start.line, ctx.start.charPositionInLine)
    }

    override fun visitFactor(ctx: GiliLangParser.FactorContext): ASTNode {
        return when {
            ctx.NUM() != null -> {
                val num = ctx.NUM().text.toDouble()
                NumberLiteralNode(num, ctx.start.line, ctx.start.charPositionInLine)
            }
            ctx.IDENTIFIER() != null -> {
                val id = ctx.IDENTIFIER().text
                IdentifierNode(id, ctx.start.line, ctx.start.charPositionInLine)
            }
            ctx.expression() != null -> {
                visit(ctx.expression()) as ExpressionNode
            }
            else -> {
                error("Unexpected factor in expression: ${ctx.text}")
            }
        }
    }
}