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
#define MAXBEES 30

sem_t put;
sem_t take;

int honey;
int init_honey;
int numbBees;         

void *Eating(void *);
void *PutHoney(void *);


int main(int argc, char *argv[]) {
  int i;
  long l;
  pthread_attr_t attr;
  pthread_t bees[MAXBEES];
  pthread_t bear;

  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  init_honey = atoi(argv[1]);
  honey = 0;
  numbBees = atoi(argv[2]);

  sem_init(&put, 0, 1);
  sem_init(&take, 0, 0);

  for(l = 0; l < numbBees; l++)
    pthread_create(&bees[l], &attr, PutHoney, (void *) l+1);

  pthread_create(&bear, &attr, Eating, (void *) 1);

  for(l = 0; l <numbBees; l++)
  	pthread_join(bees[l], NULL);

  pthread_exit(NULL);
}

void *PutHoney(void *arg) {
	long myid = (long) arg;

	while(true) {
		srand(time(0));
		sem_wait(&put);
		if(honey < init_honey) {
			honey += 1;
			printf("Bee %d has produced honey, honey: %d\n", myid, honey);
			sem_post(&put);
			sleep((rand()%10)+1);
		} else {
			sem_post(&take);
		}
	}
}

void *Eating(void *arg) {
	long myid = (long) arg;
	while(true) {
		sem_wait(&take);
		printf("Bear is eating\n");
		honey = 0;
		sem_post(&put);
	}
}
