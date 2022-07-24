package com.example.demo.core.configurationBeans;

import lombok.NoArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

@NoArgsConstructor
public class ByteSerializer implements RedisSerializer<byte[]>{
    @Override
    public byte[] serialize(byte[] bytes) throws SerializationException {
        return bytes;
    }
    @Override
    public byte[] deserialize(byte[] bytes) throws SerializationException {
        return bytes;
    }
}
