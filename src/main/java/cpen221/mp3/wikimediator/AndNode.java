package cpen221.mp3.wikimediator;
import java.util.List;
import java.util.stream.Collectors;

public class AndNode extends Node {

    /**
     * Create a new node that represents an AND operation. The two children
     * of this node represent the two operands, although they may be
     * subexpressions that need to be evaluated.
     *
     * @param leftNode one of the expression to evaluate
     * @param rightNode another expression to evaluate
     */
    public AndNode(Node leftNode, Node rightNode){
        super(leftNode,rightNode);
    }

    /**
     * Return a character that represents the operation this node represents.
     *
     * @return 'AND' for anding
     */
    @Override
    public String getOpName() {
        return "AND";
    }

    /**
     * Evaluate the AND operation by evaluating the two child nodes and
     * then anding their results.
     * @return the intersection of the results from left and right subtrees
     * @throws InvalidQueryException with bad input
     */
    @Override
    public List<String> evaluate() throws InvalidQueryException{
        List<String> left = super.getChild(0).evaluate();
        List<String> right = super.getChild(1).evaluate();
        return left.stream().filter(right::contains).collect(Collectors.toList());
    }
}
