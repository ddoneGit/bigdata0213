/**
 * @author ddone
 * @date 2020/8/2-16:32
 */
public class PrintfTest {
    public static void main(String[] args) {
        System.out.printf("Number=%5.2f\n",3.2220);
       int a = 10;
       int b = 100;
       int c =1000;
       //输出三个数对齐,右对齐
        System.out.printf("%6d\n",a);
        System.out.printf("%6d\n",b);
        System.out.printf("%6d\n",c);
        //输出三个数对齐,左对齐
        System.out.printf("%-6d\n",a);
        System.out.printf("%-6d\n",b);
        System.out.printf("%-6d\n",c);
    }
}
