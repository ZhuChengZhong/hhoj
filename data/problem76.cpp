#include<stdio.h>
int main()
{
  int a[10];
  int n,i,count=0;
  scanf("%d",&n);
  for(i=0;i<n;i++)
  {
     scanf("%d",&a[i]);
  }
  for(i=0;i<n;i++)
  {
     if(i==0){count++;continue;}
     if(a[i]!=a[i-1])
     {
        count++;
     }
  }
  printf("%d\n",count);
  return 0;
}

