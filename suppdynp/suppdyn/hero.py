'''
Created on Sept 12, 2016

@author: tvandrun
'''

# Find the best route through two parallel halls
# containing treasure. Each room in the two
# halls contains treasure. At each position
# the hero can go straight into the next room
# in the current call or switch to the other hall.
# T - an n*2 list indicating the amount of treasure
#     at each position, for example T[4][1] indicates
#     the treasure in the 4th room of the right hall.
# P - an (n-1) list indicating the penalty for switching
#     halls between the ith and i+1st positions
# returns
#     The optimal route as n n list indicating which hall 
#     the hero should be in for each position to maximize
#     the treasure collected
def heroRoute(T, P):
    assert len(T) == len(P) + 1

     return None    

import unittest

class TestHeroRoute(unittest.TestCase):
    
    def testOneJog(self) :
        self.assertEqual([1,1,0,1], heroRoute([[1,8],[4,2],[10,4],[3,6]], [6,3,2]))

    def testGoStraight(self):
        self.assertEqual([0,0,0,0], heroRoute([[2,1], [2,3], [1,1],[2,1]], [8,5,7]))
        
    
    