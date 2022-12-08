package com.mae.ai;

import lombok.Data;

@Data
public class Node {

    private Node parent;
    private int col;
    private int row;
    private int gCost;  // Distance between the current node and start node
    private int hCost;  // Distance between current node and goal node
    private int fCost;  // Total cost(g + h) of the node
    private boolean solid;
    private boolean open;
    private boolean checked;


    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }

}
