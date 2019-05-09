'''
Created on Oct 22, 2018

@author: tvandrun
'''

import math
from operator import mul

# Throughout this code polynomials are represented
# using a list of coefficients. 

# Evaluate a polynomial at a given x value,
# using Horner's rule in a loop, included here
# (though commented out), since some might find
# it more understandable than the version that follows.
#
#def polyn_eval(a, x):
#    result = 0
#    for c in reversed(a) :
#        result = x*result + c
#    return result


# Evaluate a polynomial at a given x value,
# using Horner's rule in a reduce
def polyn_eval(a, x):
    return reduce(lambda r, c : x * r + c, reversed(a), 0)


# I don't recall needing log in any base other than 2
# EVER. I don't know why programming libraries give
# common and natural log but force you to convert
# for binary log. Python at least has a log function that
# allows you to specify a base as a parameter.
def lg(x):
    return math.log(x,2)

def is_pow_two(x):
    return lg(x).is_integer()

# Can you believe Guido rejected the suggestion of a built-in product function?
def product(iterable):
    return reduce(mul, iterable, 1)

# Pad a polynomial so that its length is equal
# to a given degree bound
def expand_to_degree_bound(a, degree_bound):
    while len(a) < degree_bound :
        a.append(0)
    
# Expand two polynomials so that their lengths
# are equal to the least power of two greater than
# or equal to their greater length.
def unify_degree_bound(a, b):
    least_common_degree_bound = max(len(a),len(b))
    if least_common_degree_bound == 0 :
        least_common_degree_bound = 1
    unified_degree_bound = 2**math.ceil(lg(least_common_degree_bound))
    expand_to_degree_bound(a, unified_degree_bound)
    expand_to_degree_bound(b, unified_degree_bound)

# Build a string representation of a given polynomial
def polyn_to_string(a):
    if len(a) == 0 :
            return "0"
    else :
        result = str(a[0])
        if len(a) > 1 :
            result += " + " + str(a[1]) + "x" 
            for i in range(2, len(a)) :
                result += " + " + str(a[i]) + "x^" + str(i)
        return result

# Compute the sum of two polynomials
def polyn_sum(a, b):
    unify_degree_bound(a, b)    
    return [ca + cb for (ca,cb) in zip(a, b)]

# Compute the product of two polynomials
# by "brute force" (see CLRS pg 899)
def polyn_product_brute(a, b):
    n = max(len(a), len(b))
    nn = 2 * n - 1
    ac = list(a)
    expand_to_degree_bound(ac,nn)
    bc = list(b)
    expand_to_degree_bound(bc, nn)
    return [sum([ac[k] * bc[j-k]  for k in range(j+1)]) for j in range(nn)]

# 30-1.a
# Compute the product of two linear (degree 1)
# polynomials.
def polyn_product_linear(a, b):
    assert len(a) <= 2 and len(b) <= 2
    expand_to_degree_bound(a, 2)
    expand_to_degree_bound(b, 2)
    pass
    
# 30-1.b.i
# Compute the product of two polynomials
# using a divide and conquer approach, dividing
# each polynomial by its high and low terms
def polyn_product_dc_highlow(a, b):
    unify_degree_bound(a, b)
    return polyn_product_dchlr(a, b)

def polyn_product_dchlr(a, b):
    n = len(a)
    assert n == len(b) and is_pow_two(n)
    
    if n == 1 :
        return [a[0] * b[0], 0]
    else :
        n_half = n/2
 
        a_low = [a[i] for i in range(n_half)]
        a_high = [a[i+n_half] for i in range(n_half)]
        b_low = [b[i] for i in range(n_half)]
        b_high = [b[i+n_half] for i in range(n_half)]
        
        pass
    


# 30-1.b.ii
# Compute the product of two polynomials
# using a divide and conquer approach, dividing
# each polynomial by its even and odd terms
def polyn_product_dc_evenodd(a, b):
    unify_degree_bound(a, b)
    return polyn_product_dceor(a, b)

# "Perforate" a list by inserting 0s after every
# item, for example turning [1,2,3] into [1,0,2,0,3,0].
# For polynomials, this turns a polynomial that would
# be interpreted as a function of x^2 into a function
# of x. 
def perforate(a):
    result = []
    for i in range(len(a)):
        result.append(a[i])
        result.append(0)
    return result

def polyn_product_dceor(a, b):
    n = len(a)
    assert n == len(b) and is_pow_two(n)
    
    if n == 1 :
        return [a[0] * b[0], 0]
    else :
        n_half = n/2
        
        a_even = [a[2*i] for i in range(n_half)]
        a_odd = [a[2*i + 1] for i in range(n_half)]
        b_even = [b[2*i] for i in range(n_half)]
        b_odd = [b[2*i + 1] for i in range(n_half)]

        pass

