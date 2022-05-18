import socket
import sys
import time

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = ('localhost', 12347) # CSPEM
sock.connect(server_address)
sock.settimeout(5)

message = 'publish#D7.1#[{(id 2222) (type give) (robot pepper01) (apar sponge 1.0 100 1.1)}]'

sock.sendall(message.encode())
print("Sent:  "+ message)
