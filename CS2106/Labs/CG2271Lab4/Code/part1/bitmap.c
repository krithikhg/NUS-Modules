#include <stdio.h>
#include "bitmap.h"
#include <stdbool.h>

// Utility function to print out an array of char as bits
// Print the entire bitmap. Arguments: The bitmap itself, and the length of the bitmap
// Each bit of the bitmap represents 1 byte of memory. 0 = free, 1 = allocated.
void print_map(unsigned char *map, int len) {
    int i, j;

    for(i=0; i<len; i++) {

        unsigned char mask = 0b10000000;
        for(j=0; j<8; j++) {
            if(map[i] & mask)
                printf("1");
            else
                printf("0");
            mask = mask >> 1;
        }
        printf(" ");
    }
    printf("\n");
}

// If you want, you can implement additional functions/change the function signatures; these are just suggestions.

// Search the bitmap for the required number of zeroes (representing
// free bytes of memory). Returns index of first stretch of 0s that meet the criteria.
int search_map(unsigned char *bitmap, int len, int num_zeroes) {
    //You need to use bitwise operations/masks; see print_map.
    //Remember that each bit represents 1 byte (so each char represents 8 bytes of memory) when returning index.

    int currlen = 0;

    int i, j;

    for (i=0; i<len; i++) {
        unsigned char mask = 0b10000000;
        for (j=0; j < 8; j++) {
            int curr = bitmap[i] & mask;
            if (!curr) {
                currlen ++;
            }
            else {
                currlen = 0;
            }
            if (currlen == num_zeroes) {
                break;
            }
            mask = mask >> 1;
        }
        if (currlen == num_zeroes) {
            break;
        }
    }

    if (currlen == num_zeroes) {
        return i*8 + j - num_zeroes + 1;
    }

    return -1;
}

// Set map bits to 0 or 1 depending on whether value is non-zero
void setmap(unsigned char *map, int start, int length, int value) {
    //You need to use bitwise operations/masks; see print_map.
    int i = start / 8;
    int j = start % 8;

    for (int n=0; n<length; n++){
        if (value == 1) {
            map[i] = map[i] | (1 << (8-j-1));
        }
        else {
            map[i] = map[i] & ~(1 << (8-j-1));
        }
        j++;
        if (j > 7) {
            i++;
            j = 0;
        }
    }
}

// Marks a stretch of bits as "1", representing allocated memory
void allocate_map(unsigned char *map, int start, int length) {
    setmap(map, start, length, 1);
}

// Marks a stretch of bits as "0", representing unallocated memory
void free_map(unsigned char *map, int start, int length) {
    setmap(map, start, length, 0);
}
