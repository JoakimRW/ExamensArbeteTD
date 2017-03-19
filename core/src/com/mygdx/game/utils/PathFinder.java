package com.mygdx.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.managers.LevelManager;

import java.util.ArrayList;
import java.util.Comparator;

/** This is a util class for path finding**/
public abstract class PathFinder {

    /**
     * @param startNode - the tile position of where the path begins
     * @param endNode - the tile position where the path ends
     * @param canGoDiag - if true: path will include calculation of nodes that are diagonal ,
     *                  else will only calculate up , down left , right
     * **/
    public static ArrayList<Node> findPath(Vector2 startNode , Vector2 endNode , boolean canGoDiag){
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
        Node current = new Node(startNode , null , 0 , startNode.dst(endNode));
        openList.add(current);
        while(openList.size() > 0){
            // sorts the nodes with lowest fCost lowest fCost should be index 0
            openList.sort(nodeSorter);
            current = openList.get(0);
            if(current.getCordinates().equals(endNode)){
                ArrayList<Node> path = new ArrayList<>();
                while(current.getParent() != null){
                    path.add(current);
                    current = current.getParent();
                }
                openList.clear();
                closedList.clear();
                return path;
            }
            openList.remove(current);
            closedList.add(current);
            // loops through all adjacent tiles
            for (int i = 0; i < 9; i++){
                if(i == 4) continue;
                if(!canGoDiag){
                    if (i == 0) continue;
                    if (i == 2) continue;
                    if (i == 6) continue;
                    if (i == 8) continue;
                }
                int x = MathUtils.floor(current.getCordinates().x);
                int y = MathUtils.floor(current.getCordinates().y);
                int xDir = (i % 3) - 1;
                int yDir = (i / 3) - 1;
                Tile at = LevelManager.getTile(x + xDir , y + yDir);
                if (at == null) continue;
                if(at.getType() == TileType.WALL) continue;
                // at is a valid path
                // to prevent creating a path diagonally we have to check
                // that there arent any walls between the diagonal path
                final Tile top = LevelManager.getTile(x  , y  - 1);
                final Tile bottom = LevelManager.getTile(x , y + 1);
                final Tile right = LevelManager.getTile(x  + 1 , y );
                final Tile left = LevelManager.getTile(x  - 1, y);
                // when topleft(diagonally) is being considerd, check if it has any walls below it or to its right
                // if it does, continue
                if(i == 0){
                    if(top == null || left == null) continue;
                    if(top.getType() == TileType.WALL && left.getType() == TileType.WALL) continue;
                }
                if(i == 2){
                    if(top == null || right == null) continue;
                    if(top.getType() == TileType.WALL && right.getType() == TileType.WALL) continue;
                }
                if(i == 6){
                    if(bottom == null || left == null) continue;
                    if(bottom.getType() == TileType.WALL && left.getType() == TileType.WALL) continue;
                }
                if(i == 8){
                    if(bottom == null || right == null) continue;
                    if(bottom.getType() == TileType.WALL && right.getType() == TileType.WALL) continue;
                }
                Vector2 a = new Vector2(x + xDir , y + yDir);
                double gCost = current.getgCost() + current.getCordinates().dst(a);
                double hCost = current.getCordinates().dst(endNode);
                Node node = new Node(a , current ,gCost , hCost);
                if (vectInList(closedList, a) && gCost >= current.getgCost()) continue;
                if (!vectInList(openList, a) || gCost < current.getgCost()) openList.add(node);
            }
        }
        closedList.clear();
        System.out.println("found no path, returning null");
        return null;
    }


    private static Comparator<Node> nodeSorter = new Comparator<Node>() {
        @Override
        public int compare(Node n0, Node n1) {
            if(n1.getfCost() < n0.getfCost()) return 1;
            if(n1.getfCost() > n0.getfCost()) return -1;
            return 0;
        }
    };

    private static boolean vectInList(ArrayList<Node> list , Vector2 vector2){
        for (Node n : list) {
            if(n.getCordinates().equals(vector2)) return true;
        }
        return false;
    }


}
