#include<stdio.h>
bool isprimy(int n)
{
	if(n==2)
	{
		return true;
	}
	for(int i=2;i<n;i++)
	{
		if(n%i==0)
		{
			return false;
		}
	}
	return true;
}
int main()
{
	int n;
	int a;
	scanf("%d",&n);
	for(int i=0;i<n;i++)
	{
		scanf("%d",&a);
		if(isprimy(a))
		{
			printf("YES\n");
		}
		else
		{
			printf("NO\n");
		}
		
	}
	return 0;
}
