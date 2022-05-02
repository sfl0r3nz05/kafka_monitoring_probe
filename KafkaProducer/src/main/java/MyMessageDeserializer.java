import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;

import java.util.Map;



import org.apache.kafka.common.errors.SerializationException;

import org.apache.kafka.common.serialization.Deserializer;



public class MyMessageDeserializer implements Deserializer<MyMessageValue> {

    @Override

    public void configure(Map<String, ?> configs, boolean isKey) {

        //Nothing

    }



    @Override

    public MyMessageValue deserialize(String topic, byte[] data) {

        String NO = null;

        String Tm = null;

        String Src = null;

        String Dst = null;

        String Ptc = null;

        String Len = null;

        String Inf = null;

        try {

            if (data == null) {

                return null;

            }

            if (data.length < 28) {

                throw new SerializationException("Size of data received by IntegerDeserializer is shorter than expected...");

            }



            int strLen;

            byte[] tmpBytes;

            ByteBuffer buffer = ByteBuffer.wrap(data);



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            NO = new String(tmpBytes, "UTF-8");



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            Tm = new String(tmpBytes, "UTF-8");



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            Src = new String(tmpBytes, "UTF-8");



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            Dst = new String(tmpBytes, "UTF-8");



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            Ptc = new String(tmpBytes, "UTF-8");



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            Len = new String(tmpBytes, "UTF-8");



            strLen = buffer.getInt();

            tmpBytes = new byte[strLen];

            buffer.get(tmpBytes);

            Inf = new String(tmpBytes, "UTF-8");



        } catch (UnsupportedEncodingException e) {

            throw new SerializationException(e);

        }



        return new MyMessageValue(NO, Tm, Src, Dst, Ptc, Len, Inf);

    }



    @Override

    public void close() {

        //Nothing

    }



}