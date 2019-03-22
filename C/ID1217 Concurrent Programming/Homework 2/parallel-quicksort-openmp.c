#include<stdio.h>
#include<stdlib.h>
#include<omp.h>

#define MAXSIZE 10000000
#define MAXWORKERS 24 
#define MAXVAL 99

int size;				
int list[MAXSIZE];	
int numWorkers;           

double start_time, end_time; 

int partit(int beg, int end) {
  int i, tmp;
  int piv = list[end];
  int pidx = beg;
  for(i = beg; i < end+1; i++) {
    if(list[i] < piv){
      tmp = list[i];
      list[i] = list[pidx];
      list[pidx] = tmp;

      pidx++;
    }
  }
  tmp = list[pidx];
  list[pidx] = list[end];
  list[end] = tmp;
  return pidx;
}

void Quicksort(int start, int end){

  if(start < end){
  	int pivot = partit(start, end);

    #pragma omp task	
	Quicksort(start, pivot - 1);

	Quicksort(pivot + 1, end);
  }
}

int main(int argc, char *argv[]){
  int j;
  size = (argc > 1)? atoi(argv[1]) : MAXSIZE; 
  numWorkers = (argc > 2)? atoi(argv[2]) : MAXWORKERS;
  if (size > MAXSIZE) size = MAXSIZE;
  if (numWorkers > MAXWORKERS) numWorkers = MAXWORKERS;

  omp_set_num_threads(numWorkers);
 
  //printf("[ ");
  for (j = 0; j < size; j++) {
     	list[j] = rand()%MAXVAL;
      //printf(" %d", list[j]);
  }
  //printf(" ]\n");

  start_time = omp_get_wtime();

  #pragma omp parallel 
  {
	#pragma omp single 
  	Quicksort(0, size-1);
  }
  end_time = omp_get_wtime();

  /*printf("[ ");
	for (j = 0; j < size; j++) {
      	  printf(" %d", list[j]);
	}
	printf(" ]\n");*/
  
  printf("The execution time is %g sec\n", end_time - start_time);


} 
