package uk.co.n3tw0rk.droidcart.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.n3tw0rk.droidcart.products.repository.DataRepository;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by M00SEMARKTWO on 22/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml" })
public class DataRepositoryTest {

    @Autowired
    DataRepository dataRepository;

    @Test
    public void dataRepositoryNotNull() {
        assertNotNull(dataRepository);
    }

    @Test
    public void testDataRepository() {
        assertTrue(dataRepository.setCollection("Test"));
    }
}
