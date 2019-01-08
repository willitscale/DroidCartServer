package uk.co.n3tw0rk.droidcart.support.domain;

import com.google.common.base.Strings;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongo() {
        return new MongoClient(
                Strings.isNullOrEmpty(System.getenv("MONGO_HOST")) ?
                        "localhost" :
                        System.getenv("MONGO_HOST"),
                Strings.isNullOrEmpty(System.getenv("MONGO_PORT")) ?
                        27017 :
                        Integer.parseInt(System.getenv("MONGO_PORT"))
        );
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "droid_cart");
    }
}
