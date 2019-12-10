package com.capgemini.tdd.suits;


import com.capgemini.tdd.services.game.GameServiceMockTest;
import com.capgemini.tdd.services.game.MoveServiceMockTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GameServiceMockTest.class, MoveServiceMockTest.class })
public class MockTestsSuite
{

}
