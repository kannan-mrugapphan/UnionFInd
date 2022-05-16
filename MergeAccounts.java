//721.

class Solution {
    
    HashMap<String, Integer> emailToGroupMap = new HashMap<>(); //maps the email to account id
    HashMap<Integer, AccountInfo> resultMap = new HashMap<>(); 
    //result map is of format
    //GroupAccountId -> object of accountInfo class that has the name and sorted emails and helper methods
    
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size(); //initial number of groups
        DisjointSet dsu = new DisjointSet(n); //initialize dsu object
        
        //runtime = total emails in accounts = accounts.size() * accounts.get(i).size()
        for(int i = 0; i < n; i++)
        {
            int accountId = i;
            String accountName = accounts.get(i).get(0);
            for(int j = 1; j < accounts.get(i).size(); j++)
            {
                String email = accounts.get(i).get(j);
                //email is seen for the first time
                if(!emailToGroupMap.containsKey(email))
                {
                    emailToGroupMap.put(email, accountId); //mapping email to current accountId
                }
                //email is already seen
                else
                {
                    int prevAccountId = emailToGroupMap.get(email); //prev group which had this email
                    dsu.union(prevAccountId, accountId); //merge two accounts
                }
            }
        }
        
        //building results
        for(int i = 0; i < n; i++)
        {
            int accountId = i;
            int parentAccountId = dsu.find(accountId); //find rep of this account
            String accountName = accounts.get(i).get(0); //get account name
            //if this group (parentAccountId) is seen for the first time
            if(!resultMap.containsKey(parentAccountId))
            {
                resultMap.put(parentAccountId, new AccountInfo(accountName)); //create new entry in result map
            }
            //add emails to entry in result map
            for(int j = 1; j < accounts.get(i).size(); j++)
            {
                resultMap.get(parentAccountId).emails.add(accounts.get(i).get(j)); //add email to result set
            }
        }
        
        List<List<String>> result = new ArrayList<>(); //return list
        for(Integer accountId : resultMap.keySet())
        {
            result.add(resultMap.get(accountId).serialize()); //build result in expected format
        }
        
        return result;
    }
}

class DisjointSet {
    int[] parent; //tracks the representative of each group
    
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

class AccountInfo {
    String name;
    TreeSet<String> emails;
    
    public AccountInfo(String name) {
        this.name = name;
        emails = new TreeSet();
    }
    
    public List<String> serialize() {
        List<String> result = new ArrayList<>();
        result.add(name);
        result.addAll(emails);
        return result;
    } 
}
