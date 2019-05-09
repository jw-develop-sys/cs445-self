'''
Created on Oct 23, 2018

@author: tvandrun
'''

import test_polyn_prod
from polynomial import polyn_product_dc_highlow

class TestPolynomialProductDCHL(test_polyn_prod.TestPolynomialProduct):
    def product_fun(self, a, b):
        return polyn_product_dc_highlow(a, b)
