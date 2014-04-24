import fileinput
import sys
from subprocess import *
import os
import re

#sizes = [81, 101, 103, 105, 107, 109, 111, 113, 115, 117, 119, 121, 141, 161, 181, 201]
sizes = [3, 100]
def saveFile(size):
	lines = []
	for line in fileinput.input('src/main/resources/age.properties', inplace=1):
		line = re.sub('problem.size=[0-9]+', 'problem.size=' + str(size), line)
		lines += [line]
	f = open('src/main/resources/age.properties', 'w')
	for line in lines:
		f.write(line)
	f.close()

def execProgram(size):
	for line in os.popen('mvn -q exec:java').readlines():
		if 'Best solution ever' in line:
			offset = len('best solution ever(evaluation =   ')
			fitness = line[offset:offset+4]
			print str(size) + '\t' + fitness
			
print 'size\tfitness\tstep'
for size in sizes:
	saveFile(size)
	execProgram(size)