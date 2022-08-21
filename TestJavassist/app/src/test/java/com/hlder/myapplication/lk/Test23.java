package com.hlder.myapplication.lk;

import org.junit.Test;

public class Test23 {
    @Test
    public void test() {
//        ListNode temp = mergeKLists(create1());
        ListNode temp = mergeKLists(create3());
        while (temp != null) {
            System.out.println("输出:"+temp.val);
            temp = temp.next;
        }
    }

    private ListNode[] create3() {
        ListNode[] arr = new ListNode[2];
        arr[0] = create(3, 2, 1);
        arr[1] = create(7, 6, 5, 4);
        return arr;
    }

    private ListNode[] create2() {
        ListNode[] arr = new ListNode[2];
        arr[0] = new ListNode();
        arr[1] = create(1);
        return arr;
    }

    private ListNode[] create1() {
        ListNode[] arr = new ListNode[3];
        arr[0] = create(5, 4, 1);
        arr[1] = create(4, 3, 1);
        arr[2] = create(6, 2);
        return arr;
    }

    private ListNode create(int... arr) {
        ListNode start = null;
        for (int item : arr) {
            ListNode newNode = new ListNode(item);
            newNode.next = start;
            start = newNode;
        }
        return start;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode start = null;
        for (int i = 0; i < lists.length; i++) {
            ListNode item = lists[i];
            if (item == null) {
                continue;
            }
            if (start == null) {
                start = lists[i];
            } else {
                start = mergeTwoNode(start, lists[i]);
            }

        }
        return start;
    }

    private ListNode mergeTwoNode(ListNode node1, ListNode node2) {
        ListNode item = node2;
        while (item != null) {
            ListNode temp = item;
            item = item.next;
            node1 = insertIntoNode(node1, temp);
        }
        return node1;
    }

    private ListNode insertIntoNode(ListNode resNode, ListNode insertNode) {
        insertNode.next = null;
        ListNode item = resNode;
        ListNode lastItem = null;
        while (item != null) {
            if (item.val >= insertNode.val) {
                insertNode.next = item;
                if (lastItem == null) {
                    resNode = insertNode;
                } else {
                    lastItem.next = insertNode;
                }
                return resNode;
            }
            lastItem = item;
            item = item.next;
        }
        if (lastItem != null) {
            lastItem.next = insertNode;
        }
        return resNode;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
