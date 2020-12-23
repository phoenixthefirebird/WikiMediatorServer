package cpen221.mp3.wikimediator;
import java.util.List;

public class OrNode extends Node {

    /**
     * Create a new node that represents an OR operation. The two children
     * of this node represent the two operands, although they may be
     * subexpressions that need to be evaluated.
     *
     * @param leftNode
     * @param rightNode
     */
    public OrNode(Node leftNode, Node rightNode){
        super(leftNode,rightNode);
    }

    /**
     * Return a character that represents the operation this node represents.
     *
     * @return 'OR' for orring
     */
    @Override
    public String getOpName() {
        return "OR";
    }

    /**
     * Evaluate the OR operation by evaluating the two child nodes and
     * then combining their results.
     * @return the union of the results from left and right subtrees
     * @throws InvalidQueryException with bad input
     */
    @Override
    public List<String> evaluate() throws InvalidQueryException{
        List<String> left = super.getChild(0).evaluate();
        left.addAll(super.getChild(1).evaluate());
        return left;
    }
}