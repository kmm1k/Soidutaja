package ee.soidutaja.soidutaja;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;

import ee.soidutaja.soidutaja.Drive;

/**
 * Created by KristiP on 5.11.2015.
 */
public class DriveTest extends ApplicationTestCase<Application> {

    private Drive drive;

    public DriveTest() {
        super(Application.class);

    }
    @Before
    public void setUp() {
       drive = new Drive();
        drive.setUser("kek");
        drive.setInfo("tere");
        drive.setOrigin("Tallinn");
        drive.setDestination("Tartu");
        drive.setDateTime("31.10");
        drive.setId("mina");
        drive.setPrice("5");
        drive.setAvailableSlots(4);
    }
    @Test
    public void testUser(){
        assertEquals("kek", drive.getUser());
    }
    @Test
    public void testInfo(){
        assertEquals("tere", drive.getInfo());
    }
    @Test
    public void testOrigin(){
        assertEquals("Tallinn",drive.getOrigin());
    }
    @Test
    public void testDestination(){

        assertEquals("Tartu", drive.getDestination());
    }
    @Test
    public void testDateTime(){

        assertEquals("31.10",drive.getDateTime());
    }
    @Test
    public void testId(){

        assertEquals("mina",drive.getId());
    }
    @Test
    public void testPrice(){

        assertEquals("5",drive.getPrice());
    }
    @Test
    public void testAvailableSlots(){

        assertEquals(4,drive.getAvailableSlots());
    }
    @Test
    public void testToString(){
        String toString=drive.toString();
        assertTrue(toString.contains("user='" + drive.getUser()));
        assertTrue(toString.contains(", origin='" +drive.getOrigin()));
        assertTrue(toString.contains(", destination='" + drive.getDestination()));
        assertTrue(toString.contains(", dateTime='" + drive.getDateTime() ));
        assertTrue(toString.contains(", price='" + drive.getPrice()));
        assertTrue(toString.contains(", info='" + drive.getInfo()));
        assertTrue(toString.contains(", availableSlots=" + drive.getAvailableSlots()));
    }
}
