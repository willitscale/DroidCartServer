package uk.co.n3tw0rk.droidcart.products.repository;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.support.domain.Sequence;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MongoProductRepositoryTests {

    private static final Integer PRODUCT_ID = 123;
    private static final int LIMIT = 10;
    private static final int OFFSET = 0;

    @Mock
    private MongoTemplate mongoTemplateMock;
    @Mock
    private Product productMock;
    @Mock
    private Sequence sequenceMock;
    @InjectMocks
    private MongoProductRepository mongoProductRepository;
    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    @Captor
    private ArgumentCaptor<Query> queryArgumentCaptor;

    private List<Product> productList = Lists.newArrayList();

    @Before
    public void preFlight() {
        productList.add(productMock);
        when(mongoTemplateMock.find(
                any(Query.class),
                eq(Product.class)
        )).thenReturn(productList);
    }

    @Test
    public void findByIdValidTest() throws ProductDoesNotExistException {
        when(mongoTemplateMock.findById(
                PRODUCT_ID,
                Product.class
        )).thenReturn(productMock);
        Product returnedProduct = mongoProductRepository.findById(PRODUCT_ID);
        verify(mongoTemplateMock)
                .findById(
                        PRODUCT_ID,
                        Product.class
                );
        assertThat(returnedProduct, is(productMock));
    }

    @Test(expected = ProductDoesNotExistException.class)
    public void findByIdInvalidTest() throws ProductDoesNotExistException {
        when(mongoTemplateMock.findById(
                any(Integer.class),
                eq(Product.class)
        )).thenReturn(null);

        Product returnedProduct = mongoProductRepository.findById(PRODUCT_ID);
        verify(mongoTemplateMock)
                .findById(
                        integerArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Integer argument = integerArgumentCaptor.getValue();
        assertNull(returnedProduct);
        assertThat(argument, is(PRODUCT_ID));
    }

    @Test
    public void saveTest() {
        mongoProductRepository.insert(productMock);
        verify(mongoTemplateMock)
                .insert(productArgumentCaptor.capture());
        Product argument = productArgumentCaptor.getValue();
        assertThat(argument, is(productMock));
    }

    @Test
    public void deleteByIdValidTest() throws ProductDoesNotExistException {
        doReturn(productList)
                .when(mongoTemplateMock)
                .findAllAndRemove(
                        any(Query.class),
                        eq(Product.class)
                );
        mongoProductRepository.deleteById(PRODUCT_ID);
        verify(mongoTemplateMock)
                .findAllAndRemove(
                        queryArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Query query = queryArgumentCaptor.getValue();
        assertThat((Integer) query.getQueryObject().get("_id"), is(PRODUCT_ID));
    }

    @Test(expected = ProductDoesNotExistException.class)
    public void deleteByIDInvalidTesst() throws ProductDoesNotExistException {
        List<Product> emptyList = Lists.newArrayList();
        when(mongoTemplateMock.findAllAndRemove(
                any(Query.class),
                eq(Product.class)
        )).thenReturn(emptyList);
        mongoProductRepository.deleteById(PRODUCT_ID);
        verify(mongoTemplateMock)
                .findAllAndRemove(
                        queryArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Query query = queryArgumentCaptor.getValue();
        assertThat((Integer) query.getQueryObject().get("_id"), is(PRODUCT_ID));
    }

    @Test
    public void findAllTest() {
        List<Product> returned = mongoProductRepository.findAll(LIMIT, OFFSET);
        verify(mongoTemplateMock)
                .find(
                        queryArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Query argument = queryArgumentCaptor.getValue();
        assertThat(returned, is(productList));
        assertThat(argument.getLimit(), is(LIMIT));
        assertThat((int) argument.getSkip(), is(OFFSET));
    }

    @Test
    public void nextIdExistsTest() {
        when(mongoTemplateMock.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(Sequence.class)
        )).thenReturn(sequenceMock);
        Sequence returned = mongoProductRepository.nextId();
        verify(mongoTemplateMock)
                .findAndModify(
                        queryArgumentCaptor.capture(),
                        any(Update.class),
                        any(FindAndModifyOptions.class),
                        eq(Sequence.class)
                );
        verify(mongoTemplateMock, never())
                .insert(any(Sequence.class));
        Query argument = queryArgumentCaptor.getValue();
        assertThat(returned, is(sequenceMock));
        assertThat(
                argument.getQueryObject().get("_id"),
                is(MongoProductRepository.COLLECTION)
        );
    }

    @Test
    public void nextIdDoesNotExistTest() {
        Sequence returned = mongoProductRepository.nextId();
        verify(mongoTemplateMock)
                .findAndModify(
                        queryArgumentCaptor.capture(),
                        any(Update.class),
                        any(FindAndModifyOptions.class),
                        eq(Sequence.class)
                );
        verify(mongoTemplateMock)
                .insert(any(Sequence.class));
        Query argument = queryArgumentCaptor.getValue();
        assertThat(returned, not(sequenceMock));
        assertThat(
                argument.getQueryObject().get("_id"),
                is(MongoProductRepository.COLLECTION)
        );
        assertThat(
                returned.getCollection(),
                is(MongoProductRepository.COLLECTION)
        );
    }
}
