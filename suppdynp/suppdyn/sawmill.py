'''
Created on Sep 14, 2016

@author: tvandrun
'''

import sys

# Compute the best order to make cuts in a log, given
# the locations of the cuts, where the cost of making
# a cut is equal to the length of the (sub-)log being cut.
# Assume the input is a list of integer locations of
# cuts as distances from one end (say the "left end") of
# the log in sorted order, including the trivial cuts
# at both ends (so, L[0] = 0 and L[len(L)-1] is the 
# length of the log).
# Return a list that is a permutation of the range [1,len(L)-2],
# indicating the temporal order that the cuts should be made.
# Thus if the 3rd cut from the left is 13 units from the left
# end and that cut should be made first, then the returned
# list (say s) will have s[0]==3. Note that L[s[0]]==13.
def sawmill(L):
    assert L[0] == 0
    return form_solution([], 0, n+1)

# Reconstruct a solution (a cutting order) from the
# table of decisions.
# The form of the value returned is the same as that
# returned by the sawmill() function
def form_solution(A, i, j):
    if j <= i+1:
        return []
    else :
        k = A[i][j]
        return [k] + form_solution(A, i, k) + form_solution(A, k, j)

# Compute the total cost of all cuts for a given
# cutting order for a given log.
# In a sense this re-does the work done in populating
# table C, but for testing purposes. In other words,
# compute the cost independently of the process of building
# the table, to be used by the unit tests (which test the
# result of the table-building algorithm.
def compute_cost_of_soln(L, s):
    n = len(L) - 2
    cuts_made = [False for x in range(len(L))]
    cuts_made[0] = True
    cuts_made[n+1] = True
    cost = 0
    for cut in s :
        begin = cut - 1
        while not cuts_made[begin] :
            begin -= 1
        end = cut + 1
        while not cuts_made[end] :
            end += 1
        #print L[end], L[begin], (L[end] - L[begin])
        cost += L[end] - L[begin]
        cuts_made[cut] = True
    return cost


import unittest

class TestSawmill(unittest.TestCase):
    
    def testA(self):
        log_instance = [0,9,10,11,20]
        self.assertEqual(33, compute_cost_of_soln(log_instance, sawmill(log_instance)))

    def testB(self):
        log_instance = [0, 4, 5, 6, 12,20]
        self.assertEqual(40, compute_cost_of_soln(log_instance, sawmill(log_instance)))

    
