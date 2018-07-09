package application.io.spring.core.base.api.model;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeModel extends Identifiable {

	private static final long serialVersionUID = 1L;

	// node name
    private String name;
    
	// parent node ID
    private Long parentId;

    // left value: larger than parent node's left value
    private Long leftValue;

    // right value: smaller than parent node's right value
    private Long rightValue;
    
    // the sequence (or weight) of a node
    private Long seq;

    /**
     * 	For output display
     */
	@Override
	public String toString() {
		return "TreeModel [parentId=" + parentId + ", leftValue=" + leftValue + ", rightValue=" + rightValue + ", name="
				+ name + ", seq=" + seq + "]";
	}
}
