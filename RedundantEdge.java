// 684.
// time - O(ElogV)
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        DisjointSet dsu = new DisjointSet(n + 1); //+1 for 0 indexing of parent[]
        int[] result = new int[2];
        
        for(int[] edge: edges)
        {
            int xParent = dsu.find(edge[0]);
            int yParent = dsu.find(edge[1]);
            //merge if needed
            if(xParent != yParent)
            {
                dsu.union(xParent, yParent);
            }
            //if they are part of same group then it is the redundant edge
            //update result to handle case to return last edge if input in case of multiple redundant edges
            else
            {
                result = edge;
            }
        }
        
        return result; 
    }
}

class DisjointSet {
    private int[] parent; //tracks the representative of each group
    
    public DisjointSet(int n) {
        parent = new int[n]; 
        for(int i = 0; i < n; i++)
        {
            //initially each node is a parent of itself
            parent[i] = i;
        }
    }
    
    //time - O(log n) -> n is number of groups
    public int find(int n) {
        //if incoming node is not same as its parent, recursively call find(on incoming node's parent) to get group rep
        if(parent[n] != n)
        {
            parent[n] = find(parent[n]); //update parent to optimize when find on same node is called again
        }
        return parent[n];
    }
    
    //time - O(log n) 
    public void union(int x, int y) {
        int xParent = find(x);
        int yParent = find(y);
        //if reps of x and y are not same, merge the groups
        if(xParent != yParent)
        {
            parent[yParent] = xParent; //assinging y to x as no precendence is used
        }
        return;
    }
}
