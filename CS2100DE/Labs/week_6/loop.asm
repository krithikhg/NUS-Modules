.text
	lw s0, N
	la s1, A
	addi t0, x0, 2
	addi t2, x0, 1
	
	loop:
	addi s0, s0, -1
	lw t1, 0(s1)
	add t1, t1, t0
	sw t1, 0(s1)
	addi t0, t0, 2
	addi s1, s1, 4
	bge s0, t2, loop
	
.data
	N: .word 10
	A: .word 10 20 30 40 50 60 70 80 90 100
