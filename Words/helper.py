file= open("save.txt","r")
words= file.readlines()
y = words[-1];
oi = [x[0:len(x) - 1] for x in words]
oi[-1] = y
file.close()
print(oi)