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
    private CodecRegistry codecRegistry;

    /**
     *
     * @param codecRegistry
     */
    public DimensionCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    /**
     *
     * @param reader
     * @param decoderContext
     * @return
     */
    public Dimension decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        int id = reader.readInt32("id");

        String name = reader.readString("name");

        Dimension dimension = new Dimension();

        dimension.setId(id);
        dimension.setName(name);

        return dimension;
    }

    /**
     *
     * @param writer
     * @param dimension
     * @param encoderContext
     */
    public void encode(BsonWriter writer, Dimension dimension, EncoderContext encoderContext) {

        writer.writeStartDocument();
        writer.writeName("id");
        writer.writeInt32(dimension.getId());

        writer.writeName("name");
        writer.writeString(dimension.getName());
        writer.writeEndDocument();
    }

    /**
     *
     * @return
     */
    public Class<Dimension> getEncoderClass() {
        return Dimension.class;
    }
}
