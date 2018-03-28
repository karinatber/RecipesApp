package com.android.project3.recipesapp;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

<<<<<<< HEAD
=======
import com.google.android.exoplayer2.util.Assertions;

import org.junit.Assert;
>>>>>>> 3248a60... Started implementing test
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
=======
import java.util.regex.Matcher;

import static org.junit.Assert.*;
>>>>>>> 3248a60... Started implementing test

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
<<<<<<< HEAD
    private Context mAppContext;

    @Before
    public void setConfig(){
        mAppContext = InstrumentationRegistry.getTargetContext();

=======
    Context mAppContext;
    Instrumentation mInstrumentation;

    @Before
    public void setUpTest(){
        mAppContext = InstrumentationRegistry.getTargetContext();
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
>>>>>>> 3248a60... Started implementing test
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.project3.recipesapp", appContext.getPackageName());
    }

    @Test
<<<<<<< HEAD
    public void clickRecipeTest(){

    }

=======
    public void doAllTests(){

    }

    @Test
    public void clickOnRecipeItemAndCheckDetailsTest(){
        Espresso.onData(ViewMatchers.withId(R.id.rv_recipes_list)).atPosition(0).perform(ViewActions.click());
    }

>>>>>>> 3248a60... Started implementing test
}
