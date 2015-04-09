package com.sergeybudkov;

public class HashNode {

    long degree[];
    int largest = 8000;
    int x2 = largest*largest;

    HashNode() {
        degree = new long[x2];
        degree[0] = 1;
        degree[1] = 31;
        for (int i = 2; i < x2; i++)
            degree[i] = degree[i - 1] * degree[1];
    }

    long getHash(int i) {
        return degree[i];
    }
}