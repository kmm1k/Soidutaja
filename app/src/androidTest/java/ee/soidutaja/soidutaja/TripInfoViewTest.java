package ee.soidutaja.soidutaja;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by rehes_000 on 08.11.2015.
 */
public class TripInfoViewTest extends ApplicationTestCase<Application> {


    public TripInfoViewTest() {
        super(Application.class);
    }

    /*@Test
    public void doInBackgroundTest(){
        String testString = "lammas";
        assertEquals(testString,tripInfoView.TakeSlot.doInBackground(testString));
    }*/
    @Test
    public void doTest(){
        TripInfoView tripInfoView = new TripInfoView();
        //assertEquals(true,tripInfoView.done());
    }
}
