package ee.soidutaja.soidutaja;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock ;

/**
 * Created by kmm on 18.11.2015.
 */
public class SelectRoleActivityTest extends ActivityInstrumentationTestCase2<SelectRoleActivity> {

    public SelectRoleActivity selectRoleActivity;
    public SelectRoleActivityTest() {
        super(SelectRoleActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        selectRoleActivity = getActivity();
    }

    @Test
    public void testApp() throws Exception {
        assertNotNull(selectRoleActivity);
    }

    @Test
    public void testOnCreate() throws Exception {
        assertNotNull(R.id.driverButton);
        assertNotNull(R.id.passengerButton);
        assertNotNull(R.id.profileButton);
        assertNotNull(R.id.progressBar);
        assertNotNull(R.layout.activity_first);
    }

    @Test
    public void testRequestData() throws Exception {

    }

    @Test
    public void testIsLoggedIn() throws Exception {

    }

    @Test
    public void testIsOnline() throws Exception {

    }

    @Test
    public void testOnResume() throws Exception {

    }

    @Test
    public void testOnActivityResult() throws Exception {

    }

    @Test
    public void testAddButton() throws Exception {

    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        selectRoleActivity.finish();
        super.tearDown();
    }
}