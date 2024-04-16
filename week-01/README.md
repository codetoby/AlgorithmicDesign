# Programming Assignments README

## Overview
This document outlines the requirements for three programming tasks related to scheduling and data encoding. Each task requires the creation of a Java program to solve a specific problem as described below.

### Assignment Files
- **MachineA.java** - Greedy scheduling algorithm
- **MachineB.java** - Optimal scheduling algorithm
- **Codelength.java** - Encoding text using Huffman coding for monographs, digraphs, and trigraphs

## Task Descriptions

### Task 1: Greedy Job Scheduling on a Single Machine
**File Name:** MachineA.java

**Description:** Implement a greedy algorithm to schedule tasks on a single machine. The machine can only perform one task at a time, and tasks have specific start and end times. The objective is to maximize the number of tasks scheduled.

**Input:** 
- The first line contains an integer `k` (number of tasks, `k < 10^7`).
- Each of the next `k` lines contains a pair of integers `a b`, indicating the start and finish times of each task.

**Output:** 
- A list of tasks (start and finish times) that are scheduled, in chronological order.

### Task 2: Optimal Job Scheduling on a Single Machine
**File Name:** MachineB.java

**Description:** Design and implement an algorithm to optimally schedule the maximum number of tasks on a single machine.

**Input:** 
- Same format as Task 1.

**Output:** 
- A single integer `m`, the maximum number of tasks that can be scheduled.

### Task 3: Huffman Coding for Text Encoding
**File Name:** Codelength.java

**Description:** Write a program to determine the optimal encoding length of text using Huffman coding when considering monographs, digraphs, or trigraphs.

**Input:** 
- Up to `10^8` characters of ASCII text.

**Output:** 
- Three integers `n1 n2 n3`, representing the length of the encoded text using monographs, digraphs, and trigraphs respectively.

**Additional Notes:** 
- Text should be case-insensitive and ignore non-alphabetic characters.
- If the text length is not a multiple of 6, append up to 5 'z's to make it divisible by 6.

