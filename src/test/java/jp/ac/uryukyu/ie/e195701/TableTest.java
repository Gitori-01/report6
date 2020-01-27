package jp.ac.uryukyu.ie.e195701;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void initHash() {
    }

    @Test
    void prepareTable() {
    }

    @Test
    void initChoices() {
        Player test = new com("test");
        test.hands.add(new Card(0,1));
        test.hands.add(new Card(1,1));
        test.hands.add(new Card(0,2));
        test.hands.add(new Card(2,3));
        test.hands.add(new Card(4,12));
        test.hands.add(new Card(4,12));
        Table testTable = new Table();
        testTable.initHash(test);
        testTable.setTableNum(3);
        testTable.initChoices();
        assertEquals(1, testTable.getChoices().get(0).size());
        assertEquals(2, testTable.getChoices().get(1).size());
    }

    @Test
    void isPass() {
    }

    @Test
    void selectDiscard() {
        Player test = new com("test");
        test.hands.add(new Card(0,1));
        test.hands.add(new Card(1,1));
        test.hands.add(new Card(0,2));
        test.hands.add(new Card(2,3));
        test.hands.add(new Card(4,12));

        Table testTable = new Table();
        testTable.initHash(test);
        testTable.setTableNum(3);
        testTable.initChoices();
        testTable.SelectDiscard(test);
        assertEquals(3, testTable.getLayout().size());
    }
}