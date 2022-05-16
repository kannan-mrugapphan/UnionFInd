// 952.
class Solution {
    public int largestComponentSize(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        
        //initial number of groups = largest number in nums
        int maxElement = nums[0];
        for(int i = 1; i < nums.length; i++)
        {
            maxElement = Math.max(maxElement, nums[i]);
        }
        DisjointSet dsu = new DisjointSet(maxElement + 1); //+1 for 0 indexing of parent[]
        
        //time - O(n * sqrt(n) * log n) -> number of elements * time to find all factors * union time
        for(int num : nums)
        {
            //find each factor of num
            //1 is not factor as per qsn
            for(int factor = 2; factor * factor <= num; factor++)
            {
                //factor found
                if(num % factor == 0)
                {
                    //two factors are factor and num/factor
                    dsu.union(factor, num); //num and factor should be merged
                    dsu.union(num / factor, num); 
                }
            }
        }
        
        HashMap<Integer, Integer> sizeMap = new HashMap<>(); //tracks isze of each group
        int result = Integer.MIN_VALUE; 
        for(int num : nums)
        {
            int parent = dsu.find(num); //find rep of num
            if(!sizeMap.containsKey(parent))
            {
                sizeMap.put(parent, 1); //update size map
            }
            else
            {
                sizeMap.put(parent, sizeMap.get(parent) + 1); //update size map
            }
            
            result = Math.max(result, sizeMap.get(parent));
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
