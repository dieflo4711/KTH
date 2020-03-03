import matplotlib.pyplot as plt
import monkdata as m
import dtree as d
import drawtree_qt5 as dr
import random
import numpy as np



#print(d.entropy(m.monk1))
#print(d.entropy(m.monk2))
#print(d.entropy(m.monk3))

#for i in m.attributes:
#    print(d.averageGain(m.monk1, i))

#print("\n")

#for i in m.attributes:
#    print(d.averageGain(m.monk2, i))

#print("\n")

#for i in m.attributes:
#    print(d.averageGain(m.monk3, i))

#for i in m.attributes:
#    print(d.averageGain(m.monk1, i))

#print("\n")
#for i in m.attributes:
#    print(d.averageGain(d.select(m.monk1, m.attributes[4], 1), i))
#print("\n")
#for i in m.attributes:
#    print(d.averageGain(d.select(m.monk1, m.attributes[4], 2), i))
#print("\n")
#for i in m.attributes:
#    print(d.averageGain(d.select(m.monk1, m.attributes[4], 3), i))
#print("\n")
#for i in m.attributes:
#    print(d.averageGain(d.select(m.monk1, m.attributes[4], 4), i))
#d.select(m.monk1, 4, 2)
#d.select(m.monk1, 4, 3)
#d.select(m.monk1, 4, 4)

#t = d.buildTree(m.monk1, m.attributes, 2)
#print(d.mostCommon(d.select(d.select(m.monk1, m.attributes[4], 4), m.attributes[0], 3)))

#t1 = d.buildTree(m.monk1, m.attributes)
#print(1 - d.check(t1, m.monk1test)) #0.8287037037037037, 0.17129629629629628
#print(1 - d.check(t1, m.monk1)) #1.0, 0.0
#dr.drawTree(t)

#t2 = d.buildTree(m.monk2, m.attributes)
#print(1 - d.check(t2, m.monk2test)) #0.6921296296296297, 0.30787037037037035
#print(1 - d.check(t2, m.monk2)) #1.0, 0.0
#dr.drawTree(t)

#t3 = d.buildTree(m.monk3, m.attributes)
#print(1 - d.check(t3, m.monk3test)) #0.9444444444444444, 0.05555555555555558
#print(1 - d.check(t3, m.monk3)) #1.0, 0.0
#dr.drawTree(t)

def partition(data, fraction):
    ldata = list(data)
    random.shuffle(ldata)
    breakPoint = int(len(ldata) * fraction)
    return ldata[:breakPoint], ldata[breakPoint:]

def pruneNow(tree, data, testData):
    newVal = 1 - d.check(tree, data)

    for prunedTree in d.allPruned(tree):
        val = 1 - d.check(prunedTree, testData)
        if val < newVal:
            newVal = val
        
    return newVal

def getMean(data, testData, frac, iter):
    val = 0
    i = 0

    while i < iter:
        monktrain, monkval = partition(data, frac)
        t = d.buildTree(monktrain, m.attributes)
        val = val + pruneNow(t, monkval, testData)
        i = i+1
    return val/iter

def getMin(list):
    minIdx = 0
    idx = 0
    min = 99999999
    for ele in list:
        if ele < min:
            min = ele
            minIdx = idx
        idx = 1 + idx
    return (minIdx, min)



values = []
fractions = [0.3, 0.4, 0.5, 0.6, 0.7, 0.8]

for frac in fractions:
    values.append(getMean(m.monk1, m.monk1test, frac, 500))

idx, min = getMin(values)

print("Min: " + str(min) + " frac: " + str(fractions[idx]))

left = range(len(fractions))

plt.bar(left, values, tick_label = fractions, width = 0.7, color = ['#3498db'])

plt.xlabel('Fractions')
plt.ylabel('Means of error')
plt.title('Mean of error / Fractions for MONK-1'+'\n'+ str(np.std(values))[:5])

plt.show()
