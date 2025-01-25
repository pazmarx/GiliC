// Generated from C:/Users/pazma/IdeaProjects/GiliC/src/main/antlr/GiliLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GiliLangParser}.
 */
public interface GiliLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GiliLangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GiliLangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GiliLangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GiliLangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GiliLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GiliLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GiliLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GiliLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GiliLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GiliLangParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GiliLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GiliLangParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GiliLangParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(GiliLangParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GiliLangParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(GiliLangParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GiliLangParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(GiliLangParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GiliLangParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(GiliLangParser.FactorContext ctx);
}