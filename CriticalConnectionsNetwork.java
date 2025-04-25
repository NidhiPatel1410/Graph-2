// In this problem we are using tarjan's algorithm of strongly connected components. So here we have discovery array that tells us,
// when was the node discovered. Then, lowest array for storing the same values as discovery but then the values for the nodes which
// are in cycle, it will change. And time variable, for capturing the time in discovery. The nodes which are in cycle are strongly
// connected, but the nodes which are not in cycle, there will be a critical connection over there.
// This algorithm involves performing DFS.

// Time Complexity : O(V+E)
// Space Complexity : O(V+E) 
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
class Solution {
    int[] discovery;
    int[] lowest;
    int time;
    List<List<Integer>> result;
    // Graph we will built for performing DFS, each index will be node and the list
    // at that index will be the nodes connected to it
    List<List<Integer>> graph;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // If n==0, return empty list
        if (n == 0) {
            return new ArrayList<>();
        }
        // Initialize all
        discovery = new int[n];
        lowest = new int[n];
        // Fill it with -1 initially
        Arrays.fill(discovery, -1);
        Arrays.fill(lowest, -1);
        time = 0;
        result = new ArrayList<>();
        graph = new ArrayList<>();
        // Declare n empty arraylist in the graph for n nodes
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        // Call the buildgraph method
        buildGraph(n, connections);
        // Call dfs starting from node 0 and -1 as it's parent
        dfs(0, -1);
        return result;
    }

    private void buildGraph(int n, List<List<Integer>> connections) {
        // Go over the connections given
        for (List<Integer> connection : connections) {
            // And build graph accordingly
            int from = connection.get(0);
            int to = connection.get(1);
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
    }

    private void dfs(int v, int u) {
        // If the node is already discovered then, return
        if (discovery[v] != -1) {
            return;
        }
        // Else give to it the current time
        discovery[v] = time;
        // Also to lowest[v]
        lowest[v] = time;
        // Increment time for the next node
        time++;
        // Get it's childrens/neighbours
        List<Integer> children = graph.get(v);
        // Go over each
        for (int n : children) {
            // Check if it is parent itself, avoid otherwise infinite loop
            if (n == u) {
                continue;
            }
            // Call dfs with this child and v as it's parent
            dfs(n, v);
            // If the below condition is true then it is a critical connection
            if (lowest[n] > discovery[v]) {
                // Add to result
                List<Integer> temp = new ArrayList<>();
                temp.add(n);
                temp.add(v);
                result.add(temp);
            }
            // Then update the lowest of v, to the lowest of both
            lowest[v] = Math.min(lowest[n], lowest[v]);
        }
    }
}