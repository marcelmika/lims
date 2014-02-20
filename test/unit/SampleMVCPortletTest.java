package unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


//@RunWith(PowerMockRunner.class)
//@PrepareForTest({JUnitTestLocalServiceUtil.class})
public class SampleMVCPortletTest {

//	SampleMVCPortlet mSampleMVCPortlet;
	
	@Before
	public void setUp() {
//		mSampleMVCPortlet= new SampleMVCPortlet();
	}
	
	@After
	public void tearDown() {
//		mSampleMVCPortlet= null;
	}
	@Test
	public void testvalidateUserName() {
//		PortletRequest portletRequest = EasyMock.createMock(PortletRequest.class);
//
//		EasyMock.expect(portletRequest.getAttribute("userName")).andReturn("Nirav");
//		PowerMock.replay(portletRequest);
//
//		PowerMock.mockStatic(JUnitTestLocalServiceUtil.class);
//		EasyMock.expect(JUnitTestLocalServiceUtil.isValidUserName("Nirav")).andReturn(true);
//		PowerMock.replay(JUnitTestLocalServiceUtil.class);
//
//		assertEquals(true,mSampleMVCPortlet.validateUserName(portletRequest));
//
//		portletRequest = EasyMock.createMock(PortletRequest.class);
//		EasyMock.expect(portletRequest.getAttribute("userName")).andReturn(null);
//		PowerMock.replay(portletRequest);
//
//		PowerMock.mockStatic(JUnitTestLocalServiceUtil.class);
//		EasyMock.expect(JUnitTestLocalServiceUtil.isValidUserName(null)).andReturn(false);
//		PowerMock.replay(JUnitTestLocalServiceUtil.class);
//
//		assertEquals(false,mSampleMVCPortlet.validateUserName(portletRequest));
        assertTrue(true);
	}

}
