package com.enoxs.verify;

import com.enoxs.utillity.SettingUtils;
import com.enoxs.utillity.VerifyUtils;
import org.apache.log4j.Logger;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class TestVerifyUtils {
    private static Logger log = Logger.getLogger(TestCalculator.class);
    private static SettingUtils setup = new SettingUtils();
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        setup.initLog4j();
        log.info("init -> TestVerifyUtils.");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    String target,source,result;
    VerifyUtils chk = new VerifyUtils();

    /**
     * ASCII:"123456789"
     * 1 byte checksum	221
     * CRC-16	0xBB3D
     * CRC-16 (Modbus)	0x4B37
     * CRC-16 (Sick)	0x56A6
     * CRC-CCITT (XModem)	0x31C3
     * CRC-CCITT (0xFFFF)	0x29B1
     * CRC-CCITT (0x1D0F)	0xE5CC
     * CRC-CCITT (Kermit)	0x8921
     * CRC-DNP	0x82EA
     * CRC-32	0xCBF43926
     */
    @Test
    public void testCRCv1(){
        target = 221 + "";
        source = chk.cal.ByteToHexString("123456789".getBytes());
        result = chk.crcGetByte(source) + "";
        showMsg();
        assertEquals(target,result);
    }
    public void showMsg(){
        System.out.println("Target -> " + target);
        System.out.println("Source -> " + source);
        System.out.println("Result -> " + result);
    }
}
