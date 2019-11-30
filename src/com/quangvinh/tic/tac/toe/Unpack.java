package com.quangvinh.tic.tac.toe;

import java.io.*;

public class Unpack {
    private static FileInputStream inp;
    private static FileOutputStream out;
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static final int[] MAGIC_NUMS = {78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
    private static int[] begin;
    private static int[] end;
    private static String[] files;

    public static void main(String[] args) {
        try {
            dis = new DataInputStream(inp = new FileInputStream("bg"));

            int n = dis.readUnsignedByte();
            begin = new int[n];
            end = new int[n];
            files = new String[n];
            int size = 0;
            for (int i = 0; i < n; i++) {
                byte[] b = new byte[dis.readByte()];
                dis.read(b);
                encrypt(b);
                files[i] = new String(b);
                begin[i] = size;
                end[i] = dis.readUnsignedShort();
                size += end[i];
            }
            byte[] data = new byte[size];
            dis.readFully(data);
            encrypt(data);
            for (int i = 0; i < n; i++) writeToFile(files[i], data, begin[i], end[i]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, byte[] data, int off, int length) throws Exception {
        fileName = "./" + fileName;
        File file = new File(fileName);
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data, off, length);
        fos.flush();
        fos.close();
    }

    public static void pack() {
    }

    public static void encrypt(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) bytes[i] ^= MAGIC_NUMS[i % MAGIC_NUMS.length];
    }
}
