package uk.co.n3tw0rk.droidcart.products.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import uk.co.n3tw0rk.droidcart.products.domain.Dimension;

/**
 * Created by M00SEMARKTWO on 16/02/2016.
 */
public class DimensionCodec implements Codec<Dimension> {

    /** */
    private final CodecRegistry codecRegistry;

    /**
     *
     * @param codecRegistry
     */
    public DimensionCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    /**
     *
     * @param bsonReader
     * @param decoderContext
     * @return
     */
    public Dimension decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();

        int id = bsonReader.readInt32("id");

        String name = bsonReader.readString("name");

        Dimension dimension = new Dimension();

        dimension.setId(id);
        dimension.setName(name);

        return dimension;
    }

    /**
     *
     * @param bsonWriter
     * @param dimension
     * @param encoderContext
     */
    public void encode(BsonWriter bsonWriter, Dimension dimension, EncoderContext encoderContext) {

        bsonWriter.writeStartDocument();
        bsonWriter.writeName("id");
        bsonWriter.writeInt32(dimension.getId());

        bsonWriter.writeName("name");
        bsonWriter.writeString(dimension.getName());
        bsonWriter.writeEndDocument();
    }

    /**
     *
     * @return
     */
    public Class<Dimension> getEncoderClass() {
        return Dimension.class;
    }
}
