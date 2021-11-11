package indi.repo.springboot.java.sort;

import java.util.LinkedList;
import java.util.List;

/**
 * 选择排序
 * @author ChenHQ
 * @date: create in 2021/11/2
 */
public class SelectSort {

    /**
     * 选择排序实现
     * @param arr
     */
    public static void selectSort(List<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            //未排序的最小值下标
            int index = i;

            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i) > arr.get(j)) {
                    index = j;
                }
            }

            //交换
            int temp = arr.get(i);
            arr.set(i, arr.get(index));
            arr.set(index, temp);
        }
    }


    public static void main(String[] args) {

        List<Integer> arr = new LinkedList<>();
        arr.add(2);
        arr.add(9);
        arr.add(5);
        arr.add(7);
        arr.add(3);

        for (Integer integer : arr) {
            System.out.println(integer);
        }

        System.out.println("-----------------");

        selectSort(arr);

        for (Integer integer : arr) {
            System.out.println(integer);
        }


    }

}
