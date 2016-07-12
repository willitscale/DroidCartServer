package uk.co.n3tw0rk.droidcart.tests.carts.repository;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.repository.MongoCartRepository;
import uk.co.n3tw0rk.droidcart.support.domain.Sequence;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoCartRepositoryTests {

    private static final Integer cartId = 132;
    private static final int limit = 10;
    private static final int offset = 0;

    @Mock
    private MongoTemplate mongoTemplateMock;
    @Mock
    private Cart cartMock;
    @Mock
    private Sequence sequenceMock;
    @InjectMocks
    private MongoCartRepository mongoCartRepository;
    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    private ArgumentCaptor<Query> queryArgumentCaptor;

    private List<Cart> cartList = Lists.newArrayList();

    @Before
    public void preFlight() {
        cartList.add(cartMock);
        when(mongoTemplateMock.find(
                any(Query.class),
                eq(Cart.class)
        )).thenReturn(cartList);
        when(cartMock.getId()).thenReturn(cartId);
    }

    @Test
    public void findAllTest() {
        List<Cart> returnedList = mongoCartRepository.findAll(limit, offset);
        verify(mongoTemplateMock).find(
                queryArgumentCaptor.capture(),
                eq(Cart.class)
        );
        Query queryArgument = queryArgumentCaptor.getValue();
        assertNotNull(queryArgument);
        assertThat(queryArgument.getLimit(), is(limit));
        assertThat(queryArgument.getSkip(), is(offset));
        assertThat(returnedList, is(cartList));
    }

}
