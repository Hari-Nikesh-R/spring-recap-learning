# Missing numbers

Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.

Example 1:

Input: nums = [3,0,1]

Output: 2

Explanation:

n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number in the range since it does not appear in nums.

## Approach
### Algorithm 1
1. Sorting
2. Do the below logic.
```java
for(int i = 0; i < nums.length; i++){
if(nums[i] != i){
System.out.println(i);
return;
```
What is the time complexity?

Sorting - O(n Log n)

Traversal - O(n)

Overall Time complexity = O(n Log n)

### What we can do to make it optimal?

### Algorithm 2
#### Constraint
* Number always starts from 0
* End number will always equals to the array size - 1.
Actual
n = 4
0,1,2,3        3 (largest num in array) = (n-1)

Missing case
n = 3
0,1,3     3 = 3

Actual case
n = 5
0,1,2,3,4     4 = (n-1)

Missing case
n = 4
0,2,3,4      4 (largest num in array) = 4 size of the array


Key formula
4 (largest num in array) = 4 size of the array


n = 7 
Missing case
Largest number = 7

Dry run 2
3 + 0 + 1 = 4 (Missing case) [3, 0, 1] 
0 + 1 + 2 + 3 = 6 (Actual case)

Answer = Actual case - Missing case
ans = 2 -> Missing number


Dry run 3
n = 3
[3,2,1] 
0 + 1 + 2 + 3 = 6 (Missing case)
0 + 1 + 2 + 3 = 6 (Actual case)

Answer = Actual case - Missing case
Answer = 6 - 6 = 0
Missing value = 0


Dry run 4
n = 5
[0,5,3,2,1]
0 + 5 + 3 + 2 + 1 = 11 (Missing case)
0 + 1 + 2 + 3 + 4 + 5 = 15 (Actual case)

Answer = Actual case - Missing case
answer = 15 - 11
Missing value = 4
