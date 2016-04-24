#coding:utf-8
from django.http import HttpResponse
from django.shortcuts import render
import os
import telnetlib
import time
# Create your views here.
def home(request):
	os.popen('./learn/static/util1.sh')
	return render(request, 'home.html')
def process(request):
	text = request.GET['teststr']
#	file=os.popen('./learn/static/util2.sh ' + text)
#	results=file.read()
#	results = 'hello gkq'+ text
	host = "localhost"
	port = 9090
	tn = telnetlib.Telnet(host,port,120)
	tn.write((text + '\n').encode('utf-8'))
#	time.sleep(4)
	result = ""
	while result=="":
		result = tn.read_very_eager().decode('utf-8')
	tn.write("exit\n".encode('utf-8'))
	tn.close()

	return HttpResponse(result)
