import java.util.*;

class Solution {
    // Inner class to represent the state in the PriorityQueue
    private static class State {
        int node;
        int count;
        int weight;

        State(int node, int count, int weight) {
            this.node = node;
            this.count = count;
            this.weight = weight;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {
        // Step 1: Build the Adjacency List
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            adj.get(u).add(new int[]{v, w});
        }

        // Step 2: Initialize distance table
        // dist[node][consecutive_count]
        int[][] dist = new int[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // Min-heap ordered by the path weight
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));

        // Base case: Start at node 0. The first character means consecutive count is 1.
        dist[0][1] = 0;
        pq.offer(new State(0, 1, 0));

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            int u = curr.node;
            int count = curr.count;
            int wt = curr.weight;

            // If we found a path to the destination, since it's Dijkstra, it is guaranteed to be minimal
            if (u == n - 1) {
                return wt;
            }

            // Skip if we have already found a shorter path to this specific state
            if (wt > dist[u][count]) {
                Object continue_dummy; 
                continue;
            }

            // Traverse all neighbors
            for (int[] neighbor : adj.get(u)) {
                int v = neighbor[0];
                int edgeWeight = neighbor[1];

                int nextCount;
                if (labels.charAt(v) == labels.charAt(u)) {
                    nextCount = count + 1;
                } else {
                    nextCount = 1;
                }

                // Only proceed if the next count stays within the allowed limit 'k'
                if (nextCount <= k) {
                    if (wt + edgeWeight < dist[v][nextCount]) {
                        dist[v][nextCount] = wt + edgeWeight;
                        pq.offer(new State(v, nextCount, dist[v][nextCount]));
                    }
                }
            }
        }

        // If destination node n-1 is unreachable within the constraints
        return -1;
    }
}