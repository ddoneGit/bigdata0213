import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/8/1-08:18
 */
public class BubbleTest {
    public static void main(String[] args) {
        int[] ints = {3, 6, 2, 1, 7, 4, 0, 5};
        bubSort(ints);
        System.out.println("最后的结果"+Arrays.toString(ints));
    }

    public static int[] bubSort(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            int count=0;
            for (int j = 0; j < arr.length-1-i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                System.out.println("第"+(++count)+"次:"+Arrays.toString(arr));

            }
            System.out.println();
            sum+=count;
        }
        System.out.println(sum);
        return arr;
    }
}