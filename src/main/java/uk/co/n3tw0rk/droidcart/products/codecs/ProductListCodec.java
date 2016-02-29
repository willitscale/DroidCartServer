package uk.co.n3tw0rk.droidcart.products.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.domain.ProductList;

import java.util.Map;

/**
 * Created by M00SEMARKTWO on 27/02/2016.
 */
public class ProductListCodec implements Codec<ProductList> {

    /** */
    private final CodecRegistry codecRegistry;

    /** */
    private final ProductCodec productCodec;

    /**
     *
     * @param codecRegistry
     */
    public ProductListCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
        productCodec = new ProductCodec(codecRegistry);
    }

    /**
     *
     * @param bsonReader
     * @param decoderContext
     * @return
     */
    public ProductList decode(BsonReader bsonReader, DecoderContext decoderContext) {

        ProductList productList = new ProductList();

        bsonReader.readStartDocument();

        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            Product product = productCodec.decode(bsonReader, decoderContext);
            productList.put(bsonReader.readName(), product);
        }

        bsonReader.readEndDocument();

        return productList;
    }

    /**
     *
     * @param bsonWriter
     * @param productList
     * @param encoderContext
     */
    public void encode(BsonWriter bsonWriter, ProductList productList, EncoderContext encoderContext) {

        bsonWriter.writeStartDocument();

        if (null != productList && null != productList.getProducts()) {
            for(Map.Entry<String,Product> entry : productList.getProducts().entrySet()) {
                bsonWriter.writeName(entry.getKey());
                productCodec.encode(bsonWriter, entry.getValue(), encoderContext);
            }
        }

        bsonWriter.writeEndDocument();
    }

    /**
     *
     * @return
     */
    public Class<ProductList> getEncoderClass() {
        return ProductList.class;
    }
}
