传统I/O基于字节流和字符流进行操作的，而NIO是基本通道和缓冲区来进行的
Buffer是一个抽象类，用来封装缓冲区的操作。其常用子类有ByteBuffer、CharBuffer、MappedByteBuffer等，缓冲区对象的内部声明了一个用于存储数据的数组
NIO中所有的I/O读写都需要用到缓冲区，以网络I/O为例，要向Socket发送数据，就需要将要发送的数据写入Buffer，再通过Channel的write方法发送出去，反过来，
要从socket读取数据，需要调用Channel的read方法，将数据从Socket流读取到Buffer后，再进行操作
   CharBuffer为例：
   1、allocate(1024): 为缓冲区分配堆内存空间
   2、position()、liMit()、capacity()
   3、flip():读取数据前执行的方法，目的是将limit置为position,position置为0，然后进行读取操作
   4、compact():整理缓冲区，便于继续写入数据，目的将未读取的数据移动到缓冲区头部,position = limit-position,limit=capacity



   ehewelo