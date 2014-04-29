import fileinput
import sys
from subprocess import *
import os
import re
import random
from time import gmtime, strftime

'''========================================================
=					  CONFIGURATION					      =
========================================================'''
#tu jest cala dokumentacja
STEPS = 1000
PROBLEM_SIZES = [81, 101, 103, 105, 107, 109, 111, 113, 115, 117, 119, 121, 141, 161, 181, 201]
ISLANDS_NUMBERS = [5]
ISLANDS_SIZES = [40]
INDIVIDUAL_CHANCE_TO_MIGRATE  = 0.001
FEATURE_CHANCE_TO_MUTATE = 0.5
FEATURE_MUTATION_RANGE = 0.025

# always print problem size and fitness
PRINT_ONLY_BEST_ISLAND = True
PRINT_ALL_ISLANDS_IN_FILE = True
PRINT_ISLAND_NUMBERS = True
PRINT_ISLANDS_SIZES = True
PRINT_SOLUTION = True
PRINT_SOLUTION_IN_NEW_LINE = True

PRINT_TO_CONSOLE = True
PRINT_TO_FILE = True
FILE_DIRECTORY = 'outputs' #default: 	'.'
FILENAME_APPEND_DATE = True

RANDOMIZE_SEED = True

'''========================================================
=						SCRIPT							  =
========================================================'''
def createAgeProperties(problemSize, islandsNumber, islandsSize):
	lines = [
		"steps=" + str(STEPS),
		"problem.size=" + str(problemSize),
		"islands.number=" + str(islandsNumber),
		"islands.size=" + str(islandsSize),
		"individual.chanceToMigrate=" + str(INDIVIDUAL_CHANCE_TO_MIGRATE),
		"feature.chanceToMutate=" + str(FEATURE_CHANCE_TO_MUTATE),
		"feature.mutationRange=" + str(FEATURE_MUTATION_RANGE)
	]
	f = open('src/main/resources/age.properties', 'w')
	for line in lines:
		f.write(line + '\n')
	f.close()

def getSeed():
	random.seed()
	return random.randint(0, 999999999)
	
def createAgeXml():
	lines = []
	seed = getSeed()
	newLine = '<constructor-arg type="Long" value="' + str(seed) + '"/>'
	for line in fileinput.input('src/main/resources/age.xml', inplace=1):
		if RANDOMIZE_SEED:
			line = re.sub('<constructor-arg type="Long" value="[0-9]+"\/>', newLine, line)
		lines += [line]
	f = open('src/main/resources/age.xml', 'w')
	for line in lines:
		f.write(line)
	f.close()

def getOutputs(problemSize, islandsNumber, islandsSize):
	outputs = []
	os.popen('mvn compile').readlines()
	for line in os.popen('mvn -q exec:java').readlines():
		if 'Best solution ever' in line:
			fitness = re.search('evaluation = ([0-9]+,[0-9]+)', line).group(1)
			fitness = float(re.sub(',', '.', fitness))
			solution = re.search('representation=\[(.*?)\]', line).group(1)
			output = {}
			output['fitness'] = fitness
			output['solution'] = solution
			output['size'] = problemSize
			output['islandsNumber'] = islandsNumber
			output['islandsSize'] = islandsSize
			outputs += [output]
	return outputs

if not os.path.exists(FILE_DIRECTORY):
	os.makedirs(FILE_DIRECTORY)
filename = FILE_DIRECTORY + '/simulation'
if FILENAME_APPEND_DATE: filename += strftime("%Y%m%d-%H-%M-%S", gmtime())
filename += '.txt'
fileFirstSave = True
def printToFile(lines):
	global fileFirstSave
	if PRINT_TO_FILE: 
		if fileFirstSave:
			f = open(filename, 'w')
			f.write('')
			f.close()
			fileFirstSave = False
		f = open(filename, 'a')
		for line in lines:
			f.write(line + '\n')
		f.close()
		
def printToConsole(lines):
	if PRINT_TO_CONSOLE: 
		for line in lines:
			print line

def printAll(lines):
	printToFile(lines)
	printToConsole(lines)
	
def parseOutputs(outputs):
	lines = []
	for output in outputs:
		line = str(output['size'])
		if PRINT_ISLAND_NUMBERS: line += '\t' + str(output['islandsNumber'])
		if PRINT_ISLANDS_SIZES: line += '\t' + str(output['islandsSize'])
		line += '\t' + re.sub('\.', ',', str(output['fitness']))
		if PRINT_SOLUTION: 
			if PRINT_SOLUTION_IN_NEW_LINE: line += '\n'
			line += '\t' + output['solution']
		lines += [line]
	return lines
	
def handleOutputs(outputs):
	if PRINT_ONLY_BEST_ISLAND:
		if PRINT_ALL_ISLANDS_IN_FILE:
			lines = parseOutputs(outputs)
			printToFile(lines)
		outputs = [sorted(outputs, key=lambda k: k['fitness'])[-1]]
			
	lines = parseOutputs(outputs)
	printToConsole(lines)
	if PRINT_ONLY_BEST_ISLAND and not PRINT_ALL_ISLANDS_IN_FILE: printToFile(lines)
	
def main():
	header = 'size'
	if PRINT_ISLAND_NUMBERS: header += '\t' + 'islNum'
	if PRINT_ISLANDS_SIZES: header += '\t' + 'islSize'
	header += '\tfitness'
	printAll([header])
	fileFirstSave = True
	for problemSize in PROBLEM_SIZES:
		for islandsNumber in ISLANDS_NUMBERS:
			for islandsSize in ISLANDS_SIZES:
				createAgeProperties(problemSize, islandsNumber, islandsSize)
				createAgeXml()
				outputs = getOutputs(problemSize, islandsNumber, islandsSize)
				handleOutputs(outputs)

main()
