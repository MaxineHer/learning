#this is a code to do text stuff
import sys

# this function is a word counter
def wordcount(s):
    wordcount = 0
    s = " ".join(s.split())
    for i in s:
            if (i == ' '):
                  wordcount += 1
    return wordcount + 1

#this function is for list of words and their appearances
def listwords(s):
    s = s.strip()
    s = s.split(" ")
    d = dict()
    for i in s:
        if i in d:
            d[i] = d[i] + 1
        else:
            d[i] = 1
    output = ""
    for i in list(d.keys()):
       output+= str(i) +  ":" + str(d[i]) + "\n"
    return output

#this function is for number of times a word appears
def getword(s, word):
    count = 0
    words = s.split() 
    for i in words: 
        if(i==word): 
            count=count+1
    output = word + " appears " + str(count) + " times" 
    return output

#this function is to find if a word is in the file
def findword(s, word):
    found = False
    l = s.split()
    for i in range(0, len(l)):
        if (l[i] == word):
             found = True
    return found


def main():
    
    if (len(sys.argv) < 2):
         print("Invalid. Please input a filename and an action")
    elif (len(sys.argv) >= 2):
        if (sys.argv[1] == "-w"): # get wordcount
            print("In the w")
            f = open(sys.argv[2], 'r')
            print("Wordcount of", sys.argv[2], ":", wordcount(f.read()))
            f.close()
        elif (sys.argv[1] == "-f"): # get if a word is in a file
            f = open(sys.argv[3], 'r')
            print("Is", sys.argv[2] ,"present", ":", findword(f.read(), sys.argv[2]))
            f.close()
        elif (sys.argv[1] == "-l"): # get list of words and how many times it appears
            f = open(sys.argv[2], 'r')
            print(listwords(f.read()))
            f.close()
        elif (sys.argv[1] == "-o"): # get specific word and how many times it appears
            f = open(sys.argv[3], 'r')
            print(getword(f.read(), sys.argv[2]))
            f.close()

if __name__ == '__main__':
   main()