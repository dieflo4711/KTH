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

  int remStudents = size-1;
  int receiveData;
  int sendData;
  int responseData;
  int count = 0;
  MPI_Status status;

  int pair[2];

  if ((size-1) % 2 == 0)
    isOdd = false;

  if (rank+1 < size) {
    MPI_Send(&sendData, 1, MPI_INT, size - 1, 0, MPI_COMM_WORLD);
    MPI_Recv(&responseData, 1, MPI_INT, size - 1, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

     if(status.MPI_TAG == 0)
        printf("Student %d with %d\n", rank + 1, responseData + 1);
  } else if (rank + 1 == size) {
    while (remStudents != 0) {
      MPI_Recv(&receiveData, 1, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);

      if(remStudents == 1 && isOdd){
        pair[0] = status.MPI_SOURCE;
        MPI_Send(&pair[0], 1, MPI_INT, status.MPI_SOURCE, 0, MPI_COMM_WORLD);
      } else {
        pair[count] = status.MPI_SOURCE;
        
        if(count % 2 == 1) {
          count = 0;

          MPI_Send(&pair[1], 1, MPI_INT, pair[0], 0, MPI_COMM_WORLD);
          MPI_Send(&pair[0], 1, MPI_INT, pair[1], 0, MPI_COMM_WORLD);
        } else 
          count++;
      }
      remStudents -= 1;
    }
  }

  MPI_Finalize();

  return 0;
}