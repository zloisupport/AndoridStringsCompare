from encodings import utf_8
from xml.dom import minidom
from  googletrans import  Translator
from os.path import exists
from os import remove

InputFile = "items.xml"
FileWin = "StringTable.xml"
FileDroid = "strings.xml"

def translateLine():
	trans = Translator()
	xmlfile = minidom.parse(InputFile)
	items = xmlfile.getElementsByTagName('string')
	res = {item.getAttribute("name"): trans.translate(item.firstChild.data, src='zh-cn', dest='ru').text for item in items}
	writeDroidFile(res)


def translateLineValue():
	trans = Translator()
	xmlfile = minidom.parse(InputFile)
	items = xmlfile.getElementsByTagName('String')
	res = {item.getAttribute("id") :trans.translate(item.getAttribute("value"), src='zh-cn', dest='ru').text for item in items}
	writePcFile(res)


def writeDroidFile(elements):
	if(exists(FileDroid)):
		remove(FileDroid)
	f = open(FileDroid,'w',encoding='utf_8')
	for element in elements.items():
		key =element[0]
		value =element[1]
		f.writelines('<string name="{0}">{1}</string>\n'.format(key, value))
	f.close()


def writePcFile(elements):
	if(exists(FileWin)):
		remove(FileWin)
	f = open(FileWin,'w',encoding='utf_8')
	for element in elements.items():
		key =element[0]
		value =element[1]
		f.writelines('<String id="{0}" value="{1}" />\n'.format(key, value))
	f.close()


# translateLine()
translateLineValue()
