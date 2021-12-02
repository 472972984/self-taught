package indi.repo.springboot.java.search;

import java.util.Arrays;
import java.util.List;

/**
 * @author ChenHQ
 * @date: create in 2021/12/1
 */
public class BinarySearch {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 3, 5, 7, 11);

        int i = binarySearch(list, 3);

        System.out.println("i = " + i);

    }


    public static int binarySearch(List<Integer> list, int key) {
        int left = 0;
        int right = list.size();
        int mid = right / 2;
        while (left < right) {
            int value = list.get(mid);
            if (key == value) {
                return mid;
            } else if (key < value) {
                right = mid;
                mid = (right - left) / 2;
            } else if (key > value) {
                left = mid + 1;
                mid = (left + right) / 2;
            }
        }
        return -1;
    }


}
