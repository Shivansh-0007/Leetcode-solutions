class Solution:
    def minimumCost(self, cost1: int, cost2: int, costBoth: int, need1: int, need2: int) -> int:
  
        buy_separate = need1 * cost1 + need2 * cost2

        buy_all_both = costBoth * max(need1, need2)
    
        overlap = min(need1, need2)
        buy_hybrid = costBoth * overlap + (need1 - overlap) * cost1 + (need2 - overlap) * cost2
        
        return min(buy_separate, buy_all_both, buy_hybrid)

        