package com.mae.ai;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private GamePanel gp;
    private Node[][] nodes;
    private List<Node> openList = new ArrayList<>();
    private List<Node> pathList = new ArrayList<>();
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }

    private void instantiateNodes() {
        nodes = new Node[Settings.MAX_WORLD_COL][Settings.MAX_WORLD_ROW];
        int col = 0;
        int row = 0;
        while (col < Settings.MAX_WORLD_COL && row < Settings.MAX_WORLD_ROW) {
            nodes[col][row] = new Node(col, row);
            col++;
            if (col == Settings.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * for resetting previous result
     */
    private void resetNodes() {
        int col = 0;
        int row = 0;
        while (col < Settings.MAX_WORLD_COL && row < Settings.MAX_WORLD_ROW) {
            nodes[col][row].setOpen(false);
            nodes[col][row].setChecked(false);
            nodes[col][row].setSolid(false);
            col++;
            if (col == Settings.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }


    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);
        // solid nodes interactive tiles
        for (int i = 0; i < gp.getITiles()[GamePanel.currentMap].length; i++) {
            if (gp.getITiles()[GamePanel.currentMap][i] != null && gp.getITiles()[GamePanel.currentMap][i].isDestructible()) {
                int iTCol = gp.getITiles()[GamePanel.currentMap][i].getWorldX() / Settings.TILE_SIZE;
                int iTRow = gp.getITiles()[GamePanel.currentMap][i].getWorldY() / Settings.TILE_SIZE;
                nodes[iTCol][iTRow].setSolid(true);
            }
        }
        int col = 0;
        int row = 0;
        while (col < Settings.MAX_WORLD_COL && row < Settings.MAX_WORLD_ROW) {
            // solid nodes tiles
            int tileNum = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][col][row];
            if (gp.getTileManager().getTileTypes()[tileNum].isCollision())
                nodes[col][row].setSolid(true);
            calculateCosts(nodes[col][row]);
            col++;
            if (col == Settings.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    private void calculateCosts(Node node) {
        // G cost
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setGCost(xDistance + yDistance);
        // H cost
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.setHCost(xDistance + yDistance);
        // F cost
        node.setFCost(node.getGCost() + node.getHCost());
    }

    /**
     *  finds optimal path
     * @return path found or not
     */
    public boolean search(){
        int col;
        int row;

        while ((! goalReached) && step < 500){
            col = currentNode.getCol();
            row = currentNode.getRow();

            currentNode.setChecked(true);
            openList.remove(currentNode);

            // open the up node
            if (row - 1  >= 0)
                openNode(nodes[col][row-1]);
            // open the left node
            if (col - 1 >= 0)
                openNode(nodes[col -1][row]);
            // open the down node
            if(row + 1 < Settings.MAX_WORLD_ROW)
                openNode(nodes[col][row + 1]);
            //open the right node
            if (col + 1 < Settings.MAX_WORLD_COL)
                openNode(nodes[col+1][row]);

            // find the best decision
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;
            for (int i = 0; i < openList.size(); i++){
                if (openList.get(i).getFCost() < bestNodeFCost){ // better F cost
                    bestNodeFCost = openList.get(i).getFCost();
                    bestNodeIndex = i;
                } else if (openList.get(i).getFCost() < bestNodeFCost) { // same F better G
                    if (openList.get(i).getGCost() < openList.get(bestNodeIndex).getGCost())
                        bestNodeIndex = i;
                }
            }
            if (openList.isEmpty())
                break;

            currentNode = openList.get(bestNodeIndex);
            if (currentNode.equals(goalNode)){
                goalReached = true;
                trackTheFinalPath();
            }
            step++;
        }
        return goalReached;
    }

    private void openNode(Node node) {
        if (! node.isOpen() && ! node.isChecked() && ! node.isSolid()){
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    private void trackTheFinalPath() {
        Node current = goalNode;
        while (! current.equals(startNode)){
            pathList.add(0,current); //ordered
            current=current.getParent();
        }
    }

    public List<Node> getPathList() {
        return pathList;
    }
}
