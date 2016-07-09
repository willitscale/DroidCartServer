package uk.co.n3tw0rk.droidcart.tests.products.repository;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.support.domain.Sequence;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductRepository;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MongoProductRepositoryTests {

    private static final Integer productId = 123;
    private static final int limit = 10;
    private static final int offset = 0;

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
                productId,
                Product.class
        )).thenReturn(productMock);
        Product returnedProduct = mongoProductRepository.findById(productId);
        verify(mongoTemplateMock)
                .findById(
                        productId,
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

        Product returnedProduct = mongoProductRepository.findById(productId);
        verify(mongoTemplateMock)
                .findById(
                        integerArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Integer argument = integerArgumentCaptor.getValue();
        assertNull(returnedProduct);
        assertThat(argument, is(productId));
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
        mongoProductRepository.deleteById(productId);
        verify(mongoTemplateMock)
                .findAllAndRemove(
                        queryArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Query query = queryArgumentCaptor.getValue();
        assertThat((Integer)query.getQueryObject().get("_id"), is(productId));
    }

    @Test(expected = ProductDoesNotExistException.class)
    public void deleteByIDInvalidTesst() throws ProductDoesNotExistException {
        List<Product> emptyList = Lists.newArrayList();
        when(mongoTemplateMock.findAllAndRemove(
                any(Query.class),
                eq(Product.class)
        )).thenReturn(emptyList);
        mongoProductRepository.deleteById(productId);
        verify(mongoTemplateMock)
                .findAllAndRemove(
                        queryArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Query query = queryArgumentCaptor.getValue();
        assertThat((Integer)query.getQueryObject().get("_id"), is(productId));
    }

    @Test
    public void findAllTest() {
        List<Product> returned = mongoProductRepository.findAll(limit, offset);
        verify(mongoTemplateMock)
                .find(
                        queryArgumentCaptor.capture(),
                        eq(Product.class)
                );
        Query argument = queryArgumentCaptor.getValue();
        assertThat(returned, is(productList));
        assertThat(argument.getLimit(), is(limit));
        assertThat(argument.getSkip(), is(offset));
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
                (String) argument.getQueryObject().get("_id"),
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
                (String) argument.getQueryObject().get("_id"),
                is(MongoProductRepository.COLLECTION)
        );
        assertThat(
                returned.getCollection(),
                is(MongoProductRepository.COLLECTION)
        );
    }
}
