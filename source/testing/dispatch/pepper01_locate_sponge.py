import socket
import sys
import time

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = ('localhost', 12347) # CSPEM
sock.connect(server_address)
sock.settimeout(5)

message = 'publish#D7.1#[{(id 1111) (type locate) (robot pepper01) (apar sponge)}]'

sock.sendall(message.encode())
print("Sent:  "+ message)
