package c15p6;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class TestPartyPlanner {

    public void runTest(String personnel) {
        HashSet<PersonnelTree> guestList = 
                PartyPlanner.makeGuestList(PersonnelTree.factory(personnel));
        int totalConviviality = 0;
        for (PersonnelTree p : guestList) {
            totalConviviality += p.conviviality();        
            System.out.println(p);
        }
        assertEquals(53, totalConviviality);
    }

    @Test
    public void testA() {
        runTest("(18(6(2)(3))(5(8)(1(10)(12))))");
    }
    
}
