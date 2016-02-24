package uk.co.n3tw0rk.droidcart.products.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import uk.co.n3tw0rk.droidcart.products.domain.Dimension;
import uk.co.n3tw0rk.droidcart.products.domain.Product;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by M00SEMARKTWO on 14/02/2016.
 */
public class ProductCodec implements Codec<Product> {

    /** */
    private CodecRegistry codecRegistry;

    /**
     *
     * @param codecRegistry
     */
    public ProductCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    /**
     *
     * @param reader
     * @param decoderContext
     * @return
     */
    public Product decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        Object _id = reader.readObjectId();

        int id = reader.readInt32("id");

        String name = reader.readString("name");
        String description = reader.readString("description");
        String image = reader.readString("image");

        double price = reader.readDouble("price");

        Codec<Document> historyCodec = codecRegistry.get(Document.class);
        List<Dimension> dimensions = new LinkedList<Dimension>();

        reader.readStartArray();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            dimensions.add((Dimension)historyCodec.decode(reader, decoderContext));
        }

        reader.readEndArray();
        reader.readEndDocument();

        Product product = new Product(id, name, description, image, price);

        product.setDimensions(dimensions);

        return product;
    }

    /**
     *
     * @param writer
     * @param product
     * @param encoderContext
     */
    public void encode(BsonWriter writer, Product product, EncoderContext encoderContext) {

        writer.writeStartDocument();

        writer.writeName("id");
        writer.writeInt32(product.getId());

        writer.writeName("name");
        writer.writeString(product.getName());

        writer.writeName("description");
        writer.writeString(product.getDescription());

        writer.writeName("image");
        writer.writeString(product.getImage());

        writer.writeName("price");
        writer.writeDouble(product.getPrice());

        writer.writeStartArray("dimensions");

        for (Document document : product.getDimensions()) {
            Codec<Document> documentCodec = codecRegistry.get(Document.class);
            encoderContext.encodeWithChildContext(documentCodec, writer, document);
        }

        writer.writeEndArray();
        writer.writeEndDocument();
    }

    /**
     *
     * @return
     */
    public Class<Product> getEncoderClass() {
        return Product.class;
    }
}
