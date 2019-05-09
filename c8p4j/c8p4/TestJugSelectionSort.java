package c8p4;

import c8p4.Jug.Blue;
import c8p4.Jug.Red;

public class TestJugSelectionSort extends TestJugSort {

    protected void sort(Red[] reds, Blue[] blues) {
        JugSort.jugSelectionSort(reds, blues);
    }

}
