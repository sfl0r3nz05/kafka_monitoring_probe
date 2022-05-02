import java.nio.ByteBuffer;

import java.nio.charset.StandardCharsets;

import java.util.Map;



import org.apache.kafka.common.errors.SerializationException;

import org.apache.kafka.common.serialization.Serializer;



public class MyMessageSerializer implements Serializer<MyMessageValue> {

    @Override

    public void configure(Map<String, ?> configs, boolean isKey) {

        //Nothing

    }



    @Override

    public byte[] serialize(String topic, MyMessageValue data) {

        ByteBuffer buffer = ByteBuffer.allocate(4 * 7 + data.allLength());

        buffer.putInt(data.NO.length());

        buffer.put(data.NO.getBytes(StandardCharsets.UTF_8));

        buffer.putInt(data.Tm.length());

        buffer.put(data.Tm.getBytes(StandardCharsets.UTF_8));

        buffer.putInt(data.Src.length());

        buffer.put(data.Src.getBytes(StandardCharsets.UTF_8));

        buffer.putInt(data.Dst.length());

        buffer.put(data.Dst.getBytes(StandardCharsets.UTF_8));

        buffer.putInt(data.Ptc.length());

        buffer.put(data.Ptc.getBytes(StandardCharsets.UTF_8));

        buffer.putInt(data.Len.length());

        buffer.put(data.Len.getBytes(StandardCharsets.UTF_8));

        buffer.putInt(data.Inf.length());

        buffer.put(data.Inf.getBytes(StandardCharsets.UTF_8));

        return buffer.array();

    }



    @Override

    public void close() {

        //Nothing

    }



}