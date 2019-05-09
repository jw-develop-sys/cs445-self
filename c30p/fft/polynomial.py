from operator import mul
import cmath

class PolynomialByCoefficient :
    
    def __init__(self, coefficients):
        self.coefficients = list(coefficients)
        while len(self.coefficients) > 1 and self.coefficients[-1] == 0 :
            self.coefficients = self.coefficients[:-1]
        
    def evaluate(self, x):
        result = 0
        for c in reversed(self.coefficients) :
            result = x*result + c
        return result

    def degree_bound(self):
        return len(self.coefficients)
    
    def double_degree_bound(self):
        self.coefficients += [0 for i in range(len(self.coefficients))]
    
    def point_value_form(self, points):
        assert len(points) == len(self.coefficients)
        return PolynomialPointValue(points, [self.evaluate(x) for x in points])
    
    # Divide by (x - xk)
    def quotient_remainder(self, xk):
        n = self.degree_bound()
        assert n > 1
        q = [self.coefficients[n-1]]
        for i in reversed(range(1,n-1)) :
            q.insert(0, self.coefficients[i] + q[0] * xk)
        r = self.coefficients[0] + q[0] * xk
        return (q,r)
    
    def evaluate_by_quotient(self, x):
        n = self.degree_bound()
        if n == 0:
            return 0
        elif n == 1 :
            return PolynomialByCoefficient(self.coefficients[0])
        else :
            return self.quotient_remainder(self, x)[1]
        
    def __str__(self):
        if len(self.coefficients) == 0 :
            return "0"
        else :
            result = str(self.coefficients[0])
            if len(self.coefficients) > 1 :
                result += " + " + str(self.coefficients[1]) + "x" 
                for i in range(2, len(self.coefficients)) :
                    result += " + " + str(self.coefficients[i]) + "x^" + str(i)
            return result
        

def unify_degree_bound(a, b):
    degree = max(len(a.coefficients), len(b.coefficients))
    a.coefficients += [0 for i in range(degree - len(a.coefficients))]
    b.coefficients += [0 for i in range(degree - len(b.coefficients))]
        
 
def polyc_sum(a, b):
    unify_degree_bound(a, b)    
    return PolynomialByCoefficient([ca + cb for (ca,cb) 
                                    in zip(a.coefficients, b.coefficients)])


def polyc_brute_product(a, b):
    n = max(a.degree_bound(), b.degree_bound())
    nn = 2 * n - 1
    ac = a.coefficients + [0 for i in range(nn - a.degree_bound())]
    bc = b.coefficients + [0 for i in range(nn - b.degree_bound())]
    return PolynomialByCoefficient([sum([ac[k] * bc[j-k] 
                                         for k in range(j+1)]) 
                                         for j in range(nn)])    

unity_poly = PolynomialByCoefficient([1])
zero_poly = PolynomialByCoefficient([0])


# Can you believe Guido rejected the suggestion of a built-in product function?
def product(iterable):
    return reduce(mul, iterable, 1)
    
class PolynomialPointValue :
    def __init__(self, xs, ys):
        assert len(xs) == len(ys)
        self.xs = xs
        self.ys = ys
        
    def interpolate(self):
        numerator_terms = [PolynomialByCoefficient([-x, 1]) for x in self.xs]
        mega_numerator = reduce(polyc_brute_product,numerator_terms,unity_poly)
        #mega_numerator = unity_poly
        #for current_poly in numerator_terms :
        #    mega_numerator = polyc_brute_product(mega_numerator, current_poly)
        result = zero_poly
        for k in range(len(self.xs)) :
            xk = self.xs[k]
            yk = self.ys[k]
            numerator = mega_numerator.quotient_remainder(self.xs[k])[0]
            denominator = product([xk - xj for xj in self.xs if xj != xk])
            term = PolynomialByCoefficient([c * yk / denominator 
                                            for c in numerator])
            result = polyc_sum(result, term)
        return result   
            
    def __str__(self):
        result = "{(" + str(self.xs[0]) + "," + str(self.ys[0]) + ")"
        for i in range(1, len(self.xs)) :
            result += ", " + "(" + str(self.xs[i]) + "," + str(self.ys[i]) + ")"
        result += "}"
        return result

def polypv_compatible(a, b):
    # Python lesson I learned today:
    # Use a generator expression instead of a list comprehension
    # to take advantage of the all function's short circuit
    return len(a.xs) == len(b.xs) and all((ax == bx 
                                           for (ax, bx) in zip(a.xs, b.xs)))
    
    
 
 
def polypv_sum(a, b):
    assert polypv_compatible(a, b)
    return PolynomialPointValue(a.xs, [ay + by 
                                       for (ay,by) in zip(a.ys, b.ys)])
    
def polypv_product(a, b):
    assert polypv_compatible(a, b)
    return PolynomialPointValue(a.xs, [ay * by
                                       for (ay,by) in zip(a.ys, b.ys)])
    
i = complex(0,1)

def transform(a):
    n = len(a)
    if n == 1:
        return a
    else :
        n_half = n/2
        a_even = [a[k*2] for k in range(n_half)]
        a_odd = [a[k*2 + 1] for k in range(n_half)]
        y_even = transform(a_even)
        y_odd = transform(a_odd)

        # principle nth root of unity
        pnrou = cmath.e ** (2 * cmath.pi * i / n)
        # current nth root of unity
        cnrou = 1
        y = [None for k in range(n)]
        for k in range(n_half) :
            y[k] = y_even[k] + cnrou * y_odd[k]
            y[k + n_half] = y_even[k] - cnrou * y_odd[k]
            cnrou *= pnrou

        return y

