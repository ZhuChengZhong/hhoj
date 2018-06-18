#include<stdio.h>
int a[10001][10001];
int main()
{
	int n;
	scanf("%d",&n);
	for(int i=0;i<10000;i++)
	{
		for(int j=0;j<10000;j++)
		{
		  a[i][j]=i*j;	
		}
	}
	printf("%d\n",n);
	return 0;
}
