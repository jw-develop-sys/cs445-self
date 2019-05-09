'''
Created on Oct 23, 2018

@author: tvandrun
'''

import unittest

from polynomial import polyn_product_brute, unify_degree_bound
from c30p1.polynomial import polyn_product_dc_highlow

class TestPolynomialProduct(unittest.TestCase):
    
    
    def helpTest(self, a, b):
        ppb_res = polyn_product_brute(a, b)
        other_res = self.product_fun(a, b)
        unify_degree_bound(ppb_res, other_res)
        self.assertEqual(ppb_res, other_res)
    
    def testTwoEmpty(self) :
        self.helpTest([], [])

    def testTwoConstant(self):
        self.helpTest([3], [6])
        
    