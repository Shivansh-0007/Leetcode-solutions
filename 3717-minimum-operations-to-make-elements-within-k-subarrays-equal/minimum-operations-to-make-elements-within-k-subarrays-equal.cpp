#include <vector>
#include <set>
#include <cmath>
#include <algorithm>

class Solution {
public:
    long long minOperations(std::vector<int>& nums, int x, int k) {
        int n = nums.size();
        
        std::vector<long long> cost(n, 0);
        std::multiset<int> left, right;
        long long leftSum = 0, rightSum = 0;
        
        auto balance = [&]() {
            int targetLeftSize = (x + 1) / 2;
            
            while (left.size() < targetLeftSize && !right.empty()) {
                int val = *right.begin();
                right.erase(right.begin());
                rightSum -= val;
                left.insert(val);
                leftSum += val;
            }
            while (left.size() > targetLeftSize) {
                int val = *left.rbegin();
                left.erase(std::prev(left.end()));
                leftSum -= val;
                right.insert(val);
                rightSum += val;
            }
        };
        
        auto add = [&](int val) {
            if (left.empty() || val <= *left.rbegin()) {
                left.insert(val);
                leftSum += val;
            } else {
                right.insert(val);
                rightSum += val;
            }
            balance();
        };
        
        auto remove = [&](int val) {
            auto it = left.find(val);
            if (it != left.end()) {
                left.erase(it);
                leftSum -= val;
            } else {
                it = right.find(val);
                if (it != right.end()) {
                    right.erase(it);
                    rightSum -= val;
                }
            }
            balance();
        };

        for (int i = 0; i < n; ++i) {
            add(nums[i]);
            if (i >= x) {
                remove(nums[i - x]);
            }
            
            if (i >= x - 1) {
                long long median = *left.rbegin();
                // FIXED TYPO HERE: changed right.right.size() to right.size()
                long long currentCost = (median * left.size() - leftSum) + (rightSum - median * right.size());
                cost[i] = currentCost;
            }
        }
        
        const long long INF = 1e16; 
        std::vector<std::vector<long long>> history(n, std::vector<long long>(k + 1, INF));
        
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                history[i] = history[i - 1];
            }
            
            if (i >= x - 1) {
                for (int j = 1; j <= k; ++j) {
                    long long prevCost = 0;
                    if (j > 1) {
                        if (i - x >= 0) {
                            prevCost = history[i - x][j - 1];
                        } else {
                            prevCost = INF; 
                        }
                    }
                    
                    if (prevCost != INF) {
                        history[i][j] = std::min(history[i][j], prevCost + cost[i]);
                    }
                }
        
                history[i][1] = std::min(history[i][1], cost[i]);
            }
        }
        
        return history[n - 1][k] == INF ? -1 : history[n - 1][k];
    }
};

