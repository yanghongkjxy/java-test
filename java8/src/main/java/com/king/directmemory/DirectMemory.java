package com.king.directmemory;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class DirectMemory {
    static final int BUFFER_SIZE = 1024;
    static final String FILE_PATH = "xxxxxx.mp4";

    @Test
    /**
     * 测试直接内存文件映射读取大文件
     */
    public void testFileMap() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);) {
            //File file=new File();
            FileChannel channel = fis.getChannel();
            MappedByteBuffer mbff = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            // ByteBuffer bbf = ByteBuffer.allocateDirect(1024000);
            //MappedByteBuffer bbf=   fis.getChannel().map(FileChannel.MapMode.READ_ONLY,0,fis.getChannel().size())
            byte[] buff = new byte[1024];
            long len = fis.getChannel().size();
            long begin = System.currentTimeMillis();

            for (long offset = 0; offset < len; offset += 1024) {

                if (len - offset > BUFFER_SIZE) {
                    mbff.get(buff);
                } else {
                    mbff.get(new byte[(int) (len - offset)]);
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("time is:" + (end - begin));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试普通文件读取
     */
    @Test
    public void testNormal() {
        //File file = new File(FILE_PATH);
        try (FileInputStream in = new FileInputStream(FILE_PATH);) {
            FileChannel channel = in.getChannel();
            ByteBuffer buff = ByteBuffer.allocate(1024);

            long begin = System.currentTimeMillis();
            while (channel.read(buff) != -1) {
                buff.flip();
                buff.clear();
            }
            long end = System.currentTimeMillis();
            System.out.println("time is:" + (end - begin));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接内存映射
     */
    @Test
    public void testDircetMap() {
        try (FileInputStream in = new FileInputStream(FILE_PATH);) {

            FileChannel channel = in.getChannel();
            ByteBuffer buff = ByteBuffer.allocateDirect((int) channel.size());

            long begin = System.currentTimeMillis();

            while (channel.read(buff) != -1) {
                buff.flip();
                buff.clear();
            }
            long end = System.currentTimeMillis();
            System.out.println("time is:" + (end - begin));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
