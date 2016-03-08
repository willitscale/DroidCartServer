package uk.co.n3tw0rk.droidcart.products.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import uk.co.n3tw0rk.droidcart.products.domain.Dimension;
import uk.co.n3tw0rk.droidcart.products.domain.Product;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by M00SEMARKTWO on 14/02/2016.
 */
public class ProductCodec implements Codec<Product> {

    /** */
    private final CodecRegistry codecRegistry;

    /**
     *
     * @param codecRegistry
     */
    public ProductCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    /**
     *
     * @param bsonReader
     * @param decoderContext
     * @return
     */
    public Product decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();

        int id = bsonReader.readInt32("_id");

        String name = bsonReader.readString("name");

        String description = bsonReader.readString("description");

        String image = bsonReader.readString("image");

        double price = bsonReader.readDouble("price");

        Codec<Document> historyCodec = codecRegistry.get(Document.class);
        List<Dimension> dimensions = new LinkedList<Dimension>();

        bsonReader.readStartArray();

        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            dimensions.add((Dimension)historyCodec.decode(bsonReader, decoderContext));
        }

        bsonReader.readEndArray();
        bsonReader.readEndDocument();

        Product product = new Product(id, name, description, image, price);
        product.setDimensions(dimensions);

        return product;
    }

    /**
     *
     * @param bsonWriter
     * @param product
     * @param encoderContext
     */
    public void encode(BsonWriter bsonWriter, Product product, EncoderContext encoderContext) {

        bsonWriter.writeStartDocument();

        if (0 < product.getId()) {
            bsonWriter.writeName("_id");
            bsonWriter.writeInt32(product.getId());
        }

        if (null != product.getName()) {
            bsonWriter.writeName("name");
            bsonWriter.writeString(product.getName());
        }

        if (null != product.getDescription()) {
            bsonWriter.writeName("description");
            bsonWriter.writeString(product.getDescription());
        }

        if (null != product.getImage()) {
            bsonWriter.writeName("image");
            bsonWriter.writeString(product.getImage());
        }

        if (0 < product.getPrice()) {
            bsonWriter.writeName("price");
            bsonWriter.writeDouble(product.getPrice());
        }

        if (null != product.getDimensions() && 0 < product.getDimensions().size()) {
            bsonWriter.writeStartArray("dimensions");

            for (Document document : product.getDimensions()) {
                Codec<Document> documentCodec = codecRegistry.get(Document.class);
                encoderContext.encodeWithChildContext(documentCodec, bsonWriter, document);
            }

            bsonWriter.writeEndArray();
        }

        bsonWriter.writeEndDocument();
    }

    /**
     *
     * @return
     */
    public Class<Product> getEncoderClass() {
        return Product.class;
    }
}
