package com.sergeybudkov;
public class Node {

    long hash;
    int count;
    String s;
    Node left;
    Node right;

    Node() {
        left = null;
        right = null;
    }

    Node(String s, Node left, Node right) {
        this.left = left;
        this.right = right;
        this.s = s;
        count = 1;
        int leftCount, rightCount = 0;
        if (left != null) {
            leftCount = left.count;
            count += leftCount;
        }
        if (left != null) {
            rightCount = right.count;
            count += rightCount;
        }
        hash = 0;
        if (left != null) hash += left.hash;
        hash *= Helpers.hashNode.getHash(1);
        hash += s.charAt(0);
        if (right != null) {
            hash *= Helpers.hashNode.getHash(rightCount);
            hash += right.hash;
        }

    }
}
