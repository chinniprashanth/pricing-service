package com.sapient.coc.application.pricingservice.cache;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.sapient.coc.application.pricingservice.bo.vo.AddressVO;
import com.sapient.coc.application.pricingservice.bo.vo.Cart;

public class CacheDaoImplTest {


	private static String orderId = "22e78960-bdf4-11e9-a34b-6bcec6d1fa67";

    @Mock
    private RedisTemplate redisTemplate;

    @InjectMocks
    CacheDaoImpl redisDao = new CacheDaoImpl(redisTemplate);

    @Spy
    private HashOperations hashOperations;

	private static final String ADDRESS_KEY = "CoC-Shipping-Addr";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        List<Cart> cartItems = new ArrayList<>();
		AddressVO addressVO = new AddressVO();
		addressVO.setZipcode("12345");
		doNothing().when(hashOperations).put(ADDRESS_KEY + orderId, "methods", addressVO);
        try {
			boolean flag = redisDao.save(ADDRESS_KEY + orderId, addressVO);
			verify(hashOperations, times(1)).put(ADDRESS_KEY + orderId, "methods", addressVO);
            Assert.assertEquals(true, flag);
        } catch (Exception e) {
            Assert.fail("Save to cache failed");
        }
    }

    @Test
	void findAddressById() {
		Map<String, AddressVO> entries = new HashMap<>();
		AddressVO addressVO = new AddressVO();
		addressVO.setZipcode("12345");
		entries.put(ADDRESS_KEY + orderId, addressVO);
		when(hashOperations.entries(ADDRESS_KEY + orderId)).thenReturn(entries);

        try {
			Map<String, AddressVO> cachedData = redisDao.findAddressById(ADDRESS_KEY + orderId);
            Assert.assertEquals(cachedData.size(), entries.size());
        } catch (Exception e) {
            Assert.fail("Save to cache failed");
        }
    }

    @Test
    void remove() {
		AddressVO addressVO = new AddressVO();
		addressVO.setZipcode("12345");
		Map<String, AddressVO> entries = new HashMap<>();
		entries.put(ADDRESS_KEY + orderId, addressVO);
		when(hashOperations.delete(ADDRESS_KEY + orderId)).thenReturn(1L);

        try {
			redisDao.remove(ADDRESS_KEY + orderId);
        } catch (Exception e) {
            Assert.fail("Save to cache failed");
        }
    }


    @Test
    void test_when_save_fails() {
		AddressVO addressVO = new AddressVO();
		addressVO.setZipcode("12345");
		doThrow(IllegalStateException.class).when(hashOperations).put(ADDRESS_KEY + orderId, "methods", addressVO);
        try {
			boolean result = redisDao.save(ADDRESS_KEY + orderId, addressVO);
            if (result) {
                Assert.fail("Shouldn't have been called");
            } else {
                Assert.assertTrue(result == false);
            }
        } catch (Exception e) {
			Assert.assertTrue(e.getMessage() == null);
			// Assert.fail("Save to cache failed");
        }
    }

    @Test
	void test_when_address_is_empty() {
		AddressVO addressVO = new AddressVO();
        try {
			boolean result = redisDao.save(ADDRESS_KEY + orderId, addressVO);
            if (result) {
                Assert.fail("Shouldn't have been called");
            } else {
                Assert.assertTrue(result == false);
            }
        } catch (Exception e) {

            Assert.fail("Save to cache failed");
        }
    }


    @Test
	void test_failure_findAddressById_entrynotfound() {
		when(hashOperations.entries(ADDRESS_KEY + orderId)).thenReturn(null);
        try {
			Map<String, AddressVO> cachedData = redisDao.findAddressById(ADDRESS_KEY + orderId);
            Assert.assertNull(cachedData);
        } catch (Exception e) {
			Assert.assertTrue(e.getMessage().equalsIgnoreCase(""));
            Assert.fail("Save to cache failed");
        }
    }

    @Test
    void test_failure_findbyId_exception_occurred() {
        try {
			doThrow(IllegalStateException.class).when(hashOperations).entries(ADDRESS_KEY + orderId);
			redisDao.findAddressById(ADDRESS_KEY + orderId);
        } catch (Exception e) {
			Assert.assertTrue(e.getMessage() == null);
			// Assert.assertTrue(e.getMessage().equalsIgnoreCase(""));
			// Assert.fail("Exception not handled correctly in case of Fetch from Redis");
        }
    }
}