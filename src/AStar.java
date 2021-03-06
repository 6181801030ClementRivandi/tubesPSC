
import java.util.ArrayList;
import java.util.List;


/**
 * This is the template for a class that performs A* search on a given rush hour
 * puzzle with a given heuristic. The main search computation is carried out by
 * the constructor for this class, which must be filled in. The solution (a path
 * from the initial state to a goal state) is returned as an array of
 * <tt>State</tt>s called <tt>path</tt> (where the first element
 * <tt>path[0]</tt> is the initial state). If no solution is found, the
 * <tt>path</tt> field should be set to <tt>null</tt>. You may also wish to
 * return other information by adding additional fields to the class.
 */

/**
 * template code diambil dari : https://www.cs.princeton.edu/courses/archive/fall07/cos402/assignments/rushhour/
 * code untuk A* diambil dari github  : https://github.com/saschazar21/rushhour/tree/master/AStar
 */
public class AStar {

    /** The solution path is stored here */
    public State[] path;
    
    private SortableList<HNode> open = new SortableList<HNode>(); //list untuk menampung data f yang dapat diurutkkan 
    private List<HNode> closed = new ArrayList<HNode>(); //

    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {
    	
    	// Initialize root node w/ heuristics and path costs
    	int h = heuristic.getValue(puzzle.getInitNode().getState());
    	HNode root = new HNode(puzzle.getInitNode(), h);
    	
    	open.add(root);	// Add the root node to the open list
    	
    	while(!open.isEmpty()) {
    		
    		// Only performs sort if list was changed
    		open.sort(); 
    		
    		HNode current = open.remove(0); 
    		
    		if (current.getState().isGoal()) {
    			
    			// Set the path array size to depth of goal state;
    			// The +1 should be necessary to also include root node.
    			path = new State[current.getDepth() + 1];
    			
    			// Set the current node to pathNode
    			Node pathNode = current;
    			
    			// Get state for every node and store it in the path array,
    			// then override current path node with its parent node until parent is null.
    			while (pathNode != null) {
    				path[pathNode.getDepth()] = pathNode.getState();
    				pathNode = pathNode.getParent();
    			}
    			
    			// We found a solution, stop.
    			return;
    		}
    		
    		closed.add(current);
    		
    		for (Node successor : current.expand()) {

    			h = heuristic.getValue(successor.getState());
    			HNode hSuccessor = new HNode(successor, h);
    			
    			if (open.contains(hSuccessor)) {
    				keepBetterNodeOnOpenList(hSuccessor);
    			} else if (!closed.contains(hSuccessor)) {
    				open.add(hSuccessor);
    			}
    		}

    	}

    }
    
    // Idea from: http://web.mit.edu/eranki/www/tutorials/search/
    private void keepBetterNodeOnOpenList(HNode successor) { //method untuk remove node yang f nya besar dibandingkan dengan yang lain
    	HNode existing = open.get(successor); //mengambil successor dari open list
    	
    	if (existing != null) { //untuk mengecek apakah successsor dari open list bernilai kosong atau tidak
    		if (existing.compareTo(successor) > 0) { //untuk mengecek apakah successor yang sekarang dibandingkan dengan successor yang dahulu > 0 berdasarkan method compareTo pada HNode
    			open.remove(existing); //jika successor yang sekarang lebih besar dari successor yang dahulu, maka remove successor yang sekarang dan digantikan dengan yang dahulu
    			open.add(successor); 
    		}
    	}
    }

}
