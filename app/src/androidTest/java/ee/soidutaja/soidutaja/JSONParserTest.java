package ee.soidutaja.soidutaja;

import android.content.SyncStatusObserver;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Virx on 8.11.2015.
 */
public class JSONParserTest {

    @Test
    public void testParseDriveObjects() throws Exception {
        Drive drive = new Drive();
        List<Drive> driv = JSONParser.parseDriveObjects("{\"origin\":\"Tallinn\",\"destination\":\"Tartu\",\"creator\":\"lammas3\",\"price\":\"5 rubels\",\"start_time\":\"2012-06-30 23:59\",\"open_slots\":\"4\",\"id\":\"5\"}");
        assertNotNull(driv);
    }

    @Test
    public void testParseLocations() throws Exception {
        assertEquals("Tartu", "Tartu");
        assertNull(JSONParser.parseLocations('{' + '"' + "location" + '"' + ':' + '"' + "Tartu" + '"' + '}'));
    }
}