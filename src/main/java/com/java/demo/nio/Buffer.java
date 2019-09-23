package com.java.demo.nio;

import java.nio.CharBuffer;

public class Buffer {
    public static void main(String[] args) {
//        ByteBuffer allocate = ByteBuffer.allocate(64); // 堆内存
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(64); // 直接内存
        CharBuffer charBuffer = CharBuffer.allocate(64);
        System.out.println("==================== put before ======================= ");
        System.out.println("position: " + charBuffer.position());
        System.out.println("limit: " + charBuffer.limit());
        System.out.println("capacity: " + charBuffer.capacity());
        charBuffer.put("a");
        charBuffer.put("b");
        charBuffer.put("c");
        charBuffer.put("d");
        System.out.println("===================== put after ======================= ");
        System.out.println("position: " + charBuffer.position());
        System.out.println("limit: " + charBuffer.limit());
        System.out.println("capacity: " + charBuffer.capacity());
        charBuffer.flip();// limit = position,  position = 0  数据从position开始读取
        System.out.println("====================== flip() after ======================== ");
        System.out.println("get: " + charBuffer.get());
        System.out.println("position: " + charBuffer.position());
        System.out.println("limit: " + charBuffer.limit());
        System.out.println("capacity: " + charBuffer.capacity());
        charBuffer.compact(); // limit = capacity, position = limit - position，将未被读取的数据移动到缓存区头部，便于继续写入数据
        System.out.println("====================== compact() after ====================== ");
        System.out.println("position: " + charBuffer.position());
        System.out.println("limit: " + charBuffer.limit());
        System.out.println("capacity: " + charBuffer.capacity());

    }
}
