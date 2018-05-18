package com.enoxs.verify;

import com.enoxs.utillity.FileOperator;
import com.enoxs.utillity.SettingUtils;
import com.enoxs.utillity.TypeSettingUtils;
import org.apache.log4j.Logger;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class TestTypeSetting {
    private static Logger log = Logger.getLogger(TestTypeSetting.class);
    private static SettingUtils setup = new SettingUtils();
    private static FileOperator foe = new FileOperator();
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        setup.initLog4j();
        log.info("init -> TestTypeSetting.");
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
    TypeSettingUtils typeSet = new TypeSettingUtils();
    @Test
    public void testSplitMsgToArrayv1(){
        target = "A0 , A1 , A2 , A3 , A4 , A5 , A6 , A7 , A8 , A9 , \n" +
                "B1 , B2 , B3 , B4 , B5 , C1 , C2 , C3 , C4 , C5";
        source = foe.LoadData("src/assets/rawdata.txt");
        String [] tempArray = typeSet.splitMsgToArray(source," ");
        typeSet.setStringArrayFormat(10,false,false,true);
        result = typeSet.showStringArray(tempArray);
        showMsg();
        assertEquals(target,result);
    }
    public void showMsg(){
        System.out.println("\n === ==== ==== Show Message ==== ==== === \n");
        System.out.println("Source -> \n" + source + "\n");
        System.out.println("Target -> \n" + target + "\n");
        System.out.println("Result -> \n" + result + "\n");
    }
}
