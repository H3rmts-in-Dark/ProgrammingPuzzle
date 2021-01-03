import os
import time
import re

VERBOSE = True
MINIMALISTIC = False
INDENT = True

if INDENT:addsub = "  "    # gets added bev filepath when new folder is iterated
else:addsub = ""

if VERBOSE and not MINIMALISTIC:print()    # space bevore listing all files

folders = []    # array with all folders 

def toint(text):    # sorting stuff
    try:return int(text)
    except:return text

def customsort(text):    # sorting stuff
    return [toint(c) for c in re.split(r'(\d+)', text)]

def addpaths(path,sub):    # iterates over folder and adds folders to foldersarray
    for entry in sorted(os.listdir(path),key=customsort):
        prpath = path + "/"
        if VERBOSE and not MINIMALISTIC:print(sub + prpath + entry)
        if os.path.isdir(f"{path}/{entry}"):
            folders.append(f"{path}/{entry}")
            addpaths(f"{path}/{entry}",sub + addsub)    # if folder was found find all folders in this folder

for entry in sorted(os.listdir(),key=customsort):    # sort that 2 is bevore 10
    if VERBOSE and not MINIMALISTIC:print(entry)
    if os.path.isdir(entry):    # go over every folder in home directory
        folders.append(entry)
        addpaths(entry,addsub)

print()    # empty line between listed files or nothing if not VERBOSE and listed and folders to choose

for index,entry in enumerate(folders):    # list all folders with index in front to choose
    print(f" {index} : {entry}")

print("\nindex:",end="")    # get the index from folder
inp = input(" ")

if not MINIMALISTIC:print()    # empty line bevore errors or directory check or renaming if not VERBOSE or finished if MINIMALISTIC 

try:
    dir = folders[int(inp)]    # get folder from folderlist and catch errors
except(IndexError):
    if not MINIMALISTIC:print(f"{inp} out of range 0-{len(folders)-1}")
    exit()
except(ValueError):
    if not MINIMALISTIC:print(f"{inp} was not a number, please enter the index in front of the directory")
    exit()

entries = os.listdir(dir)    # get all files in chosen folder

files = []

for entry in entries:    
    if VERBOSE and not MINIMALISTIC:print(dir + "/" + entry,end="")    # add all files not dictonarys to files
    if not os.path.isdir(dir + "/" + entry):
        if VERBOSE and not MINIMALISTIC:print("  not dir")
        files.append(entry)
    else:
        if VERBOSE and not MINIMALISTIC:print("  dir")

if len(files) == 0:
    if not MINIMALISTIC:print(f"{dir} contained no files")    # stop programm if directory contained no files   could be added at the beginning so no emty directorys get added to folders... 
    exit()

if VERBOSE and not MINIMALISTIC:print(f"\npath:{dir}\n")    # print pathstring

for index,entry in enumerate(sorted(files,key=customsort)):
    end = "." + entry.split('.')[len(entry.split('.'))-1]    # get the filetype
    if not MINIMALISTIC:print(dir + "/" + entry + " renamed to " + dir + "/" + str(index) + end)    
    os.rename(dir + "/" + entry,dir + "/" + str(index) + end)    # rename the actual file

print("\nfinished  press enter to close or r + enter to restart ",end="")
i = input()
if i == "r":
    os.system("python renamer.py")