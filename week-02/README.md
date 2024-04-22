# Anchored Common Subsequence Assignment

## Description
This assignment requires implementing an algorithm to find the longest common subsequence (`LACS`) of two strings `s1` and `s2` which may contain a special 'anchor' character `*`. Unlike ordinary characters, `*` binds more strongly, meaning that in the subsequence, `*` from `s1` and `s2` must align without being skipped.

## Objective
Write a Java program that reads two strings from an input file and outputs the length of their longest anchored common subsequence. The strings are composed of the uppercase alphabet and the `*` anchor character, and can be up to 2,000 characters in length.

## Example
Given `s1 = A*BF*CB` and `s2 = F*CAB*D`, the `LACS` would be `F*CB`.

## Input Format
The input file consists of two lines, each containing a string of characters from the set `{A, B, ..., Z, *}`.

## Output Format
The program should output a single integer `n`, the length of the longest anchored common subsequence.

## Implementation Details
- Use dynamic programming to construct a solution.
- Pay special attention to cases where the `*` character is encountered.
- Ensure that your program handles strings of the maximum specified length efficiently.