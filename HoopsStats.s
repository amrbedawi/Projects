.text
.align 2
.globl main

main:
#TODO: change all of the values to 100 bytes from 80 bytes
    li $v0, 4
    la $a0, name  #Prints ask for name
    syscall

    li $a0, 116   #Allocate Memory - 116 bytes, 100 string 4 jersey 4 ppg 4 year 4 next
    li $v0, 9
    syscall      #$v0 contains the address of the first byte of memory
    move $s0, $v0   #move the memory address to $s0 so $s0 points at the struct (Front of list)

    la $a0, 0($v0) #$a0 contains the address of the allocated memory
    li $v0, 8
    li $a1, 100   #Reads player name and stores it in memory where $s0 and $a0 point to name
    syscall
    move $t0, $a0   #$t0 now contains the address of the name we just got from user

    #TODO: somewhere at somepoint im gonna have to get rid of the newline character and replace it
    #with an eos character ---> '\0'

    addi $sp, $sp, -8  #create a stack so i can compare the name to Done--save caller saved registers
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    la $a0, 0($t0)  #put the address of the string we got into $a0
    la $a1, done  #put the address of done into $a1 so we can compare the two
    jal loadbytes #gets the name as $a0 and done as $a1

    lw $t1, 4($sp)
    lw $t0, 0($sp)
    addi $sp, $sp, 8

    move $s7, $s0   #create a pointer ($s7) that points to the front of the list currently
    sw $0, 112($s0) #set the next value of this struct to null so rn we have one thing in our list

    beqz $v1, nolist

    #At this Point We Have not Seen Done ask for the other stuff

    li $v0, 4
    la $a0, jersey  #prints gimme jersey
    syscall

    li $v0, 5   #reads the jersey number which is stored in $v0
    syscall
    move $t1, $v0   #this jersey number is in t1
    sw $v0, 100($s0) #places the jersey number in the struct

    li $v0, 4
    la $a0, points  #prints gimme points per game
    syscall

    li $v0, 6   #reads points per game as a float
    syscall
    mov.s $f2, $f0    #places the float into register $f2
    swc1 $f2, 104($s0)

    li $v0, 4
    la $a0, year # asks for the player's year
    syscall

    li $v0, 5   #reads the year which is then stored in $v0
    syscall
    move $t2, $v0   #now the jersey number is placed in $t2
    sw $t2, 108($s0)

  #### At this point we have a fully completed struct that isnt done and has all the info###
  ### Now we want to go and create another one and see where it fits in our list###

nextstruct:
    li $v0, 4
    la $a0, name  #Prints ask for name
    syscall

    li $a0, 116   #Allocate Memory - 78 bytes, 64 string 4 jersey 4 ppg 4 year 4 next
    li $v0, 9
    syscall      #$v0 contains the address of the first byte of memory

    move $s1, $v0 #$s1 is the pointer to the current struct we are creating

    la $a0, 0($v0) #$a0 contains the address of the allocated memory
    li $v0, 8
    li $a1, 100   #Reads player name and stores it in memory where $s1 and $a0 point to name
    syscall

    move $t0, $a0   #$t0 now contains the address of the name we just got from user

    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)

    la $a0, 0($t0)
    la $a1, done    #check if this name is done
    jal loadbytes

    lw $t2, 8($sp)
    lw $t1, 4($sp)
    lw $t0, 0($sp)
    addi $sp, $sp, 12

    beqz $v1, print # if this next name is done then just print what you have in the list

    li $v0, 4
    la $a0, jersey  #Prints ask for jersey
    syscall

    li $v0, 5   #Reads the jersey input integer
    syscall
    move $t1, $v0   #stores the jersey input in t1 and then places it in s1
    sw $t1, 100($s1)

    li $v0, 4
    la $a0, points    #this asks for points
    syscall

    li $v0, 6
    syscall     #read user input for points
    mov.s $f4, $f0    #places the float into register $f4
    swc1 $f4, 104($s1) #places the float in the apprporiate spot in the struct

    li $v0, 4
    la $a0, year    #asks for year
    syscall

    li $v0, 5
    syscall     #reads user input for year and then places in struct
    move $t2, $v0
    sw $t2, 108($s1)

    sw $0, 112($s1)   #set the next field to null
    j sort

loadbytes:
   lb $t0, 0($a0)   #load the bytes of the first string $a0 and the second $a1
   lb $t1, 0($a1)
stringcomparison:         #-1 returned if t0 goes first  0 returned if t0=t1  1 returned if t0 goes second
   bgt $t0, $t1, goesafter #in this case t1 goes before t0
   blt $t0, $t1, goesbefore #in this case t0 goes before t1

   beqz $t0, seeOther #if we have reached the end of the first string, check the other
   beqz $t1, goesafter #if we have reached the end of the second string but not the first then the first is greater
                          #at this point this byte is the exact same so go to next byte and we have not reached the end
   addi $a0, $a0, 1
   addi $a1, $a1, 1
   j loadbytes
seeOther:
   bnez $t1, goesbefore #if we have not reached the end of 2nd string then the first is smaller
   move $v1, $0
   jr $ra
