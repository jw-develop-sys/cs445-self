'''
Created on Jul 27, 2016

@author: tvandrun
'''

# Find the missing number in a list which otherwise
# would have the numbers in the range [0, len(a)]
# Assumptions: parameter a is a list containing only unique elements
# from the range [0, len(a)], sorted.
# a - the list in which to search
# returns the missing number 
def findMissing(a):
     return None

import unittest

class TestFindMissing(unittest.TestCase):
    
    def testFirstMissing(self):
        self.assertEqual(0, findMissing([1,2,3,4,5]))
        
    def testLastMissing(self):
        self.assertEqual(5, findMissing([0,1,2,3,4]))

             
