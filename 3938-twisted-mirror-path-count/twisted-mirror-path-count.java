class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        int[][] rightFlow = new int[m][n];
        int[][] downFlow = new int[m][n];

        // Starting position
        rightFlow[0][0] = 1;
        downFlow[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                long fromLeft = 0;
                long fromTop = 0;

                if (j > 0) {
                    fromLeft = rightFlow[i][j - 1];
                }

                if (i > 0) {
                    fromTop = downFlow[i - 1][j];
                }

                if (grid[i][j] == 1) {
                    // Mirror: right becomes down
                    //         down becomes right

                    downFlow[i][j] = (int)(fromLeft % MOD);
                    rightFlow[i][j] = (int)(fromTop % MOD);

                } else {
                    // Empty cell: move right or down

                    long total = (fromLeft + fromTop) % MOD;

                    rightFlow[i][j] = (int) total;
                    downFlow[i][j] = (int) total;
                }
            }
        }

        // Count paths reaching destination
        long ans = 0;

        if (n > 1) {
            ans += rightFlow[m - 1][n - 2];
        }

        if (m > 1) {
            ans += downFlow[m - 2][n - 1];
        }

        return (int)(ans % MOD);
    }
}


