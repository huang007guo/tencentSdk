package com.wjj.application.util.weixinpaysdk.feignprocessor;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.gson.DoubleToIntMapTypeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import static feign.Util.ensureClosed;
import static feign.Util.resolveLastTypeParameter;

public class TencentDecoder implements Decoder {

    private final static Logger logger = LoggerFactory.getLogger(TencentDecoder.class);

    private final Gson gson;

    public TencentDecoder(Iterable<TypeAdapter<?>> adapters) {
        this(TencentDecoder.create(adapters));
    }

    public TencentDecoder() {
        this(Collections.<TypeAdapter<?>>emptyList());
    }

    public TencentDecoder(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        logger.info("imRest response status: {}", response.status());
        if (response.status() == 404)
            return Util.emptyValueOf(type);
        if (response.body() == null)
            return null;
        Reader reader = response.body().asReader();
        try {
            StringBuilder resText = new StringBuilder();
            int nowByte;
            while ((nowByte = reader.read()) != -1){
                resText.append((char)nowByte);
            }
            logger.debug("imRest response body: {}", resText);
            return gson.fromJson(resText.toString(), type);
        } catch (JsonIOException e) {
            if (e.getCause() != null && e.getCause() instanceof IOException) {
                throw IOException.class.cast(e.getCause());
            }
            throw e;
        } finally {
            ensureClosed(reader);
        }
    }

    /**
     * Registers type adapters by implicit type. Adds one to read numbers in a {@code Map<String,
     * Object>} as Integers.
     */
    static Gson create(Iterable<TypeAdapter<?>> adapters) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        builder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType(),
                new DoubleToIntMapTypeAdapter());
        for (TypeAdapter<?> adapter : adapters) {
            Type type = resolveLastTypeParameter(adapter.getClass(), TypeAdapter.class);
            builder.registerTypeAdapter(type, adapter);
        }
        return builder.create();
    }
}