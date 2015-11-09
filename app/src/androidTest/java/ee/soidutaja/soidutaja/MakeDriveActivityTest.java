//package ee.soidutaja.soidutaja;
//
//import android.app.Application;
//import android.os.HandlerThread;
//import android.os.Looper;
//import android.os.Message;
//import android.test.ApplicationTestCase;
//
//import org.junit.Before;
//import org.junit.Test;
//
//
//import java.util.Iterator;
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//import static org.junit.Assert.*;
//
//
///**
// * Created by rehes_000 on 08.11.2015.
// */
//public class MakeDriveActivityTest extends ApplicationTestCase<Application> {
//
//    private MakeDriveActivity makedrive;
//
//    public MakeDriveActivityTest() {
//        super(Application.class);
//    }
//
//    HandlerThread handlerThread = new HandlerThread("name");
//
//    handlerThread.start();
//
//    Handler threadHandler = new Handler(handlerThread.getLooper(),new Callback() { public boolean handleMessage(Message msg) {
//
//        return true;
//    }
//    });
//
//
//    @Before
//    public void setUp() throws Exception {
//        makedrive = new MakeDriveActivity();
//    }
//
//    @Test
//    public void testOnCreate() throws Exception {
//
//    }
//
//    @Test
//    public void testPushDrive() throws Exception {
//
//    }
//
//    public class DummyPrinterService extends MakeDriveActivity {
//        @Override
//        public boolean areSpinnersValid() {
//            return true;
//        }
//        @Override
//        public boolean isTimeValid() {
//            return true;
//        }
//        @Override
//        public boolean isDateValid() {
//            return true;
//        }
//        @Override
//        public boolean isPriceValid() {
//            return true;
//        }
//        @Override
//        public boolean isSpotsValid() {
//            return true;
//        }
//        @Override
//        public boolean isNameValid() {
//            return true;
//        }
//    }
//    @Test
//    public void testIsInputValid() throws Exception {
//        assertNotNull(makedrive.isInputValid());
//
//    }
//
//    @Test
//    public void testAreSpinnersValid() throws Exception {
//
//    }
//
//    @Test
//    public void testIsTimeValid() throws Exception {
//
//    }
//
//    @Test
//    public void testIsDateValid() throws Exception {
//
//    }
//
//    @Test
//    public void testIsPriceValid() throws Exception {
//
//    }
//
//    @Test
//    public void testIsSpotsValid() throws Exception {
//
//    }
//
//    @Test
//    public void testIsNameValid() throws Exception {
//
//    }
//
//    @Test
//    public void testShowTimePickerDialog() throws Exception {
//
//    }
//
//    @Test
//    public void testShowDatePickerDialog() throws Exception {
//
//    }
//
//    @Test
//    public void testOnCreateOptionsMenu() throws Exception {
//
//    }
//
//    @Test
//    public void testOnOptionsItemSelected() throws Exception {
//
//    }
//}