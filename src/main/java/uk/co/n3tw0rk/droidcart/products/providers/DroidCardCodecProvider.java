package uk.co.n3tw0rk.droidcart.products.providers;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import uk.co.n3tw0rk.droidcart.products.codecs.DimensionCodec;
import uk.co.n3tw0rk.droidcart.products.codecs.ProductCodec;
import uk.co.n3tw0rk.droidcart.products.domain.Dimension;
import uk.co.n3tw0rk.droidcart.products.domain.Product;

/**
 * Created by M00SEMARKTWO on 15/02/2016.
 */
public class DroidCardCodecProvider implements CodecProvider {

    /**
     *
     * @param docClass
     * @param codecRegistry
     * @param <T>
     * @return
     */
    public <T> Codec<T> get(Class<T> docClass, CodecRegistry codecRegistry) {
        if (docClass == Product.class) {
            return (Codec<T>) new ProductCodec(codecRegistry);
        } else if (docClass == Dimension.class) {
            return (Codec<T>) new DimensionCodec(codecRegistry);
        }

        return null;
    }
}
