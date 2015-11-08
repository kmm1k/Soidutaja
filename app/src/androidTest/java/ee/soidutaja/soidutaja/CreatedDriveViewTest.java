package ee.soidutaja.soidutaja;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.view.Menu;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by KristiP on 8.11.2015.
 */
public class CreatedDriveViewTest extends ApplicationTestCase<Application> {

    private CreatedDriveView driveView;
    private Menu menu;

    public CreatedDriveViewTest() {
        super(Application.class);
    }
    @Before
    public void setUp(){
        driveView= new CreatedDriveView();
    }
    @Test
    public void testOnCreatedOptionsMenu(){
        assertTrue(driveView.onCreateOptionsMenu(menu));
    }


}
