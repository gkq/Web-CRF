# Web-CRF 
This is an application of the crf-spark package(https://github.com/Intel-bigdata/CRF-Spark/) for Chinese Pos Tagging.

## Requirements
This project is based on Django version 1.10. Other versions haven't been yet tested.

## Example

### startup django and server

  bin/runDjango.sh &

  bin/runServer.sh &
```

### Web interface

  Enter the URL(ip + port) in your browser, you can also modify the url in the bin/runDjango.sh
  Then you can test your case in the webpage
  Note that each test sequence should end with a period
### Building From Source

```scala
sbt package
```

## Contact & Feedback

 If you encounter bugs, feel free to submit an issue or pull request.
 Also you can mail to:
 * gukunqi@gmail.com
 * qian.huang@intel.com
 * hao.cheng@intel.com
