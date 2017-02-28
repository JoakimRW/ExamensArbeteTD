package com.mygdx.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.Tile;

import java.util.*;

/** This is a util class for path finding**/
public abstract class PathFinder {

    /**
     * @param startNode - the tile position of where the path begins
     * @param endNode - the tile position where the path ends
     * @param canGoDiag - if true: path will include calculation of nodes that are diagonal ,
     *                  else will only calculate up , down left , right
     * **/
    public static ArrayList<Node> findPath(Vector2 startNode , Vector2 endNode , boolean canGoDiag){
        ArrayList<Node> openList = new ArrayList<Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();
        Node current = new Node(startNode , null , 0 , startNode.dst(endNode));
        openList.add(current);
        while(openList.size() > 0){
            // sorts the nodes with lowest fCost lowest fCost should be index 0
            openList.sort(nodeSorter);
            current = openList.get(0);
            if(current.getCordinates().equals(endNode)){
                ArrayList<Node> path = new ArrayList<Node>();
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
            // if canGoDiag is true loops 9 times to get 8 dir , else loops 9 times gets 4 dir

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
                Tile at = LevelManager.getTile(x + xDir   , y + yDir);
                if (at == null) continue;
                if(at.getType() == TileType.WALL) continue;
                Vector2 a = new Vector2(x + xDir, y + yDir);
                double gCost = current.getgCost() + current.getCordinates().dst(a);
                double hCost = current.getCordinates().dst(endNode);
                Node node = new Node(a , current ,gCost , hCost);
                if (vectInList(closedList, a) && gCost >= current.getgCost()) continue;
                if (!vectInList(openList, a) || gCost < current.getgCost()) openList.add(node);
            }
        }
        closedList.clear();
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
