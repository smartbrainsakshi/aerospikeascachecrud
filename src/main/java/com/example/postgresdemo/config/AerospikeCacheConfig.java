package com.example.postgresdemo.config;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.async.AsyncClient;
import com.example.postgresdemo.config.annotation.EnableAerospikeCacheManager;

import com.example.postgresdemo.store.StoreCompression;
import com.example.postgresdemo.store.serialization.FSTSerializer;
import com.example.postgresdemo.store.serialization.KryoSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAerospikeCacheManager(
              serializerClass = KryoSerializer.class,
                compression = StoreCompression.SNAPPY,
                defaultNamespace = "test",
                  defaultCacheName = "ITDEFAULT",
                defaultTimeToLiveInSeconds = 300

)
@EnableCaching
public class AerospikeCacheConfig {

    @Bean(destroyMethod = "close")
    public AerospikeClient aerospikeClient() throws Exception {
        return new AerospikeClient("172.28.128.4", 3000);
    }

    @Bean(destroyMethod = "close")
    public AsyncClient aerospikeAsyncClient() throws Exception {
        return new AsyncClient("172.28.128.4", 3000);
    }

}
