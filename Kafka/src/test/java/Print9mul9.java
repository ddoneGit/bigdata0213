/**
 * @author ddone
 * @date 2020/8/2-16:40
 */
public class Print9mul9 {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <=i; j++) {
//                System.out.print(j+"*"+i+"="+i*j+" ");
                System.out.printf("%d * %d = %02d  ",j,i,i*j);
            }
            System.out.println();
        }
    }
}
