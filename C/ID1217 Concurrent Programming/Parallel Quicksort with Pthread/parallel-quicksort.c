#ifndef _REENTRANT 
#define _REENTRANT 
#endif 
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <sys/time.h>
#define MAXSIZE 1000000
#define MAXWORKERS 24


int numWorkers;           /* number of workers */ 
int actWorkers = 1;
pthread_mutex_t lock; 

/* timer */
double read_timer() {
    static bool initialized = false;
    static struct timeval start;
    struct timeval end;
    if( !initialized )
    {
        gettimeofday( &start, NULL );
        initialized = true;
    }
    gettimeofday( &end, NULL );
    return (end.tv_sec - start.tv_sec) + 1.0e-6 * (end.tv_usec - start.tv_usec);
}

double start_time, end_time;
int size;
int list[MAXSIZE];

struct arg_struct {
    int start;
    int end;
};

void *Quicksort(void *);

/* read command line, initialize, and create threads */

int main(int argc, char *argv[]) {
  int i;
  pthread_t worker;

  pthread_mutex_init(&lock, NULL);
  pthread_attr_t attr;

  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  size = (argc > 1)? atoi(argv[1]) : MAXSIZE;
  numWorkers = (argc > 2)? atoi(argv[2]) : MAXWORKERS;
  if (size > MAXSIZE) size = MAXSIZE;
  if (numWorkers > MAXWORKERS) numWorkers = MAXWORKERS;

    /* initialize the matrix */
  //srand(time(0));
  for (i = 0; i < size; i++)
    list[i] = rand()%99;

  /*printf("[ ");
  for (i = 0; i < size; i++) {
    printf(" %d", list[i]);
  }
  printf(" ]\n");*/

  start_time = read_timer();

  struct arg_struct argsStruc;
  argsStruc.start = 0;
  argsStruc.end = size-1;

  pthread_create(&worker, &attr, Quicksort, &argsStruc);
  pthread_join(worker, NULL);


  end_time = read_timer();

  /*printf("[ ");
  for (i = 0; i < size; i++) {
    printf(" %d", list[i]);
  }
  printf(" ]\n");*/
  printf("The execution time is %g sec\n", end_time - start_time);
}

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

void quicksort(int start, int end) {
	if(start < end)  {
	    int pidx = partit(start, end);
	    quicksort(start, pidx-1);
	    quicksort(pidx+1, end);
	}
}


void *Quicksort(void *arg) {
    struct arg_struct *argsStruc, argsStrucLeft, argsStrucRight;
    argsStruc = arg;
    int start = argsStruc->start;
    int end = argsStruc->end;
  
	if(start < end)  {
    	pthread_t worker;
    	pthread_attr_t attr;
    	pthread_attr_init(&attr);

    	int pidx = partit(start, end);

	    if(actWorkers < numWorkers) {
			argsStrucLeft.start = start;
	        argsStrucLeft.end = pidx-1;

			pthread_mutex_lock(&lock);
			actWorkers++;
			pthread_mutex_unlock(&lock);

	      	pthread_create(&worker, &attr, Quicksort, &argsStrucLeft);

			argsStrucRight.start = pidx+1;
			argsStrucRight.end = end;

			Quicksort(&argsStrucRight);

	        pthread_join(worker, NULL);
	    } else
			quicksort(start, end);
    } 
}



