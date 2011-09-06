(WAIT)
@24576
D=M
@FILL
D,JNE
@WAIT
0,JMP

(WAIT_CLEAR)
@24576
D=M
@CLEAR
D,JEQ
@WAIT_CLEAR
0,JMP

(CLEAR)
	@8192
	D=A
	@1
	M=D
	@SCREEN
	D=A
	@2
	M=D
	(LOOP1)
		@2
		A=M
		M=0
		@2
		M=M+1
		@1
		M=M-1
		D=M
		@WAIT
		D;JEQ
		
		@24576
		D=M
		@FILL
		D,JNE
		
		@LOOP1
		0;JMP

(FILL)
	@8192
	D=A
	@1
	M=D
	@SCREEN
	D=A
	@2
	M=D
	(LOOP2)
		@2
		A=M
		M=-1
		@2
		M=M+1
		@1
		M=M-1
		D=M
		@WAIT_CLEAR
		D;JEQ
		
		@24576
		D=M
		@CLEAR
		D,JEQ
		
		@LOOP2
		0;JMP
(END)

