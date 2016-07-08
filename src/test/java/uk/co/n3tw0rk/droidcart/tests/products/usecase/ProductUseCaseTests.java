package uk.co.n3tw0rk.droidcart.tests.products.usecase;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.domain.Sequence;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductRepository;
import uk.co.n3tw0rk.droidcart.products.usecase.ProductUseCase;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductUseCaseTests {

    private static final int productId = 123;
    private static final int limit = 10;
    private static final int offset = 0;

    @Mock
    private MongoProductRepository mongoProductRepositoryMock;
    @Mock
    private Product productMock;
    @Mock
    private Sequence sequenceMock;
    @InjectMocks
    private ProductUseCase productUseCase;

    private List<Product> productList = Lists.newArrayList();

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Before
    public void preFlight() {
        productList.add(productMock);
        when(mongoProductRepositoryMock.findAll(
                any(Integer.class),
                any(Integer.class)
        )).thenReturn(productList);
        when(mongoProductRepositoryMock.nextId())
                .thenReturn(sequenceMock);
        when(productMock.getId())
                .thenReturn(productId);
    }

    @Test
    public void findAllTest() {
        List<Product> returnedProductList = productUseCase.findAll(limit,offset);
        verify(mongoProductRepositoryMock).findAll(
                integerArgumentCaptor.capture(),
                integerArgumentCaptor.capture()
        );
        assertThat(returnedProductList, is(productList));
        List<Integer> arguments = integerArgumentCaptor.getAllValues();
        assertThat(arguments.get(0), is(limit));
        assertThat(arguments.get(1), is(offset));
    }

    @Test
    public void insertTest() {
        productUseCase.insert(productMock);
        verify(mongoProductRepositoryMock)
                .insert(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue(), is(productMock));
    }

    @Test
    public void insertResourceTest() throws URISyntaxException {
        URI returnedURI = productUseCase.insertResource(productMock);
        verify(mongoProductRepositoryMock)
                .insert(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue(), is(productMock));
        assertNotNull(returnedURI);
        assertThat(
                returnedURI.getPath(),
                is(Integer.toString(productMock.getId()))
        );
    }

    @Test
    public void deleteByIdValidTest() throws ProductDoesNotExistException {
        productUseCase.deleteById(productId);
        verify(mongoProductRepositoryMock)
                .deleteById(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue(), is(productId));
    }

    @Test(expected = ProductDoesNotExistException.class)
    public void deleteByIdInvalidTest() throws ProductDoesNotExistException {
        doThrow(new ProductDoesNotExistException())
                .when(mongoProductRepositoryMock)
                .deleteById(integerArgumentCaptor.capture());
        productUseCase.deleteById(productId);
        assertThat(integerArgumentCaptor.getValue(), is(productId));
    }

    @Test
    public void findByIdValidTest() throws ProductDoesNotExistException {
        when(mongoProductRepositoryMock.findById(any(Integer.class)))
                .thenReturn(productMock);
        Product returnedProduct = productUseCase.findById(productId);
        verify(mongoProductRepositoryMock)
                .findById(integerArgumentCaptor.capture());
        assertThat(returnedProduct, is(productMock));
        assertThat(integerArgumentCaptor.getValue(), is(productId));
    }

    @Test(expected = ProductDoesNotExistException.class)
    public void findByIdInvalidTest() throws ProductDoesNotExistException {
        when(mongoProductRepositoryMock.findById(any(Integer.class)))
                .thenThrow(new ProductDoesNotExistException())
                .thenReturn(null);
        Product returnedProduct = productUseCase.findById(productId);
        verify(mongoProductRepositoryMock)
                .findById(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue(), is(productId));
        assertNull(returnedProduct);
    }

    @Test
    public void patchValidTest()
            throws IllegalAccessException, ProductDoesNotExistException, InvocationTargetException {
        productUseCase.patch(
                productId,
                productMock
        );
        verify(mongoProductRepositoryMock).update(
                integerArgumentCaptor.capture(),
                productArgumentCaptor.capture()
        );
        assertThat(integerArgumentCaptor.getValue(), is(productId));
        assertThat(productArgumentCaptor.getValue(), is(productMock));
    }

    @Test(expected = ProductDoesNotExistException.class)
    public void patchInvalidTest()
            throws IllegalAccessException, ProductDoesNotExistException, InvocationTargetException {
        doThrow(new ProductDoesNotExistException())
                .when(mongoProductRepositoryMock)
                .update(
                        any(Integer.class),
                        any(Product.class)
                );
        productUseCase.patch(
                productId,
                productMock
        );
        verify(mongoProductRepositoryMock).update(
                integerArgumentCaptor.capture(),
                productArgumentCaptor.capture()
        );
        assertThat(integerArgumentCaptor.getValue(), is(productId));
        assertThat(productArgumentCaptor.getValue(), is(productMock));
    }

    @Test
    public void putTest() {
        productUseCase.put(
                productId,
                productMock
        );
        verify(productMock).setId(integerArgumentCaptor.capture());
        verify(mongoProductRepositoryMock).save(productArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue(), is(productId));
        assertThat(productArgumentCaptor.getValue(), is(productMock));
    }
}
