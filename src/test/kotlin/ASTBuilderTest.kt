//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.assertThrows
//import org.mockito.kotlin.*
//
//class ASTBuilderTest {
//
//    private val astBuilder = ASTBuilder()
//
//    @Test
//    fun `visitProgram should return ProgramNode with statements`() {
//        // Arrange
//        val programContext = mock<GiliLangParser.ProgramContext> {
//            on { statement() } doReturn listOf(mock(), mock())
//        }
//
//        val statementNode1 = mock<StatementNode>()
//        val statementNode2 = mock<StatementNode>()
//
//        whenever(astBuilder.visit(any())).thenReturn(statementNode1, statementNode2)
//
//        // Act
//        val result = astBuilder.visitProgram(programContext)
//
//        // Assert
//        assertTrue(result is ProgramNode)
//        val programNode = result as ProgramNode
//        assertEquals(2, programNode.statements.size)
//        assertEquals(statementNode1, programNode.statements[0])
//        assertEquals(statementNode2, programNode.statements[1])
//    }
//
//    @Test
//    fun `visitStatement should return PrintStatementNode for print statement`() {
//        // Arrange
//        val statementContext = mock<GiliLangParser.StatementContext> {
//            on { PRINT() } doReturn mock()
//            on { expression() } doReturn mock()
//        }
//        val expressionNode = mock<ExpressionNode>()
//
//        whenever(astBuilder.visit(statementContext.expression())).thenReturn(expressionNode)
//
//        // Act
//        val result = astBuilder.visitStatement(statementContext)
//
//        // Assert
//        assertTrue(result is PrintStatementNode)
//        val printStatementNode = result as PrintStatementNode
//        assertEquals(expressionNode, printStatementNode.expression)
//    }
//
//    @Test
//    fun `visitStatement should return LetStatementNode for let statement`() {
//        // Arrange
//        val statementContext = mock<GiliLangParser.StatementContext> {
//            on { LET() } doReturn mock()
//            on { IDENTIFIER() } doReturn mock { on { text } doReturn "x" }
//            on { expression() } doReturn mock()
//        }
//        val expressionNode = mock<ExpressionNode>()
//
//        whenever(astBuilder.visit(statementContext.expression())).thenReturn(expressionNode)
//
//        // Act
//        val result = astBuilder.visitStatement(statementContext)
//
//        // Assert
//        assertTrue(result is LetStatementNode)
//        val letStatementNode = result as LetStatementNode
//        assertEquals("x", letStatementNode.identifier)
//        assertEquals(expressionNode, letStatementNode.expression)
//    }
//
//    @Test
//    fun `visitStatement should return AssignStatementNode for assignment statement`() {
//        // Arrange
//        val statementContext = mock<GiliLangParser.StatementContext> {
//            on { IDENTIFIER() } doReturn mock { on { text } doReturn "x" }
//            on { expression() } doReturn mock()
//        }
//        val expressionNode = mock<ExpressionNode>()
//
//        whenever(astBuilder.visit(statementContext.expression())).thenReturn(expressionNode)
//
//        // Act
//        val result = astBuilder.visitStatement(statementContext)
//
//        // Assert
//        assertTrue(result is AssignStatementNode)
//        val assignStatementNode = result as AssignStatementNode
//        assertEquals("x", assignStatementNode.identifier)
//        assertEquals(expressionNode, assignStatementNode.expression)
//    }
//
//    @Test
//    fun `visitExpression should return BinaryExpressionNode for valid expression`() {
//        // Arrange
//        val expressionContext = mock<GiliLangParser.ExpressionContext> {
//            on { expression() } doReturn mock<GiliLangParser.ExpressionContext>()
//            on { getChild(1).text } doReturn "+"
//            on { term() } doReturn mock()
//        }
//        val leftExpression = mock<ExpressionNode>()
//        val rightExpression = mock<ExpressionNode>()
//
//        whenever(astBuilder.visit(expressionContext.expression())).thenReturn(leftExpression)
//        whenever(astBuilder.visit(expressionContext.term())).thenReturn(rightExpression)
//
//        // Act
//        val result = astBuilder.visitExpression(expressionContext)
//
//        // Assert
//        assertTrue(result is BinaryExpressionNode)
//        val binaryExpressionNode = result as BinaryExpressionNode
//        assertEquals(leftExpression, binaryExpressionNode.left)
//        assertEquals(BinaryOperator.PLUS, binaryExpressionNode.operator)
//        assertEquals(rightExpression, binaryExpressionNode.right)
//    }
//
//    @Test
//    fun `visitTerm should return BinaryExpressionNode for valid term`() {
//        // Arrange
//        val termContext = mock<GiliLangParser.TermContext> {
//            on { term() } doReturn mock<GiliLangParser.TermContext>()
//            on { getChild(1).text } doReturn "*"
//            on { factor() } doReturn mock()
//        }
//        val leftTerm = mock<ExpressionNode>()
//        val rightTerm = mock<ExpressionNode>()
//
//        whenever(astBuilder.visit(termContext.term())).thenReturn(leftTerm)
//        whenever(astBuilder.visit(termContext.factor())).thenReturn(rightTerm)
//
//        // Act
//        val result = astBuilder.visitTerm(termContext)
//
//        // Assert
//        assertTrue(result is BinaryExpressionNode)
//        val binaryExpressionNode = result as BinaryExpressionNode
//        assertEquals(leftTerm, binaryExpressionNode.left)
//        assertEquals(BinaryOperator.MULTIPLY, binaryExpressionNode.operator)
//        assertEquals(rightTerm, binaryExpressionNode.right)
//    }
//
//    @Test
//    fun `visitExpression should throw error for unexpected operator`() {
//        // Arrange
//        val expressionContext = mock<GiliLangParser.ExpressionContext> {
//            on { expression() } doReturn mock<GiliLangParser.ExpressionContext>()
//            on { getChild(1).text } doReturn "%"
//            on { term() } doReturn mock()
//        }
//
//        // Act & Assert
//        val exception = assertThrows<IllegalStateException> {
//            astBuilder.visitExpression(expressionContext)
//        }
//        assertEquals("Unexpected operator in expression: %", exception.message)
//    }
//
//    @Test
//    fun `visitTerm should throw error for unexpected operator`() {
//        // Arrange
//        val termContext = mock<GiliLangParser.TermContext> {
//            on { term() } doReturn mock<GiliLangParser.TermContext>()
//            on { getChild(1).text } doReturn "^"
//            on { factor() } doReturn mock()
//        }
//
//        // Act & Assert
//        val exception = assertThrows<IllegalStateException> {
//            astBuilder.visitTerm(termContext)
//        }
//        assertEquals("Unexpected operator in expression: ^", exception.message)
//    }
//}
