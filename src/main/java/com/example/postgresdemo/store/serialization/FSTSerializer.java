/*
 * Copyright 2015 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.postgresdemo.store.serialization;

import org.iq80.snappy.SnappyFramedInputStream;
import org.iq80.snappy.SnappyFramedOutputStream;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.postgresdemo.store.StoreCompression;

import java.io.*;

/**
 * FST: fast java serialization drop in-replacement,
 *
 * @param <T>
 */
public class FSTSerializer<T> implements Serializer<T> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Compression type. Default is {@link StoreCompression.NONE}.
     */
    private StoreCompression compressionType = StoreCompression.NONE;

    private FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public FSTSerializer() {

    }

    public FSTSerializer(final StoreCompression compressionType) {
        this.compressionType = compressionType;
    }

    @Override
    public byte[] serialize(final T data) throws SerializationException {
        try (
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192);
                final OutputStream compressionOutputStream = wrapOutputStream(outputStream);
                final FSTObjectOutput output = new FSTObjectOutput(compressionOutputStream, conf);) {
            output.writeObject(data);
            output.flush();
            compressionOutputStream.flush();
            outputStream.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Serialization error: {}", e.getMessage());
            log.trace("", e);
            throw new SerializationException(data.getClass() + " serialization problem", e);
        }

    }

    @Override
    public T deserialize(final byte[] serializedData, final Class<T> type) throws SerializationException {
        try (
                final ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedData);
                final InputStream decompressionInputStream = wrapInputStream(inputStream);
                final FSTObjectInput input = new FSTObjectInput(decompressionInputStream, conf);) {

            @SuppressWarnings("unchecked")
            final T result = (T) input.readObject();
            return result;
        } catch (Exception e) {
            log.error("Deserialization error: {}", e.getMessage());
            log.trace("", e);
            throw new SerializationException(type + " deserialization problem", e);
        }

    }

    private OutputStream wrapOutputStream(final OutputStream os) throws IOException {
        switch (compressionType) {
            case SNAPPY:
                return new SnappyFramedOutputStream(os);
            default:
                return new BufferedOutputStream(os);
        }
    }

    private InputStream wrapInputStream(final InputStream is) throws IOException {
        switch (compressionType) {
            case SNAPPY:
                return new SnappyFramedInputStream(is, false);
            default:
                return new BufferedInputStream(is);
        }
    }

}
