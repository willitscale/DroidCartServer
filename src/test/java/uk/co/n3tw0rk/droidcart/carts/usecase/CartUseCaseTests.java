package uk.co.n3tw0rk.droidcart.carts.usecase;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.carts.repository.MongoCartRepository;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductInstanceRepository;
import uk.co.n3tw0rk.droidcart.support.domain.Sequence;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartUseCaseTests {

    private static final int CART_ID = 123;
    private static final int LIMIT = 10;
    private static final int OFFSET = 0;

    @Mock
    private MongoCartRepository mongoCartRepositoryMock;
    @Mock
    private MongoProductInstanceRepository mongoProductInstanceRepositoryMock;
    @Mock
    private Cart cartMock;
    @Mock
    private Sequence sequenceMock;
    @InjectMocks
    private CartUseCase cartUseCase;

    private List<Cart> cartList = Lists.newArrayList();

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;

    @Before
    public void preFlight() {
        cartList.add(cartMock);
        when(mongoCartRepositoryMock.findAll(
                any(Integer.class),
                any(Integer.class)
        )).thenReturn(cartList);
        when(mongoCartRepositoryMock.nextId())
                .thenReturn(sequenceMock);
        when(cartMock.getId())
                .thenReturn(CART_ID);
    }

    @Test
    public void findAllTests() {
        List<Cart> returnedCartLists = cartUseCase.findAll(LIMIT, OFFSET);
        verify(mongoCartRepositoryMock).findAll(
                integerArgumentCaptor.capture(),
                integerArgumentCaptor.capture()
        );
        assertThat(returnedCartLists, is(cartList));
        List<Integer> arguments = integerArgumentCaptor.getAllValues();
        assertThat(arguments.get(0), is(LIMIT));
        assertThat(arguments.get(1), is(OFFSET));
    }

    @Test
    public void insertTest() {
        cartUseCase.insert(cartMock);
        verify(mongoCartRepositoryMock)
                .insert(cartArgumentCaptor.capture());
        Cart argument = cartArgumentCaptor.getValue();
        assertThat(argument, is(cartMock));
    }

    @Test
    public void insertResourceTest() throws URISyntaxException {
        URI returnedURI = cartUseCase.insertResource(cartMock);
        verify(mongoCartRepositoryMock)
                .insert(cartArgumentCaptor.capture());
        Cart argument = cartArgumentCaptor.getValue();
        assertThat(argument, is(cartMock));
        assertNotNull(returnedURI);
        assertThat(
                returnedURI.getPath(),
                is(Integer.toString(cartMock.getId()))
        );
    }

    @Test
    public void deleteByIdValidTest() throws CartDoesNotExistException {
        cartUseCase.deleteById(CART_ID);
        verify(mongoCartRepositoryMock)
                .deleteById(integerArgumentCaptor.capture());
        Integer argument = integerArgumentCaptor.getValue();
        assertThat(argument, is(CART_ID));
    }

    @Test(expected = CartDoesNotExistException.class)
    public void deleteByIdInvalidTest() throws CartDoesNotExistException {
        doThrow(new CartDoesNotExistException())
                .when(mongoCartRepositoryMock)
                .deleteById(integerArgumentCaptor.capture());
        cartUseCase.deleteById(CART_ID);
        Integer argument = integerArgumentCaptor.getValue();
        assertThat(argument, is(CART_ID));

    }

    @Test
    public void findByIdValidTest() throws CartDoesNotExistException {
        when(mongoCartRepositoryMock.findById(any(Integer.class)))
                .thenReturn(cartMock);
        Cart returnedCart = cartUseCase.findById(CART_ID);
        verify(mongoCartRepositoryMock)
                .findById(integerArgumentCaptor.capture());
        Integer argument = integerArgumentCaptor.getValue();
        assertThat(returnedCart.getId(), is(cartMock.getId()));
        assertThat(returnedCart.getUserId(), is(cartMock.getUserId()));
        assertThat(argument, is(CART_ID));
    }

    @Test(expected = CartDoesNotExistException.class)
    public void findByIDInvalidTest() throws CartDoesNotExistException {
        when(mongoCartRepositoryMock.findById(any(Integer.class)))
                .thenThrow(new CartDoesNotExistException())
                .thenReturn(null);
        Cart returnedCart = cartUseCase.findById(CART_ID);
        verify(mongoCartRepositoryMock)
                .findById(integerArgumentCaptor.capture());
        Integer argument = integerArgumentCaptor.getValue();
        assertThat(returnedCart, is(cartMock));
        assertThat(argument, is(CART_ID));
    }
}
