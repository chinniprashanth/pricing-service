
package com.sapient.coc.application.pricingservice.actuator;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sapient.coc.application.pricingservice.service.impl.PricingServiceImpl;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class HealthIndicatorTest {

	@Mock
	PricingServiceImpl pricingServiceImpl;

	@InjectMocks
	private HealthIndicator healthIndicator = new HealthIndicator();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void test1_healthUp() {
		when(pricingServiceImpl.count()).thenReturn(new Long(1));

		try {

			Assert.assertEquals(healthIndicator.health().toString(), "UP {count=1}");
		} catch (Exception e) {
			Assert.fail("Health Indicator failed");
		}
	}

	@Test
	void test2_healthDown() {
		when(pricingServiceImpl.count()).thenReturn(new Long(0));

		try {

			Assert.assertEquals(healthIndicator.health().toString(), "DOWN {count=0}");
		} catch (Exception e) {
			Assert.fail("Health Indicator failed");
		}
	}

}
