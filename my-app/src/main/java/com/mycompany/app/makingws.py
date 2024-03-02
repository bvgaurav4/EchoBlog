#making  a code which writes ws code to print stuff
str = "Za Warudo!"
print(str)
for i in str:
    print(ascii(i))
    lol=bin(i)[2:]
    for j in lol:
        print(j)
        if(j=='1'):
            print("    ")
        else:
            print(" ")