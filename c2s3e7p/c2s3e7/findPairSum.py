'''
Created on Jul 21, 2016

CLRS 2.3-7
@author: thomasvandrunen
CSCI 445
'''

# Find a pair in a set that sums to a given number, if any.
# The problem said "set", which doesn't fully specify the problem.
# We need to assume that set can be iterated over in linear time.
# So I'm going to assume it's a list (but not that it is sorted).
# I'm also assuming it's ok to rearrange the array.
# s - the sequence we're searching
# x - the sum we want to find two addends of
# returns a tuple with the values in the set that sums to x
def findPairSum(s, x):
     return None


# --- Test cases ---

import unittest

class TestFindPairSum(unittest.TestCase):
    def testEndPoints(self):
        self.assertEqual(findPairSum([4,5,2,3,8,9], 11), (2, 9))
    def testNotAtAll(self):
        self.assertEqual(findPairSum([8, 3, 17, 51, 22, 7], 33), None)
     
    
    
    
