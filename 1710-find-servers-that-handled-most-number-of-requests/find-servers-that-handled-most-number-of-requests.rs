use std::collections::{BinaryHeap, BTreeSet};
use std::cmp::Reverse;

impl Solution {
    pub fn busiest_servers(k: i32, arrival: Vec<i32>, load: Vec<i32>) -> Vec<i32> {
        let k = k as usize;
        let mut request_counts = vec![0; k];
        
        let mut available: BTreeSet<usize> = (0..k).collect();
        let mut busy = BinaryHeap::new();
        
        for i in 0..arrival.len() {
            let current_time = arrival[i];
    
            while let Some(&Reverse((finish_time, server_id))) = busy.peek() {
                if finish_time <= current_time {
                    busy.pop();
                    available.insert(server_id);
                } else {
                    break;
                }
            } 

            if available.is_empty() {
                continue;
            }
   
            let preferred = i % k;
  
            let mut target_server = None;
            if let Some(&server) = available.range(preferred..).next() {
                target_server = Some(server);
            } else {
                if let Some(&server) = available.iter().next() {
                    target_server = Some(server);
                }
            }
    
            if let Some(server) = target_server {
                available.remove(&server);
                request_counts[server] += 1;
                
                let finish_time = current_time + load[i];
                busy.push(Reverse((finish_time, server)));
            }
        }
  
        let max_requests = *request_counts.iter().max().unwrap_or(&0);

        request_counts
            .into_iter()
            .enumerate()
            .filter(|&(_, count)| count == max_requests)
            .map(|(id, _)| id as i32)
            .collect()
    }
}

