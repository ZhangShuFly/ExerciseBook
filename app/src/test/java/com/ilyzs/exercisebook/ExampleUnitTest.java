package com.ilyzs.exercisebook;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private static volatile ExampleUnitTest  test = null;

    private ExampleUnitTest(){

    }

    public  ExampleUnitTest getInstance(){
        if(null!=test){
            synchronized(this){
                if(null==test){
                    test = new ExampleUnitTest();
                }
            }
        }
        return test;
    }
}