goesbefore:
 	 li $v1, -1 #the string is smaller so return
 	 jr $ra
goesafter:
 	 li $v1, 1 #the string is larger so return
 	 jr $ra

sort:
    move $s7, $s0     #s7 is our pointer that we use to iterate
    lwc1 $f2, 104($s1)  #$f2 contains ppg of the new node we wanna insert
    lwc1 $f4, 104($s7)  #f4 contains ppg of the  current struct we are pointing to
    c.eq.s $f2, $f4   #check if the points are equal, if so then use name to sort
    bc1t, same #if they are the same then go check by name
    c.lt.s $f4, $f2 # if the new node has more ppg than the pointed to node then put in front
    bc1t, loadfront
other:
    lw $s3, 112($s7)    #s3 contains the next struct in the list, the one after what we are pointing to
    beqz $s3, exitloop    # if the next list item is null then put the new node at the end of the list
    lwc1 $f6, 104($s3)    # the next thing exists and f6 contains the ppg value of the next player in the list
    c.lt.s $f6, $f2   # value is one if the new node has more ppg than the next node
    bc1t, insert
    c.eq.s $f6, $f2 #if the two are equal then we have to sort by name but in the middle of the pack
    bc1t, samename
    j iterate #it does not belong anywhere, thank u next, move on
loadfront: #we are here because the new node belongs in front of what we are pointing to
    sw $s0, 112($s1)    #we make the next pointer of the new node, s1, point to s0, the first thing in our list rn
    move $s0, $s1   # we move $s1 to $s0 because we want s0 to always point to the first thing in the list
    j nextstruct
same:
    addi $sp, $sp, -16
    sw $ra, 0($sp)
    sw $t0, 4($sp)
    sw $t1, 8($sp)
    sw $t2, 12($sp)
    move $a0, $s1
    move $a1, $s7
    jal loadbytes    #if the two nodes have the same points then compare by name
    lw $t2, 12($sp)
    lw $t1, 8($sp)
    lw $t0, 4($sp)
    lw $ra, 0($sp)
    addi $sp, $sp, 16
    move $t5, $v1
    li $t4, -1
    beq $t5, $t4, loadfront #if the name of the new node goes before the pointed node then put it in front of it
    j other #otherwise see where it belongs
insert:
    lw $t5, 112($s7)    #save current.next in t5
    sw $t5, 112($s1)    #make new.next equal to what is in t5
    sw $s1, 112($s7)    #make current.next equal to the new node
    j exitloop

exitloop:
    lw $t4, 112($s1)
    beqz $t4, loadback
    j nextstruct
loadback:
    sw $s1, 112($s7)    #the next field of the player we are pointing to now points to the new node
    j nextstruct
            # this means the new node is now at the end of the list
samename:
    move $a0, $s1
    move $a1, $s3
    addi $sp, $sp, -16
    sw $ra, 0($sp)
    sw $t0, 4($sp)
    sw $t1, 8($sp)
    sw $t2, 12($sp)
    jal loadbytes    #if the two nodes in the middle have the same points then compare by name
    lw $t2, 12($sp)
    lw $t1, 8($sp)
    lw $t0, 4($sp)
    lw $ra, 0($sp)
    addi $sp, $sp, 16

    li $t6, -1
    beq $t6, $v1, insert
iterate:
    lw $s7, 112($s7) #point to the next thing in the list and keep going
    j other
print:
    beq $s0, $0, exit
    la $t9, 0($s0)
    la $a0, 0($s0)  #load name into a0

    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)

    jal trim      #trim the newline character off

    lw $t2, 8($sp)
    lw $t1, 4($sp)
    lw $t0, 0($sp)
    addi $sp, $sp, 12

    move $a0, $t9
    li $v0, 4   #print the trimmed name which resides in a0
    syscall

    li $v0, 4
    la $a0, space     #print a space
    syscall

    lw $a0, 100($s0)    #print jersey numbner
    li $v0, 1
    syscall

    li $v0, 4
    la $a0, space     #print a space
    syscall

    lw $a0, 108($s0)    #print the year
    li $v0, 1
    syscall

    li $v0, 4
    la $a0, nln   #print a new line
    syscall

    lw $s0, 112($s0)    #point to the next thing in the list to print

    j print

nolist:
   li $v0,4
   la $a0, nothing
   syscall
   j exit

trim:
  li $t0, 10
  lb $t1, 0($a0)
  beq $t1, $t0, cut

  addi $a0, $a0, 1
  j trim
cut:
  sb $0, 0($a0)
  jr $ra

exit:
      li $v0, 10    #Terminate the program
      syscall


.data
name: .asciiz "Enter Player Name: "
jersey: .asciiz "Enter Player Jersey: "
points: .asciiz "Enter Player PPG: "
year: .asciiz "Enter Player Year: "
done: .asciiz "DONE\n"
gotsame: .asciiz "Code thought that the two ppg were the same"
nothing: .asciiz " "
greater: .asciiz "Code thought the first was bigger"
space: .asciiz " "
nln: .asciiz "\n"
works: .asciiz "It WORKS!!"