def fft(a):
    n = a.degree_bound()
    pnrou = cmath.e ** (2 * cmath.pi * i / n)
    nrous = [1]
    for k in range(1, n) :
        nrous.append(nrous[-1] * pnrou)
    return PolynomialPointValue(nrous, transform(a.coefficients))

def transform_inverse(y):
    n = len(y)
    if n == 1:
        return y
    else :
        n_half = n/2
        y_even = [y[k*2] for k in range(n_half)]
        y_odd = [y[k*2 + 1] for k in range(n_half)]
        a_even = transform_inverse(y_even)
        a_odd = transform_inverse(y_odd)

        # principle nth root of unity inverse
        pnroui = 1 / cmath.e ** (2 * cmath.pi * i / n)
        # current nth root of unity
        cnrou = 1
        a = [None for k in range(n)]
        for k in range(n_half) :
            a[k] = a_even[k] + cnrou * a_odd[k]
            a[k + n_half] = a_even[k] - cnrou * a_odd[k]
            cnrou *= pnroui

        return a

def fft_inverse(a):
    n = len(a.xs)
    inverse_result = transform_inverse(a.ys)
    return PolynomialByCoefficient([c/n for c in inverse_result])

def polyc_fft_product(a, b):
    a.double_degree_bound()
    b.double_degree_bound()
    a_fft = fft(a)
    b_fft = fft(b)
    c_fft = polypv_product(a_fft, b_fft)
    return fft_inverse(c_fft)

a = PolynomialByCoefficient([1,2,3])
print str(a)
b = PolynomialByCoefficient([3,2,1])
print str(b)
s = polyc_sum(a, b)
print str(s)
p = polyc_brute_product(a, b)
print str(p)
aa = a.point_value_form([1,2,3])
print str(aa)
bb = b.point_value_form([1,2,3])
print str(bb)
ss = s.point_value_form([1,2,3])
print str(ss)

aaa = PolynomialPointValue([1,2,3], [6,17,34])
print str(aaa)
bbb = PolynomialPointValue([1,2,3], [6,11,18])
print str(bbb)
sss = polypv_sum(aaa, bbb)
print str(sss)

aaaa = aaa.interpolate()
print str(aaaa)
bbbb = bbb.interpolate()
print str(bbbb)
ssss = sss.interpolate()
print str(ssss)

print "*******"
          
a = PolynomialByCoefficient([8])
print "a = " + str(a)
b = PolynomialByCoefficient([1])
print "b = " + str(b)
c = polyc_brute_product(a, b)
print "brute force product: " + str(c)

c = polyc_fft_product(a, b)
print "fft product: " + str(c)

print "*******"
          
a = PolynomialByCoefficient([8,4])
print "a = " + str(a)
b = PolynomialByCoefficient([1,2])
print "b = " + str(b)
c = polyc_brute_product(a, b)
print "brute force product: " + str(c)

c = polyc_fft_product(a, b)
print "fft product: " + str(c)

print "*******"
          
a = PolynomialByCoefficient([8,4,2,6])
print "a = " + str(a)
b = PolynomialByCoefficient([1,2,0,6])       
print "b = " + str(b)
c = polyc_brute_product(a, b)
print "brute force product: " + str(c)

c = polyc_fft_product(a, b)
print "fft product: " + str(c)

print "*******"
          
a = PolynomialByCoefficient([8,4,2,6,3,5,6,2])
print "a = " + str(a)
b = PolynomialByCoefficient([1,2,0,6,4,2,6,3])         
print "b = " + str(b)
c = polyc_brute_product(a, b)
print "brute force product: " + str(c)

c = polyc_fft_product(a, b)
print "fft product: " + str(c)


 
import unittest
 
class TestPolynomial(unittest.TestCase):

    def testEvaluateEmpty(self):
        poly = PolynomialByCoefficient([])
        self.assertEqual(0, poly.evaluate(0))
        self.assertEqual(0, poly.evaluate(5))
        self.assertEqual(0, poly.evaluate(21))
        
    def testEvaluateConstant(self):
        poly = PolynomialByCoefficient([5])
        self.assertEqual(5, poly.evaluate(0))
        self.assertEqual(5, poly.evaluate(5))
        self.assertEqual(5, poly.evaluate(21))
        
    def testEvaluateQuadratic(self):
        poly = PolynomialByCoefficient([2, 4, 5])
        self.assertEqual(2, poly.evaluate(0))
        self.assertEqual(147, poly.evaluate(5))
        self.assertEqual(2291, poly.evaluate(21))

    def testEvaluateQEmpty(self):
        poly = PolynomialByCoefficient([])
        self.assertEqual(0, poly.evaluate_by_quotient(0))
        self.assertEqual(0, poly.evaluate_by_quotient(5))
        self.assertEqual(0, poly.evaluate_by_quotient(21))
        
    def testEvaluateQConstant(self):
        poly = PolynomialByCoefficient([5])
        self.assertEqual(5, poly.evaluate_by_quotient(0))
        self.assertEqual(5, poly.evaluate_by_quotient(5))
        self.assertEqual(5, poly.evaluate_by_quotient(21))
        
    def testEvaluateQQuadratic(self):
        poly = PolynomialByCoefficient([2, 4, 5])
        self.assertEqual(2, poly.evaluate_by_quotient(0))
        self.assertEqual(147, poly.evaluate_by_quotient(5))
        self.assertEqual(2291, poly.evaluate_by_quotient(21))

