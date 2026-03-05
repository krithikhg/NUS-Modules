.text
	# Write your code here
	lw s1, N # load N to s1
	la s2, ARRAY # load ARRAY's address to s2
	addi s7, x0, 2 #store integer 2 in s7
	
	addi s0, x0, -1 #this will track if anything was swapped, 0 for not swapped, -1 for swapped
	
	sort:
	bgez s0, halt #if no swaps occurred in last pass, end program (array is sorted)
	addi s0, x0, 0 #set s0 to 0 at the start of every pass
	addi s3, s1, 0 #use s3 as counter, resets to N every time here
	addi s4, s2, 0 #use s4 to point to current index in array, resets to start of array every time here
	
	loop:
	lw s5, 0(s4) #loads A[i] to s5
	lw s6, 4(s4) #loads A[i+1] to s6
	
	bge s6, s5, continue
	sw s6, 0(s4) #store s6 in s5's original position if s5<s6
	sw s5, 4(s4) #swapping s5 and s6
	addi s0, x0, -1 #set s0 to -1 when swap occurs
		
	continue:
	addi s3, s3, -1 #s3 --
	addi s4, s4, 4 #increment array pointer by 4 to point to next index
	bge s3, s7, loop
	j sort
	
	# DO NOT MODIFY THE CODE BEYOND THIS POINT:
	halt:
		jal halt
.data
	# The following are two example test cases you can use to test your program.
	# Try changing the values to try different test cases. 
	#N: .word 7					# N < 100
	#ARRAY: .word 326 3878 148 16 1820 750 655
	# Expected outcome of this test case: 16 148 326 655 750 1820 3878
	
	N: .word 25
	ARRAY: .word 1234 -567 8901 -2345 6789 -9012 3456 -7890 1357 -2468 9123 -4567 8246 -1357 5791 -8642 2468 -9753 6185 -3217 7531 -4862 1975 -6398 8024
