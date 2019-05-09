package c4p5;

import java.util.Random;
import java.util.Set;

/**
 * Class to model the chips that Professor Diogenes
 * is testing.
 * 
 * The class is designed to prevent an algorithm from
 * using anything sneaky (like instanceof) to determine
 * whether a chip is bad or not. To make it easier to
 * test solutions to Problem 4-5, the chips can be tagged
 * as good or bad, and they are untagged by default. 
 * Thus the solution can rearrange the collection of
 * chips any way the programmer wants. It doesn't have to
 * return anything, just tag the chips as good or bad.
 * So that the JUnit tests (or whatever) can "reveal" the
 * whether the chip's tag is correct. But once the reveal()
 * method is called on a chip, the chip is broken and nothing
 * can be called on it again---it can't be re-tagged or
 * even re-revealed. Hence if the solution to Problem 4-5 calls
 * reveal() at all, the JUnit test is sure to fail when it
 * calls reveal() on that chip.
 * 
 * CLRS 4-5
 * @author Thomas VanDrunen
 * CSCI 445
 * July 22, 2016
 */
public class Chip {
    
    private static Random randy = new Random();

    // Classes to model the inner part of the chip
    // (the "die" or the "core" on the chip)
    // to which the chip's test method delegates the
    // actual testing. I didn't want to make different chip
    // classes for good and bad chips, because then 
    // an algorithm could test if they're good or not by
    // doing instanceof.
    private static interface Die {
        boolean testChip(Die other);
    }
    private static class Good implements Die {
        public boolean testChip(Die other) { return other instanceof Good; }
    }
    // There are many kinds of bad chips...
    // Some chips always report the opposite of the truth.
    private static class Liar implements Die {
        public boolean testChip(Die other) { return ! (other instanceof Good); }
    }
    // Some identify all chips as good, others identify all chips as bad.
    // (Both are an abomination. Proverbs 17:15.)
    private static class JustifyWicked implements Die {
        public boolean testChip(Die other) { return true; }
    }
    private static class CondemnRighteous implements Die {
        public boolean testChip(Die other) { return false; }
    }
    // Some act randomly
    public static class FlipCoin implements Die {
        public boolean testChip(Die other) { return randy.nextBoolean(); }
    }

    public static void printSet(Set<Chip> chips) {
        for (Chip c : chips) 
            System.out.print(c.core instanceof Good ? "G " : "B ");
        System.out.println("");
    }
    
    /**
     * Exception class to indicate that a chip has been used
     * after its goodness has been "revealed".
     */
    private static class BrokenChipException extends RuntimeException {
        private static final long serialVersionUID = -7146617927210957212L;
    }

    /**
     * The core of the chip, a die as defined above.
     */
    private Die core;

    /**
     * A tag for the chip, whether it has been identified as
     * good or bad (null if it has not been tagged yet).
     */
    private Boolean tag; 

    /**
     * If the chip has already been "revealed", then it cannot be used again.
     */
    private boolean broken;

    /**
     * The constructor is private. Use the public static factory methods.
     */
    private Chip(Die core) {
        this.core = core;
        tag = null;
        broken = false;
    }

    /**
     * Test another chip for goodness.
     * @param other The chip to be tested
     * @return true, if (this chip thinks) the other chip is good,
     * false otherwise.
     */
    public boolean test(Chip other) {
        if (broken) throw new BrokenChipException();
        else return core.testChip(other.core);
    }

    /**
     * Set this chip's tag.
     * @param tag true, if this chip has been found to be good, false
     * otherwise.
     */
    public void setTag(boolean tag) { 
        if (broken) throw new BrokenChipException();
        this.tag = tag; 
    }
    
    /**
     * Reveal whether this chip has been tagged correctly.
     * @return true, if this chip has been tagged and that tag
     * is correct; false if this chip is untagged or has an 
     * incorrect tag.
     */
    public boolean reveal() {
        if (broken) throw new BrokenChipException();
        broken = true;
        return tag != null && tag == (core instanceof Good);
    }

    // --- factory methods ---
    public static Chip makeGood() { return new Chip(new Good()); }
    public static Chip makeLiar() { return new Chip(new Liar()); }
    public static Chip makeJW() { return new Chip(new JustifyWicked()); }
    public static Chip makeCR() { return new Chip(new CondemnRighteous()); }
    // Note the difference between makeFC(), which makes a chip that
    // acts randomly, and makeRandom(), which randomly decides which
    // kind of chip to make.
    public static Chip makeFC() { return new Chip(new FlipCoin()); }
    public static Chip makeRandom() {
        switch(randy.nextInt(5)) {
        case 0: return new Chip(new Good());
        case 1: return new Chip(new Liar());
        case 2: return new Chip(new JustifyWicked());
        case 3: return new Chip(new CondemnRighteous());
        // there should be a "default assert" label;
        // 4 should be the only option at this point.
        case 4: default: return new Chip(new FlipCoin());
        }
    }
}
