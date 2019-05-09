'''
Created on Oct 23, 2018

@author: thomasvandrunen
'''

 
import unittest

from polynomial import polyn_product_linear, polyn_product_brute, unify_degree_bound
 
class TestPolynomialProductLinear(unittest.TestCase):

    def helpTest(self, a, b):
        ppb_res = polyn_product_brute(a, b)
        ppl_res = polyn_product_linear(a, b)
        unify_degree_bound(ppb_res, ppl_res)
        self.assertEqual(ppb_res, ppl_res)

    def testTwoEmpty(self) :
        self.helpTest([], [])

    def testTwoConstant(self):
        self.helpTest([3], [6])
        
        
    def testA(self):
        self.helpTest([2,7], [2,5])
        
    def testB(self):
        self.helpTest([8,1], [4,3])