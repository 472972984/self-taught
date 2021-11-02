package indi.repo.springboot.java.sort;

import java.util.LinkedList;
import java.util.List;

/**
 * 冒泡排序
 * @author ChenHQ
 * @date: create in 2021/11/2
 */
public class MaoPaoSort {

    /**
     * 冒泡排序实现
     * @param arr
     */
    public static void maoPaoSort(List<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                //交换
                if (arr.get(i) > arr.get(j)) {
                    int temp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, temp);
                }
            }
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

        maoPaoSort(arr);
        for (Integer integer : arr) {
            System.out.println(integer);
        }



    }


}
