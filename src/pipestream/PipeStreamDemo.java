package pipestream;

import java.io.*;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package pipestream
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-23 23:02
 **/
public class PipeStreamDemo {

    public static void writeData(PipedOutputStream pipedOutputStream) throws IOException {
        System.out.println("=====PipedOutputStream write:");
        for(int i = 0;i<300;i++){
            String outData = ""+(i+1);
            pipedOutputStream.write(outData.getBytes());
            System.out.print(outData);
        }
        System.out.println();
        pipedOutputStream.close();
    }

    public static void writeData(PipedWriter pipedWriter) throws IOException {
        System.out.println("=====PipedWriter write:");
        for(int i = 0;i<300;i++){
            String outData = ""+(i+1);
            pipedWriter.write(outData);
            System.out.print(outData);
        }
        System.out.println();
        pipedWriter.close();
    }

    public static void readData(PipedInputStream pipedInputStream) throws IOException {
        System.out.println("====PipedInputStream read:");
        byte[] buffer = new byte[20];
        int readLength = pipedInputStream.read(buffer);
        while (readLength != -1){
//            System.out.println("buffer.toString()="+buffer.toString());
            System.out.print(new String(buffer,0,readLength));
            readLength = pipedInputStream.read(buffer);
        }
        pipedInputStream.close();
    }

    public static void readData(PipedReader pipedReader) throws IOException {
        System.out.println("====PipedReader read:");
        char[] buffer = new char[20];
        int readLength = pipedReader.read(buffer);
        while (readLength != -1){
            System.out.print(new String(buffer,0,readLength));
            readLength = pipedReader.read(buffer);
        }
        pipedReader.close();
    }

    public static void testPipedStream() throws IOException {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        pipedInputStream.connect(pipedOutputStream);
        writeData(pipedOutputStream);
        System.out.println("=========================================================");
        readData(pipedInputStream);
    }

    public static void testPipedReadrAndWriter() throws IOException {
        PipedReader pipedReader = new PipedReader();
        PipedWriter pipedWriter = new PipedWriter();
        pipedReader.connect(pipedWriter);
        writeData(pipedWriter);
        System.out.println("=========================================================");
        readData(pipedReader);

    }
    public static void main(String[] args) throws IOException {
//        testPipedStream();
        testPipedReadrAndWriter();
    }
}
