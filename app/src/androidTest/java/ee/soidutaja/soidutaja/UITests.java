package ee.soidutaja.soidutaja;

import android.content.DialogInterface;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.CheckReturnValue;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;



public class UITests {

    @Rule
    public IntentsTestRule<SelectRoleActivity> mActivityRule = new IntentsTestRule<>(SelectRoleActivity.class);

    @Test
    public void testViewPassenger() {
        onView(withId(R.id.passengerButton)).check(matches(notNullValue()));
        onView(withId(R.id.passengerButton)).perform(click());

        onView(withId(R.id.startSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Parnu"))).perform(click());

        onView(withId(R.id.endSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Turi"))).perform(click());

        onView(withId(R.id.nextButton)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        onView(withId(R.id.takeSpaceInCar)).perform(click());
//        onView(withId(R.id.freeSpacesTitle)).check(matches(notNullValue()));

        onView(withText("Cancel")).perform(click());
        onView(withId(R.id.takeSpaceInCar)).perform(click());
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testMakeDrive() {
        onView(withId(R.id.driverButton)).check(matches(notNullValue()));
        onView(withId(R.id.driverButton)).perform(click());

        onView(withId(R.id.chooseStart)).check(matches(notNullValue()));

        onView(withId(R.id.startSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Parnu"))).perform(click());

        onView(withId(R.id.endSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Parnu"))).perform(click());

        onView(withId(R.id.nextButton)).perform(click());

        onView(withId(R.id.priceField)).perform(typeText("5"));
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.spotsField)).perform(typeText("5"));
        onView(withId(R.id.nextButton)).perform(click());

        onView(withId(R.id.additionalInfo)).perform(typeText("lisainfo"));

        onView(withId(R.id.dateButton)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.nextButton)).perform(click());

        onView(withId(R.id.timeButton)).perform(click());
        //onView(withText("5")).perform(click());
        //onView(withText("20")).perform(click());
        onView(withText("OK")).perform(click());
        ViewActions.closeSoftKeyboard();
        onView(withId(R.id.name)).perform(typeText("LAMMAS"));
        ViewActions.closeSoftKeyboard();
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.endSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Turi"))).perform(click());
        onView(withId(R.id.nextButton)).perform(click());
        onView(withText("OK")).perform(click());
    }
    @Test
    public void testIfUserRetardCantSelectCorrectThings(){
        onView(withId(R.id.driverButton)).check(matches(notNullValue()));
        onView(withId(R.id.profileButton)).perform(click());

    }
    @Test
    public void facebookLogInTest(){
        onView(withId(R.id.login_button)).perform(click());
    }
}
