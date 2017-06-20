# unicornfind
Unicornfind is a java command that reads 3d cartesian coordinates from a file and keeps track of the n closest coordinates to a specified point. The usage follows.

### Usage
```
Usage: command <input> <"point"> <quantity> <output>

  input    - Fully qualified name of .txt input data file of unicorn locations. For example "/Users/netapp/in.txt" or "" to use stdin.
             Each line consists of a point in the format (x,y,z) where x,y and z are integers in the range of -999999 to 999999
  point    - Origin point to locate closest unicorns. For example "(1,-1,1)".
  quantity - Number of unicorn locations to output as a positive integer. For example: 10
             Note that the first points encountered are kept if more equisdistant points than quantity are present.
  output   - Fully qualified name of .txt output data file. For example "/Users/netapp/out.txt" or "" to use stdout.
             Each line consists of a point in the format (x,y,z) where x,y and z are integers in the range of -999999 to 999999
             The output file will be created if it does not exist or overwritten if it does exist.
```
### Design Notes
The input file is read sequentially and each coordinate is read from the input file only once in O(n) time. The smallest values encountered are maintained in a java  PriorityQueue. As such the enque and dequeuing times are O(log(n)) where n is the quantity desired to be maintained. As such each time and element is added an enqueu and deque operation occurs.
Thus the worst case number of operations would be the one per record in the file if all of the points were stored sorted furthest away from the origin selected or the quantity if there opposite were true. 

#### Build Notes
This is a java 1.8 project built using gradle 4.0. To build:
```
./gradlew build
```
To run from build environment and output to stdout:
```
./gradlew run -PappArgs="['/var/tmp/uni.txt','(0,0,0)',2,'']"
```
To create an installation tarball to install and run from the command line use:
```
./gradlew distTar
cp ./build/Distributions/unicornFind.tar <install dir>
cd <install dir>
tar -xvzf unicornFind.tar 
./unicornFind/bin/unicornfind /infile.txt "(0,0,0)", 10, /outfile.txt 
```
