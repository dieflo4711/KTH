#ifndef _REENTRANT 
#define _REENTRANT 
#endif 
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <semaphore.h>
#include <time.h>
#include <sys/time.h>
#define MAXBIRDS 30

sem_t full;
sem_t take;

int worms;
int init_worms;
int numbBirds; 

void *Eating(void *);
void *PutWorms(void *);

int main(int argc, char *argv[]) {
  int i, j;
  long l;
  pthread_attr_t attr;
  pthread_t babyBird[MAXBIRDS];
  pthread_t parentBird;

  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  worms = atoi(argv[1]);
  init_worms = worms;
  numbBirds = atoi(argv[2]);

  sem_init(&full, 0, 0);
  sem_init(&take, 0, 1);

  pthread_create(&parentBird, &attr, PutWorms, (void *) 1);

  for (l = 0; l < numbBirds; l++)
    pthread_create(&babyBird[l], &attr, Eating, (void *) l+1);


  pthread_exit(NULL);
}

void *PutWorms(void *arg) {
	while(true) {
		sem_wait(&full);
		worms = init_worms;
		sem_post(&take);
		printf("Mom got more food\n");
	}
}

void *Eating(void *arg) {
	long myid = (long) arg;

	while(true) {
		srand(time(0));

		sem_wait(&take);
		if(worms > 0) {
			worms -= 1;
			printf("Bird %d is eating, %d worms left\n", myid, worms);
			sem_post(&take);
			sleep((rand()%10)+1);
		} else {
			printf("No food for bird %d\n", myid);
			sem_post(&full);
		}
	}

}
