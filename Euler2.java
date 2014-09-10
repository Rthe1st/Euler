public class Euler2
{
	public static void main(String[] args)
	{
		int first = 1;
		int second = 2;
		int sum = 2;
		int limit = 4000000;
		while(second < limit){
			int third = first+second;
			if((third % 2)==0){
				sum += third;
			}
			first = second;
			second = third;
		}
		System.out.println("sum is "+sum);
	}
}