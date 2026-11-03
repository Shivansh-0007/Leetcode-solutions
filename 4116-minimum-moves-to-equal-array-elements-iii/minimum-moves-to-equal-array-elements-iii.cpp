#include <vector>
#include <numeric>
#include <algorithm>

class Solution {
public:
    int minMoves(std::vector<int>& nums) {
       
        int max_val = *std::max_element(nums.begin(), nums.end());
        
        int total_moves = 0;
        for (int num : nums) {
            total_moves += (max_val - num);
        }
        
        return total_moves;
    }
};
