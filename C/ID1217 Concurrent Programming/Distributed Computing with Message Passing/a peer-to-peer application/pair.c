#include <stdlib.h>
#include <stdio.h>
#include <mpi.h>
#include <stdbool.h>


int main( argc, argv  ) 
int argc; 
char  **argv; 
{ 
  int size, rank;
  bool isOdd = true;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  int sendData, responseData;
  int randomRank, i;
  MPI_Status status;
  int list[size-1];
  int pair[2];

  time_t t;

  	if ((size-1) % 2 == 0)
    	isOdd = false;

    srand((unsigned) time(&t));
    randomRank = rand() % (size-1);

  	if (rank+1 < size) {
	    MPI_Recv(&list, size, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

		if(list[size-1] != 0) {
			list[size-1] -= 1;

			if(list[size-1] == 1 && isOdd)
		    	printf("Student %d with %d\n", rank+1, rank+1);
			else {
				list[rank] = 1;
				while(list[randomRank] == 1 && list[size-1] != 1)
					randomRank = rand() % (size-1);

				if(status.MPI_TAG == 1) {
					list[randomRank] = 1;
					printf("Student %d with %d\n", rank+1, randomRank+1);
					MPI_Send(&list, size, MPI_INT, randomRank, 0, MPI_COMM_WORLD);
				} else {
					printf("in Student %d with %d\n", rank+1, status.MPI_SOURCE+1);
					MPI_Send(&list, size, MPI_INT, randomRank, 1, MPI_COMM_WORLD);
				}
			}
		}
	} else if (rank + 1 == size) {
		randomRank = rand() % (size-1);
		list[size-1] = size;
		MPI_Send(&list, size, MPI_INT, randomRank, 1, MPI_COMM_WORLD);
    }

  MPI_Finalize();

  return 0;
}